package com.example.study_application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TaskCreateScreen extends AppCompatActivity {
    EditText taskSpecification, taskName, taskTime;
    Button createTask;

    String fileTasks = "TaskSpecifications.txt";
    String fileNames = "TaskNames.txt";

    int width;
    int height;

    Intent intent;
    ReadAndWrite readAndWrite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        readAndWrite = new ReadAndWrite(TaskCreateScreen.this);

        //checks if file exists if not then it will create
        // a file with the first values as placeholders
        if (fileDoesNotExist(fileNames)) {
            saveFile(fileNames, "important", "important", "important", false);
        }

        if (fileDoesNotExist(fileTasks)) {
            saveFile(fileTasks, "important", "important", "important", false);
        }

        //link to next or last page
        intent = new Intent(this, HomeScreen.class);

        taskTime = findViewById(R.id.TimeTask);
        taskName = findViewById(R.id.TaskName);
        taskSpecification = findViewById(R.id.TaskSpecification);
        createTask = findViewById(R.id.createTask);

        //this code changes the size of the Activity Screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        getWindow().setLayout(width, (int) (height * 0.7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    public boolean fileDoesNotExist(String name) {
        File file = getBaseContext().getFileStreamPath(name);
        return !file.exists();
    }

    public void saveFile(String file, String text, String body, String TimeForTask, Boolean create) {
        int id = 0;
        String textNameData = "";
        String textBodyData;
        //used so that no errors will occur with different spacing as i am using
        // the space to differentiate between the different attributes of a task.
        String textName = text.replace(" ", "_");
        String textBody = body.replace(" ", "_");
        String typeOfCompletion;
        typeOfCompletion = "not_started";

        //checks if im creating a new file or adding to a file
        if (!create) {
            String[][] DataForLength;
            DataForLength = readAndWrite.ReadTaskNameData(file, true);

            for (int i = 0; i < DataForLength.length; i++) {
                id = id + 1;
            }
            // this is the layout of how its going to be saved inside the text file.
            textNameData = id + " " + textName + " " + typeOfCompletion + " " + TimeForTask + "\n";
            textBodyData = id + " " + textBody + "\n";

            readAndWrite.write("TaskSpecifications.txt", textBodyData);
        }
        readAndWrite.write(file, textNameData);
    }

    public void CreateTaskButton(View v) {
        attemptCreateTask();
    }

    //this code are the restrictions on how a task should be created
    private void attemptCreateTask() {
        View focusView = null;

        if (taskTime.getText().toString().equals("") || taskName.getText().toString().equals("") || taskSpecification.getText().toString().equals("")) {
            if (taskTime.getText().toString().equals("")) {
                taskTime.setError("Time is to short try again");
                focusView = taskTime;
            }
            if (String.valueOf(taskSpecification).equals("")) {
                taskSpecification.setError("Specification is to short try again");
                focusView = taskSpecification;
            }
            if (String.valueOf(taskName).equals("")) {
                taskName.setError("Name is to short try again");
                focusView = taskTime;
            }
            //will change depending on what the user writes inside each text box.
            assert focusView != null;
            focusView.requestFocus();
        } else {
            saveFile(fileNames, taskName.getText().toString(),
                    taskSpecification.getText().toString(), taskTime.getText().toString(), false);
            //brings user to last page
            finish();
        }
    }
}