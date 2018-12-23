package com.t6.babyvaccin.babyvaccin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VaccineDetail extends AppCompatActivity {

    TextView txtName, txtDescription;
    Button btnVD, btnVB;
    String uidVaccine;
    ProgressBar VDL;

    FirebaseDatabase db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_detail);

        txtName = (TextView) findViewById(R.id.txtVaccineName);
        txtDescription = (TextView) findViewById(R.id.txtVaccineDescription);
        btnVD = (Button) findViewById(R.id.btnVDelete);
        btnVB = (Button) findViewById(R.id.btnVBack);
        VDL = (ProgressBar) findViewById(R.id.VDLoading);
        VDL.setVisibility(View.GONE);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Vaccine");

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            this.uidVaccine = mBundle.getString("uid");
            txtName.setText(mBundle.getString("name"));
            txtDescription.setText(mBundle.getString("description"));
        }

        btnVD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VDL.setVisibility(View.VISIBLE);
                myRef.child(uidVaccine).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VaccineDetail.this, "Delete Vaccine successfully", Toast.LENGTH_SHORT).show();
                            VDL.setVisibility(View.GONE);
                            finish();
                        } else {
                            Toast.makeText(VaccineDetail.this, "Remove failed", Toast.LENGTH_SHORT).show();
                            VDL.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
