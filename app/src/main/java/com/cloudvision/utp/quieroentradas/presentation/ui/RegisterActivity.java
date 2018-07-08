package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.User;
import com.cloudvision.utp.quieroentradas.helpers.enums.UserSexEnum;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Date;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private FirebaseAuth auth;
    private FirebaseUser user;
    private TextInputLayout emailWrap = null, passwordWrap = null, nameWrap = null, lastnameWrap = null, component = null;
    private RadioGroup rbSexGroup = null;
    private RadioButton rbSexButton = null;
    private boolean validateRBSelected = true;
    private UserSexEnum userSexEnum = null;
    CircularProgressButton btnSignUp = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        showToolbar();

        auth = FirebaseAuth.getInstance();

        emailWrap = (TextInputLayout) findViewById(R.id.emailWrap);
        passwordWrap = (TextInputLayout) findViewById(R.id.passwordWrap);
        nameWrap = (TextInputLayout) findViewById(R.id.nameWrap);
        lastnameWrap = (TextInputLayout) findViewById(R.id.lastnameWrap);
        rbSexGroup = (RadioGroup) findViewById(R.id.rbSexGroup);

        btnSignUp = (CircularProgressButton) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSignUp.startAnimation();
                String email = emailWrap.getEditText().getText().toString().trim(),
                        password = passwordWrap.getEditText().getText().toString();
                final String name = nameWrap.getEditText().getText().toString().trim(),
                        lastname = lastnameWrap.getEditText().getText().toString().trim();
                int rbSelected = rbSexGroup.getCheckedRadioButtonId();
                hideKeyBoard();
                boolean isOk = preValidateFields(email, password, name, lastname, rbSelected);

                if (isOk) {
                    Log.d(TAG, "onClick: enter validate OK");

                    emailWrap.setErrorEnabled(false);
                    passwordWrap.setErrorEnabled(false);
                    nameWrap.setErrorEnabled(false);
                    lastnameWrap.setErrorEnabled(false);

                    //Crear usuario
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    String username = null;
                                    if (!task.isSuccessful()) {
                                        Log.d(TAG, "error when press register" + task.getException());
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthUserCollisionException invalidEmail) {
                                            emailWrap.setErrorEnabled(true);
                                            emailWrap.setError(getResources().getString(R.string.inputEmailExistError));
                                        } catch (Exception e) {
                                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.unkwownError), Toast.LENGTH_LONG).show();
                                        }
                                        btnSignUp.revertAnimation();
                                        AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                                                .setTitle(getResources().getString(R.string.dialog_title_error))
                                                .setMessage(getResources().getString(R.string.dialog_error_register_message))
                                                .setNeutralButton(getResources().getString(R.string.dialog_ok_button), null)
                                                .create();
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.show();
                                    } else {
                                        Log.d(TAG, "onComplete: success is ok");

                                        user = FirebaseAuth.getInstance().getCurrentUser();
                                        String[] lastNameArr = lastname.split(" ");
                                        if (lastNameArr != null && lastNameArr.length > 0) {
                                            username = name.substring(0, 1).toLowerCase() + lastNameArr[0].toLowerCase();
                                        }
                                        btnSignUp.revertAnimation();
                                        if(userSexEnum != null){
                                            User firebaseUser = new User(
                                                    user.getUid(),
                                                    name,
                                                    lastname,
                                                    user.getEmail(),
                                                    new Date().getTime(),
                                                    username,
                                                    userSexEnum.getName(),
                                                    userSexEnum.getUri()
                                            );

                                            if (user.getEmail() != null && username != null) {
                                                FirebaseDatabase.getInstance()
                                                        .getReference()
                                                        .child("user")
                                                        .child(user.getUid())
                                                        .setValue(firebaseUser);
                                                AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                                                        .setTitle(getResources().getString(R.string.dialog_register_title_success))
                                                        .setMessage(getResources().getString(R.string.dialog_register_message_success))
                                                        .setNeutralButton(getResources().getString(R.string.dialog_login_button), new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                                finish();
                                                            }
                                                        })
                                                        .create();
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.show();
                                            }
                                        }
                                    }
                                }
                            });
                } else {
                    if (component != null) {
                        component.requestFocus();
                    }
                    btnSignUp.revertAnimation();
                    if (!validateRBSelected) {
                        AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle(getResources().getString(R.string.dialog_title_error))
                                .setMessage(getResources().getString(R.string.dialog_error_rb_sex_message))
                                .setNeutralButton(getResources().getString(R.string.dialog_ok_button), null)
                                .create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
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
        btnSignUp.dispose();
    }

    public void showToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Registro de usuario");
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    private boolean preValidateFields(String email, String password, String name, String lastname, int rbSelected) {
        boolean isOk = true;
        //Valida correo eletronico
        if (TextUtils.isEmpty(email)) {
            emailWrap.setErrorEnabled(true);
            emailWrap.setError(getResources().getString(R.string.inputEmailError));
            isOk = false;
            if (component == null) {
                passwordWrap.setErrorEnabled(false);
                nameWrap.setErrorEnabled(false);
                lastnameWrap.setErrorEnabled(false);
                component = emailWrap;
            }
        } else if (!email.contains("@")) {
            emailWrap.setErrorEnabled(true);
            emailWrap.setError(getResources().getString(R.string.wrongEmailError));
            isOk = false;
            if (component == null) {
                passwordWrap.setErrorEnabled(false);
                nameWrap.setErrorEnabled(false);
                lastnameWrap.setErrorEnabled(false);
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
                nameWrap.setErrorEnabled(false);
                lastnameWrap.setErrorEnabled(false);
                component = passwordWrap;
            }
        } else if (password.length() < 6) {
            passwordWrap.setErrorEnabled(true);
            passwordWrap.setError(getResources().getString(R.string.inputPasswordLengthError));
            isOk = false;
            if (component == null) {
                emailWrap.setErrorEnabled(false);
                nameWrap.setErrorEnabled(false);
                lastnameWrap.setErrorEnabled(false);
                component = passwordWrap;
            }
        }
        //Valida nombre
        if (TextUtils.isEmpty(name)) {
            nameWrap.setErrorEnabled(true);
            nameWrap.setError(getResources().getString(R.string.inputNameError));
            isOk = false;
            if (component == null) {
                emailWrap.setErrorEnabled(false);
                passwordWrap.setErrorEnabled(false);
                lastnameWrap.setErrorEnabled(false);
                component = nameWrap;
            }
        }
        //Valida apellidos
        if (TextUtils.isEmpty(lastname)) {
            lastnameWrap.setErrorEnabled(true);
            lastnameWrap.setError(getResources().getString(R.string.inputLastnameError));
            isOk = false;
            if (component == null) {
                emailWrap.setErrorEnabled(false);
                passwordWrap.setErrorEnabled(false);
                nameWrap.setErrorEnabled(false);
                component = lastnameWrap;
            }
        }
        //Valida radio button
        rbSexButton = (RadioButton) findViewById(rbSelected);
        if (rbSexButton == null) {
            isOk = false;
            validateRBSelected = false;
        } else {
            validateRBSelected = true;
            changeUserSexEnumValue(rbSelected);
        }
        return isOk;
    }

    private void changeUserSexEnumValue(int selected){
        if(selected == R.id.rbMale){
            userSexEnum = UserSexEnum.MALE;
        } else if(selected == R.id.rbFemale){
            userSexEnum = UserSexEnum.FEMALE;
        }
    }

    private void hideKeyBoard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //Toast.makeText(RegisterActivity.this, getResources().getString(R.string.hideKeyboardViewError), Toast.LENGTH_LONG).show();
        }
    }
}
