package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.app.Activity.RESULT_OK;
import static com.cloudvision.utp.quieroentradas.presentation.ui.MainActivity.userResponse;

/**
 * Created by Walberth Gutierrez Telles on 05,June,2018
 */
public class CommentFragment extends Fragment {
    private static final String TAG = "CommentFragment";
    private FirebaseListAdapter<CommentPlace> adapter;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String idLocation;
    private EditText inputComment;
    private TextView messageText;
    private TextView messageUser;
    private TextView messageTime;
    private ListView listOfMessages;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null) {
            Bundle mBundle = getArguments();
            idLocation = mBundle.getString("idLocation");
        }

        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        inputComment = view.findViewById(R.id.inputComment);
        listOfMessages = view.findViewById(R.id.listOfMessages);

        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getActivity(), "Must be redirect to login", Toast.LENGTH_LONG).show();

        } else {
            displayChatMessages();
        }

        fab.setOnClickListener(new sendCommentToFirebase());
    }

    class sendCommentToFirebase implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            CommentPlace commentPlace = new CommentPlace();
            commentPlace.setMessageText(inputComment.getText().toString());
            commentPlace.setDateTimeComment(new Date().getTime());
            commentPlace.setIdUser(user.getUid());
            commentPlace.setIdSongclickPlace(idLocation);

            FirebaseDatabase.getInstance().getReference().child("chats").push().setValue(commentPlace);

            inputComment.setText("");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            displayChatMessages();
        }
    }

    private void displayChatMessages() {
        Query query = FirebaseDatabase.getInstance().getReference().child("chats");

        FirebaseListOptions<CommentPlace> options = new FirebaseListOptions.Builder<CommentPlace>()
                .setQuery(query, CommentPlace.class)
                .setLayout(R.layout.message)
                .setLifecycleOwner(this)
                .build();

        adapter = new FirebaseListAdapter<CommentPlace>(options) {
            @Override
            @SuppressLint("SetTextI18n")
            protected void populateView(View view, CommentPlace model, int position) {
                if(model.getIdSongclickPlace().equals(idLocation)) {
                    messageText = view.findViewById(R.id.message_text);
                    messageUser = view.findViewById(R.id.message_user);
                    messageTime = view.findViewById(R.id.message_time);

                    messageText.setText(model.getMessageText());
                    messageUser.setText(userResponse.getName() + " " + userResponse.getLastName());
                    messageTime.setText(convertTime(model.getDateTimeComment()));
                }
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }
}
