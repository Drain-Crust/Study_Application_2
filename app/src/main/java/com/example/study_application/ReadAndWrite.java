package com.example.study_application;

import android.content.Context;
import android.content.ContextWrapper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReadAndWrite extends AppCompatActivity {

    private ContextWrapper contextWrapper;
    private final Context mContext;
    String[][] textBodyData;
    String[][] textNameData;
    String[] StringArrays;
    String[] DataString;
    Integer[] ids;

    public ReadAndWrite(Context mContext) {
        this.mContext = mContext;
    }

    public String[][] readTaskNameData(String file, Boolean TextOrBody) {
        String fileData = readFile(file);
        DataString = fileData.split("\n");

        String StringArray = Arrays.toString(DataString);
        StringArrays = StringArray.split(",");

        if (!TextOrBody) {
            textBodyData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                String IdSpecifications = values[0];
                String TaskSpecification = values[1];

                String[] valueSpecificationData = {IdSpecifications, TaskSpecification};

                textBodyData[i] = valueSpecificationData;
            }
            return textBodyData;
        } else {
            textNameData = new String[StringArrays.length][];
            for (int i = 1; i < DataString.length; i++) {
                String[] values = DataString[i].split(" ");

                String IdName = values[0];
                String taskName = values[1];
                String taskCompletion = values[2];
                String timeRequired = values[3];

                String[] valueNameData = {IdName, taskName, taskCompletion, timeRequired};

                textNameData[i] = valueNameData;
            }
            return textNameData;
        }
    }

    //code already explained
    public String readFile(String file) {
        contextWrapper = new ContextWrapper(mContext);
        String text = "";
        try {
            FileInputStream fis = contextWrapper.openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Error reading file", Toast.LENGTH_SHORT).show();
        }
        return text;
    }

    //
    public void write(String file, String textData) {
        contextWrapper = new ContextWrapper(mContext);
        try {
            FileOutputStream fos = contextWrapper.openFileOutput(file, MODE_APPEND);
            fos.write(textData.getBytes());
            fos.close();
            Toast.makeText(mContext, "saving file successful", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceLines(String oldFileLine, String newFileLine, String fileName) {
        contextWrapper = new ContextWrapper(mContext);
        String[] fileDataLines = readFile(fileName).split("\n");

        try {
            FileOutputStream fos = contextWrapper.openFileOutput(fileName, MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> fileContent = Arrays.asList(fileDataLines);
        //finds specific line inside txt file then replaces the file and puts back
        // into the list which then the list is pasted back inside the txt file
        for (int i = 0; i < fileContent.size(); i++) {
            fileContent.set(i, fileContent.get(i)+"\n");
        }

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldFileLine+"\n")) {
                if (newFileLine.equals("")){
                    fileContent.set(i, newFileLine);
                } else {
                    fileContent.set(i, newFileLine+"\n");
                }
                break;
            }
        }

        for (int i = 0; i < fileContent.size(); i++) {
            write(fileName, fileContent.get(i));
        }

    }

    public String findBiggestId(){
        String biggestId;
        readTaskNameData("TaskNames.txt", true);
        ids = new Integer[textNameData.length - 1];

        for (int i = 1; i < DataString.length; i++){
            int value = Integer.parseInt(textNameData[i][0]);
            ids[i - 1] = value;
        }

        Arrays.sort(ids, Collections.reverseOrder());
        int errorCheck = textNameData.length - 1;
        if (errorCheck == 0){
            biggestId = "1";
        } else {
            biggestId = String.valueOf(ids[0] + 1);
        }
        return biggestId;
    }
}
