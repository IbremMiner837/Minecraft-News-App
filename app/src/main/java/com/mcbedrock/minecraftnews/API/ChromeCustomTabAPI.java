package com.mcbedrock.minecraftnews.API;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;

import com.mcbedrock.minecraftnews.R;

public class ChromeCustomTabAPI {

    private Activity activity;

    public ChromeCustomTabAPI(Activity activity) {
        activity = activity;
    }

    public ChromeCustomTabAPI(Context context, String article_url, int app_bar_dark_color) {

    }

    public void OpenCustomTab(Activity activity, String URL) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(URL));
    }
}
