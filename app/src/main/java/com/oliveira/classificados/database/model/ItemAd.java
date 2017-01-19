package com.oliveira.classificados.database.model;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.oliveira.classificados.App;
import com.oliveira.classificados.activity.FormItemActivity;
import com.oliveira.classificados.database.MyStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemAd implements Serializable {

    private String mImage;
    private String mTitle;
    private String mDescription;
    private String mGuid;


    public ItemAd(String image, String title, String description) {
        mImage = image;
        mTitle = title;
        mDescription = description;
    }

    public ItemAd(Cursor cursor) {
        mGuid = cursor.getString(cursor.getColumnIndex(MyStore.ItemAdTable.GUID));
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

    public String getGuid() {
        return mGuid;
    }

    public void setGuid(String mGuid) {
        this.mGuid = mGuid;
    }

    public static ItemAd getByGuid(Activity activity, String guid) {
        if (guid == null) return null;

        final SQLiteDatabase db = App.getInstance(activity).getDbHelper().getWritableDatabase();
        try (Cursor cursor = db.query(MyStore.ItemAdTable.TABLE_NAME, null,
                MyStore.ItemAdTable.GUID + " = ?", new String[]{guid},
                null, null, null, "1")) {
            if (cursor.moveToNext()) {
                return new ItemAd(cursor);
            }
            return null;
        }

    }
}
