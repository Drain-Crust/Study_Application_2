package com.example.study_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TaskScreen extends AppCompatActivity {

    Button StartEndTimer;
    ProgressBar timerBar;
    TextView timeBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);
        timerBar = findViewById(R.id.timerBar);
        StartEndTimer = findViewById(R.id.StartEndTimer);
        timeBarText = findViewById(R.id.timeBarText);

        StartEndTimer.setOnClickListener(v -> startTimer(60));
    }

    public void startTimer(int minuti){
         Object countDownTimer = new CountDownTimer(60 * minuti * 1000, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                timerBar.setProgress((int) seconds);
                timeBarText.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                if (timeBarText.getText().equals("00:00")) {
                    timeBarText.setText("STOP");
                } else {
                    timeBarText.setText("2:00");
                    timerBar.setProgress(60 * minuti);
                }
            }
        }.start();
    }
}