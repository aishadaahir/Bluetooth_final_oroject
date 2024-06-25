package com.example.android.bluetooth_final_project.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CustomeDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "custome.db";
    public static final String TABLE_NAME = "custome_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "name";
    public static final String COL3 = "array";



    public CustomeDatabase (Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT, " +
                " array TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item1,String item2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item1);
        contentValues.put(COL3, item2);


        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }




    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean deletecustome(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "ID=?", new String[]{id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, null, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}