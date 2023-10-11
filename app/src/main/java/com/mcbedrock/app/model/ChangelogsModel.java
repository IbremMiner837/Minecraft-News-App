package com.mcbedrock.app.model;

public class ChangelogsModel {
    private String title, version, image, url;

    public ChangelogsModel() {}

    public ChangelogsModel(String title, String version, String image, String url) {
        this.title = title;
        this.version = version;
        this.image = image;
        this.url = url;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
