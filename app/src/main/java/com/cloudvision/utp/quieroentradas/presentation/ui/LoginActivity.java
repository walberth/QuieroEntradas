package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.datasource.session.UserSessionManager;
import com.cloudvision.utp.quieroentradas.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG  = "LoginActivity";

    private FirebaseAuth auth;
    private TextInputLayout emailWrap = null, passwordWrap = null, component = null;
    ImageView imageView;
    CircularProgressButton btnLogin = null;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        Button btnSignUp = findViewById(R.id.btnSignup);
        btnLogin = (CircularProgressButton) findViewById(R.id.btnLogin);
        emailWrap = findViewById(R.id.emailWrap);
        passwordWrap = findViewById(R.id.passwordWrap);
        imageView = findViewById(R.id.logo);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.startAnimation();
                String email = emailWrap.getEditText().getText().toString();
                final String password = passwordWrap.getEditText().getText().toString();
                hideKeyBoard();
                boolean isOk = preValidateFields(email, password);
                if (isOk) {
                    emailWrap.setErrorEnabled(false);
                    passwordWrap.setErrorEnabled(false);
                    auth = FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
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
                                        btnLogin.revertAnimation();
                                    } else {
                                        emailWrap.setErrorEnabled(false);
                                        passwordWrap.setErrorEnabled(false);
                                        //Query query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("user").equalTo("gramos");
                                        //Log.d(TAG, "uid " + auth.getCurrentUser().getUid());
                                        //Log.d(TAG, "uiemail init d " + auth.getCurrentUser().getEmail());
                                        databaseReference.orderByChild("email").equalTo(auth.getCurrentUser().getEmail())
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                String email =  snapshot.child("email").getValue(String.class);
                                                                String idLogin =  snapshot.child("idLogin").getValue(String.class);
                                                                String lastName =  snapshot.child("lastName").getValue(String.class);
                                                                String name =  snapshot.child("name").getValue(String.class);
                                                                String sexName =  snapshot.child("sexName").getValue(String.class);
                                                                String sexUri =  snapshot.child("sexUri").getValue(String.class);
                                                                String username =  snapshot.child("username").getValue(String.class);
                                                                Long created =  snapshot.child("userCreatedTime").getValue(Long.class);
                                                                Log.d(TAG, "sex uri " + sexUri);
                                                                User user = new User(idLogin, name, lastName, email, created, username, sexName, sexUri);
                                                                new UserSessionManager(getApplicationContext(), user);
                                                                btnLogin.revertAnimation();
                                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                    }
                                }
                            });
                } else {
                    if (component != null) {
                        component.requestFocus();
                    }
                    btnLogin.revertAnimation();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btnLogin.dispose();
    }

    private boolean preValidateFields(String email, String password) {
        boolean isOk = true;
        //Valida correo eletronico
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

    private void hideKeyBoard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //Toast.makeText(LoginActivity.this, getResources().getString(R.string.hideKeyboardViewError), Toast.LENGTH_LONG).show();
        }
    }
}
