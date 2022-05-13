package com.mcbedrock.minecraftnews.api;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

import com.mcbedrock.minecraftnews.R;

public class CustomTabAPI {

    public void open(Context context, String URL) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setUrlBarHidingEnabled(true);
        builder.setShowTitle(true);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(URL));
    }
}
