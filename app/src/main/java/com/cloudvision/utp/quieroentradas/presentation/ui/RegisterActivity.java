package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private EditText inputEmail, inputPassword, name, lastName;
    private Button btnSignUp, btnSignIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        btnSignUp = findViewById(R.id.sign_up_button);
        btnSignIn = findViewById(R.id.sign_in);
        inputEmail = findViewById(R.id.registerEmail);
        inputPassword = findViewById(R.id.registerPassword);
        progressBar = findViewById(R.id.progressBar);
        name = findViewById(R.id.registerName);
        lastName = findViewById(R.id.registerLastName);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    //TODO: SAVE EXTRA INFORMATION TO THE DATABASE
                                    user = FirebaseAuth.getInstance().getCurrentUser();

                                    //Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("email").equalTo(user.getEmail());
                                    //String idLogin = user.getUid();

                                    assert user != null;

                                    Log.d(TAG, "onComplete: idLogin " + user.getUid());
                                    Log.d(TAG, "onComplete: name " + name.getText().toString());
                                    Log.d(TAG, "onComplete: lastName " + lastName.getText().toString());
                                    Log.d(TAG, "onComplete: email " + user.getEmail());

                                    String firsLetter = name.getText().toString().substring(0, 1);
                                    String[] firstLastName = lastName.getText().toString().split(" ");

                                    String username = firsLetter +firstLastName[0];

                                    User firebaseUser = new User();
                                    firebaseUser.setEmail(user.getEmail());
                                    firebaseUser.setName(name.getText().toString());
                                    firebaseUser.setLastName(lastName.getText().toString());
                                    firebaseUser.setIdLogin(user.getUid());
                                    firebaseUser.setUserCreatedTime(new Date().getTime());

                                    if(user.getEmail() != null) {
                                        FirebaseDatabase.getInstance()
                                                .getReference()
                                                .child("user")
                                                .child(username)
                                                .setValue(firebaseUser);
                                        finish();
                                    }
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
