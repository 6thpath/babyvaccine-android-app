package com.t6.babyvaccin.babyvaccin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChild extends AppCompatActivity {
    EditText txtACN, txtACB;
    Button btnACA, btnACC;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        txtACN = (EditText)findViewById(R.id.txtACName);
        txtACB = (EditText)findViewById(R.id.txtACBirthday);
        btnACA = (Button)findViewById(R.id.btnAddChild);
        btnACC = (Button)findViewById(R.id.btnACCancel);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid = user.getUid();

        // Connect databse + create reference
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference(uid);

        btnACA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtACN.getText().toString();
                String dob = txtACB.getText().toString();

                if(name.matches("^\\s*$") || dob.matches("^\\s*$")) {
                    Toast.makeText(AddChild.this,
                            "Child's name or birthday is invalid!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String newChildKey = myRef.push().getKey();
                    myRef.child("ChildList").child(name).setValue(new ChildClass(newChildKey, name, dob))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddChild.this,
                                        "Add child successfully!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddChild.this,
                                        "Add child failed!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                }
            }
        });

        btnACC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
