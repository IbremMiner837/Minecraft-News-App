package com.mcbedrock.minecraftnews.utils;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mcbedrock.minecraftnews.R;

public class DialogsUtil {

    public void showErrorDialog(Context context, String error) {
        new MaterialAlertDialogBuilder(context)
                //.setIcon(R.drawable.ic_round_error_outline_24)
                .setTitle(R.string.error)
                .setMessage(error)
                .setNegativeButton(R.string.copy, (dialogInterface, i) -> {
                    new OtherAPI().copyTextToClipboard(context, error);
                })
                .setPositiveButton(R.string.OK, null)
                .show();
    }

    public void showLinkDialog(Context context, String url) {
        new MaterialAlertDialogBuilder(context)
                //.setIcon(R.drawable.ic_link_24)
                .setTitle(R.string.link)
                .setMessage(url)
                .setNegativeButton(R.string.copy_link, (dialogInterface, i) -> {
                    new OtherAPI().copyTextToClipboard(context, url);
                })
                .setPositiveButton(R.string.open_link, ((dialogInterface, i) -> {
                    new CustomTabUtil().open(context, url);
                }))
                .show();
    }

    public void downloadTranslateModel(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_round_translate_24)
                .setTitle("Скачать модель перевода?")
                .setMessage("Для перевода статей необходимо скачать модель перевода. Это займет некоторое время.")
                .setPositiveButton(R.string.OK, ((dialogInterface, i) -> {
                    ArticleTranslationHelper.init(context);
                }))
                .setNegativeButton("Позже", null)
                .show();
    }

    public void translateModelDownloaded(Context context) {
        new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_round_translate_24)
                .setTitle("Модель перевода скачана")
                .setMessage("Модель перевода скачана. Теперь вы можете переводить статьи.")
                .setPositiveButton(R.string.OK, null)
                .show();
    }
}
