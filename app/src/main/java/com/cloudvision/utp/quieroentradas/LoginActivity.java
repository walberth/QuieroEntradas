package com.cloudvision.utp.quieroentradas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private TextInputLayout emailWrap = null, passwordWrap = null, component = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        Button btnSignIn = (Button) findViewById(R.id.btnLogin);
        Button btnSignUp = (Button) findViewById(R.id.btnSignup);
        emailWrap = (TextInputLayout) findViewById(R.id.emailWrap);
        passwordWrap = (TextInputLayout) findViewById(R.id.passwordWrap);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.logo);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailWrap.getEditText().getText().toString();
                final String password = passwordWrap.getEditText().getText().toString();
                boolean isOk = preValidateFields(email, password);
                if (isOk) {
                    emailWrap.setErrorEnabled(false);
                    passwordWrap.setErrorEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthInvalidUserException invalidEmail) {
                                            emailWrap.setErrorEnabled(true);
                                            emailWrap.setError(getResources().getString(R.string.wrongEmailError));
                                        } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                            passwordWrap.setErrorEnabled(true);
                                            passwordWrap.setError(getResources().getString(R.string.wrongPasswordError));
                                        } catch (Exception e) {
                                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.unkwownError), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        emailWrap.setErrorEnabled(false);
                                        passwordWrap.setErrorEnabled(false);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                            });
                } else {
                    if (component != null) {
                        component.requestFocus();
                    }
                }
            }
        });
    }
    //FirebaseAuth.getInstance().signOut();

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private boolean preValidateFields(String email, String password) {
        boolean isOk = true;
        //Valida correo eletronico o usuario
        if (TextUtils.isEmpty(email)) {
            emailWrap.setErrorEnabled(true);
            emailWrap.setError(getResources().getString(R.string.inputEmailError));
            isOk = false;
            if (component == null) {
                passwordWrap.setErrorEnabled(false);
                component = emailWrap;
            }
        }
        //Valida contraseña
        if (TextUtils.isEmpty(password)) {
            passwordWrap.setErrorEnabled(true);
            passwordWrap.setError(getResources().getString(R.string.inputPasswordError));
            isOk = false;
            if (component == null) {
                emailWrap.setErrorEnabled(false);
                component = passwordWrap;
            }
        } else {
            if (password.length() < 6) {
                passwordWrap.setErrorEnabled(true);
                passwordWrap.setError(getResources().getString(R.string.inputPasswordLengthError));
                isOk = false;
                if (component == null) {
                    emailWrap.setErrorEnabled(false);
                    component = passwordWrap;
                }
            }
        }
        return isOk;
    }
}