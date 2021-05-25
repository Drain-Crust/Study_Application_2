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

public class TaskCreateScreen extends AppCompatActivity {
    EditText taskSpecification, taskName;
    Button createTask, ReadTxt;

    String fileTasks = "TaskSpecifications.txt";
    String fileNames = "TaskNames.txt";
    String fileFinishedAndUnfinishedTasks = "FinishedAndUnfinishedTasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        saveFile(fileNames, "",true);
        saveFile(fileFinishedAndUnfinishedTasks, "", true);
        saveFile(fileTasks,"", true);

        taskName = findViewById(R.id.TaskName);
        taskSpecification = findViewById(R.id.TaskSpecfication);
        createTask = findViewById(R.id.createTask);
        ReadTxt = findViewById(R.id.readTxt);

        createTask.setOnClickListener(v -> saveFile(fileTasks, taskName.getText().toString(), false));

        ReadTxt.setOnClickListener(v -> {
            String txts = readFile(fileTasks);
            Toast.makeText(TaskCreateScreen.this, txts, Toast.LENGTH_SHORT).show();
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout(width, (int)(height*0.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = 0;
        params.y=-20;

        getWindow().setAttributes(params);
    }

    public void saveFile(String  file,String text,Boolean create){
        int id = 0;
        boolean empty;
        File files;
        String textDataNew = "";
        files = new File(file);

        if (!create){
            empty = files.exists() && files.length() == 0;

            String fileData = readFile(file);
            System.out.println(fileData);
            String[] DataString = fileData.split("\n");

            for (int i=0; i<DataString.length; i++){
                if(empty){
                    id = id;
                } else {
                    id = id + 1;
                }
                System.out.println(id);
            }




            textDataNew = id + "||" + text + "\r\n";
        }

        try {
            FileOutputStream fos = openFileOutput(file, Context.MODE_APPEND);
            fos.write(textDataNew.getBytes());
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