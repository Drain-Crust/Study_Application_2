package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.util.Arrays;

public class ContentPoppupScreen extends AppCompatActivity {
    public static final String EXTRA_STRING_ID = "package com.example.study_application";

    String[][] TextBodyData;
    String[][] TextNameData;
    String[] valueSpecificationData;
    String[] valueNameData;

    String IdName, taskCompletion, timeRequired, taskName;
    String IdSpecifications, TaskSpecification;

    String textReadFile;

    String Names, Specifications, Completion, Times, numberString;

    TextView taskNames, taskSpecification, taskCompletions, taskTimes;
    Button StartTask;

    Intent intent1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_poppup_screen);


        ReadTaskNameData("TaskNames.txt", true);
        ReadTaskNameData("TaskSpecifications.txt", false);

        StartTask = findViewById(R.id.StartTask);
        taskNames = findViewById(R.id.taskName);
        taskSpecification = findViewById(R.id.taskSpecifications);
        taskCompletions = findViewById(R.id.taskCompletion);
        taskTimes = findViewById(R.id.taskTime);
        intent1 = new Intent(this, TaskScreen.class);

        intent = getIntent();
        String number = intent.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER);
        int actualNumber = Integer.parseInt(number);

        taskNames.setText(TextNameData[actualNumber][1]);
        taskSpecification.setText(TextBodyData[actualNumber][1]);
        taskCompletions.setText(TextNameData[actualNumber][2]);
        taskTimes.setText(TextNameData[actualNumber][3]);

        Names = TextNameData[actualNumber][1];
        Specifications = TextBodyData[actualNumber][1];
        Completion = TextNameData[actualNumber][2];
        Times = TextNameData[actualNumber][3];
        numberString = Integer.toString(actualNumber);

        StartTask.setOnClickListener(v -> sendData());

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void sendData() {
        intent1.putExtra(EXTRA_STRING_ID, intent.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER));
        startActivity(intent1);
    }

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

    public String readFile(String file) {
        textReadFile = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            textReadFile = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textReadFile;
    }
}