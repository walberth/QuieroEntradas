package com.cloudvision.utp.quieroentradas;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener autstates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth= FirebaseAuth.getInstance();

        btnSignIn= (Button) findViewById(R.id.button_login);
        inputEmail= (EditText) findViewById(R.id.email);
        inputPassword= (EditText) findViewById(R.id.password);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        btnSignUp= (Button) findViewById(R.id.register);


        btnSignUp.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, retorno.class);
                startActivity(intent);
            }
        });

     btnSignIn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = inputEmail.getText().toString();
            final String password = inputPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            //authenticate user
            auth= FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() < 6) {
                                    inputPassword.setError("contraseña incorrecta");
                                } else {
                                    Toast.makeText(MainActivity.this, "paràmetros incorrectos" , Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Intent intent = new Intent(MainActivity.this, retorno.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    });
        }
     });


}
    public  void  goCreateAccount(View vi){
        Intent intentRe = new Intent(this,Map .class);
        startActivity(intentRe);
    }
}