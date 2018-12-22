package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t6.babyvaccin.babyvaccin.model.VaccinClass;

import java.util.ArrayList;

public class VaccinesFragment extends Fragment {

    Button btnAddVaccine;
    ListView listvaccine;
    FirebaseDatabase db;
    DatabaseReference vaccineCollection;
    ArrayList<VaccinClass> vaccines = new ArrayList<VaccinClass>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccines, container, false);

        btnAddVaccine = (Button) view.findViewById(R.id.btnStartAddVaccin);
        listvaccine = (ListView) view.findViewById(R.id.listvaccine);

        final Intent addVaccine = new Intent(getContext(), VaccineAdd.class);


        // Connect databse + create reference
        db = FirebaseDatabase.getInstance();
        vaccineCollection = db.getReference("Vaccine");

        vaccineCollection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vaccines.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String uid = childDataSnapshot.child("uid").getValue().toString();
                    String name = childDataSnapshot.child("name").getValue().toString();
                    String description = childDataSnapshot.child("description").getValue().toString();
//                    Log.d("UID", uid);
                    vaccines.add(new VaccinClass(uid, name, description));

                }
                VaccineAdapter vaccineAdapter = new VaccineAdapter(getContext(), vaccines);
                listvaccine.setAdapter(vaccineAdapter);
                listvaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent mIntent = new Intent(getContext(), VaccineDetail.class);
                        mIntent.putExtra("uid", vaccines.get(i).getUid());
                        mIntent.putExtra("name", vaccines.get(i).getName());
                        mIntent.putExtra("description", vaccines.get(i).getDescription());
                        startActivity(mIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addVaccine);
            }
        });

        return view;
    }
}
