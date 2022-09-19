package com.mcbedrock.minecraftnews.utils;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Locale;

public class ArticleTranslationHelper {

    private static final String TAG = "ArticleTranslationHelper";
    public static Boolean isTranslated = false;

    public static void translateArticle(String article, TextView textView, ExtendedFloatingActionButton fab) {

        fab.setText("Перевод...");

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(getSystemLanguage())
                        .build();

        Translation.getClient(options)
                .downloadModelIfNeeded(new DownloadConditions.Builder().build())
                .addOnSuccessListener(
                        v -> {
                            // Model downloaded successfully. Okay to start translating.
                            // (Set a flag, unhidden the translation UI, etc.)
                            Log.d(TAG, "onSuccess: Model downloaded successfully. Okay to start translating.");
                        })
                .addOnFailureListener(
                        e -> {
                            // Model could’t be downloaded or other internal error.
                            // ...
                            Log.d(TAG, "onFailure: Model could’t be downloaded or other internal error.");
                        });

        Translation.getClient(options)
                .translate(article)
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

    public static Boolean isTranslated() {
        return isTranslated;
    }

    private static String getSystemLanguage() {
        String language = Locale.getDefault().getLanguage();
        Log.d(TAG, "getSystemLanguage: " + language);
        return language;
    }
}
