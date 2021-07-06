package com.example.study_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

public class BreakTimerScreen extends AppCompatActivity {

    long BreakTimeLeft;
    private CountDownTimer countBreakTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_timer_screen);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void startBreakTimer() {
        // as we are using milliseconds i have to increase it by multiplying it by 1000 the 1500 originally is the 25 minute mark.
        BreakTimeLeft = 1500000;
        //creates new count down timer
        countBreakTimer = new CountDownTimer(1500000, 500) {

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                BreakTimeLeft = leftTimeInMilliseconds;
            }

            @Override
            public void onFinish() {
                countBreakTimer.cancel();
            }
        }.start();
    }
}