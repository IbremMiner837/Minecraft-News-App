package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

public class CustomTabUtil {

    public void open(Context context, String URL) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setUrlBarHidingEnabled(true);
        builder.setShowTitle(true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(URL));
    }
}
