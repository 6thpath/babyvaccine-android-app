package com.t6.babyvaccin.babyvaccin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyDetail extends AppCompatActivity {

    TextView txtBabyname, txtDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail);

        txtBabyname = (TextView) findViewById(R.id.txtBabyname);
        txtDob = (TextView) findViewById(R.id.txtDob);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            txtBabyname.setText(mBundle.getString("babyname"));
            txtDob.setText(mBundle.getString("dob"));
        }
    }
}
