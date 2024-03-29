package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.classes.DailyTreatment;
import com.example.healthapp.classes.GlycemicProfile;
import com.example.healthapp.util.Constants;
import com.example.healthapp.util.FirebaseUtil;

import java.util.ArrayList;
import java.util.Date;
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

        ArrayAdapter<String> mealsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.timeOfMeal));
        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mealsAdapter);

        bloodsugarLevelTie.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Integer bloodSugarLevel = Integer.parseInt(bloodsugarLevelTie.getText().toString());
                if(bloodSugarLevel>180){
                    TextView tv = findViewById(R.id.daily_treatment_blood_sugar_level_attention);
                    tv.setText(R.string.hyperglycemic);
                    findViewById(R.id.daily_treatment_blood_sugar_level_attention).setVisibility(View.VISIBLE);
                }
                if(bloodSugarLevel<70){
                    TextView tv = findViewById(R.id.daily_treatment_blood_sugar_level_attention);
                    tv.setText(R.string.hipoglicemic);
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
                Integer bloodSugarLevel = Integer.parseInt(bloodsugarLevelTie.getText().toString());
                double nrOfShots = Double.parseDouble(nroffUnitsTie.getText().toString());
                long date = new Date().getTime();
                String timeOfTheDay = spinner.getSelectedItem().toString();

                DailyTreatment dailyTreatment = new DailyTreatment(bloodSugarLevel, n, nrOfShots, date, timeOfTheDay);
                String dailyID = FirebaseUtil.mDatabase.push().getKey();
                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid()+"/daily_treatments").child(dailyID).setValue(dailyTreatment);


                GlycemicProfile glycemicProfile = new GlycemicProfile(timeOfTheDay, true, bloodSugarLevel);
                String id = FirebaseUtil.mDatabase.push().getKey();
                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid()+"/glycemic_profile").child(id).setValue(glycemicProfile);


                Intent intent = new Intent(getApplicationContext(), MenuActivity.class).putExtra("nrOfCarbs",n.toString());

                if(bloodSugarLevel < 70) {
                    Toast toast = Toast.makeText(getApplicationContext(),  R.string.advice_hipo_meal, Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                    toastMessage.setTextSize(25);
                    toastMessage.setTextColor(getResources().getColor(R.color.red));
                    toastMessage.setGravity(Gravity.CENTER);
                    toastMessage.setCompoundDrawablePadding(16);
                    toastView.setBackgroundColor(getResources().getColor(R.color.lightpurple));
                    toast.show();
                } else if(bloodSugarLevel > 180){
                    Toast toast = Toast.makeText(getApplicationContext(),  R.string.advice_hiper_meal, Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                    toastMessage.setTextSize(25);
                    toastMessage.setTextColor(getResources().getColor(R.color.red));
                    toastMessage.setGravity(Gravity.CENTER);
                    toastMessage.setCompoundDrawablePadding(16);
                    toastView.setBackgroundColor(getResources().getColor(R.color.lightpurple));
                    toast.show();

                }

                startActivity(intent);
            }
        };
    }
}
