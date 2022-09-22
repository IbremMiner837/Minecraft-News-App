package com.mcbedrock.minecraftnews.utils;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.mcbedrock.minecraftnews.R;

import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TranslationHelper {

    private static Context context;

    public TranslationHelper(Context context) {
        this.context = context;
    }

    private static final String TAG = "ArticleTranslationHelper";
    public static Boolean isTranslated = false;
    public static MutableLiveData<List<String>> availableModels = new MutableLiveData<>();

    public static void translateArticle(String article, TextView textView, MaterialButton button) {

        button.setText(R.string.translating);
        translator().translate(article)
                .addOnSuccessListener(
                        translatedText -> {
                            // Translation successful.
                            Log.d(TAG, "onSuccess: Translation successful.");
                            textView.setText("");
                            textView.append(Html.fromHtml(((String) translatedText)));
                            button.setText("Перевести обратно");
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
        getRemoteModelManager()
                .getDownloadedModels(TranslateRemoteModel.class)
                .addOnSuccessListener(
                        remoteModels -> {
                            List<String> modelCodes = new ArrayList<>(remoteModels.size());
                            for (TranslateRemoteModel model : remoteModels) {
                                modelCodes.add(model.getLanguage());
                            }
                            Collections.sort(modelCodes);
                            availableModels.setValue(modelCodes);
                        })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting downloaded models", e);
                });
    }

    public static boolean isLanguageDownloaded(String language) {
        return language.equals(getSystemLanguage());
    }

    public static void deleteModelTranslateRemoteModel() {
        getRemoteModelManager().deleteDownloadedModel(getModel(getSystemLanguage()))
                .addOnSuccessListener(o -> {
                    // Success.
                })
                .addOnFailureListener(e -> {
                    // Error.
                });
    }

    public static void downloadModel() {
        getRemoteModelManager().download(getModel(getSystemLanguage()), setDownloadConditions())
                .addOnSuccessListener(o -> {
                    new DialogsUtil().translateModelDownloaded(context);
                })
                .addOnFailureListener(e -> {
                    // Error.
                });
    }

    public static Translator translator() {
        return Translation.getClient(setOptions());
    }

    private static TranslatorOptions setOptions() {
        return new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(getSystemLanguage())
                .setExecutor(Runnable::run)
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
