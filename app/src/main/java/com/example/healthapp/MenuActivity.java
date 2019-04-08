package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.classes.Menu;
import com.example.healthapp.network.FoodParser;
import com.example.healthapp.network.HTTPManager;
import com.example.healthapp.util.MenuAdapter;
import com.example.healthapp.util.Nutrients;
import com.example.healthapp.util.Validations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    List<Menu> menus = new ArrayList<>();
    ListView lv_menus;
    private MenuAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
    }

    private void init() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        typeFood = findViewById(R.id.menu_type_food_tie);
        quantity = findViewById(R.id.menu_quantity);
        addBtn = findViewById(R.id.menu_add_btn);
        addBtn.setOnClickListener(addBtnClickEvent());
        lv_menus = findViewById(R.id.menu_lv);

        nutrientsList = new ArrayList<>();

        adapter = new MenuAdapter(this, R.layout.lv_menus_row, menus, getLayoutInflater());
        lv_menus.setAdapter(adapter);


       String nrOfCarbs =getIntent().getStringExtra("nrOfCarbs");
        remainingCarbs = Double.parseDouble(nrOfCarbs);
        remainingCarbsTV = findViewById(R.id.menu_remainingCarbs_tv);
        remainingCarbsTV.setText(remainingCarbs + " left and "+ totalKcals +" consumed");

        builder = new AlertDialog.Builder(this);

        lv_menus.setOnItemLongClickListener(deleteEvent());
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
                                                        remainingCarbsTV.setText(-remainingCarbs.intValue() + " carbs exceeded and "+totalKcals.intValue()+" calories consumed");
                                                        dialog.cancel();
                                                    }
                                                });

                                        AlertDialog alert = builder.create();
                                        alert.setTitle("AlertDialogExample");
                                        alert.show();
                                    }else{
                                        remainingCarbs-=nrOfCarbs/100*quant;
                                        remainingCarbsTV.setText(remainingCarbs.intValue() + " carbs left and "+totalKcals.intValue()+" calories consumed");
                                    }

                                    typeFood.getText().clear();
                                    quantity.getText().clear();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Menu menu = new Menu(foodName, quant);
                            menus.add(menu);

                            addInFirebase(menu);

                            notifyAdapter();
                        }
                    };
                    manager.execute(URL);
                }else{
                    Toast.makeText(getApplicationContext(), "nu e", Toast.LENGTH_SHORT).show();
                }


            }
        };

    }

    private void addInFirebase(Menu menu) {
        String menuId = mDatabase.push().getKey();
        mDatabase.child("menus").child(menuId).setValue(menu);

    }

    private AdapterView.OnItemLongClickListener deleteEvent() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);

                builder.setTitle(R.string.menu_delete_alert_title)
                        .setMessage(R.string.menu_delete_alert)
                        .setPositiveButton(R.string.yes, positiveEventDelete(position))
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
            builder.create().show();
            return true;
            }
        };
    }

    private DialogInterface.OnClickListener positiveEventDelete(final int position) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                menus.remove(position);
                notifyAdapter();
            }
        };
    }

    private void notifyAdapter() {
        MenuAdapter adapter = (MenuAdapter) lv_menus.getAdapter();
        adapter.notifyDataSetChanged();
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
