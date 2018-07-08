package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.domain.model.LastSearch;
import com.cloudvision.utp.quieroentradas.data.model.UserSearch;
import com.cloudvision.utp.quieroentradas.presentation.adapter.LastSearchAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Walberth Gutierrez Telles on 05,June,2018
 */
public class LastSearchFragment extends Fragment {
    private static final int CAMERA_REQUEST_CODE = 102;
    private static final int IMAGE_QUALITY = 100;
    private static final String INFORMATION_TO_GET = "data";
    private Bitmap bitmap;
    private File sdCardDirectory;
    private File image;
    private StorageReference storageReference;
    private String imagePath;
    private String directoryPath;
    private File newFile;
    private SimpleDateFormat dateFormat;
    private String currentTimeStamp;
    private String imageName;
    private FirebaseUser user;
    private Uri downloadUrl;
    private Bitmap bitmapPicture;
    private ProgressBar progressBarLastSearch;
    private String keyUserImageSearch;
    private List<LastSearch> lastSearchList;
    private RecyclerView recyclerLastSearch;
    private LastSearchAdapter lastSearchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.nav_item_mysearching));
        FloatingActionButton fabCamera = view.findViewById(R.id.fabCamera);
        storageReference = FirebaseStorage.getInstance().getReference();
        progressBarLastSearch =  view.findViewById(R.id.progressBarLastSearch);
        fabCamera.setOnClickListener(new btnTakePhotoClicker());
        user = FirebaseAuth.getInstance().getCurrentUser();

        lastSearchList = new ArrayList<>();
        recyclerLastSearch = view.findViewById(R.id.recyclerLasSearch);
        recyclerLastSearch.setLayoutManager(new LinearLayoutManager(getContext()));

        setRecyclerView();

        getLastSearchItems();
    }

    class btnTakePhotoClicker implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            takePictureFromCamera();
        }
    }

    public void takePictureFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressBarLastSearch.setVisibility(View.VISIBLE);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            boolean result = savePicture(data);
            bitmapPicture = (Bitmap) Objects.requireNonNull(data.getExtras()).get(INFORMATION_TO_GET);

            if(result){
                boolean uploadFileResult = uploadFireabsePicture();

                if(uploadFileResult) {
                    Toast.makeText(getActivity(), "Image saved in storage with success", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "Error during image saving", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public boolean savePicture (Intent data){
        bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
        sdCardDirectory = Environment.getExternalStorageDirectory();
        directoryPath = sdCardDirectory.getPath() + "/DCIM/Camera";
        newFile = new File(directoryPath);
        dateFormat = new SimpleDateFormat("HH-mm-ss");
        currentTimeStamp = dateFormat.format(new Date());
        imageName = "IMG" + "-" + currentTimeStamp + ".png";
        imagePath = directoryPath + "/" + imageName;
        image = new File(newFile, imageName);

        boolean success = false;
        FileOutputStream outStream;

        try {
            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean uploadFireabsePicture () {
        boolean success = false;

        try {
            Uri file = Uri.fromFile(new File(imagePath));
            final StorageReference riversRef = storageReference.child("images/"+file.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("filePath", "Upload Failed -> " + exception);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri;

                            boolean saveToFirebaseDatabase = saveRegisterToFirebase();

                            if(saveToFirebaseDatabase) {
                                Toast.makeText(getActivity(), "Image saved in database with success", Toast.LENGTH_LONG).show();

                                sendBundleToFragment();
                            }
                        }
                    });
                }
            });

            success = true;
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public boolean saveRegisterToFirebase() {
        boolean success = false;

        try {
            String imageNameFirebase = imageName.replace(".png", "");

            keyUserImageSearch = FirebaseDatabase
                                        .getInstance()
                                        .getReference()
                                        .child("userSearch")
                                        .push()
                                        .getKey();

            UserSearch firebaseUserSearch = new UserSearch(
                    keyUserImageSearch,
                    imageNameFirebase,
                    user.getUid(),
                    downloadUrl.toString(),
                    new Date().getTime(),
                    ""
            );

            FirebaseDatabase.getInstance()
                    .getReference()
                    .child("userSearch")
                    .child(Objects.requireNonNull(keyUserImageSearch))
                    .setValue(firebaseUserSearch);

            success = true;
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public void sendBundleToFragment(){
        try {
            Bundle args  = new Bundle();
            args.putParcelable("picture", bitmapPicture);
            args.putString("keyUserImageSearch", keyUserImageSearch);

            android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ImageToSendFragment imageToSendFragment = new ImageToSendFragment();
            imageToSendFragment.setArguments(args);

            progressBarLastSearch.setVisibility(View.INVISIBLE);
            fragmentTransaction.replace(R.id.content, imageToSendFragment).commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setRecyclerView(){
        lastSearchAdapter = new LastSearchAdapter(recyclerLastSearch, lastSearchList, getContext());
        recyclerLastSearch.setHasFixedSize(true);
        recyclerLastSearch.setAdapter(lastSearchAdapter);
    }

    public void getLastSearchItems() {
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();

        try {
            mFirebaseDatabase.child("userSearch").orderByChild("idUser").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    UserSearch lastSearch = dataSnapshot.getValue(UserSearch.class);

                    if (lastSearch != null
                            && lastSearch.getGroupName() != null
                            && !lastSearch.getGroupName().equals("")
                            && lastSearch.getIdUser().equals(user.getUid())) {
                        final LastSearch search = new LastSearch();

                        search.setGroupName(Objects.requireNonNull(lastSearch).getGroupName());
                        search.setDateTimeSearched(Long.toString(lastSearch.getDateTimeSearch()));
                        search.setPictureSearched(lastSearch.getPicture());
                        search.setImageStorageName(lastSearch.getImageStorageName());
                        search.setKeyUserImageSearch(lastSearch.getUid());

                        lastSearchList.add(search);
                        lastSearchAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
