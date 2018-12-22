package com.t6.babyvaccin.babyvaccin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BabiesFragment extends Fragment {
    TextView txthUsr;
    Button btnLO, btnAC;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase db;
    DatabaseReference myRef;
    ListView listbaby;
    ArrayList<ChildClass> babies = new ArrayList<ChildClass>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_babies, container, false);
        txthUsr = (TextView) view.findViewById(R.id.homeUsername);
        btnLO = (Button) view.findViewById(R.id.btnLogout);
        btnAC = (Button) view.findViewById(R.id.btnAddChild);
        listbaby = (ListView) view.findViewById(R.id.listview);

//         Get user information
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        String email = user.getEmail();
        String uid = user.getUid();
        txthUsr.setText(email);



        // Connect databse + create reference
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference(uid).child("ChildList");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                babies.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    String uid = childDataSnapshot.child("uid").getValue().toString();
                    String name = childDataSnapshot.child("name").getValue().toString();
                    String dob = childDataSnapshot.child("dob").getValue().toString();
//                    Log.d("UID", uid);
                    babies.add(new ChildClass(uid, name, dob));
                }
                String[] babynames = new String[babies.size()];
                for(int i = 0; i < babies.size() ; i++){
                    babynames[i] = babies.get(i).getName();
                }
                BabyAdapter babyAdapter = new BabyAdapter(getContext(), babynames);
                listbaby.setAdapter(babyAdapter);
                listbaby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent mIntent = new Intent(getContext(), BabyDetail.class);
                        mIntent.putExtra("uid", babies.get(i).getUid());
                        mIntent.putExtra("babyname", babies.get(i).getName());
                        mIntent.putExtra("dob", babies.get(i).getDob());
                        startActivity(mIntent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("BabiesFragment", "Failed to read value.", error.toException());
            }
        });

        final Intent addchild = new Intent(getContext(), AddChild.class);

        btnLO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();

            }
        });

        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addchild);
            }
        });


        return view;
    }

}
