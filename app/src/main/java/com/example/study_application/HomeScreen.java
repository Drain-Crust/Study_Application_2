
package com.example.study_application;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

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
    String[][] fileDataArray;
    PieDataSet pieDataSet;
    ReadAndWrite readAndWrite;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mIds = new ArrayList<>();

    //placeholder data
    float[] yData = {36.5f, 42.4f, 22.3f};
    String[] xData = new String[]{"Not Started", "Uncompleted", "Completed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        readAndWrite = new ReadAndWrite(HomeScreen.this);

        //reads file data
        ReadData("TaskNames.txt");

        //links the objects on screen
        toolbar = findViewById(R.id.toolBar);
        task_create = findViewById(R.id.task_create);
        drawer = findViewById(R.id.navigation_layout);
        navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.recyclerView);
        pieChart = findViewById(R.id.pieCharts);

        //checks if any item was clicked on the navigation view.
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        //checks if the drawer button has been clicked
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //creates the pie graph
        addDataSet();

        //creates the recycler view items and displays them
        initRecyclerView();

        navigationView.bringToFront();
    }

    // the different options of the navigation view when an item is clicked
    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                System.out.println("working 1");
                Intent intent_1 = new Intent(HomeScreen.this, TaskListScreen.class);
                startActivity(intent_1);
                break;
            case R.id.nav_logout:
                Intent intent_4 = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(intent_4);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerView");

        //decided on how the recyclerview is going to face, vertical aor horizontal.
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        recyclerView.bringToFront();
        recyclerView.setLayoutManager(layoutManager);
        // makes the different items of the recyclerview and orders it
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(HomeScreen.this, mNames, mIds);
        //calls upon the adapter that's been created (shows the recycler view)
        recyclerView.setAdapter(adapter);
    }


    public void ReadData(String file) {
        fileDataArray = readAndWrite.ReadTaskNameData(file, true);

        //this for loop separates the different levels of completeness of task
        // this data is used to form the pie graph
        for (int i = 1; i < fileDataArray.length; i++) {
            switch (fileDataArray[i][2]) {
                case "not_started":
                    NotStarted += 1;
                    mNames.add(fileDataArray[i][1]);
                    mIds.add(fileDataArray[i][0]);
                    break;
                case "Uncompleted":
                    Uncompleted += 1;
                    mNames.add(fileDataArray[i][1]);
                    mIds.add(fileDataArray[i][0]);
                    break;
                case "Completed":
                    Completed += 1;
                    break;
            }

        }
        // yData is the data shown on the pie graph
        // while xData is the data shown beneath the pie graph such as the names
        yData = new float[]{NotStarted, Uncompleted, Completed};
    }

    public void onClickCreateTask(View v) {
        // the .setEnabled makes it so that the task create button cant be opened more than once
        task_create.setEnabled(false);
        Intent intent = new Intent(getApplicationContext(), TaskCreateScreen.class);
        startActivity(intent);
    }

    // closes the drawer instead of going to last screen if the drawer is open
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            FirebaseAuth.getInstance().signOut();
        } else {
            super.onBackPressed();
        }
    }

    public void addDataSet() {
        //puts the y and x data inside a ArrayList
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < yData.length; i++) {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        //creates another Arraylist using the values of pie entries as one of the inputs
        pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(COLORFUL_COLORS);
        pieDataSet.setSliceSpace(0);
        pieDataSet.setValueTextSize(0);

        PieData pieData = new PieData(pieDataSet);

        // disables the description of the pie graph
        pieChart.getDescription().setEnabled(false);

        //centers the the names of the different parts of the graph
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);

        pieChart.getLegend().setTextColor(Color.WHITE);
        pieChart.getLegend().setFormToTextSpace(10);
        pieChart.getLegend().setTextSize(12);

        //enables it so that there is text underneath the pie Graph
        pieChart.getLegend().setEnabled(true);
        pieChart.setTouchEnabled(false);
        //disables the text to be within the graph
        pieChart.setDrawEntryLabels(false);
        //shows the pie graph
        pieChart.setData(pieData);
        //reloads the pie graph data
        pieChart.invalidate();
    }

    //an animation that happens when the user clicks onto a new screen calling the finish method inside
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    //code to update the recyclerview and graph data
    @Override
    protected void onResume() {
        super.onResume();
        //resets all the values to original
        NotStarted = 0;
        Uncompleted = 0;
        Completed = 0;
        yData = new float[0];
        mIds = new ArrayList<>();
        mNames = new ArrayList<>();
        task_create.setEnabled(true);
        fileDataArray = new String[0][0];

        ReadData("TaskNames.txt");
        addDataSet();
        initRecyclerView();
        navigationView.bringToFront();
    }
}
