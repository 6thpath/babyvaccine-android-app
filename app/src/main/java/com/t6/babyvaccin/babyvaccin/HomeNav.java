package com.t6.babyvaccin.babyvaccin;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeNav extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav);

        final BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new BabiesFragment()).commit();

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
                        bottomNav.getMenu().findItem(R.id.nav_admin).setVisible(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()){
                    case R.id.nav_babies:
                        selectedFragment = new BabiesFragment();
                        break;
                    case R.id.nav_vaccin:
                        selectedFragment = new VaccinesFragment();
                        break;
                    case R.id.nav_address:
                        selectedFragment = new InjectLocationFragment();
                        break;
                    case R.id.nav_admin:
                        selectedFragment = new AdminFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }

    };

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
