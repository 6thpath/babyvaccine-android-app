package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t6.babyvaccin.babyvaccin.model.VaccinClass;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BabyDetail extends AppCompatActivity {

    String uidBaby;
    TextView txtBabyname, txtDob, txtSchedule;
    Button btnDelete, btnBDB;
    ProgressBar BDL;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference myRef, anotherRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail);

        txtBabyname = (TextView) findViewById(R.id.txtBabyname);
        txtDob = (TextView) findViewById(R.id.txtDob);
        txtSchedule = (TextView) findViewById(R.id.schedule);
        txtSchedule.setMovementMethod(new ScrollingMovementMethod());
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBDB = (Button) findViewById(R.id.btnBDBack);
        BDL = (ProgressBar) findViewById(R.id.BDLoading);
        BDL.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference(uid).child("ChildList");

        anotherRef = db.getReference("Vaccine");


        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            this.uidBaby = mBundle.getString("uid");
            txtBabyname.setText(mBundle.getString("babyname"));
            txtDob.setText(mBundle.getString("dob"));

            final String DOB = mBundle.getString("dob");

            anotherRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        String name = childDataSnapshot.child("name").getValue().toString();
                        String injectAt = childDataSnapshot.child("injectAt").getValue().toString();

                        // calculate date
                        try {
                            Date result = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(DOB);
                            Log.d("xz", result + "");
                            Calendar c = Calendar.getInstance();
                            c.setTime(result);
                            c.add(Calendar.MONTH, Integer.parseInt(injectAt));
                            int year = c.get(Calendar.YEAR);
                            int month = c.get(Calendar.MONTH)+1;
                            int day = c.get(Calendar.DAY_OF_MONTH);
                            // create schdule string
                            txtSchedule.append(name + ": " + "\t\t\t" + day+"/"+month+"/"+year + "\r\n");
                        } catch (ParseException pe) {
                            pe.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            BDL.setVisibility(View.VISIBLE);
            if(uidBaby != null){
                myRef.child(uidBaby).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(BabyDetail.this, "Delete baby successfully", Toast.LENGTH_SHORT).show();
                            BDL.setVisibility(View.GONE);
                            finish();
                        } else {
                            Toast.makeText(BabyDetail.this, "Remove failed", Toast.LENGTH_SHORT).show();
                            BDL.setVisibility(View.GONE);
                        }
                    }
                });

            }
            }
        });

        btnBDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
