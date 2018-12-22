package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;

    EditText txtU, txtP;
    Button btnL, btnTR;
    ProgressBar lgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        txtU = (EditText)findViewById(R.id.lgtxtUsername);
        txtP = (EditText)findViewById(R.id.lgtxtPassword);
        btnL = (Button)findViewById(R.id.btnLogin);
        btnTR = (Button)findViewById(R.id.btnToRegister);
        lgLoading = (ProgressBar)findViewById(R.id.loginLoading);
        lgLoading.setVisibility(View.GONE);

        final Intent register = new Intent(this, Register.class);
        final Intent homeNav = new Intent(this, HomeNav.class);


        if (user != null) {
            Toast.makeText(MainActivity.this, "Authentication successfully!", Toast.LENGTH_SHORT).show();
            startActivity(homeNav);
        }

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtU.getText().toString();
                String password = txtP.getText().toString();
                lgLoading.setVisibility(View.VISIBLE);

                if(username.matches("^\\s*$") | password.matches("^\\s*$")) {
                    Toast.makeText(MainActivity.this,
                            "Username or Password is invalid!",
                            Toast.LENGTH_SHORT).show();

                    lgLoading.setVisibility(View.GONE);
                } else {
                    SignIn(username, password);
                }
            }
        });

        btnTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(register);
            }
        });
    }

    public void SignIn(String email, String password) {
        final Intent homeNav = new Intent(this, HomeNav.class);
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Log.d("Login, "signInWithEmail:success");
                    Toast.makeText(MainActivity.this,
                            "Authentication successfully!",
                            Toast.LENGTH_SHORT).show();
                    lgLoading.setVisibility(View.GONE);
                    startActivity(homeNav);
                } else {
                    // Log.w("Login", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this,
                            "Authentication failed." + task.getException().toString().split(":")[1],
                            Toast.LENGTH_LONG).show();
                    lgLoading.setVisibility(View.GONE);
                }
                }
            });
    }

}
