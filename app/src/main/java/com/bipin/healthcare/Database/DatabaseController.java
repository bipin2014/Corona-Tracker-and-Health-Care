package com.bipin.healthcare.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Data.db";
    private static final String TABLE_NAME = "Meanings";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_CONFIRMED = "Confirmed";
    private static final String KEY_CCHANGED = "Cchanged";
    private static final String KEY_DEATH = "Death";
    private static final String KEY_DCHANGED = "Dchanged";
    private static final String KEY_RECOVERED = "Recovered";
    private static final String KEY_SERIOUS = "Serious";

    public DatabaseController (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                " ("+KEY_ID+" INTEGER PRIMARY KEY, "+
                KEY_NAME+" TEXT, "+
                KEY_CONFIRMED+" TEXT, "+
                KEY_CCHANGED+" TEXT, "+
                KEY_DEATH+" TEXT, "+
                KEY_DCHANGED+" TEXT, "+
                KEY_RECOVERED+" TEXT, "+
                KEY_SERIOUS+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insertData (Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, model.getName());
        contentValues.put(KEY_CONFIRMED, model.getConfirmed());
        contentValues.put(KEY_CCHANGED, model.getCtodayschange());
        contentValues.put(KEY_DEATH, model.getDeath());
        contentValues.put(KEY_DCHANGED, model.getDtodayschange());
        contentValues.put(KEY_RECOVERED, model.getRecovered());
        contentValues.put(KEY_SERIOUS, model.getSerious());
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateData (Model model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, model.getName());
        contentValues.put(KEY_CONFIRMED, model.getConfirmed());
        contentValues.put(KEY_CCHANGED, model.getCtodayschange());
        contentValues.put(KEY_DEATH, model.getDeath());
        contentValues.put(KEY_DCHANGED, model.getDtodayschange());
        contentValues.put(KEY_RECOVERED, model.getRecovered());
        contentValues.put(KEY_SERIOUS, model.getSerious());
        int i=db.update(TABLE_NAME, contentValues, KEY_NAME+"='"+model.getName()+"'",null);
        Log.i("UPDATE", String.valueOf(i));
        db.close();
    }

    public ArrayList<Model> getAllData() {
        ArrayList<Model> modelArrayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                model.setConfirmed(cursor.getString(cursor.getColumnIndex(KEY_CONFIRMED)));
                model.setCtodayschange(cursor.getString(cursor.getColumnIndex(KEY_CCHANGED)));
                model.setDeath(cursor.getString(cursor.getColumnIndex(KEY_DEATH)));
                model.setDtodayschange(cursor.getString(cursor.getColumnIndex(KEY_DCHANGED)));
                model.setRecovered(cursor.getString(cursor.getColumnIndex(KEY_RECOVERED)));
                model.setSerious(cursor.getString(cursor.getColumnIndex(KEY_SERIOUS)));
                // Adding contact to list
                modelArrayList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return modelArrayList;
    }

    public ArrayList<Model> searchData(String query) {
        ArrayList<Model> dataArrayList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME+" WHERE "+KEY_NAME+" LIKE '%"+query+"%'";
        Log.i("SQL",selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model model = new Model();
                model.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                model.setConfirmed(cursor.getString(cursor.getColumnIndex(KEY_CONFIRMED)));
                model.setCtodayschange(cursor.getString(cursor.getColumnIndex(KEY_CCHANGED)));
                model.setDeath(cursor.getString(cursor.getColumnIndex(KEY_DEATH)));
                model.setDtodayschange(cursor.getString(cursor.getColumnIndex(KEY_DCHANGED)));
                model.setRecovered(cursor.getString(cursor.getColumnIndex(KEY_RECOVERED)));
                model.setSerious(cursor.getString(cursor.getColumnIndex(KEY_SERIOUS)));
                // Adding to list
                dataArrayList.add(model);

            } while (cursor.moveToNext());
        }
        cursor.close();
        // return data list
        return dataArrayList;
    }

    public boolean checkName(String name) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME+ " WHERE "+KEY_NAME+" = '"+name+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return true;
        }
        cursor.close();
        return false;
    }
}
