package com.example.healthapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.network.FoodParser;
import com.example.healthapp.network.HTTPManager;
import com.example.healthapp.util.Nutrients;
import com.example.healthapp.util.Validations;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    TextInputEditText typeFood;
    TextInputEditText quantity;
    Button addBtn;
    TextView remainingCarbsTV;
    Nutrients nutrients = new Nutrients(0.0, 0.0);
    List<Nutrients> nutrientsList;
    Double remainingCarbs = 0.0;
    Double totalKcals = 0.0;
    AlertDialog.Builder builder;
    Double nrOfCarbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
    }

    private void init() {

        typeFood = findViewById(R.id.menu_type_food_tie);
        quantity = findViewById(R.id.menu_quantity);
        addBtn = findViewById(R.id.menu_add_btn);
        addBtn.setOnClickListener(addBtnClickEvent());

        nutrientsList = new ArrayList<>();

       String nrOfCarbs =getIntent().getStringExtra("nrOfCarbs");
        remainingCarbs = Double.parseDouble(nrOfCarbs);
        remainingCarbsTV = findViewById(R.id.menu_remainingCarbs_tv);
        remainingCarbsTV.setText(remainingCarbs + " left and "+ totalKcals +" consumed");

        builder = new AlertDialog.Builder(this);
    }

    private View.OnClickListener addBtnClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){



                    final String foodName = typeFood.getText().toString();
                    final Integer quant = Integer.parseInt(quantity.getText().toString());
                    String URL;
                    String[] splited = foodName.split("\\s+");
                    if(splited.length==1){
                        URL  = "https://api.edamam.com/api/food-database/parser?ingr=" +
                                foodName + "&app_id=7ac64b8e&app_key=22eb5d58662867094ba13a8880946bb0";
                    }else{
                        URL  = "https://api.edamam.com/api/food-database/parser?ingr=" +
                                splited[0]+"%20"+splited[1] + "&app_id=7ac64b8e&app_key=22eb5d58662867094ba13a8880946bb0";
                    }
                    @SuppressLint("StaticFieldLeak") HTTPManager manager = new HTTPManager(){
                        @Override
                        protected void onPostExecute(String s){
                            try {
                                if(FoodParser.fromJson(s)==null){
                                    typeFood.setError("No food found with the name: "+ typeFood.getText().toString()+" was found");
                                }else{
                                    nrOfCarbs = FoodParser.fromJson(s).getCarbs();
                                    nutrients.setCarbs(nrOfCarbs);
                                    Double nrOfKcals = FoodParser.fromJson(s).getCalories();
                                    nutrients.setCalories(nrOfKcals);
                                    nutrientsList.add(nutrients);
                                    totalKcals = quant*nrOfKcals/100;

                                    if(remainingCarbs-nrOfCarbs/100*quant <0){
                                        //dialog to inform user that he has exceeded the set nrOfCarbs
                                        builder.setMessage(R.string.dialog_ok) .setTitle(R.string.dialog_title);

                                        builder.setMessage(R.string.dialog_exceededCarbs_text)
                                                .setCancelable(false)
                                                .setPositiveButton(R.string.dialog_exceeded_ok_text, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        remainingCarbs-=nrOfCarbs/100*quant;
                                                        remainingCarbsTV.setText(-remainingCarbs + " carbs exceeded and "+totalKcals+" calories consumed");
                                                        dialog.cancel();
                                                    }
                                                });

                                        AlertDialog alert = builder.create();
                                        alert.setTitle("AlertDialogExample");
                                        alert.show();
                                    }else{
                                        remainingCarbs-=nrOfCarbs/100*quant;
                                        remainingCarbsTV.setText(remainingCarbs + " carbs left and "+totalKcals+" calories consumed");
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    manager.execute(URL);
                }else{
                    Toast.makeText(getApplicationContext(), "nu e", Toast.LENGTH_SHORT).show();
                }


            }
        };

    }

    public boolean isValid(){
        if(Validations.isEmptyField(typeFood.getText().toString())){
            typeFood.setError("Insert food");
            return false;
        }

        if(Validations.isEmptyField(quantity.getText().toString())){
            quantity.setError("Insert quantity");
            return false;
        }
        return true;
    }
}
