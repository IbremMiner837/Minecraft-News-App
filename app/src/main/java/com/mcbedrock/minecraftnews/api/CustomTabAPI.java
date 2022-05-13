package com.mcbedrock.minecraftnews.api;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

public class CustomTabAPI {

    private Activity activity;

    public CustomTabAPI(Activity activity) {
        activity = activity;
    }

    public CustomTabAPI(Context context, String article_url, int app_bar_dark_color) {

    }

    public void OpenCustomTab(Activity activity, String URL) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(URL));
    }
}
