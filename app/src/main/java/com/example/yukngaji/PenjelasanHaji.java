package com.example.yukngaji;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.yukngaji.ui.Item.ItemHaji;

import java.util.Objects;

public class PenjelasanHaji extends AppCompatActivity {
    ImageView foto;
    TextView Deskripsi, Nama, alamat;
    Button telepon, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjelasan_haji);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Deskripsi=findViewById(R.id.penjelasanhaji);
        Nama=findViewById(R.id.namatravel);
        foto=findViewById(R.id.fotohaji);
        alamat = findViewById(R.id.alamathaji);
        telepon = findViewById(R.id.telepon);
        website = findViewById(R.id.website);
        Intent i = getIntent();
        final ItemHaji item = (ItemHaji) i.getSerializableExtra("data");
        setTitle(item.getNama());
        Nama.setText(item.getNama());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Deskripsi.setText(Html.fromHtml(item.getDeskripsi(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            Deskripsi.setText(Html.fromHtml(item.getDeskripsi()));
        }
        alamat.setText(item.getAlamat());
        Glide.with(this)
                .load(item.getGambar())
                .centerCrop()
                .placeholder(R.drawable.ic_lock_black_24dp)
                .into(foto);
        telepon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent panggil = new Intent(Intent.ACTION_DIAL);
                panggil.setData(Uri.fromParts("tel", item.getTelepon(), null));
                startActivity(panggil);
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bukabrowser = new Intent(Intent.ACTION_VIEW);
                bukabrowser.setData(Uri.parse(item.getWebsite()));
                startActivity(bukabrowser);
            }
        });
    }
}