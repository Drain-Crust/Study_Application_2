package com.example.study_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TaskListScreen extends AppCompatActivity {

    RecyclerView recyclerViewTasks;
    RecyclerViewTasksAdapter taskAdapter;

    //vars
    List<TasksList> tasksListList;

    String[] valueNameData;
    String[][] fileDataArray;

    String[][] SpecficationsDataArray;

    String Specfications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list_screen);

        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        initData();
        initRecyclerView();

        EditText editText = findViewById(R.id.searchTextView);

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

    public void createButton(View v){
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
        taskAdapter = new RecyclerViewTasksAdapter(tasksListList,this);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void initData() {
        tasksListList = new ArrayList<>();
        ReadData("TaskNames.txt");
        Specfications = readFile("TaskSpecifications.txt");
        String[] DataStringSpecfications = Specfications.split("\n");
        SpecficationsDataArray = new String[DataStringSpecfications.length][];

        for (int i = 1; i < DataStringSpecfications.length; i++) {
            String[] valuesSpecifications = DataStringSpecfications[i].split(" ");
            tasksListList.add(new TasksList(fileDataArray[i][0],fileDataArray[i][1], fileDataArray[i][2], valuesSpecifications[1]));
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
}