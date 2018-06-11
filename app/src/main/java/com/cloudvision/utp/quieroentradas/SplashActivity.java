package com.cloudvision.utp.quieroentradas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cloudvision.utp.quieroentradas.data.datasource.session.UserSessionManager;
import com.cloudvision.utp.quieroentradas.presentation.ui.LoginActivity;
import com.cloudvision.utp.quieroentradas.presentation.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserSessionManager session = new UserSessionManager(getApplicationContext());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null && session.checkLogin()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
