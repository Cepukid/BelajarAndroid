package com.example.yukngaji;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.itemdaftar;

import java.util.Objects;

public class Pembayaran extends AppCompatActivity {
    TextView Nama,Alamat,NoHp,Prefguru,Paket;
    Button bayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Nama=findViewById(R.id.namabayar);
        Alamat=findViewById(R.id.alamatbayar);
        NoHp=findViewById(R.id.nomorhp);
        Prefguru=findViewById(R.id.prefgurubayar);
        bayar=findViewById(R.id.bayar);
        UserPreference preference=new UserPreference(this);
        itemdaftar daftar=preference.getitem();
        Nama.setText(preference.getNama());
        Alamat.setText(daftar.getAlamat());
        Prefguru.setText(daftar.getPrefGuru());
        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Pembayaran.this,BayarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
