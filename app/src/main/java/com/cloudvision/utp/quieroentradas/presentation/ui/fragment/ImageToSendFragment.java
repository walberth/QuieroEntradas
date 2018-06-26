package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.datasource.rest.VolleyController;
import com.cloudvision.utp.quieroentradas.data.model.EventSearch;
import com.cloudvision.utp.quieroentradas.data.model.EventsFound;
import com.cloudvision.utp.quieroentradas.data.model.Place;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Walberth Gutierrez Telles on 20,June,2018
 */
public class ImageToSendFragment extends Fragment {
    private final static String TAG = "ImageToSendFragment";
    protected String DATA_RECEIVE = "picture";
    private final String CLOUD_VISION_API_KEY = "AIzaSyDv0nRvPmzDpTZayTKW9jLiQvaWl6AvKOc";
    private ProgressBar imageToSendProgressBar;
    private static String[] visionAPI = new String[]{ "LOGO_DETECTION", "WEB_DETECTION"};
    private static String API_TYPE = visionAPI[1];
    protected TextView visionInformation;
    private Feature feature;
    private Bitmap bitmapImage;

    public ImageToSendFragment() {
    }

    public interface VolleyCallback{
        void onSuccessResponse(String result);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null) {
            Bundle mBundle = getArguments();
            bitmapImage = mBundle.getParcelable(DATA_RECEIVE);
        }

        return inflater.inflate(R.layout.fragment_image_to_send, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAccept = view.findViewById(R.id.btnAccept);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        ImageView imgPicture = view.findViewById(R.id.imgPicture);

        imageToSendProgressBar =  view.findViewById(R.id.imageToSendProgressBar);
        visionInformation = view.findViewById(R.id.visionAPIData);
        feature = new Feature();
        feature.setType(API_TYPE);
        feature.setMaxResults(10);

        imgPicture.setImageBitmap(bitmapImage);

        btnAccept.setOnClickListener(new btnSendPictureToFirebase());
        btnCancel.setOnClickListener(new btnCancelPictureSending());
    }

    class btnSendPictureToFirebase implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            passImageToCloudVision(feature);
        }
    }

    class btnCancelPictureSending implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LastSearchFragment lastSearchFragment = new LastSearchFragment();
            fragmentTransaction.replace(R.id.content, lastSearchFragment).commit();
        }
    }

    public void passImageToCloudVision(Feature feature) {
        try {
            callCloudVision(bitmapImage, feature);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void callCloudVision(final Bitmap bitmap, final Feature feature) {
        imageToSendProgressBar.setVisibility(View.VISIBLE);

        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);

        //TODO: VISION API CALL WITH VOLLEY
        //wsVisionCallback(annotateImageRequests);

        new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                try {

                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                    Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                    annotateRequest.setDisableGZipContent(true);
                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response);
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " + e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                Log.d(TAG, "onPostExecute: " + result);


                Bundle data = new Bundle();
                data.putString("groupName", result);
                android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EventsFoundFragment eventsFoundFragment = new EventsFoundFragment();
                eventsFoundFragment.setArguments(data);
                fragmentTransaction.replace(R.id.content, eventsFoundFragment).commit();

                imageToSendProgressBar.setVisibility(View.INVISIBLE);
            }
        }.execute();
    }

    @NonNull
    private static Image getImageEncodeImage(Bitmap bitmap) {
        Image base64EncodedImage = new Image();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
    }

    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
        AnnotateImageResponse imageResponses = response.getResponses().get(0);
        List<EntityAnnotation> entityAnnotations;
        String message = "";

        switch (API_TYPE) {
            case "LOGO_DETECTION":
                entityAnnotations = imageResponses.getLogoAnnotations();
                message = formatAnnotation(entityAnnotations);
                break;

            case "WEB_DETECTION":
                WebDetection webDetection = imageResponses.getWebDetection();
                message = getWebDetection(webDetection);
                break;
        }

        return message;
    }

    private static String getWebDetection(WebDetection webDetection) {
        String message = "";
        List<WebEntity> webEntitys = webDetection.getWebEntities();

        if (webEntitys !=null) {
            for (WebEntity entity : webEntitys) {
                message= message +""+ entity.getDescription() + " : " + entity.getEntityId() + " : "+ entity.getScore();

                message = message + "\n";
            }
        } else {
            message = "Nothing Found";
        }

        return message;
    }

    private static String formatAnnotation(List<EntityAnnotation> entityAnnotation) {
        String message = "";

        if (entityAnnotation != null) {
            for (EntityAnnotation entity : entityAnnotation) {
                message = message + "    " + entity.getDescription() + " " + entity.getScore();
                message += "\n";
            }
        } else {
            message = "Nothing Found";
        }

        return message;
    }

    //TODO: CALLING VISION API WITH VOLLEY
    public void wsVisionCallback(final List<AnnotateImageRequest> annotateRequest) {
        getVisionInformation(annotateRequest, null, new ImageToSendFragment.VolleyCallback() {

            @Override
            public void onSuccessResponse(String result) {

            }
        });
    }

    //TODO: CALLING VISION API WITH VOLLEY
    private void getVisionInformation(final List<AnnotateImageRequest> annotateRequest, String url, final VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                        VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                        builder.setVisionRequestInitializer(requestInitializer);

                        Vision vision = builder.build();



                        callback.onSuccessResponse(response);
                    } catch (Exception ex) {
                        Log.e(TAG, "onResponse: error throw " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: error throw " + error.getMessage());
                    //dialog.hide();
                }
            });
        VolleyController.getInstance(getActivity()).addToRequestQueue(request);
    }
}
