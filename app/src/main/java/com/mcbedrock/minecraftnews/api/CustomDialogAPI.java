package com.mcbedrock.minecraftnews.api;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mcbedrock.minecraftnews.R;

public class CustomDialogAPI {

    public void showErrorDialog(Context CONTEXT, String ERROR) {
        new MaterialAlertDialogBuilder(CONTEXT)
                .setIcon(R.drawable.ic_round_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(ERROR)
                .setNegativeButton(R.string.copy, null)
                .setPositiveButton(R.string.OK, null)
                .show();
    }
}
