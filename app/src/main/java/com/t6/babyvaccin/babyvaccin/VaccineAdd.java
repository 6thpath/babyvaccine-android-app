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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.t6.babyvaccin.babyvaccin.model.VaccinClass;

public class VaccineAdd extends AppCompatActivity {

    Button btnAddVaccine, btnCancel;
    EditText txtName, txtDescription;
    FirebaseDatabase db;
    DatabaseReference vaccineCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_add);

        btnAddVaccine = (Button) findViewById(R.id.btnAddVaccin);
        btnCancel = (Button) findViewById(R.id.btnCancelAddVaccin);
        txtName = (EditText) findViewById(R.id.inputVaccineName);
        txtDescription = (EditText) findViewById(R.id.inputDescription);

        db = FirebaseDatabase.getInstance();
        vaccineCollection = db.getReference("Vaccine");

        btnAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String description = txtDescription.getText().toString();
//                acloading.setVisibility(View.VISIBLE);

                if(name.matches("^\\s*$") || description.matches("^\\s*$")) {
                    Toast.makeText(VaccineAdd.this,
                            "Vaccine's name or description is invalid!",
                            Toast.LENGTH_SHORT).show();
//                    acloading.setVisibility(View.GONE);
                } else {
                    String newChildKey = vaccineCollection.push().getKey();
                    vaccineCollection.child(newChildKey).setValue(new VaccinClass(newChildKey, name, description))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(VaccineAdd.this,
                                            "Add vaccine successfully!",
                                            Toast.LENGTH_SHORT).show();
//                                    acloading.setVisibility(View.GONE);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(VaccineAdd.this,
                                            "Add vaccine failed!",
                                            Toast.LENGTH_LONG).show();
//                                    acloading.setVisibility(View.GONE);
                                }
                            });
                }
            }
        });
    }
}
