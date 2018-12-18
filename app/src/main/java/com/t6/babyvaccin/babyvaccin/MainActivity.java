package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;

    EditText txtU, txtP;
    Button btnL, btnTR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();

        txtU = (EditText)findViewById(R.id.lgtxtUsername);
        txtP = (EditText)findViewById(R.id.lgtxtPassword);
        btnL = (Button)findViewById(R.id.btnLogin);
        btnTR = (Button)findViewById(R.id.btnToRegister);

        final Intent register = new Intent(this, Register.class);
        final Intent home = new Intent(this, Home.class);

        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName();
            // String email = user.getEmail();
            // Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            // boolean emailVerified = user.isEmailVerified();
            // String uid = user.getUid();
            Toast.makeText(MainActivity.this, "Authentication successfully!", Toast.LENGTH_SHORT).show();
            startActivity(home);
        }

        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtU.getText().toString();
                String password = txtP.getText().toString();

                if(username.matches("^\\s*$") | password.matches("^\\s*$")) {
                    Toast.makeText(MainActivity.this,
                            "Username or Password is invalid!",
                            Toast.LENGTH_SHORT).show();
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

        myRef = db.getReference("message");

//        myRef.setValue("Hello, World!");
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    public void SignIn(String email, String password) {
        final Intent home = new Intent(this, Home.class);
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Log.d("Login, "signInWithEmail:success");
                    Toast.makeText(MainActivity.this,
                            "Authentication successfully!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(home);
                } else {
                    // Log.w("Login", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this,
                            "Authentication failed." + task.getException().toString().split(":")[1],
                            Toast.LENGTH_LONG).show();
                }
                }
            });
    }

}
