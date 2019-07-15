package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
        save = findViewById(R.id.glycemic_profile_save_btn);

        save.setOnClickListener(saveEvent());

        spinner = findViewById(R.id.create_treatment_profile_spinner);

        ArrayAdapter<String> mealsAdapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.timeOfMeal));
        mealsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mealsAdapter);

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
                    tv.setText(R.string.hyperglycemic);
                    tv.setVisibility(View.VISIBLE);
                }
                if(bloodSugarLevel<70){
                    TextView tv = findViewById(R.id.glycemic_profile_tv_important);
                    tv.setText(R.string.hipoglicemic);
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

                GlycemicProfile glycemicProfile = new GlycemicProfile(meal, beforeMeal, bloodLevel);
                String id = FirebaseUtil.mDatabase.push().getKey();
                FirebaseUtil.mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid()+"/glycemic_profile").child(id).setValue(glycemicProfile);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if(bloodLevel < 70) {
                    Toast toast = Toast.makeText(getApplicationContext(),  R.string.advice_hipo, Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                    toastMessage.setTextSize(25);
                    toastMessage.setTextColor(getResources().getColor(R.color.red));
                    toastMessage.setGravity(Gravity.CENTER);
                    toastMessage.setCompoundDrawablePadding(16);
                    toastView.setBackgroundColor(getResources().getColor(R.color.lightpurple));
                    toast.show();
                } else if(bloodLevel > 180){
                    Toast toast = Toast.makeText(getApplicationContext(),  R.string.advice_hiper, Toast.LENGTH_LONG);
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
