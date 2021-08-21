package com.alfred.moviefiledownloadertask.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 121);
        // Where 180 is the width of your grid item. You can change it as per your convention.
        return noOfColumns;
    }
    public static int calculateNoOfColumnsGallery(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 110);
        // Where 180 is the width of your grid item. You can change it as per your convention.
        return noOfColumns;
    }
}