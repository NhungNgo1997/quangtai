package com.example.pnam.doctruyen.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pnam.doctruyen.Object.Truyen;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME ="databa_truyen";
    private static final String TABLE_NAME ="table_truyen";
    private static final String TITLETRUYEN ="titletruyen";
    private static final String DESCRIPTIONTRUYEN ="descriptiontruyen";
    private static final String LINKTRUYEN ="linktruyen";
    private static final String URLIMGTRUYEN ="urlImgtruyen";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQuery = "CREATE TABLE " + TABLE_NAME + "("
                + TITLETRUYEN + " TEXT,"
                + DESCRIPTIONTRUYEN + " TEXT,"
                + LINKTRUYEN + " TEXT,"
                + URLIMGTRUYEN + " TEXT" + ")";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public long addTruyen(Truyen truyen){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLETRUYEN,truyen.getTitleTruyen());
        values.put(DESCRIPTIONTRUYEN,truyen.getDescriptionTruyen());
        values.put(LINKTRUYEN,truyen.getLinkTruyen());
        values.put(URLIMGTRUYEN,truyen.getUrlImgTruyen());

        long i = db.insert(TABLE_NAME,null,values);
        db.close();
        return i;
    }

    public ArrayList<Truyen> getAllData(){
        ArrayList<Truyen> truyens = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Truyen truyen = new Truyen();
                truyen.setTitleTruyen(cursor.getString(0));
                truyen.setDescriptionTruyen(cursor.getString(1));
                truyen.setLinkTruyen(cursor.getString(2));
                truyen.setUrlImgTruyen(cursor.getString(3));
                truyens.add(truyen);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return truyens;
    }

    public void deleteTruyen(Truyen truyen) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TITLETRUYEN + " = ?",
                new String[] { String.valueOf(truyen.getTitleTruyen()) });
        db.close();
    }

}
