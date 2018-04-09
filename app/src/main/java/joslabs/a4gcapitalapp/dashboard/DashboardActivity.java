package joslabs.a4gcapitalapp.dashboard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.Map;

import joslabs.a4gcapitalapp.R;

public class DashboardActivity extends AppCompatActivity {
HorizontalBarChart barChart;
PieChart pieChart;;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
barChart=findViewById(R.id.barchart);
pieChart=findViewById(R.id.chart);
        ArrayList<Entry>entries=new ArrayList<>();
        entries.add(new Entry(95.98f,0) );
        entries.add(new Entry(5.45f,1));

        PieDataSet dataSet=new PieDataSet(entries,"Collection vs Dues");;
        ArrayList labels=new ArrayList();;
        labels.add("collections");
        labels.add("Dues");;
        PieData pieData=new PieData(labels,dataSet);
        pieChart.setData(pieData);
        pieChart.setDescription("Collection vs Dues");



    }

}
