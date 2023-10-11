package com.mcbedrock.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mcbedrock.app.R;

public class DialogsUtil {

    public void showErrorDialog(Context context, String error) {
        new MaterialAlertDialogBuilder(new ContextThemeWrapper(context, R.style.Theme_AppTheme_MaterialAlertDialog))
                .setIcon(R.drawable.ic_round_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(error)
                .setNegativeButton(R.string.copy, (dialogInterface, i) -> {
                    new OtherAPI().copyTextToClipboard(context, error);
                })
                .setPositiveButton(R.string.OK, null)
                .show();
    }

    public void showLinkDialog(Context context, String url) {
        new MaterialAlertDialogBuilder(new ContextThemeWrapper(context, R.style.Theme_AppTheme_MaterialAlertDialog))
                //.setIcon(R.drawable.ic_link_24)
                .setTitle(R.string.link)
                .setMessage(url)
                .setNegativeButton(R.string.copy_link, (dialogInterface, i) -> {
                    new OtherAPI().copyTextToClipboard(context, url);
                })
                .setPositiveButton(R.string.open_link, ((dialogInterface, i) -> {
                    new CustomTabUtil().OpenCustomTab(context, url, context.getColor(R.color.md_theme_light_onSecondary));
                }))
                .show();
    }
}
