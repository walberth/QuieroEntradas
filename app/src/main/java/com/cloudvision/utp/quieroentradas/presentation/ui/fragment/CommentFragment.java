package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.CommentPlace;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Walberth Gutierrez Telles on 05,June,2018
 */
public class CommentFragment extends Fragment {

    private static final String TAG = "CommentsActivity";

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<CommentPlace> adapter;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(getContext(),
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            Log.d(TAG, "onCreate: getDisplayName " + user.getDisplayName());
            Log.d(TAG, "onCreate: getUid " + user.getUid());
            Log.d(TAG, "onCreate: getEmail " + user.getEmail());
            Log.d(TAG, "onCreate: zzn " + user.zzn());
            Log.d(TAG, "onCreate: " + user);

            ListView listOfMessages = view.findViewById(R.id.list_of_messages);

            Query query = FirebaseDatabase.getInstance().getReference().child("chats");

            FirebaseListOptions<CommentPlace> options = new FirebaseListOptions.Builder<CommentPlace>()
                    .setQuery(query, CommentPlace.class)
                    .setLayout(R.layout.message)
                    .setLifecycleOwner(this)
                    .build();

            adapter = new FirebaseListAdapter<CommentPlace>(options) {
                @Override
                protected void populateView(View v, CommentPlace model, int position) {
                    // Get references to the views of message.xml
                    TextView messageText = (TextView)v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getIdUser());
                    /*messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getDateTimeComment()));*/
                }
            };

            listOfMessages.setAdapter(adapter);
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = view.findViewById(R.id.input);

                // Read the input field and push a new instance
                // of CommentPlace to the Firebase database
                /*FirebaseDatabase.getInstance()
                        .getReference()
                        .child("chats")
                        .push()
                        *//*.setValue(new CommentPlace(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );*//*

                // Clear the input*/
                input.setText("");
            }
        });
    }

    /*public void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userCommentsAdapter = new UserCommentsAdapter(recyclerView,commentList,getContext());
        recyclerView.setAdapter(userCommentsAdapter);
    }*/
}
