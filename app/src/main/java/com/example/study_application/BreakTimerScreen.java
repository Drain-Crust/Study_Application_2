package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BreakTimerScreen extends AppCompatActivity {

    long BreakTimeLeft;
    private CountDownTimer countBreakTimer;
    TextView progressBreakBar;
    Intent TaskScreen;
    Button stopButton, startButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_break_timer_screen);
        TaskScreen = new Intent(this, TaskScreen.class);
        stopButton = findViewById(R.id.stopButton);
        startButton = findViewById(R.id.startButton);
        cancelButton = findViewById(R.id.cancelButton);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        stopButton.setVisibility(View.INVISIBLE);

        startButton.setOnClickListener(v -> {
            startButton.setVisibility(View.GONE);
            startBreakTimer();
        });

        cancelButton.setOnClickListener(v -> startActivity(TaskScreen));
    }

    private void startBreakTimer() {
        // as we are using milliseconds i have to increase it by multiplying it by 1000 the 1500 originally is the 25 minute mark.
        BreakTimeLeft = 1500000;
        //creates new count down timer
        countBreakTimer = new CountDownTimer(1500000, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                BreakTimeLeft = leftTimeInMilliseconds;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                countBreakTimer.cancel();
                progressBreakBar.setText("Break Has Ended");
                cancelButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
                stopButton.setOnClickListener(v -> startActivity(TaskScreen));
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (BreakTimeLeft / 1000) / 60;
        int seconds = (int) (BreakTimeLeft / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        progressBreakBar.setText(timeLeftFormatted);
    }
}