package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.classes.GlycemicProfile;
import com.example.healthapp.util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.List;

public class GlycemicProfileActivity extends AppCompatActivity {

    RadioGroup rg;
    static Boolean beforeMeal;
    TextInputEditText bloodsugarLevelTie;
    RadioButton beforeMealBtn;
    Button save;
    Spinner spinner;
    static String timeOfTheDay;
    SmileRating smileRating;
    private static final String TAG = "GlycemicProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glycemic_profile);

        init();
    }

    private void init() {

        bloodsugarLevelTie = findViewById(R.id.glycemic_profile_blood_sugar_te);
        rg = findViewById(R.id.glycemic_profile_rg);
        beforeMealBtn = findViewById(R.id.glycemic_profile_before_radioBtn);
        beforeMealBtn.setChecked(true);
       // smileRating = (SmileRating) findViewById(R.id.smile_rating);
        save = findViewById(R.id.glycemic_profile_save_btn);

        save.setOnClickListener(saveEvent());

        spinner = findViewById(R.id.create_treatment_profile_spinner);

        final List<String> timeOfTheMeal = new ArrayList<>();
        timeOfTheMeal.add("Breakfast");
        timeOfTheMeal.add("Lunch");
        timeOfTheMeal.add("Dinner");

        ArrayAdapter<String> mealsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, timeOfTheMeal);
        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mealsAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeOfTheDay = spinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), timeOfTheDay, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
//            @Override
//            public void onSmileySelected(@BaseRating.Smiley int smiley, boolean reselected) {
//                // reselected is false when user selects different smiley that previously selected one
//                // true when the same smiley is selected.
//                // Except if it first time, then the value will be false.
//                switch (smiley) {
//                    case SmileRating.BAD:
//                        Log.i(TAG, "Bad");
//                        break;
//                    case SmileRating.GOOD:
//                        Log.i(TAG, "Good");
//                        break;
//                    case SmileRating.GREAT:
//                        Log.i(TAG, "Great");
//                        break;
//                    case SmileRating.OKAY:
//                        Log.i(TAG, "Okay");
//                        break;
//                    case SmileRating.TERRIBLE:
//                        Log.i(TAG, "Terrible");
//                        break;
//                }
//            }
//        });
//
//        smileRating.setNameForSmile(BaseRating.TERRIBLE, "Angry");
//        smileRating.setNameForSmile(BaseRating.BAD, "Sad");
//        smileRating.setNameForSmile(BaseRating.GOOD, "Normal");
//        smileRating.setNameForSmile(BaseRating.GREAT, "Happy");
//        smileRating.setNameForSmile(BaseRating.OKAY, "No feelings");


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.glycemic_profile_before_radioBtn){
                    beforeMeal = true;
                }else {
                    beforeMeal=false;
                }
            }
        });


        bloodsugarLevelTie.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Integer bloodSugarLevel = Integer.parseInt(bloodsugarLevelTie.getText().toString());
                if(bloodSugarLevel>180){
                    TextView tv = findViewById(R.id.glycemic_profile_tv_important);
                    tv.setText("Blood sugar level is too high!");
                    tv.setVisibility(View.VISIBLE);
                }
                if(bloodSugarLevel<70){
                    TextView tv = findViewById(R.id.glycemic_profile_tv_important);
                    tv.setText("Blood sugar level is too low!");
                    tv.setVisibility(View.VISIBLE);
                }
                if(bloodSugarLevel>70&&bloodSugarLevel<180){
                    TextView tv = findViewById(R.id.glycemic_profile_tv_important);
                    tv.setVisibility(View.INVISIBLE);
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

    private View.OnClickListener saveEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer bloodLevel = Integer.parseInt(bloodsugarLevelTie.getText().toString());
                String meal = spinner.getSelectedItem().toString();
//                int level = smileRating.getRating();
//                Toast.makeText(getApplicationContext(), level, Toast.LENGTH_LONG).show();

                GlycemicProfile glycemicProfile = new GlycemicProfile(meal, beforeMeal, bloodLevel);
                String id = FirebaseUtil.mDatabase.push().getKey();
                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid()+"/glycemic_profile").child(id).setValue(glycemicProfile);


            }
        };
    }
}
