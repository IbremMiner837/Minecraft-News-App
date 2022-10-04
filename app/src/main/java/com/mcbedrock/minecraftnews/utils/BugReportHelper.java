package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mcbedrock.minecraftnews.BuildConfig;
import com.mcbedrock.minecraftnews.R;

public class BugReportHelper {

    public static void sendEmail(Context context) {

        String manufacturer = android.os.Build.MANUFACTURER;
        String model_name = Build.DEVICE;
        String number_model = Build.MODEL;
        String android_version_name = Build.VERSION.RELEASE_OR_CODENAME;
        int android_version_int = Build.VERSION.SDK_INT;
        String device_info =
                "Device Info:" + "\n" +
                        "Device Name:" + " " + manufacturer + " " + model_name + "\n" +
                        "Device Model:" + " " + number_model + "\n" +
                        "Android Version:" + " " + Build.VERSION.CODENAME + " " + android_version_name + " " + "(" + android_version_int + ")" + "\n" +
                        "Connection Type:" + " " + getNetworkClass(context);

        String app_info = "\n\n\n" +
                "App Info:" + "\n" +
                context.getString(R.string.app_name) + "\n" +
                BuildConfig.APPLICATION_ID + "\n" +
                "App Version:" + " " + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")" + "\n" +
                "Build Type:" + " " + BuildConfig.BUILD_TYPE;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL , new String[]{"ibremminer837.dev@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.report_a_bug));
        intent.putExtra(Intent.EXTRA_TEXT , app_info + "\n\n" + device_info);
        try {
            context.startActivity(Intent.createChooser(intent, ""));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.not_installed_email_client, Toast.LENGTH_SHORT).show();
        }
    }

    private static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "Not Connected"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:     // api< 8: replace by 11
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:   // api< 9: replace by 12
                case TelephonyManager.NETWORK_TYPE_EHRPD:    // api<11: replace by 14
                case TelephonyManager.NETWORK_TYPE_HSPAP:    // api<13: replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:      // api<11: replace by 13
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                case 19: // LTE_CA
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:       // api<29: replace by 20
                    return "5G";
                default:
                    return "Unknown";
            }
        }
        return "?";
    }
}
