package com.oliveira.classificados.database.model;


import java.io.Serializable;

public class Category implements Serializable {

    private String mId;
    private String mDescription;

    public Category(String id, String description) {
        mId = id;
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return mDescription;
    }
}
