package com.example.marcb.databaseexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper{

    /*
    * DEFINITION:
    * This class is the one which talks with the DB.
    */
    private final String TAG = "MyDataBaseHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyDataBase.db";


    private static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + MyDataBaseContract.Table1.TABLE_NAME + " (" +
                    MyDataBaseContract.Table1._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDataBaseContract.Table1.COLUMN_TEXT + " TEXT)";

    private static final String SQL_DELETE_TABLE1 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table1.TABLE_NAME;

    private static MyDataBaseHelper instance;
    private static SQLiteDatabase writable;
    private static SQLiteDatabase readable;

    //We will use this method instead the default constructor to get a reference.
    //With this we will use all the time the same reference.
    public static MyDataBaseHelper getInstance(Context c){
        if(instance == null){
            instance = new MyDataBaseHelper(c);
            writable = instance.getWritableDatabase();
            readable = instance.getReadableDatabase();
        }
        return instance;
    }

    //With this, all must use getInstance(Context) to use this class
    private MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //We execute here the SQL sentence to create the DB
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //This method will be executed when the system detects that DATABASE_VERSION has been upgraded
        sqLiteDatabase.execSQL(SQL_DELETE_TABLE1);
        onCreate(sqLiteDatabase);
    }

    public long createRow(String s) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_TEXT,s);
        long newId = writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);
        return newId;
    }

    public int updateRow(String s) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_TEXT, s.charAt(0)+s);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_TEXT + " LIKE ? ",                 //Selection args
                new String[] {s});                                                  //Selection values

        return rows_afected;
    }

    public int deleteRow(String s) {
        int afected = readable.delete(MyDataBaseContract.Table1.TABLE_NAME,         //Table name
                MyDataBaseContract.Table1.COLUMN_TEXT + " LIKE ? ",                 //Selection args
                new String[] {s});                                                  //Selection values

        return afected;
    }

    public String queryRow(String s) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                new String[] {MyDataBaseContract.Table1._ID},       //Columns we select
                MyDataBaseContract.Table1.COLUMN_TEXT + " = ? ",    //Columns for the WHERE clause
                new String[] {s},                                   //Values for the WHERE clause
                null,                                               //Group By
                null,                                               //Having
                null);                                              //Sort

        String returnValue = "Not found";

        if(c.moveToFirst()){
            //We go here if the cursor is not empty
            long l = c.getLong(c.getColumnIndex(MyDataBaseContract.Table1._ID));
            returnValue = String.valueOf(l);
        }
        //Always close the cursor after you finished using it
        c.close();

        return returnValue;
    }

    @Override
    public synchronized void close() {
        super.close();
        //Always close the SQLiteDatabase
        writable.close();
        readable.close();
        Log.v(TAG,"close()");
    }
}
