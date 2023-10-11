package com.mcbedrock.app.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

public class OtherAPI {

    public void copyTextToClipboard(Context context, String text) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(text);
        } else {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Copied Text", text);
            clipboardManager.setPrimaryClip(clipData);
        }
    }

    //date formatter
    public static String formatDate(String date) {
        return date.split("T")[0];
    }

    //time formatter
    public static String formatTime(String date) {
        return date.split("T")[1].split("\\.")[0];
    }

    //duration formatter
    public static String formatDuration(String duration) {
        String[] time = duration.split("PT")[1].split("M");
        String minutes = time[0];
        String seconds = time[1].split("S")[0];
        return minutes + ":" + seconds;
    }

    //views formatter
    public static String formatViews(String views) {
        if (views.length() > 6) {
            return views.substring(0, views.length() - 6) + "M";
        } else if (views.length() > 3) {
            return views.substring(0, views.length() - 3) + "K";
        } else {
            return views;
        }
    }

    //likes formatter
    public static String formatLikes(String likes) {
        if (likes.length() > 6) {
            return likes.substring(0, likes.length() - 6) + "M";
        } else if (likes.length() > 3) {
            return likes.substring(0, likes.length() - 3) + "K";
        } else {
            return likes;
        }
    }

}
