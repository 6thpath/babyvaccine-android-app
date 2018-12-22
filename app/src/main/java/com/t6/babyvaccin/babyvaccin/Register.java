package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText txtrU, txtrP;
    Button btnR, btnTL;
    ProgressBar rloading;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtrU = (EditText)findViewById(R.id.regtxtUsername);
        txtrP = (EditText)findViewById(R.id.regtxtPassword);
        btnR = (Button)findViewById(R.id.btnRegister);
        btnTL = (Button)findViewById(R.id.btnToLogin);
        rloading = (ProgressBar)findViewById(R.id.regLoading);
        rloading.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        btnTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtrU.getText().toString();
                String password = txtrP.getText().toString();
                rloading.setVisibility(View.VISIBLE);

                if(username.matches("^\\s*$") | password.matches("^\\s*$")) {
                    Toast.makeText(Register.this,
                            "Username or Password is invalid!",
                            Toast.LENGTH_SHORT).show();
                    rloading.setVisibility(View.GONE);
                } else {
                    SignUp(username, password);
                }
            }
        });
    }

    public void SignUp(String email, String password ) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(Register.this, "Successfully registered!",
                                Toast.LENGTH_SHORT).show();
                        rloading.setVisibility(View.GONE);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Register.this,
                                "Authentication failed. " + task.getException().toString().split(":")[1],
                                Toast.LENGTH_LONG).show();
                        rloading.setVisibility(View.GONE);
                    }
                }
            });
    }
}
