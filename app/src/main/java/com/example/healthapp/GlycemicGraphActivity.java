package com.example.healthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glycemic_graph);

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
                    dailys.add(glycemicProfile);
                }

                mpLineChart = findViewById(R.id.chart);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                lineDataSet = new LineDataSet(dataValues1(), "Data set 1");
                lineDataSet.setCircleColors(colors);
                lineDataSet.setCircleRadius(20);
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
            dataValues.add(entry);

            if(dailys.get(i).getBloodSugarLevel() < 70) {
                colors.add(getResources().getColor(R.color.red));
            } else if (dailys.get(i).getBloodSugarLevel() >= 70 &&  dailys.get(i).getBloodSugarLevel() < 180) {
                colors.add(getResources().getColor(R.color.green));
            } else {
                colors.add(getResources().getColor(R.color.yellow));
            }

        }
        return dataValues;
    }
}
