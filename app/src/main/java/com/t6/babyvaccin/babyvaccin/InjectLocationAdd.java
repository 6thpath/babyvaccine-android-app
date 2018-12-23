package com.t6.babyvaccin.babyvaccin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t6.babyvaccin.babyvaccin.model.IAClass;
import com.t6.babyvaccin.babyvaccin.model.VaccinClass;

public class InjectLocationAdd extends AppCompatActivity {
    Button btnAIA, btnCIA;
    EditText txtAIAN, txtAIAA;
    ProgressBar AIAL;
    FirebaseDatabase db;
    DatabaseReference IACollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_add);

        btnAIA = (Button) findViewById(R.id.btnIAAdd);
        btnCIA = (Button) findViewById(R.id.btnIAACancel);
        txtAIAN = (EditText) findViewById(R.id.IAAtxtName);
        txtAIAA = (EditText) findViewById(R.id.IAAtxtAddress);
        AIAL = (ProgressBar) findViewById(R.id.AIALoading);
        AIAL.setVisibility(View.GONE);

        db = FirebaseDatabase.getInstance();
        IACollection = db.getReference("InjectAddress");

        btnAIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtAIAN.getText().toString();
                String address = txtAIAA.getText().toString();
                AIAL.setVisibility(View.VISIBLE);

                if(name.matches("^\\s*$") || address.matches("^\\s*$")) {
                    Toast.makeText(InjectLocationAdd.this,
                            "Address's name or address is invalid!",
                            Toast.LENGTH_SHORT).show();
                    AIAL.setVisibility(View.GONE);
                } else {
                    String newChildKey = IACollection.push().getKey();
                    IACollection.child(newChildKey).setValue(new IAClass(newChildKey, name, address))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(InjectLocationAdd.this,
                                            "Add Inject address successfully!",
                                            Toast.LENGTH_SHORT).show();
                                    AIAL.setVisibility(View.GONE);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(InjectLocationAdd.this,
                                            "Add Inject address failed!",
                                            Toast.LENGTH_LONG).show();
                                    AIAL.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });

        btnCIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}