package com.example.yukngaji;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.itemprofilmurid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfilMurid extends AppCompatActivity {
    DatabaseReference reference;
    TextView Nama, kecamatan, alamat, email, telepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_murid);
        Nama = findViewById(R.id.namamurid);
        kecamatan = findViewById(R.id.kecamatanmurid);
        alamat = findViewById(R.id.alamatmurid);
        email = findViewById(R.id.emailmurid);
        telepon = findViewById(R.id.teleponmurid);
        Intent intent = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String uid = intent.getStringExtra("uid");
        Tampildata(uid);
    }

    public void Tampildata(String uid) {
        UserPreference preference = new UserPreference(this);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Murid").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            itemprofilmurid item = dataSnapshot.getValue(itemprofilmurid.class);
                            Nama.setText(item.getNama());
                            kecamatan.setText(item.getKecamatan());
                            alamat.setText(item.getAlamat());
                            email.setText(item.getEmail());
                            telepon.setText(item.getNohp());
                        } else {
                            //here means the value not exist
                            //do whatever you want to do
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
