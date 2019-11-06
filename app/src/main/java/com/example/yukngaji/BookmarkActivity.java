package com.example.yukngaji;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.db.Bookmarkhelper;
import com.example.yukngaji.ui.Adapter.BookmarkAdapter;
import com.example.yukngaji.ui.Item.itembokmark;

import java.util.ArrayList;
import java.util.Objects;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
