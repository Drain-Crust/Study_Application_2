package com.example.study_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TaskScreen extends AppCompatActivity {

    Button startTimerButton, stopTimerButton;
    ProgressBar timerBar;
    TextView timeBarText;
    private long TimeLeft;
    private CountDownTimer countDownTimer;

    String[][] TextBodyData;
    String[][] TextNameData;
    String[] valueSpecificationData;
    String[] valueNameData;

    String IdName, taskCompletion, timeRequired, taskName;
    String IdSpecifications, TaskSpecification;

    Intent lastPageInformation;
    String Data;
    String[] fileData;

    String taskNames, taskCompletions, taskSpecification, taskTimes, taskPosition;

    int Time;
    Intent HomeScreen;

    int actualNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);

        HomeScreen = new Intent(this, HomeScreen.class);
        lastPageInformation = getIntent();
        String number = lastPageInformation.getStringExtra(ContentPopupScreen.EXTRA_STRING_ID);
        actualNumber = Integer.parseInt(number);

        fileDataInformation();

        Time = Integer.parseInt(taskTimes);
        // time multiplied by 1000 as without it you cant get the specific minutes
        TimeLeft = Time * 1000;

        timerBar = findViewById(R.id.timerBar);
        startTimerButton = findViewById(R.id.StartTimer);
        timeBarText = findViewById(R.id.timeBarText);
        stopTimerButton = findViewById(R.id.StopTimer);

        //starts timer if pressed and makes it disappear
        startTimerButton.setOnClickListener(v -> {
            startTimerButton.setVisibility(View.GONE);
            stopTimerButton.setVisibility(View.VISIBLE);
            startTimer();
        });

        //appears after startTimerButton has been pressed
        stopTimerButton.setOnClickListener(v -> {
            startTimerButton.setVisibility(View.VISIBLE);
            stopTimerButton.setVisibility(View.GONE);
            stopTimer();
        });
    }


    private void startTimer() {
        //creates new count down timer
        countDownTimer = new CountDownTimer(TimeLeft, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                TimeLeft = leftTimeInMilliseconds;
                updateCountDownText();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                if (timeBarText.getText().equals("00:00")) {
                    timeBarText.setText("STOP");
                    fileDataInformation();

                    //saves the new task data to the file
                    String textNameDataOld = taskPosition + " " + taskNames + " " + taskCompletions + " " + taskTimes;
                    String textNameDataNew = taskPosition + " " + taskNames + " " + "Completed" + " " + 0;
                    replaceLines(textNameDataOld, textNameDataNew);
                    //starts next screen
                    startActivity(HomeScreen);
                }
            }
        }.start();
    }


    private void updateCountDownText() {
        int minutes = (int) (TimeLeft / 1000) / 60;
        int seconds = (int) (TimeLeft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeBarText.setText(timeLeftFormatted);
    }


    private void stopTimer() {
        fileDataInformation();

        String textNameDataOld = taskPosition + " " + taskNames + " " + taskCompletions + " " + taskTimes;
        String textNameDataNew = taskPosition + " " + taskNames + " " + "Uncompleted" + " " + (TimeLeft/1000);
        replaceLines(textNameDataOld, textNameDataNew);
        countDownTimer.cancel();
        //save the time into file
    }

    private void fileDataInformation(){
        Data = "";
        fileData = new String[0];
        Data = readFile("TaskNames.txt");
        fileData = Data.split("\n");

        ReadTaskNameData("TaskNames.txt", true);
        ReadTaskNameData("TaskSpecifications.txt", false);
        taskTimes = TextNameData[actualNumber][3];
        taskNames = TextNameData[actualNumber][1];
        taskSpecification = TextBodyData[actualNumber][1];
        taskCompletions = TextNameData[actualNumber][2];
        taskPosition = Integer.toString(actualNumber);
    }

    //used to find
    private void replaceLines(String oldFileLine, String newFileLine) {
        try {
            FileOutputStream fos = openFileOutput("TaskNames.txt", Context.MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> fileContent = Arrays.asList(fileData);
        //finds specific line inside txt file then replaces the file and puts back
        // into the list which then the list is pasted back inside the txt file
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldFileLine)) {
                fileContent.set(i, newFileLine);
                break;
            }
        }

        for (int i = 0; i < fileContent.size(); i++) {
            write("TaskNames.txt", fileContent.get(i));
            write("TaskNames.txt", "\n");
        }
    }

    //code already explained
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

    //code already explained
    public void write(String file, String textData) {
        try {
            FileOutputStream fos = openFileOutput(file, Context.MODE_APPEND);
            fos.write(textData.getBytes());
            fos.close();
            Toast.makeText(this, "saving file successful", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    //code already explained
    public void ReadTaskNameData(String file, Boolean TextOrBody) {
        String fileData = readFile(file);
        String[] DataString = fileData.split("\n");

        String StringArray = Arrays.toString(DataString);
        String[] StringArrays = StringArray.split(",");

        if (!TextOrBody) {
            TextBodyData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                IdSpecifications = values[0];
                TaskSpecification = values[1];

                valueSpecificationData = new String[]{IdSpecifications, TaskSpecification};

                TextBodyData[i] = valueSpecificationData;
            }
        } else {
            TextNameData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                IdName = values[0];
                taskName = values[1];
                taskCompletion = values[2];
                timeRequired = values[3];

                valueNameData = new String[]{IdName, taskName, taskCompletion, timeRequired};

                TextNameData[i] = valueNameData;
            }
        }
    }

    public void onBackPressed(){
        startTimerButton.setVisibility(View.VISIBLE);
        stopTimerButton.setVisibility(View.GONE);
        stopTimer();
        super.onBackPressed();
    }
}