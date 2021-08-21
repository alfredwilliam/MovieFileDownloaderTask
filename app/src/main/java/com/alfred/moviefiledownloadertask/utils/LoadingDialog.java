package com.alfred.moviefiledownloadertask.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.alfred.moviefiledownloadertask.R;


public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity/*,R.style.CustomDialog*/);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_custom_dialog_prog,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        Window window = alertDialog.getWindow();

        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    public void stopLoader(){
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
}
