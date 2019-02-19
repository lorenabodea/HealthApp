package com.example.healthapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyTreatmentActivity extends AppCompatActivity {

    Button createMenuBtn;
    TextInputEditText bloodsugarLevelTie;
    TextInputEditText nrOfCarbsTie;
    TextInputEditText nroffUnitsTie;

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

        
    }

    private View.OnClickListener createMenuOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        };
    }
}
