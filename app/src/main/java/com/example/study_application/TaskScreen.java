package com.example.study_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TaskScreen extends AppCompatActivity {

    Button startTimerButton, stopTimerButton;
    ProgressBar timerBar;
    TextView timeBarText;
    private long TimeLeft;
    private CountDownTimer countDownTimer;

    Bundle intent;
    String Data;
    String[] fileData;

    String taskNames,taskCompletions,taskSpecification, taskTimes,taskPosition;

    int Time, originalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);

        Data = readFile("TaskNames.txt");
        fileData = Data.split("\n");

        intent = getIntent().getExtras();
        taskTimes = intent.getString(ContentPoppupScreen.EXTRA_STRING_TIME);
        taskNames = intent.getString(ContentPoppupScreen.EXTRA_STRING_NAME);
        taskSpecification = intent.getString(ContentPoppupScreen.EXTRA_STRING_SPECIFICATIONS);
        taskCompletions = intent.getString(ContentPoppupScreen.EXTRA_STRING_COMPLETION);
        taskPosition = intent.getString(ContentPoppupScreen.EXTRA_STRING_POSITION);

        Time = Integer.parseInt(taskTimes);

        TimeLeft = Time * 1000;
        originalTime = (int) TimeLeft;

        timerBar = findViewById(R.id.timerBar);
        startTimerButton = findViewById(R.id.StartTimer);
        timeBarText = findViewById(R.id.timeBarText);
        stopTimerButton = findViewById(R.id.StopTimer);

        startTimerButton.setOnClickListener(v -> {
            startTimerButton.setVisibility(View.GONE);
            stopTimerButton.setVisibility(View.VISIBLE);
            startTimer();
        });

        stopTimerButton.setOnClickListener(v -> {
            startTimerButton.setVisibility(View.VISIBLE);
            stopTimerButton.setVisibility(View.GONE);
            stopTimer();
        });
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(TimeLeft , 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                TimeLeft = leftTimeInMilliseconds;
                updateCountDownText();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                if (timeBarText.getText().equals("00:00")) {
                    timeBarText.setText("STOP");
                    String textNameDataOld = taskPosition + " " + taskNames + " " + taskCompletions + " " + taskTimes;
                    String textNameDataNew = taskPosition + " " + taskNames + " " + "Completed" + " " + 0;
                    replaceLines(textNameDataOld,textNameDataNew);
                }
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (TimeLeft/1000) / 60;
        int seconds = (int) (TimeLeft/1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeBarText.setText(timeLeftFormatted);
    }

    private void stopTimer(){
        countDownTimer.cancel();
        //save the time into file
    }


    private void replaceLines(String oldFileLine, String newFileLine) {

        try {
            FileOutputStream fos = openFileOutput("TaskNames.txt", Context.MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> fileContent = Arrays.asList(fileData);

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldFileLine)) {
                System.out.println("works");
                System.out.println(oldFileLine);
                fileContent.set(i, newFileLine);
                break;
            }
        }
        System.out.println(fileContent);

        for (int i =0; i<fileContent.size(); i++){
            write("TaskNames.txt", fileContent.get(i));
            write("TaskNames.txt", "\n");
        }

        //Files.write(Paths.get("TaskNames.txt"), fileContent, StandardCharsets.UTF_8);
        //("TaskNames.txt",fileContent);
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
}