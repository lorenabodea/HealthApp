package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.util.Constants;
import com.example.healthapp.util.FirebaseUtil;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button addDailyTreatment;
    Button glycemicProfile;
    TextView text;
     Long carbsPerDay ;
     Integer kcalsPerDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUtil.openFbReference("menus", this);

        addDailyTreatment = findViewById(R.id.main_daily_treatment_btn);
        addDailyTreatment.setOnClickListener(addDailyTreatmentOnclick());
        glycemicProfile = findViewById(R.id.main_glycemic_profile_btn);
        glycemicProfile.setOnClickListener(glycemicProfileOnClick());

        text = findViewById(R.id.main_text);
        setDailyNutrients();
    }

    private void setDailyNutrients() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert );
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean found = false;
                        for(DataSnapshot child: dataSnapshot.getChildren()) {
                            if(child.getKey().equals("carbs")) {
                                carbsPerDay = child.getValue(Long.class);
                                found = true;
                            } else if(child.getKey().equals("kcals")){
                                kcalsPerDay = child.getValue(Integer.class);
                            }
                        }

                        text.setText("Hello, User! For today, you have "+ carbsPerDay + " left and you consumed "+ kcalsPerDay +" calories");

                        if(found == false) {
                            setNutrientsFromProfile();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setNutrientsFromProfile() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/user_treatment");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child: dataSnapshot.getChildren()) {
                            if(child.getKey().equals("carbs_per_day")) {
                                 carbsPerDay = child.getValue(Long.class);
                            }
                        }
                        kcalsPerDay = 0;
                        text.setText("Hello, User! For today, you have "+ carbsPerDay + " left and you consumed "+ kcalsPerDay +" calories");

                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("tmz", "Failed to read value.", error.toException());
                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.attachListener();
    }



    private View.OnClickListener glycemicProfileOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GlycemicProfileActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener addDailyTreatmentOnclick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyTreatmentActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_home);
        item.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.menu_profile:
                intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_treatment:
                intent = new Intent(getApplicationContext(), CreateTreatmentProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_graph:
                intent = new Intent(getApplicationContext(), GlycemicGraphActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_log_out:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout", "User logged out");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                break;
            default:
                break;
        }
        return true;
    }
}
