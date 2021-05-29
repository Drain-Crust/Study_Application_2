package com.example.study_application;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;

public class TaskCreateScreen extends AppCompatActivity {
    EditText taskSpecification, taskName,taskTime;
    Button createTask;

    String fileTasks = "TaskSpecifications.txt";
    String fileNames = "TaskNames.txt";

    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        if (fileDosentExist(fileNames)) {
            saveFile(fileNames, "important", "important", "important", false);
        }

        if (fileDosentExist(fileTasks)){
            saveFile(fileTasks,"important", "important", "important", false);
        }

        taskTime = findViewById(R.id.TimeTask);
        taskName = findViewById(R.id.TaskName);
        taskSpecification = findViewById(R.id.TaskSpecfication);
        createTask = findViewById(R.id.createTask);

        createTask.setOnClickListener(v -> saveFile(fileNames, taskName.getText().toString(),
                taskSpecification.getText().toString(), taskTime.getText().toString(), false));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        getWindow().setLayout(width, (int)(height*0.7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = 0; params.y=-20;
        getWindow().setAttributes(params);
    }

    public boolean fileDosentExist(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return !file.exists();
    }

    public void saveFile(String  file,String text, String body, String TimeForTask,Boolean create){
        int id = 0;
        String textNameData = "";
        String textBodyData;
        String textName = text.replace(" ","_");
        String textBody = body.replace(" ","_");
        String typeOfCompletion;
        typeOfCompletion = "not_started";

        if (!create){
            String fileData = readFile(file);
            String[] DataString = fileData.split("\n");
            String[][] DataForLength = new String[DataString.length][];

            for (int i = 1; i < DataString.length;i++){
                String[] values = DataString[i].split(" ");

                String TaskName = values[0];
                String TaskSpecification = values[1];
                String TaskType = values[2];
                String TimeRequired = values[3];

                String[] value = {TaskName, TaskSpecification, TaskType ,TimeRequired};

                DataForLength[i]= value;
            }

            for (int i=0; i<DataForLength.length; i++){
                id = id + 1;
            }
            textNameData = id + " " + textName + " " + typeOfCompletion + " " + TimeForTask + "\n";
            textBodyData = id + " " + textBody + "\n";

            write("TaskSpecifications.txt",textBodyData);
        }
        write(file,textNameData);
    }

    public void write(String file, String textData){
        try {
            FileOutputStream fos = openFileOutput(file, Context.MODE_APPEND);
            fos.write(textData.getBytes());
            fos.close();
            Toast.makeText(this,"saving file successful",Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Error saving file",Toast.LENGTH_SHORT).show();
        }

    }

    public String readFile(String file){
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e ){
            e.printStackTrace();
            Toast.makeText(this,"Error reading file",Toast.LENGTH_SHORT).show();
        }
        return text;
    }
}