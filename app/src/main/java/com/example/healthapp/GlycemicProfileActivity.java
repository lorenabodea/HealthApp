package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

public class GlycemicProfileActivity extends AppCompatActivity {

    RadioGroup rg;
    Boolean beforeMeal;
    TextInputEditText bloodsugarLevelTie;
    RadioButton beforeMealBtn;
    Button save;


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

                Integer bloodLevel = Integer.parseInt(bloodsugarLevelTie.getText().toString());

            }
        };
    }
}
