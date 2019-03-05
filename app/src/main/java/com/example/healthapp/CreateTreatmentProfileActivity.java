package com.example.healthapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateTreatmentProfileActivity extends AppCompatActivity {

    private TextInputEditText carbsPerDay;
    private Spinner meals;
    private Spinner snacks;
    private Spinner shots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treatment_profile);

        initComponents();
    }

    private void initComponents() {
        carbsPerDay = findViewById(R.id.treatment_profile_carbs_tie);
        meals = findViewById(R.id.treatment_meals_spinner);
        snacks = findViewById(R.id.treatment_snacks_spinner);
        shots = findViewById(R.id.treatment_injections_spinner);

        List<Integer> mealsNumber = new ArrayList<>();
        mealsNumber.add(1);
        mealsNumber.add(2);
        mealsNumber.add(3);
        mealsNumber.add(4);

        ArrayAdapter<Integer> mealsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, mealsNumber);
        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meals.setAdapter(mealsAdapter);

        List<Integer> snacksNumber = new ArrayList<>();
        snacksNumber.add(1);
        snacksNumber.add(2);
        snacksNumber.add(3);
        snacksNumber.add(4);

        ArrayAdapter<Integer> snacksAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, snacksNumber);
        snacksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snacks.setAdapter(snacksAdapter);

        List<Integer> shotsNumber = new ArrayList<>();
        shotsNumber.add(1);
        shotsNumber.add(2);
        shotsNumber.add(3);
        shotsNumber.add(4);

        ArrayAdapter<Integer> shotsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, shotsNumber);
        shotsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shots.setAdapter(shotsAdapter);

      meals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = meals.getSelectedItem().toString();
                Integer number = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        snacks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = snacks.getSelectedItem().toString();
                Integer number = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        shots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = shots.getSelectedItem().toString();
                Integer number = Integer.parseInt(string);
                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
