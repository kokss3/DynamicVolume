package com.example.koks.dynamicvolume.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Archiver extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "archive";
    private final static String DATABASE_MESSAGE = "message";
    private final static String ID = "id";
    private final static int VERSION = 1;

    private static final String EXEC_NAME = "create table " + DATABASE_NAME + " ( "+ ID + " int(10) primary key autoincrement, "+ DATABASE_MESSAGE + " varchar(255))";

    public Archiver(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EXEC_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void saveMessage(String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATABASE_MESSAGE, message);
        db.insert(DATABASE_NAME, null, contentValues);
        db.close();
    }

    public List<String> getAllMessages(){
        List<String> messages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cd = db.rawQuery("select " + DATABASE_MESSAGE + " from " + DATABASE_NAME,null);

        if(cd!=null) while (cd.moveToNext())messages.add(cd.getString(cd.getColumnIndex(DATABASE_MESSAGE)));
        assert cd != null;
        cd.close();
        return messages;
    }

    public String getMessage(int id){
        String message = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cd = db.rawQuery("select "+DATABASE_MESSAGE+" from "+DATABASE_NAME+" where "+ID+"=?", new String[] {String.valueOf(id)} );

        if(cd!=null) while (cd.moveToNext())message=cd.getString(cd.getColumnIndex(DATABASE_MESSAGE));
        assert cd != null;
        cd.close();
        return message;
    }
}
