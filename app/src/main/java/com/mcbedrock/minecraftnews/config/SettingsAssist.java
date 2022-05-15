package com.mcbedrock.minecraftnews.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class SettingsAssist {

    public static void save(File file, Class<?> settingsClass) throws IOException {
        JSONObject settings = new JSONObject();
        for (Field field : settingsClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                try {
                    settings.put(field.getAnnotation(Parameter.class).jsonKey(), field.get(null));
                } catch (IllegalAccessException | JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }
        }
        if (!file.exists()) file.createNewFile();
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(settings.toString());
        myWriter.close();
    }

    public static void load(File file, Class<?> settingsClass) throws IOException, JSONException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null)
                result.append(line);
        }

        JSONObject settings = new JSONObject(result.toString());
        for (Field field : settingsClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Parameter.class)) {
                try {
                    String jsonKey = field.getAnnotation(Parameter.class).jsonKey();
                    if (settings.has(jsonKey))
                        field.set(null, settings.get(jsonKey));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
