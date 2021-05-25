package com.example.study_application;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS;

public class HomeScreen extends AppCompatActivity {
    Dialog myDialog;
    private DrawerLayout drawer;

    Button task_create;

    float[] yData = {36.5f,42.4f,22.3f};
    String[] xData = {"","",""};

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolBar);
        task_create = findViewById(R.id.task_create);

        drawer = findViewById(R.id.navigation_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_message:
                    System.out.println("working 1");
                    Intent intent_1 = new Intent(HomeScreen.this, TaskScreen.class);
                    startActivity(intent_1);
                    break;

                case R.id.nav_progress:
                    System.out.println("working 2");
                    Intent intent_2 = new Intent(HomeScreen.this, PlantScreen.class);
                    startActivity(intent_2);
                    break;

                case R.id.nav_tasks:
                    System.out.println("working 3");
                    Intent intent_3 = new Intent(HomeScreen.this, MenuScreen.class);
                    startActivity(intent_3);
                    break;
            }
            System.out.println(item.getItemId());
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        addDataSet();

    }


    public void onClick(View v){
        Intent intent = new Intent(getApplicationContext(), TaskCreateScreen.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        System.out.println("working not");
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    private void addDataSet(){
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i=0; i< yData.length; i++) {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"");
        pieDataSet.setColors(COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);


        PieData pieData = new PieData(pieDataSet);

        PieChart pieChart = findViewById(R.id.pieCharts);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }
}