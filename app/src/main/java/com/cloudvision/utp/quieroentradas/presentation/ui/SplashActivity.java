package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}