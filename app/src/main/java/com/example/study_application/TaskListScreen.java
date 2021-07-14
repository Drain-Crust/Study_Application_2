package com.example.study_application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TaskListScreen extends AppCompatActivity {

    private RecyclerView recyclerViewTasks;
    private RecyclerViewTasksAdapter taskAdapter;
    private Button createTask;
    private Button deleteTask;
    private Button confirmDeletion;
    private Button cancelDeletion;

    //vars
    private List<TasksList> tasksListList = new ArrayList<>();
    private List<TasksList> selectedItems = new ArrayList<>();
    private String[][] fileDataArray;
    private String[][] DataStringSpecifications;

    private ReadAndWrite readAndWrite;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_screen);

        readAndWrite = new ReadAndWrite(TaskListScreen.this);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        createTask = findViewById(R.id.createTaskTaskList);
        deleteTask = findViewById(R.id.deleteButton);
        confirmDeletion = findViewById(R.id.confirmDeletionButton);
        cancelDeletion = findViewById(R.id.cancelButton);
        Toolbar toolBar = findViewById(R.id.toolBar);
        drawer = findViewById(R.id.navigation_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        EditText searchFilter = findViewById(R.id.searchTextView);

        //gets data from file and displays it in the recyclerview
        initData();
        initRecyclerView();

        //checks if any item was clicked on the navigation view.
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        //checks if the drawer button has been clicked
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();

        //used to check if the EditText area has changed.
        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main_page:
                Intent toHomeScreen = new Intent(TaskListScreen.this, HomeScreen.class);
                startActivity(toHomeScreen);
                break;
            case R.id.nav_message:
                Intent toTaskListScreen = new Intent(TaskListScreen.this, TaskListScreen.class);
                startActivity(toTaskListScreen);
                break;
            case R.id.nav_logout:
                Intent toSignInScreen = new Intent(TaskListScreen.this, MainActivity.class);
                startActivity(toSignInScreen);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createButton(View v) {
        createButton();
    }

    private void createButton() {
        createTask.setEnabled(false);
        Intent intent = new Intent(this, TaskCreateScreen.class);
        startActivity(intent);
    }

    public void deleteButton(View v) {
        deleteButton();
    }

    private void deleteButton() {
        createTask.setEnabled(false);
        createTask.setVisibility(View.INVISIBLE);
        deleteTask.setEnabled(false);
        deleteTask.setVisibility(View.INVISIBLE);
        confirmDeletion.setVisibility(View.VISIBLE);
        cancelDeletion.setVisibility(View.VISIBLE);

        RecyclerViewTasksAdapter.deletingTasks(true);
        taskAdapter.notifyDataSetChanged();

        confirmDeletion.setOnClickListener(v12 -> {
            createTask.setEnabled(true);
            createTask.setVisibility(View.VISIBLE);
            deleteTask.setEnabled(true);
            deleteTask.setVisibility(View.VISIBLE);

            confirmDeletion.setVisibility(View.INVISIBLE);
            cancelDeletion.setVisibility(View.INVISIBLE);

            RecyclerViewTasksAdapter.deletingTasks(false);

            selectedItems = taskAdapter.getSelectedItems();
            System.out.println(selectedItems);

            fileDataArray = readAndWrite.readTaskNameData("TaskNames.txt", true);
            DataStringSpecifications = readAndWrite.readTaskNameData("TaskSpecifications.txt", false);

            for (TasksList i : selectedItems) {
                for (int e = 1; e < fileDataArray.length; e++) {
                    if (i.getIDs().equals(fileDataArray[e][0])) {
                        String new_line_Name = "", new_line_Body = "";
                        String old_line_Name = i.getIDs() + " " + i.getTitle() + " " + i.getStatus() + " " + fileDataArray[e][3];
                        String old_line_Body = i.getIDs() + " " + i.getSpecifications();
                        readAndWrite.replaceLines(old_line_Name, new_line_Name, "TaskNames.txt");
                        readAndWrite.replaceLines(old_line_Body, new_line_Body, "TaskSpecifications.txt");
                    }
                }
            }

            taskAdapter.notifyDataSetChanged();
            onResume();
        });

        cancelDeletion.setOnClickListener(v1 -> {
            createTask.setEnabled(true);
            createTask.setVisibility(View.VISIBLE);
            deleteTask.setEnabled(true);
            deleteTask.setVisibility(View.VISIBLE);

            confirmDeletion.setVisibility(View.INVISIBLE);
            cancelDeletion.setVisibility(View.INVISIBLE);

            RecyclerViewTasksAdapter.deletingTasks(false);
            taskAdapter.notifyDataSetChanged();
        });
    }

    //this filter creates a new arraylist using the original
    // arraylist by filtering out the items through the text view
    private void filter(String text) {
        ArrayList<TasksList> filteredList = new ArrayList<>();
        //for loop to check through all the items inside the original arraylist
        for (TasksList item : tasksListList) {

            //adds the item to the new arraylist
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        //applies the new filtered list to the adapter.
        taskAdapter.filterList(filteredList);
    }

    private void initRecyclerView() {
        taskAdapter = new RecyclerViewTasksAdapter(tasksListList, this);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void initData() {
        tasksListList = new ArrayList<>();
        fileDataArray = readAndWrite.readTaskNameData("TaskNames.txt", true);
        DataStringSpecifications = readAndWrite.readTaskNameData("TaskSpecifications.txt", false);

        for (int i = 1; i < fileDataArray.length; i++) {
            tasksListList.add(new TasksList(fileDataArray[i][0], fileDataArray[i][1], fileDataArray[i][2], DataStringSpecifications[i][1]));
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            FirebaseAuth.getInstance().signOut();
        } else {
            super.onBackPressed();
        }
    }

    //updates the recyclerview
    protected void onResume() {
        createTask.setEnabled(true);
        fileDataArray = new String[0][0];
        initData();
        initRecyclerView();
        super.onResume();
    }
}