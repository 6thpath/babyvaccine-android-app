package com.t6.babyvaccin.babyvaccin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BabyDetail extends AppCompatActivity {

    String uidBaby;
    TextView txtBabyname, txtDob;
    Button btnDelete;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail);

        txtBabyname = (TextView) findViewById(R.id.txtBabyname);
        txtDob = (TextView) findViewById(R.id.txtDob);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String uid = user.getUid();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference(uid).child("ChildList");

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            this.uidBaby = mBundle.getString("uid");
            txtBabyname.setText(mBundle.getString("babyname"));
            txtDob.setText(mBundle.getString("dob"));
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uidBaby != null){
                    myRef.child(uidBaby).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(BabyDetail.this, "Delete baby successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(BabyDetail.this, "Remove failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}
