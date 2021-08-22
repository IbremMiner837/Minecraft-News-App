package com.mcbedrock.minecraftnews.API;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class snackbarAPI {
    private Activity activity;
    private View view;
    private Context context;

    public snackbarAPI(Activity myActivity) {
        activity = myActivity;
    }

    public snackbarAPI(Context context) {
        context = context;
    }

    public snackbarAPI(View view) {
        view = view;
    }

    public void BasicSnackbar(int snackbarText) {
        Snackbar snackbar = Snackbar.make(view, snackbarText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


}
