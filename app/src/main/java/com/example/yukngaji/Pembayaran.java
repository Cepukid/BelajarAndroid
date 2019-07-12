package com.example.yukngaji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.itemdaftar;

public class Pembayaran extends AppCompatActivity {
    TextView Nama,Alamat,NoHp,Prefguru,Paket;
    Button bayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        Nama=findViewById(R.id.namabayar);
        Alamat=findViewById(R.id.alamatbayar);
        NoHp=findViewById(R.id.nomorhp);
        Prefguru=findViewById(R.id.prefgurubayar);
        Paket=findViewById(R.id.paketbayar);
        bayar=findViewById(R.id.bayar);
        UserPreference preference=new UserPreference(this);
        itemdaftar daftar=preference.getitem();
        Nama.setText(preference.getNama());
        Alamat.setText(daftar.getAlamat());
        Prefguru.setText(daftar.getPrefGuru());
        Paket.setText(daftar.getPaket());
        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Pembayaran.this,BayarActivity.class);
                startActivity(intent);
            }
        });
    }
}
