package com.mcbedrock.minecraftnews.model;

public class NewsModel {

    String title, text, image, readMoreUrl;

    public NewsModel() {
        //
    }

    public NewsModel(String title, String text, String image, String readMoreUrl) {
        this.title = title;
        this.text = text;
        this.image = image;
        this.readMoreUrl = readMoreUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReadMoreUrl() {
        return readMoreUrl;
    }

    public void setReadMoreUrl(String readMoreUrl) {
        this.readMoreUrl = readMoreUrl;
    }
}
