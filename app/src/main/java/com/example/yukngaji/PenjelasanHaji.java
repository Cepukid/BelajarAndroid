package com.example.yukngaji;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yukngaji.ui.Item.ItemHaji;

public class PenjelasanHaji extends AppCompatActivity {
    ImageView foto;
    TextView Deskripsi,Nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjelasan_haji);
        Deskripsi=findViewById(R.id.penjelasanhaji);
        Nama=findViewById(R.id.namatravel);
        foto=findViewById(R.id.fotohaji);
        Intent i = getIntent();
        ItemHaji item = (ItemHaji)i.getSerializableExtra("data");
        Nama.setText(item.getNama());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Deskripsi.setText(Html.fromHtml(item.getPenjelasan(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            Deskripsi.setText(Html.fromHtml(item.getPenjelasan()));
        }
        Glide.with(this)
                .load(item.getGambar())
                .centerCrop()
                .placeholder(R.drawable.ic_lock_black_24dp)
                .into(foto);
    }
}
