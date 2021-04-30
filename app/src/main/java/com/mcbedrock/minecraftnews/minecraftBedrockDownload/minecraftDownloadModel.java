package com.mcbedrock.minecraftnews.minecraftBedrockDownload;

public class minecraftDownloadModel {
    String img, name, version, download_link, file_size;

    public minecraftDownloadModel() {
    }

    public minecraftDownloadModel(String img, String name, String version, String download_link, String file_size) {
        this.img = img;
        this.name = name;
        this.version = version;
        this.download_link = download_link;
        this.file_size = file_size;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size() {
        this.file_size = file_size;
    }
}
