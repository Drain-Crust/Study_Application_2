package com.example.study_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TaskListScreen extends AppCompatActivity {


    RecyclerView recyclerViewTasks;

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
    }

    private void initRecyclerView() {
        RecyclerViewTasksAdapter taskAdapter = new RecyclerViewTasksAdapter(tasksListList);
        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);
    }

    private void initData(){
        tasksListList = new ArrayList<>();
        ReadData("TaskNames.txt");
        Specfications = readFile("TaskSpecifications.txt");
        String[] DataStringSpecfications = Specfications.split("\n");
        SpecficationsDataArray = new String[DataStringSpecfications.length][];

        for (int i = 1; i < DataStringSpecfications.length; i++) {
            String[] valuesSpecifications = DataStringSpecfications[i].split(" ");
            tasksListList.add(new TasksList(fileDataArray[i][1],fileDataArray[i][2],valuesSpecifications[1]));
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