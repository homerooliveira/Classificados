package com.oliveira.classificados.database;

public class MyStrore {

    static abstract class BaseTable {
        static final String GUID = "guid";

    }

    static abstract class ItemAdTable extends BaseTable {

        static final String TABLE_NAME = "item_ad";
        static final String TITLE = "title";
        static final String DESCRIPTION = "description";
        //         static final String PRICE = "price";
        static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                        GUID + " TEXT PRIMARY KEY, " +
                        TITLE + " TEXT, " +
                        DESCRIPTION + " TEXT "
                        + ")";
    }


    //ADD OTHER TABLES
}
