package com.mcbedrock.minecraftnews.API;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

public class loadingDialogAPI {
    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private AlertDialog alertDialog;

    loadingDialogAPI(Activity myActivity) {
        activity = myActivity;
    }

    void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        //builder.setView(inflater.inflate(R.layout.progress_dialog, null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
    }

    void dismissDialog() {
        alertDialog.dismiss();
    }

    //init -> final LoadingDialog loadingDialog = LoadingDialog([activity].this);
    //show -> loadingDialog.startLoadingDialog();
    //dismiss -> loadingDialog.dismissDialog();
}
