package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Locale;
import java.util.Set;

public class TranslationHelper {

    private static final String TAG = "ArticleTranslationHelper";
    public static Boolean isTranslated = false;

    public static void translateArticle(String article, TextView textView, ExtendedFloatingActionButton fab) {

        fab.setText("Перевод...");

        downloadModel();
        translator().translate(article)
                .addOnSuccessListener(
                        translatedText -> {
                            // Translation successful.
                            Log.d(TAG, "onSuccess: Translation successful.");
                            textView.setText("");
                            textView.append(Html.fromHtml(((String) translatedText)));
                            fab.setText("Перевести обратно");
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

    private static void getDownloadedModels() {
        // Get translation models stored on the device.
        getRemoteModelManager().getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        //
                    }

                    public void onSuccess(Set models) {
                        //
                    }
        })
    .addOnFailureListener(e -> {
        // Error.
    });
    }

    public static void deleteModelTranslateRemoteModel() {
        getRemoteModelManager().deleteDownloadedModel(getTranslateRemoteModel())
                .addOnSuccessListener(o -> {
                    // Success.
                })
                .addOnFailureListener(e -> {
                    // Error.
                });
    }

    public static void downloadModel() {
        getRemoteModelManager().download(getTranslateRemoteModel(), setDownloadConditions())
                .addOnSuccessListener(o -> {
                    // Model downloaded.
                })
                .addOnFailureListener(e -> {
                    // Error.
                });
    }

    public static Translator translator() {
        final Translator translator =
                Translation.getClient(setOptions());
        return translator;
    }

    private static TranslatorOptions setOptions() {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(getSystemLanguage())
                        .setExecutor(Runnable::run)
                        .build();
        return options;
    }

    private static DownloadConditions setDownloadConditionsWithWifi() {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        return conditions;
    }

    private static DownloadConditions setDownloadConditions() {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .build();
        return conditions;
    }

    private static RemoteModelManager getRemoteModelManager () {
        RemoteModelManager modelManager = RemoteModelManager.getInstance();
        return modelManager;
    }

    private static TranslateRemoteModel getTranslateRemoteModel() {
        TranslateRemoteModel model =
                new TranslateRemoteModel.Builder(getSystemLanguage()).build();
        return model;
    }

    private static String getSystemLanguage() {
        String language = Locale.getDefault().getLanguage();
        Log.d(TAG, "getSystemLanguage: " + language);
        return language;
    }
}
