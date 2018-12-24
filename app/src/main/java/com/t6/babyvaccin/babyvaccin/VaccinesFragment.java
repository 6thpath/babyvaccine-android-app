package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    DatabaseReference role, vaccineCollection;
    ProgressBar fbloading;


    ArrayList<VaccinClass> vaccines = new ArrayList<VaccinClass>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vaccines, container, false);

        btnAddVaccine = (Button) view.findViewById(R.id.btnStartAddVaccin);
        listvaccine = (ListView) view.findViewById(R.id.listvaccine);
        fbloading = (ProgressBar) view.findViewById(R.id.fragVaccinesLoading);
        fbloading.setVisibility(View.VISIBLE);
        // Connect databse + create reference
        db = FirebaseDatabase.getInstance();

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
                        btnAddVaccine.setVisibility(View.VISIBLE);
//                        Toast.makeText(getContext(),
//                                "Logged as Administrator",
//                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Intent addVaccine = new Intent(getContext(), VaccineAdd.class);

        // Connect databse + create reference
        vaccineCollection = db.getReference("Vaccine");

        vaccineCollection.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vaccines.clear();
                fbloading.setVisibility(View.VISIBLE);

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String uid = childDataSnapshot.child("uid").getValue().toString();
                    String name = childDataSnapshot.child("name").getValue().toString();
                    String description = childDataSnapshot.child("description").getValue().toString();
                    String injectAt = childDataSnapshot.child("injectAt").getValue().toString();
//                    Log.d("UID", uid);
                    vaccines.add(new VaccinClass(uid, name, description, injectAt));

                }
                if (getActivity() != null){
                    VaccineAdapter vaccineAdapter = new VaccineAdapter(getContext(), vaccines);
                    listvaccine.setAdapter(vaccineAdapter);
                    listvaccine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent mIntent = new Intent(getContext(), VaccineDetail.class);
                            mIntent.putExtra("uid", vaccines.get(i).getUid());
                            mIntent.putExtra("name", vaccines.get(i).getName());
                            mIntent.putExtra("description", vaccines.get(i).getDescription());
                            mIntent.putExtra("injectAt", vaccines.get(i).getInjectAt());
                            startActivity(mIntent);
                        }
                    });
                }

                fbloading.setVisibility(View.GONE);
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
