package com.example.study_application;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS;

public class HomeScreen extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //values used to determine the ratio of the pie chart
    int NotStarted = 0;
    int Uncompleted = 0;
    int Completed = 0;

    //the different widgets used
    private DrawerLayout drawer;
    Button task_create;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView recyclerView;
    PieChart pieChart;

    //the arrays used to get file information and store it
    String[] valueNameData;
    String[][] fileDataArray;

    //vars
    private final ArrayList<String> mNames = new ArrayList<>();
    List<PieEntry> pieEntries = new ArrayList<>();

    //placeholder data
    float[] yData = {36.5f, 42.4f, 22.3f};
    String[] xData = {"Not Started", "Uncompleted", "Completed"};

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ReadData("TaskNames.txt");

        toolbar = findViewById(R.id.toolBar);
        task_create = findViewById(R.id.task_create);
        drawer = findViewById(R.id.navigation_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.recyclerView);
        pieChart = findViewById(R.id.pieCharts);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        addDataSet();
        initRecyclerView();
        navigationView.bringToFront();
    }


    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
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
    }


    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.bringToFront();
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(HomeScreen.this, mNames);
        recyclerView.setAdapter(adapter);
    }


    public void ReadData(String file) {
        String fileData = readFile(file);
        String[] DataString = fileData.split("\n");
        fileDataArray = new String[DataString.length][];


        for (int i = 1; i < DataString.length; i++) {
            String[] values = DataString[i].split(" ");

            String ID = values[0];
            String TaskName = values[1];
            String TaskCompletion = values[2];
            String TimeRequired = values[3];

            valueNameData = new String[]{ID, TaskName, TaskCompletion, TimeRequired};
            fileDataArray[i] = valueNameData;
        }

        for (int i = 1; i < fileDataArray.length; i++) {
            switch (fileDataArray[i][2]) {
                case "not_started":
                    NotStarted += 1;
                    break;
                case "Uncompleted":
                    Uncompleted += 1;
                    break;
                case "Completed":
                    Completed += 1;
                    break;
            }
            mNames.add(fileDataArray[i][1]);
        }
        yData = new float[]{NotStarted, Uncompleted, Completed};
    }


    public String readFile(String file) {
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
        return text;
    }

    public void onClickCreateTask(View v) {
        Intent intent = new Intent(getApplicationContext(), TaskCreateScreen.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        System.out.println("working not");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void addDataSet() {
        for (int i = 0; i < yData.length; i++) {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        PieData pieData = new PieData(pieDataSet);

        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }
}