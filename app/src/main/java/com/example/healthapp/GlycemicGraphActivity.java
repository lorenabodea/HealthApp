package com.example.healthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healthapp.classes.DailyTreatment;
import com.example.healthapp.classes.GlycemicProfile;
import com.example.healthapp.util.FirebaseUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GlycemicGraphActivity extends AppCompatActivity {

    LineChart mpLineChart;
    final List<GlycemicProfile> dailys = new ArrayList<GlycemicProfile>();
     LineDataSet lineDataSet = null;
    ArrayList<Integer> colors = new ArrayList<Integer>();
    String optionIsBeforeMeal;
    String optionTimeOfMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glycemic_graph);

        optionIsBeforeMeal = getIntent().getStringExtra("optionIsBeforeMeal");
        optionTimeOfMeal = getIntent().getStringExtra("optionTimeOfMeal");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseUtil.currentFirebaseUser.getUid()+"/glycemic_profile");
       getValues(myRef);
    }

    private void getValues(DatabaseReference myRef ) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GlycemicProfile glycemicProfile = postSnapshot.getValue(GlycemicProfile.class);
                    if(dailys.size()<30)
                        dailys.add(glycemicProfile);
                }

                mpLineChart = findViewById(R.id.chart);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                lineDataSet = new LineDataSet(dataValues1(), "Glicemii");
                lineDataSet.setCircleColors(colors);
                lineDataSet.setCircleRadius(15);
                dataSets.add(lineDataSet);

                LineData data = new LineData(dataSets);
                mpLineChart.setData(data);
                mpLineChart.invalidate();

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("tmz", "Failed to read value.", error.toException());
            }
        });

    }


    private ArrayList<Entry> dataValues1()   {



        ArrayList<Entry> dataValues = new ArrayList<>();
        for(int i=0; i<dailys.size(); i++) {
            Entry entry = new Entry(i, dailys.get(i).getBloodSugarLevel());
            //no preference selected
            if (optionIsBeforeMeal.equalsIgnoreCase("none") && optionTimeOfMeal.equalsIgnoreCase("none")) {
                dataValues.add(entry);
                //no time of meal selected
            } else if (optionTimeOfMeal.equalsIgnoreCase("none") && optionIsBeforeMeal.equalsIgnoreCase("Before meal") &&
                    dailys.get(i).isBeforeMeal()) {
                dataValues.add(entry);
            } else if (optionTimeOfMeal.equalsIgnoreCase("none") && optionIsBeforeMeal.equalsIgnoreCase("After meal") &&
                    !dailys.get(i).isBeforeMeal()) {
                dataValues.add(entry);
                //no isBefore meal selected
            } else if (optionIsBeforeMeal.equalsIgnoreCase("none") && optionTimeOfMeal.equalsIgnoreCase("Breakfast") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("breakfast")) {
                dataValues.add(entry);
            } else if (optionIsBeforeMeal.equalsIgnoreCase("none") && optionTimeOfMeal.equalsIgnoreCase("Lunch") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("Lunch")) {
                dataValues.add(entry);
            } else if (optionIsBeforeMeal.equalsIgnoreCase("none") && optionTimeOfMeal.equalsIgnoreCase("Dinner") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("Dinner")) {
                dataValues.add(entry);
                //Before meal selected and breakfast
            } else if (optionIsBeforeMeal.equalsIgnoreCase("Before meal") && dailys.get(i).isBeforeMeal() &&
                    optionTimeOfMeal.equalsIgnoreCase("Breakfast") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("breakfast")) {
                dataValues.add(entry);
                //before meal lunch
            } else if (optionIsBeforeMeal.equalsIgnoreCase("Before meal") && dailys.get(i).isBeforeMeal() &&
                    optionTimeOfMeal.equalsIgnoreCase("lunch") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("lunch")) {
                dataValues.add(entry);
                //before meal dinner
            }else if (optionIsBeforeMeal.equalsIgnoreCase("Before meal") && dailys.get(i).isBeforeMeal() &&
                    optionTimeOfMeal.equalsIgnoreCase("dinner") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("dinner")) {
                dataValues.add(entry);
                //after meal breakfast
            } else if (optionIsBeforeMeal.equalsIgnoreCase("After meal") && !dailys.get(i).isBeforeMeal() &&
                    optionTimeOfMeal.equalsIgnoreCase("Breakfast") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("breakfast")) {
                dataValues.add(entry);
                //after meal lunch
            } else if (optionIsBeforeMeal.equalsIgnoreCase("After meal") && !dailys.get(i).isBeforeMeal() &&
                    optionTimeOfMeal.equalsIgnoreCase("lunch") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("lunch")) {
                dataValues.add(entry);
                //after meal dinner
            }else if (optionIsBeforeMeal.equalsIgnoreCase("After meal") && !dailys.get(i).isBeforeMeal() &&
                    optionTimeOfMeal.equalsIgnoreCase("dinner") &&
                    dailys.get(i).getTimeofTheDay().equalsIgnoreCase("dinner")) {
                dataValues.add(entry);
            }
        }

        for(int i=0; i<dataValues.size(); i++) {

            if(dataValues.get(i).getY()<70) {
                colors.add(getResources().getColor(R.color.red));
            } else if (dataValues.get(i).getY() >= 70 &&  dataValues.get(i).getY() < 180) {
                colors.add(getResources().getColor(R.color.green));
            } else {
                colors.add(getResources().getColor(R.color.yellow));
            }
        }
        return dataValues;
    }
}
