package com.example.healthapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    static Double shot1Units;
    static Double shot2Units;
    static Double shot3Units;
    static Double shot4Units;
    static Integer carbs1Grams;
    static Integer carbs2Grams;
    static Integer carbs3Grams;
    static Integer carbs4Grams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treatment_profile);

        initComponents();
    }

    private void initComponents() {

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference().child("tratment_profiles");

        carbsPerDay = findViewById(R.id.create_treatment_profile_carbs_per_day_tie);
        dayTreatmentName = findViewById(R.id.create_treatment_profile_t1_name_te);
        nightTreatmentName = findViewById(R.id.create_treatment_profile_t2_name_te);
        shot1 = findViewById(R.id.create_reatmen_profile_i1_sp);
        shot2 = findViewById(R.id.create_reatmen_profile_i2_sp);
        shot3 = findViewById(R.id.create_reatmen_profile_i3_sp);
        shot4 = findViewById(R.id.create_reatmen_profile_i4_sp);
        meal1 = findViewById(R.id.create_treatmen_profile_c1_sp);
        meal2 = findViewById(R.id.create_treatmen_profile_c2_sp);
        meal3 = findViewById(R.id.create_treatmen_profile_c3_sp);
        meal4 = findViewById(R.id.create_treatmen_profile_c4_sp);
        saveBtn = findViewById(R.id.create_reatment_profile_save_btn);

        saveBtn.setOnClickListener(saveEvent());

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

        meal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = meal2.getSelectedItem().toString();
                carbs2Grams = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), carbs2Grams.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromSpinners();
                String dayTreatment = dayTreatmentName.getText().toString();
                String nightTreatment = nightTreatmentName.getText().toString();
                Integer carbs = Integer.parseInt(carbsPerDay.getText().toString());

                Treatment treatment = new Treatment(carbs, carbs1Grams, carbs2Grams, carbs3Grams, carbs4Grams, shot1Units, shot2Units, shot3Units, shot4Units,dayTreatment, nightTreatment);

                Toast.makeText(getApplicationContext(), treatment.toString(), Toast.LENGTH_SHORT).show();

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

    public void getValuesFromSpinners() {
        meal1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = meal1.getSelectedItem().toString();
                carbs1Grams = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), carbs1Grams.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meal2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = meal2.getSelectedItem().toString();
                carbs2Grams = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), carbs2Grams.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meal3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = meal3.getSelectedItem().toString();
                carbs3Grams = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), carbs3Grams.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        meal4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = meal4.getSelectedItem().toString();
                carbs4Grams = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), carbs4Grams.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shot1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = shot1.getSelectedItem().toString();
                shot1Units = Double.parseDouble(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shot2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = shot2.getSelectedItem().toString();
                shot2Units = Double.parseDouble(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shot3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = shot3.getSelectedItem().toString();
                shot3Units = Double.parseDouble(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        shot4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = shot4.getSelectedItem().toString();
                shot4Units = Double.parseDouble(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
