package com.example.mylogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 5;
    public static final String TABLE_NAME = "User";
    private static String DROP_TABLE = "drop_table";
    public static final String ID = "_id";
    public static final String NAME = "productname";
    public static final String CATEGORY = "category";
    public static final String TYPE = "type";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB_TABLE =
                " CREATE TABLE " + TABLE_NAME +
                        " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " productname TEXT NOT NULL, " + " category TEXT NOT NULL , " +
                        " type TEXT NOT NULL);";
        db.execSQL(CREATE_DB_TABLE);
    }

    public static void dropTable(SQLiteDatabase db) {

        db.execSQL(DROP_TABLE + NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String name, String category, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(CATEGORY, category);
        contentValues.put(TYPE, type);
        long result = db.insert(TABLE_NAME, null, contentValues);
        Log.i("Log", "result:" + result);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateData(int id, String name, String category, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(CATEGORY, category);
        contentValues.put(TYPE, type);
        long result = db.update(TABLE_NAME, contentValues, "_id = ?", new String[]{String.valueOf(id)});
        Log.i("Log", "result:" + result);
        if (result == 0)
            return false;
        else
            return true;
    }

    public Integer deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<NoteModel> getAllData() {
        ArrayList<NoteModel> arrayList = new ArrayList<>();


        String select_query = "SELECT *FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);


        if (cursor.moveToFirst()) {
            do {
                NoteModel noteModel = new NoteModel();
                noteModel.set_id(cursor.getInt(0));
                noteModel.setName(cursor.getString(1));
                noteModel.setCategory(cursor.getString(2));
                noteModel.setType(cursor.getString(3));
                arrayList.add(noteModel);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }


}
