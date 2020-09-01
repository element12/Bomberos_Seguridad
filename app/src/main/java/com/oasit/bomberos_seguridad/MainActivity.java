package com.oasit.bomberos_seguridad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        private FirebaseAuth auth;
        ProgressBar progressBar;
        EditText desg_user,desg_password;
        Button desg_login;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                Intent intent = new Intent (MainActivity.this, Menu_General.class);
                startActivity(intent);
                finish();
            }

            setContentView(R.layout.activity_main);

            //Definir cada uno de los recursos
            progressBar = findViewById(R.id.progressBar);
            desg_user = findViewById(R.id.desg_user);
            desg_password = findViewById(R.id.desg_password);
            desg_login = findViewById(R.id.desg_ingresar);

            //Proceso de ingreso
            auth = FirebaseAuth.getInstance();
            desg_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = desg_user.getText().toString();
                    final String password = desg_password.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Falta Usuario", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Falta contraseña", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //proceso de verficacion
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
                                            desg_password.setError(getString(R.string.incorrecta));
                                        } else {

                                            Toast.makeText(MainActivity.this, getString(R.string.corto), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent (MainActivity.this, Menu_General.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            });
        }
    }
}