package com.example.study_application;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.FileWriter;
import java.io.IOException;

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


        taskName = findViewById(R.id.TaskName);
        taskSpecification = findViewById(R.id.TaskSpecfication);
        createTask = findViewById(R.id.createTask);
        ReadTxt = findViewById(R.id.readTxt);

        createTask.setOnClickListener(v -> saveFile(fileTasks, taskName.getText().toString()));

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

    public void saveFile(String  file,String text){
        try {
            File root = new File(Environment.getExternalStorageDirectory(),"My Folder");

            if(!root.exists()){
                root.mkdir();
            }

            File filepath = new File(root, file);

            FileWriter writer = new FileWriter(filepath);
            writer.append(text);
            writer.flush();
            writer.close();

        } catch (IOException e){







        }
        try {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
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