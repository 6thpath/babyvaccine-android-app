package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText txtrU, txtrP;
    Button btnR, btnTL;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtrU = (EditText)findViewById(R.id.regtxtUsername);
        txtrP = (EditText)findViewById(R.id.regtxtPassword);
        btnR = (Button)findViewById(R.id.btnRegister);
        btnTL = (Button)findViewById(R.id.btnToLogin);

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
                if(username.matches("^\\s*$") | password.matches("^\\s*$")) {
                    Toast.makeText(Register.this,
                            "Username or Password is invalid!",
                            Toast.LENGTH_SHORT).show();
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
                        // Log.d("Register", "createUserWithEmail:success");
                        Toast.makeText(Register.this, "Successfully registered!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Register", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(Register.this,
                                "Authentication failed. " + task.getException().toString().split(":")[1],
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
}
