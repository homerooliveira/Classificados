package com.oliveira.classificados.database.model;


import android.app.Activity;
import android.database.Cursor;

import com.oliveira.classificados.database.MyStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemAd implements Serializable {

    private String mImage;
    private String mTitle;
    private String mDescription;

    public ItemAd(String image, String title, String description) {
        mImage = image;
        mTitle = title;
        mDescription = description;
    }

    public ItemAd(Cursor cursor) {
        mTitle = cursor.getString(cursor.getColumnIndex(MyStore.ItemAdTable.TITLE));
        mDescription = cursor.getString(cursor.getColumnIndex(MyStore.ItemAdTable.DESCRIPTION));
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

    public static List<ItemAd> getList(Activity activity){
        List<ItemAd> items = new ArrayList<>();


        return items;
    }
}
