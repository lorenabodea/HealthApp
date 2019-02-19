package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button createMenuBtn;
    Button addDailyTreatment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createMenuBtn = findViewById(R.id.main_create_menu_btn);
//        createMenuBtn.setOnClickListener(createMenuOnClick());

        addDailyTreatment = findViewById(R.id.main_daily_treatment_btn);
        addDailyTreatment.setOnClickListener(addDailyTreatmentOnclick());

    }

    private View.OnClickListener addDailyTreatmentOnclick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DailyTreatmentActivity.class);
                startActivity(intent);
            }
        };
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_home);
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
            case R.id.menu_treatment:
                intent = new Intent(getApplicationContext(), CreateTreatmentProfileActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return true;
    }
}
