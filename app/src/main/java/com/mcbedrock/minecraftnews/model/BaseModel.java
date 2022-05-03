package com.mcbedrock.minecraftnews.model;

public class BaseModel {
    String title, version, url_text, image_url;

    public BaseModel() {
        //
    }

    public BaseModel(String title, String version, String url_text, String image_url) {
        this.title = title;
        this.version = version;
        this.url_text = url_text;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl_text() {
        return url_text;
    }

    public void setUrl_text(String url_text) {
        this.url_text = url_text;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
