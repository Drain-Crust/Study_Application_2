package com.example.study_application;

import android.view.View;

public class arrowAnimation {
    public static boolean toggleArrow(View view, boolean isExpanded) {

        if (isExpanded) {
            view.animate().setDuration(400).rotation(90);
            return true;
        } else {
            view.animate().setDuration(400).rotation(0);
            return false;
        }
    }
}
