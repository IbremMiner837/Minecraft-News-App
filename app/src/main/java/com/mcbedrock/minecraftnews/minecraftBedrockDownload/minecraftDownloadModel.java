package com.mcbedrock.minecraftnews.minecraftBedrockDownload;

public class minecraftDownloadModel {
    String img_link, name_title, version, download_link, file_size, modification;

    public minecraftDownloadModel() {
    }

    public minecraftDownloadModel(String img_link, String name_title, String version, String download_link, String file_size, String modification) {
        this.img_link = img_link;
        this.name_title = name_title;
        this.version = version;
        this.download_link = download_link;
        this.file_size = file_size;
        this.modification = modification;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getName_title() {
        return name_title;
    }

    public void setName_title(String name_title) {
        this.name_title = name_title;
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

    public String getModification() {
        return modification;
    }

    public void setModification() {
        this.modification = modification;
    }
}
