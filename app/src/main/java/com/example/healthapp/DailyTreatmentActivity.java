package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DailyTreatmentActivity extends AppCompatActivity {

    Button createMenuBtn;
    TextInputEditText bloodsugarLevelTie;
    TextInputEditText nrOfCarbsTie;
    TextInputEditText nroffUnitsTie;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_treatment);

        init();
    }

    private void init() {
        createMenuBtn = findViewById(R.id.dailytreatment_add_menu_btn);
        createMenuBtn.setOnClickListener(createMenuOnClick());

        bloodsugarLevelTie = findViewById(R.id.dailytreatment_blood_sugar_tie);
        nrOfCarbsTie = findViewById(R.id.dailytreatment_carbs_tie);
        nroffUnitsTie = findViewById(R.id.dailytreatment_nrofshots_tie);

        spinner = findViewById(R.id.dailytreatment_meal);


        List<String> timeOfTheMeal = new ArrayList<>();
        timeOfTheMeal.add("Breakfast");
        timeOfTheMeal.add("Lunch");
        timeOfTheMeal.add("Dinner");

        ArrayAdapter<String> mealsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, timeOfTheMeal);
        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mealsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bloodsugarLevelTie.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Integer bloodSugarLevel = Integer.parseInt(bloodsugarLevelTie.getText().toString());
                if(bloodSugarLevel>180){
                    TextView tv = findViewById(R.id.daily_treatment_blood_sugar_level_attention);
                    tv.setText("Blood sugar level is too high!");
                    findViewById(R.id.daily_treatment_blood_sugar_level_attention).setVisibility(View.VISIBLE);
                }
                if(bloodSugarLevel<70){
                    TextView tv = findViewById(R.id.daily_treatment_blood_sugar_level_attention);
                    tv.setText("Blood sugar level is too low!");
                    findViewById(R.id.daily_treatment_blood_sugar_level_attention).setVisibility(View.VISIBLE);
                }
                if(bloodSugarLevel>70&&bloodSugarLevel<180){
                    TextView tv = findViewById(R.id.daily_treatment_blood_sugar_level_attention);
                    findViewById(R.id.daily_treatment_blood_sugar_level_attention).setVisibility(View.INVISIBLE);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("ResourceAsColor")
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });

    }

    private View.OnClickListener createMenuOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double nrOfCarbs = Double.parseDouble(nrOfCarbsTie.getText().toString());
                Integer n = (int)nrOfCarbs;



                Intent intent = new Intent(getApplicationContext(), MenuActivity.class).putExtra("nrOfCarbs",n.toString());
                startActivity(intent);
            }
        };
    }
}
