package com.example.yukngaji.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_Bookmark = "Bookmark";
    static final class BookmarkColumns implements BaseColumns {
        static String IDAYAT = "IdAyat";
        static String IDSURAT = "IdSurat";
        static String NAMASURAT = "NamaSurat";
        static String ARTISURAT= "ArtiSurat";
    }
}
