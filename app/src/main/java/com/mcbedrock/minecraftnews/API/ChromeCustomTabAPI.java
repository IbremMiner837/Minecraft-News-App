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

    public ChromeCustomTabAPI(Activity myActivity) {
        activity = myActivity;
    }

    public void OpenCustomTab(Activity activity, String URL, int colorInt) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(activity, Uri.parse(URL));

        //Set tab color
        CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder()
                .setToolbarColor(colorInt)
                .build();
        builder/*intentBuilder*/.setDefaultColorSchemeParams(defaultColors);

        //Set animation
        //builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        //builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
