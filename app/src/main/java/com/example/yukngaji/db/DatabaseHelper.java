package com.example.yukngaji.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbSahabatMengaji";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_Bookmark = String.format("CREATE TABLE %s"
                    + " (%s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_Bookmark,
            DatabaseContract.BookmarkColumns.IDAYAT,
            DatabaseContract.BookmarkColumns.IDSURAT,
            DatabaseContract.BookmarkColumns.NAMASURAT,
            DatabaseContract.BookmarkColumns.ARTISURAT
    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_Bookmark);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_Bookmark);
        onCreate(db);
    }
}
