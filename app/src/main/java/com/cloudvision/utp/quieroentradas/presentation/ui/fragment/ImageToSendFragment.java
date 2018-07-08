package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.cloudvision.utp.quieroentradas.R;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Walberth Gutierrez Telles on 20,June,2018
 */
public class ImageToSendFragment extends Fragment {
    private final static String TAG = "ImageToSendFragment";
    private final static String WEB_API_TYPE = "WEB_DETECTION";
    private final static String LOGO_API_TYPE = "LOGO_DETECTION";
    protected String DATA_RECEIVE = "picture";
    private final String CLOUD_VISION_API_KEY = "AIzaSyDv0nRvPmzDpTZayTKW9jLiQvaWl6AvKOc";
    private ProgressBar imageToSendProgressBar;
    protected TextView visionInformation;
    private Bitmap bitmapImage;
    private String groupName;
    private String keyUserImageSearch;

    public ImageToSendFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null) {
            Bundle mBundle = getArguments();
            bitmapImage = mBundle.getParcelable(DATA_RECEIVE);
            keyUserImageSearch = mBundle.getString("keyUserImageSearch");
        }

        return inflater.inflate(R.layout.fragment_image_to_send, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAccept = view.findViewById(R.id.btnAccept);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        ImageView imgPicture = view.findViewById(R.id.imgPicture);
        visionInformation = view.findViewById(R.id.visionInformation);
        imageToSendProgressBar =  view.findViewById(R.id.imageToSendProgressBar);

        Feature feature = new Feature();
        feature.setType(LOGO_API_TYPE);
        feature.setMaxResults(1);

        imgPicture.setImageBitmap(bitmapImage);
        passImageToCloudVision(feature);

        btnAccept.setOnClickListener(new btnSendBundleToFragment());
        btnCancel.setOnClickListener(new btnCancelPictureSending());
    }

    class btnSendBundleToFragment implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                Bundle data = new Bundle();
                data.putString("groupName", groupName);
                data.putString("keyUserImageSearch", keyUserImageSearch);
                android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EventsFoundFragment eventsFoundFragment = new EventsFoundFragment();
                eventsFoundFragment.setArguments(data);
                fragmentTransaction.replace(R.id.content, eventsFoundFragment).commit();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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

    @SuppressLint("StaticFieldLeak")
    private void callCloudVision(final Bitmap bitmap, final Feature feature) {
        imageToSendProgressBar.setVisibility(View.VISIBLE);

        final List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        final List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        annotateImageReq.setImage(getImageEncodeImage(bitmap));
        annotateImageRequests.add(annotateImageReq);

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
                    return convertResponseToString(response, LOGO_API_TYPE);
                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " + e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String logoResult) {
                Log.d(TAG, "onPostExecute: RESULT OF LOGO VISION API - " + logoResult);

                final List<Feature> featureListWeb = new ArrayList<>();
                final List<AnnotateImageRequest> annotateImageRequestsWeb = new ArrayList<>();
                final Feature featureWeb = new Feature();
                final AnnotateImageRequest annotateImageReqWeb = new AnnotateImageRequest();


                if (logoResult.equals("Nothing Found")) {
                    new AsyncTask<Object, Void, String>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                            featureWeb.setType(WEB_API_TYPE);
                            featureWeb.setMaxResults(3);
                            featureListWeb.add(featureWeb);
                            annotateImageReqWeb.setFeatures(featureListWeb);
                            annotateImageReqWeb.setImage(getImageEncodeImage(bitmap));
                            annotateImageRequestsWeb.add(annotateImageReqWeb);
                        }

                        @Override
                        protected String doInBackground(Object... objects) {
                            try {
                                HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                                JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                                VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                                Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                                builder.setVisionRequestInitializer(requestInitializer);

                                Vision vision = builder.build();

                                BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                                batchAnnotateImagesRequest.setRequests(annotateImageRequestsWeb);

                                Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                                annotateRequest.setDisableGZipContent(true);
                                BatchAnnotateImagesResponse response = annotateRequest.execute();
                                return convertResponseToString(response, WEB_API_TYPE);
                            } catch (GoogleJsonResponseException e) {
                                Log.d(TAG, "failed to make API request because " + e.getContent());
                            } catch (IOException e) {
                                Log.d(TAG, "failed to make API request because of other IOException " + e.getMessage());
                            }
                            return "Cloud Vision API request failed. Check logs for details.";
                        }

                        @Override
                        protected void onPostExecute(String webResult) {
                            Log.d(TAG, "onPostExecute: RESULT OF WEB VISION API - " + webResult);

                            groupName = webResult;
                            visionInformation.setText(webResult);
                            imageToSendProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }.execute();
                } else {
                    groupName = logoResult;
                    visionInformation.setText(logoResult);
                    imageToSendProgressBar.setVisibility(View.INVISIBLE);
                }
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

    private static String convertResponseToString(BatchAnnotateImagesResponse response, String API_TYPE) {
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
        StringBuilder message = new StringBuilder();
        List<WebEntity> webEntities = webDetection.getWebEntities();

        if (webEntities !=null) {
            for (WebEntity entity : webEntities) {
                message.append(entity.getDescription()).append(" : ").append(entity.getScore()).append("\n");
            }
        } else {
            message = new StringBuilder("Nothing Found");
        }

        return message.toString();
    }

    private static String formatAnnotation(List<EntityAnnotation> entityAnnotation) {
        StringBuilder message = new StringBuilder();

        if (entityAnnotation != null) {
            for (EntityAnnotation entity : entityAnnotation) {
                message.append(entity.getDescription()).append(" : ").append(entity.getScore()).append("\n");
            }
        } else {
            message = new StringBuilder("Nothing Found");
        }

        return message.toString();
    }
}
