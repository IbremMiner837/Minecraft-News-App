package com.mcbedrock.minecraftnews.utils;

import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.Charset;

public class NewTranslationHelper {

    private static String fetchTranslation(CharSequence text, String fromLanguage, String toLanguage, long minDuration) {
        String uri = "";
        String translatedText = "";
        HttpURLConnection connection = null;
        try {
            uri = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=";
            uri += Uri.encode(fromLanguage);
            uri += "&tl=";
            uri += Uri.encode(toLanguage);
            uri += "&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=7&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&q=";
            uri += Uri.encode(text.toString());
            connection = (HttpURLConnection) new URI(uri).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
            connection.setRequestProperty("Content-Type", "application/json");

            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")))) {
                int c;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            String jsonString = textBuilder.toString();

            JSONTokener tokener = new JSONTokener(jsonString);
            JSONArray array = new JSONArray(tokener);
            JSONArray array1 = array.getJSONArray(0);
            String sourceLanguage = null;
            try {
                sourceLanguage = array.getString(2);
            } catch (Exception e2) {}
            if (sourceLanguage != null && sourceLanguage.contains("-")) {
                sourceLanguage = sourceLanguage.substring(0, sourceLanguage.indexOf("-"));
            }
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < array1.length(); ++i) {
                String blockText = array1.getJSONArray(i).getString(0);
                if (blockText != null && !blockText.equals("null")) {
                    result.append(blockText);
                }
            }
            if (text.length() > 0 && text.charAt(0) == '\n') {
                result.insert(0, "\n");
            }
            translatedText = result.toString();
        } catch (Exception e) {
            try {
                Log.e("translate", "failed to translate a text " + (connection != null ? connection.getResponseCode() : null) + " " + (connection != null ? connection.getResponseMessage() : null));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
        return translatedText;
    }

    public static String translate(CharSequence text, String fromLanguage, String toLanguage) {
        long time = SystemClock.elapsedRealtime();
        String translatedText = fetchTranslation(text, fromLanguage, toLanguage, 0);
        Log.d("translate", "translation took " + (SystemClock.elapsedRealtime() - time) + "ms");
        return translatedText;
    }

    public void main(String[] args) {
        translate("Hello World!", "en", "ru");
    }
}
