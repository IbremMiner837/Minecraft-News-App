package com.mcbedrock.minecraftnews.model;

public class BaseModel {
    String title, version, image_url;

    public BaseModel() {
        //
    }

    public BaseModel(String title, String version, String image_url) {
        this.title = title;
        this.version = version;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
