package com.oliveira.classificados.database;

public class MyStore {

    static abstract class BaseTable {
        public static final String GUID = "guid";

    }

    public static abstract class ItemAdTable extends BaseTable {

        public static final String TABLE_NAME = "item_ad";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";


        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ( " +
                        GUID + " TEXT PRIMARY KEY, " +
                        TITLE + " TEXT, " +
                        DESCRIPTION + " TEXT, " +
                        PRICE + " TEXT "
                        + ")";
    }


    //ADD OTHER TABLES
}
