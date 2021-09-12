package com.mcbedrock.minecraftnews.models;

public class categoryTwoModel {
    private String title;
    private String description;
    private String imageURL;
    private String articleURL;

    public categoryTwoModel() {

    }

    public categoryTwoModel(String title, String description, String imageURL, String articleURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
        this.articleURL = articleURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }
}