package com.example.yukngaji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yukngaji.db.Bookmarkhelper;
import com.example.yukngaji.ui.Item.itembokmark;
import com.example.yukngaji.ui.Adapter.BookmarkAdapter;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    private itembokmark itembokmarks;
    private int position;
    private Bookmarkhelper bookmarkhelper;
    private BookmarkAdapter adapter;
    private RecyclerView rvbookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        bookmarkhelper=Bookmarkhelper.getInstance(getApplicationContext());
        rvbookmark = findViewById(R.id.rv__bookmark);
        setTitle("Bookmark");
        rvbookmark.setLayoutManager(new LinearLayoutManager(this));
        rvbookmark.setHasFixedSize(true);
        bookmarkhelper.open();
        ArrayList<itembokmark>a=bookmarkhelper.getAllNotes();
        adapter = new BookmarkAdapter(this);
        adapter.setlistbokmark(a);
        rvbookmark.setAdapter(adapter);
    }
}
