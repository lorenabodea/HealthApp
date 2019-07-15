package com.example.healthapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.healthapp.util.Constants;
import com.example.healthapp.util.FirebaseUtil;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

     TextView text;
     TextView text1;
     Long carbsPerDay ;
     Integer kcalsPerDay;
     ImageView addGlycemicValue;
     ImageView addDailyTreatment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUtil.openFbReference("menus", this);

        addGlycemicValue = findViewById(R.id.main_add_glycemic_value);
        addDailyTreatment = findViewById(R.id.main_add_daily_treatment);

        text = findViewById(R.id.main_text);
        text.setVisibility(View.INVISIBLE);
        text1 = findViewById(R.id.main_text1);
        text1.setVisibility(View.INVISIBLE);
        setDailyNutrients();
    }

    private void setDailyNutrients() {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert );
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot child: dataSnapshot.getChildren()) {
                            if(child.getKey().equals("carbs")) {
                                carbsPerDay = child.getValue(Long.class);
                            } else if(child.getKey().equals("kcals")){
                                kcalsPerDay = child.getValue(Integer.class);
                            }
                        }
                        text1.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                        if(carbsPerDay >= 0) {
                            String textToShow1 = getString(R.string.main_text_1)+ " " + carbsPerDay +" " + getString(R.string.main_text_2);
                            text1.setText(textToShow1);

                        } else {
                            String textToShow = getString(R.string.menu_text2_1)+ " " + -carbsPerDay +" " + getString(R.string.menu_text2_2);
                            text1.setText(textToShow);
                        }
                        String textToShow = getString(R.string.main_text_1_1)+" " + kcalsPerDay+ " " +getString(R.string.main_text_3);
                        text.setText(textToShow);
                        text1.setTextColor(getResources().getColor(R.color.blueish));
                        text.setTextColor(getResources().getColor(R.color.blueish));

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.attachListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       // Intent intent;
        switch (item.getItemId()){
            case R.id.menu_log_out:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout", "User logged out");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                break;
            case R.id.menu_treatment_profile:
                Intent intent = new Intent(getApplicationContext(), CreateTreatmentProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_general_profile:
               Intent intent2 = new Intent(getApplicationContext(), CreateProfileActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_language:
                showChangeLanguageDialog();
                break;
            case R.id.menu_terms:
                openDialogTerms();
                break;
            case R.id.menu_guide:
                openDialogGuide();
                break;
        }
        return true;
    }

    private void openDialogGuide() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(R.string.guide);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        alert11.getWindow().setBackgroundDrawableResource(android.R.color.holo_purple);
    }

    private void openDialogTerms() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(R.string.terms_and_conditions);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        alert11.getWindow().setBackgroundDrawableResource(android.R.color.holo_purple);
    }

    private void showChangeLanguageDialog() {
        final String[] languages = {"English", "Romanian"};

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(R.string.dialog_language_title);
        mBuilder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    setLocale("en");
                    recreate();
                } else {
                    setLocale("ro");
                    recreate();
                }
                dialog.dismiss();
            }
        });

        AlertDialog mdialog = mBuilder.create();
        mdialog.show();
    }

    private void setLocale(String lang) {
       Locale locale = new Locale(lang);
       Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_lang", lang);
        editor.apply();
    }

    public void go_to_glycemic_profile(View view) {
        Intent intent = new Intent(getApplicationContext(), GlycemicProfileActivity.class);
        startActivity(intent);
    }

    public void go_to_daily_treatment(View view) {
        Intent intent = new Intent(getApplicationContext(), DailyTreatmentActivity.class);
        startActivity(intent);
    }

    public void go_to_jurnal(View view) {
        Intent intent = new Intent(getApplicationContext(), JurnalActivity.class);
        startActivity(intent);

    }

    public void go_to_graphs(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.graph_options_dialog_spinner, null);
        mBuilder.setTitle(R.string.graph_options_title);
        final Spinner isBeforeMealSpinner = mView.findViewById(R.id.graph_options_isBeforeMeal_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.isBeforeMealListDialog));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isBeforeMealSpinner.setAdapter(adapter);

        final Spinner timeOfMealSpinner = mView.findViewById(R.id.graph_options_timeOfTheDay_spinner);
        ArrayAdapter<String> adapterMeal = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.timeOfMealDialog));
        adapterMeal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeOfMealSpinner.setAdapter(adapterMeal);

        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String optionIsBeforeMeal = isBeforeMealSpinner.getSelectedItem().toString();
                String optionTimeOfMeal = timeOfMealSpinner.getSelectedItem().toString();

                Intent intent = new Intent(getApplicationContext(), GlycemicGraphActivity.class);
                Bundle extras = new Bundle();
                if(optionIsBeforeMeal.equals("Nimic")) {
                    optionIsBeforeMeal = "none";
                }
                if(optionIsBeforeMeal.equals("Înainte de masă")) {
                    optionIsBeforeMeal = "Before meal";
                }
                if(optionIsBeforeMeal.equals("După masă")) {
                    optionIsBeforeMeal = "After meal";
                }
                if(optionTimeOfMeal.equals("Nimic")) {
                    optionTimeOfMeal = "none";
                }
                if(optionTimeOfMeal.equals("Mic dejun")) {
                    optionTimeOfMeal = "Breakfast";
                }
                if(optionTimeOfMeal.equals("Prânz")) {
                    optionTimeOfMeal = "Lunch";
                }
                if(optionTimeOfMeal.equals("Cină")) {
                    optionTimeOfMeal = "Dinner";
                }
                extras.putString("optionIsBeforeMeal",optionIsBeforeMeal);
                extras.putString("optionTimeOfMeal",optionTimeOfMeal);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        mBuilder.setView(mView);
        mBuilder.create().show();

    }
}
