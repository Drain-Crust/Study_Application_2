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
    public static final String EXTRA_STRING_NAME = "package com.example.study_application";
    public static final String EXTRA_STRING_COMPLETION = "package com.example.study_application";
    public static final String EXTRA_STRING_SPECIFICATIONS = "package com.example.study_application";

    String[][] TextBodyData;
    String[][] TextNameData;
    String TaskName, TaskSpecifications, TaskCompletion, TaskTime;

    Button StartTask;

    TextView taskNames,taskSpecification,taskCompletions,taskTimes;


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

        Intent intent = getIntent();
        int number = intent.getIntExtra(RecyclerViewAdapter.EXTRA_NUMBER, 0);
        System.out.print(TextNameData[number][1]);
        System.out.print(TextBodyData[number][1]);
        System.out.print(TextNameData[number][2]);
        System.out.print(TextNameData[number][3]);

        TaskName = TextNameData[number][1];
        TaskSpecifications = TextBodyData[number][1];
        TaskCompletion = TextNameData[number][2];
        TaskTime = TextNameData[number][3];

        taskNames.setText(TaskName);
        taskSpecification.setText(TaskSpecifications);
        taskCompletions.setText(TaskCompletion);
        taskTimes.setText(TaskTime);

        StartTask.setOnClickListener(v -> {
            Intent intent1 = new Intent(ContentPoppupScreen.this, TaskScreen.class);
            intent1.putExtra(EXTRA_STRING_TIME, TaskTime);
            intent1.putExtra(EXTRA_STRING_NAME, TaskName);
            intent1.putExtra(EXTRA_STRING_COMPLETION, TaskCompletion);
            intent1.putExtra(EXTRA_STRING_SPECIFICATIONS, TaskSpecifications);
            startActivity(intent1);
        });
    }

    public void ReadTaskNameData(String file, Boolean TextOrBody) {
        String fileData = readFile(file);
        String[] DataString = fileData.split("\n");

        String StringArray = Arrays.toString(DataString);
        String[] StringArrays = StringArray.split(",");

        if (!TextOrBody){
            TextBodyData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                String ID = values[0];
                String TaskSpecification = values[1];

                String[] value = {ID, TaskSpecification,};

                TextBodyData[i] = value;
            }
        } else {
            TextNameData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                String ID = values[0];
                String TaskName = values[1];
                String TaskCompletion = values[2];
                String TimeRequired = values[3];

                String[] value = {ID, TaskName, TaskCompletion ,TimeRequired};

                TextNameData[i] = value;
            }
        }

    }
    public String readFile(String file){
        String text = "";
        try {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e ){
            e.printStackTrace();
        }
        return text;
    }
}