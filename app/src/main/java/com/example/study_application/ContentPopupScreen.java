package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.util.Arrays;

public class ContentPopupScreen extends AppCompatActivity {
    public static final String EXTRA_STRING_ID = "package com.example.study_application";

    String[][] TextBodyData;
    String[][] TextNameData;
    String[] valueSpecificationData;
    String[] valueNameData;

    String IdName, taskCompletion, timeRequired, taskName;
    String IdSpecifications, TaskSpecification;

    String textReadFile;

    TextView taskNames, taskSpecification, taskCompletions, taskTimes;
    Button StartTask;

    Intent intent1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_poppup_screen);

        //reads file data and saves it into arrays
        ReadTaskNameData("TaskNames.txt", true);
        ReadTaskNameData("TaskSpecifications.txt", false);

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
        taskNames.setText(TextNameData[actualNumber][1]);
        taskSpecification.setText(TextBodyData[actualNumber][1]);
        taskCompletions.setText(TextNameData[actualNumber][2]);
        taskTimes.setText(TextNameData[actualNumber][3]);

        //checks to see if the Start Task button has been pressed
        StartTask.setOnClickListener(v -> sendData());

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void sendData() {
        intent1.putExtra(EXTRA_STRING_ID, intent.getStringExtra(RecyclerViewAdapter.EXTRA_NUMBER));
        startActivity(intent1);
    }

    public void ReadTaskNameData(String file, Boolean TextOrBody) {
        //reads file data and splits it into separate items with each new line in the file data
        String fileData = readFile(file);
        String[] DataString = fileData.split("\n");

        //separates each item into parts because of the commas when reading the file
        String StringArray = Arrays.toString(DataString);
        String[] StringArrays = StringArray.split(",");

        //checks which file has been put through the method
        if (!TextOrBody) {
            //creates new array as it is only used here and does for loop to to add the parts back together to form groups
            TextBodyData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                IdSpecifications = values[0];
                TaskSpecification = values[1];

                valueSpecificationData = new String[]{IdSpecifications, TaskSpecification};

                TextBodyData[i] = valueSpecificationData;
            }
        } else {
            //does the same thing from the top code only difference is that its for the other file
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
        //stating the variable used to return
        textReadFile = "";
        try {
            FileInputStream fis = openFileInput(file);
            //checks and sets the size of the byte
            int size = fis.available();
            byte[] buffer = new byte[size];
            //reads the file even though it says its ignored
            fis.read(buffer);
            fis.close();
            textReadFile = new String(buffer);
        //if there was an error it would run this code instead of an error message.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textReadFile;
    }
}