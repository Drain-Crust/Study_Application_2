package com.example.study_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlantScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_screen);

        Button progress_button = (Button) findViewById(R.id.progress_button);
        progress_button.setTransformationMethod(null);
    }
    public void toProgressScreen (View aView){
        Intent intent = new Intent(this,ProgressScreen.class);
        startActivity(intent);
    }
}