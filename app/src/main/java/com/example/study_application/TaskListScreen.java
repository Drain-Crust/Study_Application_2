package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TaskListScreen extends AppCompatActivity {

    RecyclerView recyclerViewTasks;
    RecyclerViewTasksAdapter taskAdapter;
    Button createTaskTaskList;

    //vars
    List<TasksList> tasksListList;

    String[] valueNameData;
    String[][] fileDataArray;
    String[][] SpecificationsDataArray;

    String Specifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_screen);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);
        createTaskTaskList = findViewById(R.id.createTaskTaskList);
        EditText editText = findViewById(R.id.searchTextView);

        initData();
        initRecyclerView();

        editText.addTextChangedListener(new TextWatcher() {
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


    public void createButton(View v) {
        createTaskTaskList.setEnabled(false);
        Intent intent = new Intent(this, TaskCreateScreen.class);
        startActivity(intent);
    }


    private void filter(String text) {
        ArrayList<TasksList> filteredList = new ArrayList<>();
        for (TasksList item : tasksListList) {

            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        taskAdapter.filterList(filteredList);
    }

    private void initRecyclerView() {
        taskAdapter = new RecyclerViewTasksAdapter(tasksListList, this);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void initData() {
        tasksListList = new ArrayList<>();
        ReadData("TaskNames.txt");
        Specifications = readFile("TaskSpecifications.txt");
        String[] DataStringSpecifications = Specifications.split("\n");
        SpecificationsDataArray = new String[DataStringSpecifications.length][];

        for (int i = 1; i < DataStringSpecifications.length; i++) {
            String[] valuesSpecifications = DataStringSpecifications[i].split(" ");

            tasksListList.add(new TasksList(fileDataArray[i][0], fileDataArray[i][1], fileDataArray[i][2], valuesSpecifications[1]));
        }
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

    protected void onResume() {
        createTaskTaskList.setEnabled(true);
        valueNameData = new String[0];
        fileDataArray = new String[0][0];
        SpecificationsDataArray = new String[0][0];
        Specifications = "";
        initData();
        initRecyclerView();
        super.onResume();
    }
}