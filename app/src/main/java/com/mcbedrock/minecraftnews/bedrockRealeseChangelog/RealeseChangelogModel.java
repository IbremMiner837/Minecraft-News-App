package com.mcbedrock.minecraftnews.bedrockRealeseChangelog;

public class RealeseChangelogModel {

    String img_link, changelog_link, name_title, version;

    public RealeseChangelogModel() {
    }

    public RealeseChangelogModel(String img_link, String changelog_link, String name_title, String version) {
        this.img_link = img_link;
        this.changelog_link = changelog_link;
        this.name_title = name_title;
        this.version = version;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getChangelog_link() {
        return changelog_link;
    }

    public void setLink(String changelog_link) {
        this.changelog_link = changelog_link;
    }

    public String getName_title() {
        return name_title;
    }

    public void setName(String name_title) {
        this.name_title = name_title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
