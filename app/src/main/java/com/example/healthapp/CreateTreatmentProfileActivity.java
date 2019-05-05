package com.example.healthapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.classes.GlycemicProfile;
import com.example.healthapp.classes.Treatment;
import com.example.healthapp.util.Constants;
import com.example.healthapp.util.FirebaseUtil;
import com.firebase.ui.auth.AuthUI;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateTreatmentProfileActivity extends AppCompatActivity {

    private TextInputEditText carbsPerDay;
    private TextInputEditText dayTreatmentName;
    private TextInputEditText nightTreatmentName;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treatment_profile);

        initComponents();

        displayExistingValues();
    }

    private void displayExistingValues() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/user_treatment");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot child: dataSnapshot.getChildren()) {
                                    if(child.getKey().equals("carbs_per_day")) {
                                        Long name = child.getValue(Long.class);
                                        carbsPerDay.setText(name.toString());
                                     } else   if(child.getKey().equals("day_treatment_name")) {
                                        String name = child.getValue(String.class);
                                        dayTreatmentName.setText(name);
                                    } else {
                                        String name = child.getValue(String.class);
                                        nightTreatmentName.setText(name);
                                }
                            }
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

    private void initComponents() {

        carbsPerDay = findViewById(R.id.create_treatment_profile_carbs_per_day_tie);
        dayTreatmentName = findViewById(R.id.create_treatment_profile_t1_name_te);
        nightTreatmentName = findViewById(R.id.create_treatment_profile_t2_name_te);
        saveBtn = findViewById(R.id.create_reatment_profile_save_btn);
        saveBtn.setOnClickListener(saveEvent());
    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayTreatment = dayTreatmentName.getText().toString();
                String nightTreatment = nightTreatmentName.getText().toString();
                Integer carbs = Integer.parseInt(carbsPerDay.getText().toString());

                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid() + "/user_treatment").child("carbs_per_day").setValue(carbs);
                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid() + "/user_treatment").child("day_treatment_name").setValue(dayTreatment);
                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid() + "/user_treatment").child("night_treatment_name").setValue(nightTreatment);

            }
        };
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_treatment);
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
            case R.id.menu_home:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
