package com.oliveira.classificados;


public class ItemAd {

    private String mImage;
    private String mTitle;
    private String mDescription;

    public ItemAd(String image, String title, String description) {
        mImage = image;
        mTitle = title;
        mDescription = description;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
