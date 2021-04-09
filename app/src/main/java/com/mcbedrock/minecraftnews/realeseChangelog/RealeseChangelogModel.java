package com.mcbedrock.minecraftnews.realeseChangelog;

public class RealeseChangelogModel {

    String img, link, name, version, download_link;

    public RealeseChangelogModel() {
    }

    public RealeseChangelogModel(String img, String link, String name, String version, String download_link) {
        this.img = img;
        this.link = link;
        this.name = name;
        this.version = version;
        this.download_link = download_link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownload_link() {
        return download_link;
    }

    public void setDownload_link() {
        this.download_link = download_link;
    }
}
