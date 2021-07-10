package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ContentPopupScreen extends AppCompatActivity {
    public static final String EXTRA_STRING_ID = "package com.example.study_application";

    String[][] TextBodyData;
    String[][] TextNameData;

    TextView taskNames, taskSpecification, taskCompletions, taskTimes;
    Button StartTask;

    Intent intent1;
    Intent intent;
    ReadAndWrite readAndWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_poppup_screen);

        readAndWrite = new ReadAndWrite(ContentPopupScreen.this);

        //reads file data and saves it into arrays
        TextNameData = readAndWrite.ReadTaskNameData("TaskNames.txt", true);
        TextBodyData = readAndWrite.ReadTaskNameData("TaskSpecifications.txt", false);

        //finds and links the different objects within the screen
        StartTask = findViewById(R.id.StartTask);
        taskNames = findViewById(R.id.taskName);
        taskSpecification = findViewById(R.id.taskSpecifications);
        taskCompletions = findViewById(R.id.taskCompletion);
        taskTimes = findViewById(R.id.taskTime);

        //creates a link with the next screen
        intent1 = new Intent(this, TaskScreen.class);

        //gets sent data from last screen
        intent = getIntent();
        String number = intent.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER);
        int actualNumber = Integer.parseInt(number);

        //changes the initial text and puts in the text from the data in the arrays
        taskNames.setText(TextNameData[actualNumber][1].replace("_"," "));
        taskSpecification.setText(TextBodyData[actualNumber][1].replace("_"," "));
        taskCompletions.setText(TextNameData[actualNumber][2].replace("_"," "));
        taskTimes.setText(TextNameData[actualNumber][3]);

        //checks to see if the Start Task button has been pressed
        StartTask.setOnClickListener(v -> sendData());

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void sendData() {
        intent1.putExtra(EXTRA_STRING_ID, intent.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER));
        startActivity(intent1);
    }
}