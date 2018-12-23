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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.t6.babyvaccin.babyvaccin.model.IAClass;

import java.util.ArrayList;

public class InjectLocationFragment extends Fragment {
    Button btnAddIA;
    ListView listIAs;
    ProgressBar IALL;

    FirebaseDatabase db;
    DatabaseReference IA, role;
    ArrayList<IAClass> ias = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_locations, container, false);
        btnAddIA = (Button)view.findViewById(R.id.btnAddInjectLocation);
        listIAs = (ListView) view.findViewById(R.id.listIA);
        IALL = (ProgressBar) view.findViewById(R.id.IALLoading);
        IALL.setVisibility(View.GONE);

        // Connect database + create reference
        db = FirebaseDatabase.getInstance();
        IA = db.getReference("InjectAddress");

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
                        btnAddIA.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        IA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                IALL.setVisibility(View.VISIBLE);
                ias.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String uid = childDataSnapshot.child("uid").getValue().toString();
                    String name = childDataSnapshot.child("name").getValue().toString();
                    String address = childDataSnapshot.child("address").getValue().toString();
                    ias.add(new IAClass(uid, name, address));

                }
                if(getActivity() != null){
                    InjectLocationAdapter IAAdapter = new InjectLocationAdapter(getContext(), ias);
                    listIAs.setAdapter(IAAdapter);
                    listIAs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent mIntent = new Intent(getContext(), InjectLocationDetail.class);
                            mIntent.putExtra("uid", ias.get(i).getUid());
                            mIntent.putExtra("name", ias.get(i).getName());
                            mIntent.putExtra("address", ias.get(i).getAddress());
                            startActivity(mIntent);
                            IALL.setVisibility(View.GONE);
                        }
                    });
                }
                IALL.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Button open Add form
        final Intent AIA = new Intent(getContext(), InjectLocationAdd.class);
        btnAddIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AIA);
            }
        });

        return view;
    }
}
