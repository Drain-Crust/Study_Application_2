package com.example.study_application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TaskScreen extends AppCompatActivity {

    private Button startTimerButton, stopTimerButton;
    private ProgressBar timerBar;
    private TextView timeBarText;

    private Boolean BreakTimerRunning = false;
    private Boolean countdownTimeRunning = false;

    private String taskNames;
    private String taskCompletions;
    private String taskTimes;
    private String taskPosition;

    private long TimeLeft;
    private CountDownTimer countDownTimer;
    private CountDownTimer countBreakTimer;
    private int actualNumber;
    private long BreakTimeLeft;

    Intent HomeScreen;
    ReadAndWrite readAndWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);

        readAndWrite = new ReadAndWrite(TaskScreen.this);

        timerBar = findViewById(R.id.timerBar); // haven't finished time bar
        startTimerButton = findViewById(R.id.StartTimer);
        timeBarText = findViewById(R.id.timeBarText);
        stopTimerButton = findViewById(R.id.StopTimer);

        HomeScreen = new Intent(this, HomeScreen.class);
        Intent lastPageInformation = getIntent();
        String number = lastPageInformation.getStringExtra(ContentPopupScreen.EXTRA_STRING_ID);
        actualNumber = Integer.parseInt(number);

        fileDataInformation();

        int time = Integer.parseInt(taskTimes);
        // time multiplied by 1000 as without it you cant get the specific minutes
        TimeLeft = time * 1000;

        updateCountDownText();

        //starts timer if pressed and makes it disappear
        startTimerButton.setOnClickListener(v -> {
            countdownTimeRunning = true;
            startTimerButton.setVisibility(View.INVISIBLE);
            stopTimerButton.setVisibility(View.VISIBLE);
            if (TimeLeft > 1500000) {
                BreakTimerRunning = true;
                startBreakTimer();
            }
            startTimer();
        });

        //appears after startTimerButton has been pressed
        stopTimerButton.setOnClickListener(v -> stopTimer());
    }

    private void startTimer() {
        //creates new count down timer
        countDownTimer = new CountDownTimer(TimeLeft, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                TimeLeft = leftTimeInMilliseconds;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                if (timeBarText.getText().equals("00:00")) {
                    fileDataInformation();

                    //saves the new task data to the file
                    String textNameDataOld = taskPosition + " " + taskNames + " " + taskCompletions + " " + taskTimes;
                    String textNameDataNew = taskPosition + " " + taskNames + " " + "Completed" + " " + 0;
                    readAndWrite.replaceLines(textNameDataOld, textNameDataNew, "TaskNames.txt");
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
        startTimerButton.setVisibility(View.VISIBLE);
        stopTimerButton.setVisibility(View.INVISIBLE);
        fileDataInformation();

        if (BreakTimerRunning){
            BreakTimerRunning = false;
            countBreakTimer.cancel();
            String textNameDataOld = taskPosition + " " + taskNames + " " + taskCompletions + " " + taskTimes;
            String textNameDataNew = taskPosition + " " + taskNames + " " + "Uncompleted" + " " + (TimeLeft / 1000);
            readAndWrite.replaceLines(textNameDataOld, textNameDataNew, "TaskNames.txt");
        }

        if (countdownTimeRunning){
            countdownTimeRunning = false;
            countDownTimer.cancel();
        }
    }

    @SuppressLint("SetTextI18n")
    private void fileDataInformation() {
        String[][] textNameData = readAndWrite.readTaskNameData("TaskNames.txt", true);

        taskTimes = textNameData[actualNumber][3];
        taskNames = textNameData[actualNumber][1];
        taskCompletions = textNameData[actualNumber][2];
        taskPosition = Integer.toString(actualNumber);


        if (taskCompletions.equals("Uncompleted")) {
            startTimerButton.setText("Resume");
        }
    }

    public void onBackPressed() {
        startTimerButton.setVisibility(View.VISIBLE);
        stopTimerButton.setVisibility(View.GONE);
        stopTimer();
        super.onBackPressed();
    }

    private void startBreakTimer() {
        // as we are using milliseconds i have to increase it by multiplying it by 1000 the 1500 originally is the 25 minute mark.
        BreakTimeLeft = 15000;
        //creates new count down timer
        countBreakTimer = new CountDownTimer(15000, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                BreakTimeLeft = leftTimeInMilliseconds;
            }

            @Override
            public void onFinish() {
                Intent BreakTimerScreen = new Intent(TaskScreen.this, BreakTimerScreen.class);

                stopTimer();
                //starts next screen
                startActivity(BreakTimerScreen);
            }
        }.start();
    }
}