package com.mcbedrock.minecraftnews.API;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.textview.MaterialTextView;
import com.mcbedrock.minecraftnews.R;

public class loadingDialogAPI {
    private Activity activity;
    private AppCompatActivity appCompatActivity;
    private AlertDialog alertDialog;

    public loadingDialogAPI(Activity myActivity) {
        activity = myActivity;
    }

    public void LoadingState(String state) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        alertDialog.setContentView(R.layout.loading_state);
        alertDialog.setCancelable(false);

        ProgressBar progressBar = (ProgressBar) alertDialog.findViewById(R.id.loading_state_loadbar);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        MaterialTextView materialTextView = (MaterialTextView) alertDialog.findViewById(R.id.loading_state_text);
        materialTextView.setText(state);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissDialog() {
        alertDialog.dismiss();
        alertDialog.hide();
    }

    //init -> final LoadingDialog loadingDialog = LoadingDialog([activity].this);
    //show -> loadingDialog.startLoadingDialog();
    //dismiss -> loadingDialog.dismissDialog();
}