package com.example.healthapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.healthapp.classes.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateTreatmentProfileActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    private TextInputEditText carbsPerDay;
    private TextInputEditText dayTreatmentName;
    private TextInputEditText nightTreatmentName;
    private Spinner shot1;
    private Spinner shot2;
    private Spinner shot3;
    private Spinner shot4;
    private Spinner meal1;
    private Spinner meal2;
    private Spinner meal3;
    private Spinner meal4;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treatment_profile);

        initComponents();
    }

    private void initComponents() {

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("tratments");

        carbsPerDay = findViewById(R.id.create_treatment_profile_carbs_per_day_tie);
        dayTreatmentName = findViewById(R.id.create_treatment_profile_t1_name_te);
        nightTreatmentName = findViewById(R.id.create_treatment_profile_t2_name_te);
        shot1 = findViewById(R.id.create_reatmen_profile_i1_sp);
        shot2 = findViewById(R.id.create_reatmen_profile_i2_sp);
        shot3 = findViewById(R.id.create_reatmen_profile_i4_sp);
        shot4 = findViewById(R.id.create_reatmen_profile_i4_sp);
        meal1 = findViewById(R.id.create_treatmen_profile_c1_sp);
        meal2 = findViewById(R.id.create_treatmen_profile_c2_sp);
        meal3 = findViewById(R.id.create_treatmen_profile_c3_sp);
        meal4 = findViewById(R.id.create_treatmen_profile_c4_sp);
        saveBtn = findViewById(R.id.create_reatment_profile_save_btn);

        //saveBtn.setOnClickListener(saveEvent());

        List<Integer> carbsList = new ArrayList<>();

        for(int i=1; i<20; i++) {
            carbsList.add(i*5);
        }

        ArrayAdapter<Integer> carbsListAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, carbsList);
        carbsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meal1.setAdapter(carbsListAdapter);
        meal2.setAdapter(carbsListAdapter);
        meal3.setAdapter(carbsListAdapter);
        meal4.setAdapter(carbsListAdapter);

        List<Double> shotsList = new ArrayList<>();
        for(int i=1; i<200; i++) {
            shotsList.add(i*0.5);
        }

        ArrayAdapter<Double> shotsListAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, shotsList);
        shotsListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shot1.setAdapter(shotsListAdapter);
        shot2.setAdapter(shotsListAdapter);
        shot3.setAdapter(shotsListAdapter);
        shot4.setAdapter(shotsListAdapter);

//        List<Integer> mealsNumber = new ArrayList<>();
//        mealsNumber.add(1);
//        mealsNumber.add(2);
//        mealsNumber.add(3);
//        mealsNumber.add(4);
//
//        ArrayAdapter<Integer> mealsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, mealsNumber);
//        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        meals.setAdapter(mealsAdapter);
//
//        List<Integer> snacksNumber = new ArrayList<>();
//        snacksNumber.add(1);
//        snacksNumber.add(2);
//        snacksNumber.add(3);
//        snacksNumber.add(4);
//
//        ArrayAdapter<Integer> snacksAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, snacksNumber);
//        snacksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        snacks.setAdapter(snacksAdapter);
//
//        List<Integer> shotsNumber = new ArrayList<>();
//        shotsNumber.add(1);
//        shotsNumber.add(2);
//        shotsNumber.add(3);
//        shotsNumber.add(4);
//
//        ArrayAdapter<Integer> shotsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, shotsNumber);
//        shotsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        shots.setAdapter(shotsAdapter);
//
//      meals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                String string = meals.getSelectedItem().toString();
////                Integer number = Integer.parseInt(string);
//                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        snacks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                String string = snacks.getSelectedItem().toString();
////                Integer number = Integer.parseInt(string);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        shots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//               // String string = shots.getSelectedItem().toString();
//                Integer number = Integer.parseInt(string);
//                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer c1 ;
                Integer c2 ;
                Integer c3;
                Integer c4;
                Double s1;
                Double s2;
                Double s3;
                Double s4;
//                String dayTreatment = dayTreatmentName.getText().toString();
//                String nightTreatment = nightTreatmentName.getText().toString();
//                Integer carbs = Integer.parseInt(carbsPerDay.getText().toString());
//
//                Treatment treatment = new Treatment(carbs, c1, c2, c3, c4, s1, s2, s3, s4, dayTreatment, nightTreatment);
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
            default:
                break;
        }
        return true;
    }


}
