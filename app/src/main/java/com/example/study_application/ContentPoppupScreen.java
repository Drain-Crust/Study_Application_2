package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.util.Arrays;

public class ContentPoppupScreen extends AppCompatActivity {
    public static final String EXTRA_STRING_TIME = "package com.example.study_application";
    public static final String EXTRA_STRING_NAME = "package com.example.study_application's";
    public static final String EXTRA_STRING_COMPLETION = "package com.example.study_applications'";
    public static final String EXTRA_STRING_SPECIFICATIONS = "package com.example.study_applications";
    public static final String EXTRA_STRING_POSITION = "package com.example.study_applications";

    String[][] TextBodyData;
    String[][] TextNameData;
    String[] valueSpecificationData;
    String[] valueNameData;

    String IdName, taskCompletion, timeRequired, taskName;
    String IdSpecifications, TaskSpecification;

    TextView taskNames, taskSpecification, taskCompletions, taskTimes;
    Button StartTask;

    String textReadFile;

    Intent intent1;

    String Names, Specifications, Completion, Times, numberString;

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

        Intent intent = getIntent();
        int number = intent.getIntExtra(RecyclerViewAdapter.EXTRA_NUMBER, 0);

        taskNames.setText(TextNameData[number][1]);
        taskSpecification.setText(TextBodyData[number][1]);
        taskCompletions.setText(TextNameData[number][2]);
        taskTimes.setText(TextNameData[number][3]);

        Names = TextNameData[number][1];
        Specifications = TextBodyData[number][1];
        Completion = TextNameData[number][2];
        Times = TextNameData[number][3];
        numberString = Integer.toString(number);

        StartTask.setOnClickListener(v -> sendData());
    }

    public void sendData() {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_STRING_SPECIFICATIONS, Specifications);
        extras.putString(EXTRA_STRING_TIME, Times);
        extras.putString(EXTRA_STRING_NAME, Names);
        extras.putString(EXTRA_STRING_COMPLETION, Completion);
        extras.putString(EXTRA_STRING_POSITION, numberString);
        intent1.putExtras(extras);
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