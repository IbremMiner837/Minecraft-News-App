package com.mcbedrock.minecraftnews.model;

public class NewsModel {

    String title, sub_header, article_url, image_url;

    public NewsModel() {
        //
    }

    public NewsModel(String title, String sub_header, String article_url, String image_url) {
        this.title = title;
        this.sub_header = sub_header;
        this.article_url = article_url;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_header() {
        return sub_header;
    }

    public void setSub_header(String sub_header) {
        this.sub_header = sub_header;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
