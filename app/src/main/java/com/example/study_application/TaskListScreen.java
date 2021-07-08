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
    Button deleteTaskTaskList;
    Button confirmationDeletionTaskList;
    Button cancelDeletionTaskList;

    //vars
    List<TasksList> tasksListList;
    List<TasksList> selectedItems;

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
        deleteTaskTaskList = findViewById(R.id.deleteButton);
        confirmationDeletionTaskList = findViewById(R.id.confirmDeletionButton);
        cancelDeletionTaskList = findViewById(R.id.cancelButton);
        EditText editText = findViewById(R.id.searchTextView);

        //gets data from file and displays it in the recyclerview
        initData();
        initRecyclerView();

        //used to check if the EditText area has changed.
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

    public void deleteButton(View v){
        createTaskTaskList.setEnabled(false);
        createTaskTaskList.setVisibility(View.INVISIBLE);
        deleteTaskTaskList.setEnabled(false);
        deleteTaskTaskList.setVisibility(View.INVISIBLE);
        confirmationDeletionTaskList.setVisibility(View.VISIBLE);
        cancelDeletionTaskList.setVisibility(View.VISIBLE);

        RecyclerViewTasksAdapter.deletingTasks(true);
        taskAdapter.notifyDataSetChanged();

        confirmationDeletionTaskList.setOnClickListener(v12 -> {
            selectedItems = taskAdapter.getSelectedItems();
            System.out.println(selectedItems);
            taskAdapter.notifyDataSetChanged();
        });

        cancelDeletionTaskList.setOnClickListener(v1 -> {
            createTaskTaskList.setEnabled(true);
            createTaskTaskList.setVisibility(View.VISIBLE);
            deleteTaskTaskList.setEnabled(true);
            deleteTaskTaskList.setVisibility(View.VISIBLE);
            confirmationDeletionTaskList.setVisibility(View.INVISIBLE);
            cancelDeletionTaskList.setVisibility(View.INVISIBLE);

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

    //updates the recyclerview
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