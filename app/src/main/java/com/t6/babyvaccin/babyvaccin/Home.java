package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Home extends AppCompatActivity {
    TextView txthUsr;
    Button btnLO, btnAC;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txthUsr = (TextView)findViewById(R.id.homeUsername);
        btnLO = (Button)findViewById(R.id.btnLogout);
        btnAC = (Button)findViewById(R.id.btnAddChild);

        // Get user information
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        String email = user.getEmail();
        String uid = user.getUid();
        txthUsr.setText(email);

        // Connect databse + create reference
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference(uid).child("ChildList");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Home", "Failed to read value.", error.toException());
            }
        });

        final Intent addchild = new Intent(this, AddChild.class);

        btnLO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mAuth.getInstance().signOut();
            finish();
            }
        });

        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addchild);
            }
        });
    }
}
