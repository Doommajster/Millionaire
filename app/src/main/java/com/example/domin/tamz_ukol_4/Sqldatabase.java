package com.example.domin.tamz_ukol_4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Sqldatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db";
    private static final String TABLE_NAME = "tablo";
    private static final String ID = "id";
    private static final String SKORE = "skore";
    private static final String CAS = "cas";
    private static final int DATABASE_VERSION = 1;
    public Sqldatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createcmd =
                new StringBuilder().append("CREATE TABLE ")
                        .append(TABLE_NAME).append(" ( ")
                        .append(ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                        .append(CAS).append(" TEXT, ")
                        .append(SKORE).append(" INTEGER ); ").toString();
        db.execSQL(createcmd);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addScore(int score, String datum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(SKORE, score);
        values.put(CAS, ""+datum);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public void deleteAllScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }


    public ArrayList<PolozkyDb> getAllScores() {
        ArrayList<PolozkyDb> seznamPolozek =new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex(ID));
            int score =cursor.getInt(cursor.getColumnIndex(SKORE));
            String datum =cursor.getString(cursor.getColumnIndex(CAS));
            PolozkyDb newscores = new PolozkyDb(id,datum,score);
            seznamPolozek.add(newscores);
        }
        cursor.close();
        db.close();
        return seznamPolozek;


    }

    public ArrayList<PolozkyDb> getAllScoresHighest() {
        ArrayList<PolozkyDb> seznamPolozek =new ArrayList<>();
        String sql_command = "SELECT * FROM " + TABLE_NAME+ " ORDER BY "+ SKORE +" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql_command,null);
        while(cursor.moveToNext()){
            Long id = cursor.getLong(cursor.getColumnIndex(ID));
            int score =cursor.getInt(cursor.getColumnIndex(SKORE));
            String datum =cursor.getString(cursor.getColumnIndex(CAS));
            PolozkyDb newscores = new PolozkyDb(id,datum,score);
            seznamPolozek.add(newscores);
        }
        cursor.close();
        db.close();
        return seznamPolozek;


    }

}
