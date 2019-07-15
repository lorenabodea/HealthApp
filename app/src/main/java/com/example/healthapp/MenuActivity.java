package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapp.classes.Menu;
import com.example.healthapp.network.FoodParser;
import com.example.healthapp.network.HTTPManager;
import com.example.healthapp.util.Constants;
import com.example.healthapp.util.FirebaseUtil;
import com.example.healthapp.util.MenuAdapter;
import com.example.healthapp.util.Nutrients;
import com.example.healthapp.util.Validations;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {

    TextInputEditText typeFood;
    TextInputEditText quantity;
    Button addBtn;
    TextView remainingCarbsTV;
    Double nrOfCarbsLeft = 0.0;
    Double kcalsConsumed = 0.0;
    AlertDialog.Builder builder;
    Double nrOfCarbs;
    List<Menu> menus = new ArrayList<>();
    List<Nutrients> nutrientsList = new ArrayList<>();
    ListView lv_menus;
    private MenuAdapter adapter;
    private DatabaseReference mDatabase;
    Button done;

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
        done = findViewById(R.id.menu_finish);

        adapter = new MenuAdapter(this, R.layout.lv_menus_row, menus, getLayoutInflater());
        lv_menus.setAdapter(adapter);


        String nrOfCarbs = getIntent().getStringExtra("nrOfCarbs");
        nrOfCarbsLeft = Double.parseDouble(nrOfCarbs);
        remainingCarbsTV = findViewById(R.id.menu_remainingCarbs_tv);
        remainingCarbsTV.setText(nrOfCarbsLeft + " left and " + kcalsConsumed + " consumed");

        builder = new AlertDialog.Builder(this);

        lv_menus.setOnItemLongClickListener(deleteEvent());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert);
       //ref.child( "/nutrients").removeValue();
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private View.OnClickListener addBtnClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    final String foodName = typeFood.getText().toString();

                    final Integer quant = Integer.parseInt(quantity.getText().toString());
                    String URL;
                    String[] splited = foodName.split("\\s+");
                    if (splited.length == 1) {
                        URL = "https://api.edamam.com/api/food-database/parser?ingr=" +
                                foodName + "&app_id=7ac64b8e&app_key=22eb5d58662867094ba13a8880946bb0";
                    } else {
                        URL = "https://api.edamam.com/api/food-database/parser?ingr=" +
                                splited[0] + "%20" + splited[1] + "&app_id=7ac64b8e&app_key=22eb5d58662867094ba13a8880946bb0";
                    }
                    @SuppressLint("StaticFieldLeak") HTTPManager manager = new HTTPManager() {
                        @Override
                        protected void onPostExecute(String s) {
                            try {
                                if (FoodParser.fromJson(s) == null) {
                                    typeFood.setError(getString(R.string.noFoodError1)+ " " + typeFood.getText().toString() + " " +getString(R.string.noFoodError2));
                                } else {

                                    nrOfCarbs = FoodParser.fromJson(s).getCarbs();
                                    final Double nrOfKcals = FoodParser.fromJson(s).getCalories();
                                    Nutrients nutrients = new Nutrients(nrOfCarbs, nrOfKcals);
                                    nutrientsList.add(nutrients);

                                    final Menu menu = new Menu(foodName, quant);

                                    if (nrOfCarbsLeft - nrOfCarbs / 100 * quant < 0) {
                                        //dialog to inform user that he has exceeded the set nrOfCarbs
                                        builder.setMessage(R.string.dialog_ok).setTitle(R.string.dialog_title);

                                        builder.setMessage(R.string.dialog_exceededCarbs_text)
                                                .setCancelable(false)
                                                .setPositiveButton(R.string.dialog_exceeded_ok_text, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        kcalsConsumed += (quant * nrOfKcals) / 100;
                                                        addInFirebase(menu);

                                                        nrOfCarbsLeft -= nrOfCarbs / 100 * quant;
                                                        remainingCarbsTV.setText(-nrOfCarbsLeft.intValue()+ " " + getString(R.string.carbsExceeded1) + " " + kcalsConsumed.intValue() + " " +getString(R.string.carbsExceeded2));
                                                        dialog.cancel();
                                                    }
                                                });

                                        AlertDialog alert = builder.create();
                                        alert.setTitle(getString(R.string.menuDialogTitle));
                                        alert.show();
                                    } else {
                                        kcalsConsumed += (quant * nrOfKcals) / 100;
                                        addInFirebase(menu);

                                        nrOfCarbsLeft -= nrOfCarbs / 100 * quant;
                                        remainingCarbsTV.setText(nrOfCarbsLeft.intValue()+ " " + getString(R.string.carbsExceeded3)+ " " + kcalsConsumed.intValue()+ " " + getString(R.string.carbsExceeded2));
                                    }

                                    typeFood.getText().clear();
                                    quantity.getText().clear();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            notifyAdapter();
                        }
                    };
                    manager.execute(URL);
                } else {
                    Toast.makeText(getApplicationContext(), "nu există", Toast.LENGTH_SHORT).show();
                }


            }
        };

    }

    private void addInFirebase(Menu menu) {
        menus.add(menu);

       final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    resetNutrients(nrOfCarbsLeft, kcalsConsumed);
                }

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

        String menuId = mDatabase.push().getKey();
        mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert + "/menus").child(menuId).setValue(menu);

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
                Nutrients nutrientToRemove = nutrientsList.get(position);
                Menu menuToRemove = menus.get(position);
                kcalsConsumed -= nutrientToRemove.getCalories() / 100 * menuToRemove.getQuantity();
                nrOfCarbsLeft += nutrientToRemove.getCarbs() / 100 * menuToRemove.getQuantity();

                resetNutrients(nrOfCarbsLeft, kcalsConsumed);

                if (nrOfCarbsLeft < 0) {
                    remainingCarbsTV.setText(-nrOfCarbsLeft.intValue()+ " " + getString(R.string.carbsExceeded1)+ " " + kcalsConsumed.intValue()+ " " + getString(R.string.carbsExceeded2));
                } else {
                    remainingCarbsTV.setText(nrOfCarbsLeft.intValue()+ " " + getString(R.string.carbsExceeded3)+ " " + kcalsConsumed.intValue()+ " " + getString(R.string.carbsExceeded2));
                }


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query foodQuery = ref.child(FirebaseUtil.currentFirebaseUser.getUid() + "/"+ Constants.dateToInsert + "/menus").orderByChild("food").equalTo(menuToRemove.getFood());

                foodQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                nutrientsList.remove(position);
                menus.remove(position);
                notifyAdapter();
            }
        };
    }

    public void resetNutrients(Double nrOfCarbsLeft, Double kcalsConsumed) {
        mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert + "/carbs").setValue(nrOfCarbsLeft);
        mDatabase.child(FirebaseUtil.currentFirebaseUser.getUid() + "/" + Constants.dateToInsert + "/kcals").setValue(kcalsConsumed);

    }

    private void notifyAdapter() {
        MenuAdapter adapter = (MenuAdapter) lv_menus.getAdapter();
        adapter.notifyDataSetChanged();
    }

    public boolean isValid() {
        if (Validations.isEmptyField(typeFood.getText().toString())) {
            typeFood.setError("Insert food");
            return false;
        }

        if (Validations.isEmptyField(quantity.getText().toString())) {
            quantity.setError("Insert quantity");
            return false;
        }
        return true;
    }
}
