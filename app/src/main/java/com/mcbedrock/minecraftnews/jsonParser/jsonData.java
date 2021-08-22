package com.mcbedrock.minecraftnews.jsonParser;

public class jsonData {
    private String subHeader;
    private String imageURL;
    private String title;
    private String articleURL;
    private String primaryCategory;

    public jsonData() {
    }

    public jsonData(String subHeader, String imageURL, String title, String articleURL, String primaryCategory) {
        this.subHeader = subHeader;
        this.imageURL = imageURL;
        this.title = title;
        this.articleURL = articleURL;
        this.primaryCategory = primaryCategory;
    }

    public String getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(String subHeader) {
        this.subHeader = subHeader;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getPrimary_category() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }
}
