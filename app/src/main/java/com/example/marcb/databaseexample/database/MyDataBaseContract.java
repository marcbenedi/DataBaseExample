package com.example.marcb.databaseexample.database;

import android.provider.BaseColumns;

public final class MyDataBaseContract {
    //We prevent that someone extends this class by making it final

    /*
    * DEFINITION:
    * A formal declaration of how the database is organized.
    * Definition by Google in: https://developer.android.com/training/basics/data-storage/databases.html
    */

    // We prevent that someone instantiate this class making the constructor private
    private MyDataBaseContract(){}

    //We create as much public static classes as tables we have in our database
    public static class Table1 implements BaseColumns {
        public static final String TABLE_NAME = "tablename";
        //public static final String COLUMN_ID = "id"; <- Actually we don't need a dedicated COLUMN_ID
        //because this class implements BaseColumns which has a _ID constant for that
        public static final String COLUMN_TEXT = "text";
    }

    //Declare here other inner classes for other tables in the database using the same format

    /*
    public static class Table2 implements BaseColumns{
        public static final String TABLE_NAME = "othertable";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_PHONE = "phone";
    }
    */


}
