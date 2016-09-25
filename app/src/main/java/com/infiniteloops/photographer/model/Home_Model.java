package com.infiniteloops.photographer.model;

/**
 * Created by asna on 9/6/16.
 */
public class Home_Model {
    String id;
    String author;
    String category;
    String color;
    String date;
    String displayDate;
    String featured;
    String height;
    String identifier;
    String loved;
    String modifiedDate;
    String original;
    String platform;
    String ratio;
    String thumbnail;
    String width;
    public Home_Model(String id, String author, String category, String color, String date, String displayDate, String featured, String height, String identifier, String loved, String modifiedDate, String original, String platform, String ratio, String thumbnail, String width) {
        this.id = id;
        this.author = author;
        this.category = category;
        this.color = color;
        this.date = date;
        this.displayDate = displayDate;
        this.featured = featured;
        this.height = height;
        this.identifier = identifier;
        this.loved = loved;
        this.modifiedDate = modifiedDate;
        this.original = original;
        this.platform = platform;
        this.ratio = ratio;
        this.thumbnail = thumbnail;
        this.width = width;
    }
    public Home_Model(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getLoved() {
        return loved;
    }

    public void setLoved(String loved) {
        this.loved = loved;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }


}
