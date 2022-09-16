package com.mcbedrock.minecraftnews.model;

import android.graphics.drawable.Drawable;

public class DashboardModel {

    String title;
    int image;

    public DashboardModel(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
