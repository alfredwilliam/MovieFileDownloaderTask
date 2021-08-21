package com.alfred.moviefiledownloadertask.utils;

import android.content.Context;
import android.widget.Toast;

import com.alfred.moviefiledownloadertask.R;

import java.io.IOException;


public class ToastUtil {
    public static void connectionErrorToast(Context context){
        Toast.makeText(context, context.getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
    }

    public static void dataErrorToast(Context context){
        Toast.makeText(context, context.getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
    }
    public static void dataErrorToast(Context context, Throwable t){
        if(t instanceof IOException)
            connectionErrorToast(context);
        else
            Toast.makeText(context, context.getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
    }
}
