package com.example.healthapp;

import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healthapp.classes.DailyTreatment;
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
import java.util.concurrent.TimeUnit;


public class GlycemicGraphActivity extends AppCompatActivity {

    LineChart mpLineChart;
    final List<DailyTreatment> dailys = new ArrayList<DailyTreatment>();
     LineDataSet lineDataSet = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glycemic_graph);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference(FirebaseUtil.currentFirebaseUser.getUid()+"/daily_treatments");

       getValues(myRef);
    }

    private void getValues(DatabaseReference myRef ) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DailyTreatment dailyTreatment = postSnapshot.getValue(DailyTreatment.class);
                    dailys.add(dailyTreatment);
                }

                mpLineChart = findViewById(R.id.chart);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                lineDataSet = new LineDataSet(dataValues1(), "DAta set 1");
                dataSets.add(lineDataSet);

                LineData data = new LineData(dataSets);


                mpLineChart.setData(data);
                mpLineChart.invalidate();

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("tmz", "Failed to read value.", error.toException());
            }
        });

    }


    private ArrayList<Entry> dataValues1()   {

        ArrayList<Entry> dataValues = new ArrayList<>();
        for(int i=0; i<dailys.size(); i++) {
            Entry entry = new Entry(i, dailys.get(i).getBloodSugarLevel());
            dataValues.add(entry);
        }
        return dataValues;
    }
}
