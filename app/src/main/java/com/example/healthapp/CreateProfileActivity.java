package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthapp.classes.User;
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

import java.text.ParseException;
import java.util.Date;

public class CreateProfileActivity extends AppCompatActivity {

    private TextInputEditText fName;
    private TextInputEditText lname;
    private TextInputEditText bDate;
    private TextInputEditText weight;
    private TextInputEditText height;
    private RadioGroup rgGender;
    private Button btnSave;
    String gender;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        initComponents();
        btnSave = findViewById(R.id.create_profile_save_btn);
        btnSave.setOnClickListener(saveEvent());

        displayExistingValues();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        return super.onCreateOptionsMenu(menu);
    }

    private void initComponents(){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        fName = findViewById(R.id.create_profile_fname_tie);
        lname = findViewById(R.id.create_profile_lname_tie);
        bDate = findViewById(R.id.create_profile_date_tie);
        weight = findViewById(R.id.create_profile_weight_tie);
        height = findViewById(R.id.create_profile_height_tie);
        rgGender = findViewById(R.id.create_profile_gender_rg);
        btnSave = findViewById(R.id.create_profile_save_btn);
    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = fName.getText().toString();
                String lastName = lname.getText().toString();
                Date birthDate = null;

                try {
                     birthDate = Constants.simpleDateFormamt.parse(bDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Integer w = Integer.parseInt(weight.getText().toString());
                Integer h = Integer.parseInt(height.getText().toString());
                rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId==R.id.create_profile_male){
                          gender = "M";
                        }else {
                            if(checkedId==R.id.create_profile_female){
                                gender = "F";
                            }
                        }
                    }
                });
                User user = new User(firstName, lastName, birthDate, w, h, gender);
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                user.setUserID(currentFirebaseUser.getDisplayName());
                String userId = mDatabase.push().getKey();
                mDatabase.child(currentFirebaseUser.getUid()+"/add_activity").child(userId).setValue(user);


            }
        };
    }

    private void displayExistingValues() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/add_activity");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getChildrenCount()>0) {
                            for(DataSnapshot child: dataSnapshot.getChildren()) {
                              // User user = child.getValue(User.class);
                               // Toast.makeText(getApplicationContext(), user.toString(), Toast.LENGTH_LONG).show();
                            }
                        }

//                        for(DataSnapshot child: dataSnapshot.getChildren()) {
//                            if(child.getKey().equals("carbs_per_day")) {
//                                Long name = child.getValue(Long.class);
//                                carbsPerDay.setText(name.toString());
//                            } else   if(child.getKey().equals("day_treatment_name")) {
//                                String name = child.getValue(String.class);
//                                dayTreatmentName.setText(name);
//                            } else {
//                                String name = child.getValue(String.class);
//                                nightTreatmentName.setText(name);
//                            }
//                        }


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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
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
