package com.example.study_application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TaskScreen extends AppCompatActivity {

    Button startTimerButton, stopTimerButton;
    ProgressBar timerBar;
    TextView timeBarText;
    private long TimeLeft;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TimeLeft = 10 * 1000;

        setContentView(R.layout.activity_task_screen);
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

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                if (timeBarText.getText().equals("00:00")) {
                    timeBarText.setText("STOP");
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
}