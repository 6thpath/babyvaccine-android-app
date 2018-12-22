package com.t6.babyvaccin.babyvaccin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VaccineDetail extends AppCompatActivity {

    TextView txtName, txtDescription;
    String uidVaccine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_detail);

        txtName = (TextView) findViewById(R.id.txtVaccineName);
        txtDescription = (TextView) findViewById(R.id.txtVaccineDescription);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            this.uidVaccine = mBundle.getString("uid");
            txtName.setText(mBundle.getString("name"));
            txtDescription.setText(mBundle.getString("description"));
        }
    }
}
