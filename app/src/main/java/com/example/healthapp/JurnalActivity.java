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

public class JurnalActivity extends AppCompatActivity {

    LineChart mpLineChart;
    final List<DailyTreatment> dailys = new ArrayList<DailyTreatment>();
    LineDataSet lineDataSet1 = null;
    LineDataSet lineDataSet2 = null;
    LineDataSet lineDataSet3 = null;
    ArrayList<Integer> colors = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal);

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
                    if(dailys.size()<30)
                        dailys.add(dailyTreatment);
                }

                mpLineChart = findViewById(R.id.chart);

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                lineDataSet1 = new LineDataSet(dataValues1(), "Glicemii");
                lineDataSet2 = new LineDataSet(dataValues2(), "doze");
                lineDataSet3 = new LineDataSet(dataValues3(), "carbs");
                lineDataSet1.setColor(getResources().getColor(R.color.yellow));
                lineDataSet2.setColor(getResources().getColor(R.color.red));
                lineDataSet3.setColor(getResources().getColor(R.color.blueish));
                lineDataSet1.setLineWidth(20);
                lineDataSet2.setLineWidth(20);
                lineDataSet3.setLineWidth(20);
                dataSets.add(lineDataSet1);
                dataSets.add(lineDataSet2);
                dataSets.add(lineDataSet3);

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
        }

        for(int i=0; i<dataValues.size(); i++) {

                colors.add(getResources().getColor(R.color.yellow));
        }
        return dataValues;
    }

    private ArrayList<Entry> dataValues2()   {
        ArrayList<Entry> dataValues = new ArrayList<>();

        for(int i=0; i<dailys.size(); i++) {
            Entry entry = new Entry(i, (float)dailys.get(i).getNrOfShots()*5);
            dataValues.add(entry);
        }

        for(int i=0; i<dataValues.size(); i++) {

            colors.add(getResources().getColor(R.color.green));
        }
        return dataValues;
    }

    private ArrayList<Entry> dataValues3()   {
        ArrayList<Entry> dataValues = new ArrayList<>();

        for(int i=0; i<dailys.size(); i++) {
            Entry entry = new Entry(i, (float)dailys.get(i).getNrOfCarbs());
            dataValues.add(entry);
        }

        for(int i=0; i<dataValues.size(); i++) {

            colors.add(getResources().getColor(R.color.green));
        }
        return dataValues;
    }

}
