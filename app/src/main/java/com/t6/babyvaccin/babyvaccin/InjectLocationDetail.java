package com.t6.babyvaccin.babyvaccin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InjectLocationDetail extends AppCompatActivity {
    TextView IASN, IASA;
    Button btnDIA, btnDIB;
    ProgressBar IADL;
    String IAuid;

    FirebaseDatabase db;
    DatabaseReference myRef, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        IASN = (TextView) findViewById(R.id.IADShowName);
        IASA = (TextView) findViewById(R.id.IADShowAddress);
        btnDIA = (Button) findViewById(R.id.btnDeleteIA);
        btnDIB = (Button) findViewById(R.id.btnIABack);
        IADL = (ProgressBar) findViewById(R.id.IADLoading);
        IADL.setVisibility(View.GONE);

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("InjectAddress");

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        role = db.getReference(uid).child("role");
        role.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    String role = dataSnapshot.getValue().toString();
                    String[] roles = role.split(",");
                    boolean isAdmin = false;
                    for(int i = 0; i < roles.length ; i++ ){
                        if(roles[i].equals("admin")){
                            Log.w("VaccineFragment" ,"Admin logged");
                            isAdmin = true;
                        }
                    }
                    if(isAdmin){
                        btnDIA.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            this.IAuid = mBundle.getString("uid");
            IASN.setText(mBundle.getString("name"));
            IASA.setText(mBundle.getString("address"));
        }

        // Button Delete
        btnDIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IADL.setVisibility(View.VISIBLE);
                myRef.child(IAuid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(InjectLocationDetail.this, "Delete inject address successfully", Toast.LENGTH_SHORT).show();
                            IADL.setVisibility(View.GONE);
                            finish();
                        } else {
                            Toast.makeText(InjectLocationDetail.this, "Remove failed", Toast.LENGTH_SHORT).show();
                            IADL.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        // button cancel
        btnDIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
