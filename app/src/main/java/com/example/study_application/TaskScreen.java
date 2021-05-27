package com.example.study_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TaskScreen extends AppCompatActivity {
    private int progres = 0;

    Button increase,decrease;
    ProgressBar timerBar;
    TextView timeBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);

        increase = findViewById(R.id.buttonIncrease);
        decrease = findViewById(R.id.buttonDecrease);

        increase.setOnClickListener(v -> {
            if (progres <=90){
                progres += 10;
                updateProgressBar();
            }
        });

        decrease.setOnClickListener(v -> {
            if (progres >=10){
                progres -= 10;
                updateProgressBar();
            }
        });
    }

    private void updateProgressBar(){
        Intrinsics.checkNotNullExpressionValue(timerBar, "progress_bar");
        timerBar.setProgress(this.progres);
        Intrinsics.checkNotNullExpressionValue(timeBarText, "text_view_progress");
        timeBarText.setText((CharSequence)("" + this.progres + '%'));
    }
}