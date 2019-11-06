package com.example.yukngaji.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yukngaji.ui.Item.itembokmark;

import java.util.ArrayList;

import static com.example.yukngaji.db.DatabaseContract.BookmarkColumns.ARTISURAT;
import static com.example.yukngaji.db.DatabaseContract.BookmarkColumns.IDAYAT;
import static com.example.yukngaji.db.DatabaseContract.BookmarkColumns.IDSURAT;
import static com.example.yukngaji.db.DatabaseContract.BookmarkColumns.NAMASURAT;
import static com.example.yukngaji.db.DatabaseContract.TABLE_Bookmark;

public class Bookmarkhelper {
    private static DatabaseHelper dataBaseHelper;
    private static Bookmarkhelper INSTANCE;
    private static SQLiteDatabase database;
    private Bookmarkhelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static Bookmarkhelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Bookmarkhelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }
    public ArrayList<itembokmark> getAllNotes() {
        ArrayList<itembokmark> arrayList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_Bookmark, null,
                null,
                null,
                null,
                null,
                IDSURAT + " ASC",
                null);
        cursor.moveToFirst();
        itembokmark itembokmarks;
        if (cursor.getCount() > 0) {
            do {
                itembokmarks = new itembokmark();
                itembokmarks.setIdayat(cursor.getString(cursor.getColumnIndexOrThrow(IDAYAT)));
                itembokmarks.setIdsurat(cursor.getString(cursor.getColumnIndexOrThrow(IDSURAT)));
                itembokmarks.setNamasurat(cursor.getString(cursor.getColumnIndexOrThrow(NAMASURAT)));
                itembokmarks.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTISURAT)));
                arrayList.add(itembokmarks);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    public long insertNote(itembokmark itembokmarks) {
        ContentValues args = new ContentValues();
        args.put(IDAYAT, itembokmarks.getIdayat());
        args.put(IDSURAT, itembokmarks.getIdsurat());
        args.put(NAMASURAT, itembokmarks.getNamasurat());
        args.put(ARTISURAT, itembokmarks.getArti());
        return database.insert(TABLE_Bookmark, null, args);
    }
    public int deleteNote(String id) {
        return database.delete(TABLE_Bookmark, IDSURAT + " = '" + id + "'", null);
    }
}
