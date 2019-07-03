package com.example.yukngaji;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yukngaji.ui.Item.ItemStore;

public class Penjelasan extends AppCompatActivity {
    ImageView fotobarang;
    TextView Harga,Stok,Deskripsi,Nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjelasan);
        fotobarang=findViewById(R.id.fotostore);
        Harga=findViewById(R.id.hargabarang);
        Stok=findViewById(R.id.Stokbarang);
        Deskripsi=findViewById(R.id.penjelasan);
        Nama=findViewById(R.id.namabarang);
        Intent i = getIntent();
        ItemStore itemStore = (ItemStore)i.getSerializableExtra("data");
        Nama.setText(itemStore.getNama());
        Harga.setText(itemStore.getHarga());
        Stok.setText(itemStore.getStok());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Deskripsi.setText(Html.fromHtml(itemStore.getDeskripsi(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            Deskripsi.setText(Html.fromHtml(itemStore.getDeskripsi()));
        }
        Glide.with(this)
                .load(itemStore.getGambar())
                .centerCrop()
                .placeholder(R.drawable.ic_lock_black_24dp)
                .into(fotobarang);
    }
}
