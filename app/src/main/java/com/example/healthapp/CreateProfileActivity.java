package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthapp.classes.User;
import com.example.healthapp.util.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_profile);
        item.setVisible(false);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Toast.makeText(getApplicationContext(), currentFirebaseUser.getUid(), Toast.LENGTH_LONG).show();

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
                user.setUserID(currentFirebaseUser.toString());
                String userId = mDatabase.push().getKey();
                mDatabase.child(currentFirebaseUser.getUid()+"/add_activity").child(userId).setValue(user);


            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.menu_home:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_treatment:
                intent = new Intent(getApplicationContext(), CreateTreatmentProfileActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return true;
    }
}
