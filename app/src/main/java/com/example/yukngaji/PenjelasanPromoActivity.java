package com.example.yukngaji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PenjelasanPromoActivity extends AppCompatActivity {
    TextView Judul,Penjelasan;
    ImageView Gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjelasan_promo);
        Judul=findViewById(R.id.judulnotifikasi);
        Penjelasan=findViewById(R.id.penjelasannotifikasi);
        Gambar=findViewById(R.id.gambarnotifikasi);
        Intent intent = getIntent();
        String judul = intent.getStringExtra("judul");
        String penjelasan = intent.getStringExtra("penjelasan");
        String gambar = intent.getStringExtra("gambar");
        Judul.setText(judul);
        Penjelasan.setText(penjelasan);
        Glide.with(this)
                .load(gambar)
                .centerCrop()
                .placeholder(R.drawable.ic_lock_black_24dp)
                .into(Gambar);
    }
}
