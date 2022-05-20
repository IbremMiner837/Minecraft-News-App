package com.mcbedrock.minecraftnews.config;

public class Settings {
    @Parameter(jsonKey = "content_language")
    public static String contentLanguage = "Russian";

    @Parameter(jsonKey = "text_size")
    public static int textSize = 20;

    @Parameter(jsonKey = "is_title_bolded")
    public static boolean isTitleBolded = true;
}
