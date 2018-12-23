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
import android.widget.ProgressBar;

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
    DatabaseReference IA;
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
