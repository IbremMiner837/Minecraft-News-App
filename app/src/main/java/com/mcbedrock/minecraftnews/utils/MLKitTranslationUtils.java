package com.mcbedrock.minecraftnews.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.mcbedrock.minecraftnews.R;

import java.util.ArrayList;
import java.util.Locale;

public class MLKitTranslationUtils {

    private static Context context;

    public MLKitTranslationUtils(Context context) {
        this.context = context;
    }

    private static ArrayList<String> availableModels = new ArrayList<>();
    private static final String TAG = "ArticleTranslationHelper";
    public static Boolean isTranslated = false;

    public static void translateArticle(String article, TextView textView, MaterialButton button) {

        button.setText(R.string.translating);
        translator().translate(article)
                .addOnSuccessListener(
                        translatedText -> {
                            // Translation successful.
                            Log.d(TAG, "onSuccess: Translation successful.");
                            textView.setText("");
                            textView.append(Html.fromHtml(((String) translatedText)));
                            button.setText(R.string.show_original);
                            isTranslated = true;
                        })
                .addOnFailureListener(
                        e -> {
                            // Error.
                            // ...
                            Log.d(TAG, "onFailure: Error.");
                            isTranslated = false;
                        });
    }

    public static boolean isLanguageDownloaded(String language) {
        for (int i = 0; i < availableModels.size(); i++) {
            if (availableModels.get(i).equals(language)) {
                return true;
            }
        }
        return false;
    }

    public static void getAvailableModels() {
        getRemoteModelManager()
                .getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(translateRemoteModels -> {
                    for (TranslateRemoteModel model : translateRemoteModels) {
                        availableModels.add(model.getLanguage());
                        Log.d(TAG, "getAvailableModels: " + availableModels);
                    }
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: " + availableModels);
                    }
                });
    }

    public static void deleteModelTranslateRemoteModel() {
        for (int i = 0; i < availableModels.size(); i++) {
            getRemoteModelManager()
                    .deleteDownloadedModel(new TranslateRemoteModel.Builder(availableModels.get(i)).build())
                    .addOnSuccessListener(o -> {
                        new DialogsUtil().deletingTranslationModelDone(context);
                        getAvailableModels();
                    });
        }
    }

    public static void downloadModel() {
        getRemoteModelManager()
                .download(getModel(getSystemLanguage()), setDownloadConditions())
                .addOnSuccessListener(o -> {
                    new DialogsUtil().translateModelDownloaded((Activity) context);
                    getAvailableModels();
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "onFailure: " + e);
                });
    }

    public static Translator translator() {
        return Translation.getClient(setOptions());
    }

    private static TranslatorOptions setOptions() {
        return new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(getSystemLanguage())
                .build();
    }

    private static DownloadConditions setDownloadConditionsWithWifi() {
        return new DownloadConditions.Builder()
                .requireWifi()
                .build();
    }

    private static DownloadConditions setDownloadConditions() {
        return new DownloadConditions.Builder()
                .build();
    }

    private static RemoteModelManager getRemoteModelManager () {
        return RemoteModelManager.getInstance();
    }

    private static TranslateRemoteModel getModel(String languageCode) {
        return new TranslateRemoteModel.Builder(languageCode).build();
    }

    public static Boolean isTranslated() {
        return isTranslated;
    }

    public static String getSystemLanguage() {
        String language = Locale.getDefault().getLanguage();
        Log.d(TAG, "getSystemLanguage: " + language);
        return language;
    }
}