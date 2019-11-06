package com.example.yukngaji;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.ui.Adapter.AdapterNotifikasi;
import com.example.yukngaji.ui.Item.ItemNotif;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class NotifikasiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterNotifikasi mAdapter;
    private ArrayList<ItemNotif> listNotif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        recyclerView =  findViewById(R.id.rv_list_notif);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        setTitle("Notification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Tampildata();
    }
    public void Tampildata(){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Promo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<ItemNotif> itemss = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    String deskripsi = noteDataSnapshot.child("deskripsi").getValue().toString();
                    String gambar = noteDataSnapshot.child("gambar").getValue().toString();
                    String judul = noteDataSnapshot.child("judul").getValue().toString();
                    ItemNotif murid = new ItemNotif(judul, gambar, deskripsi);
                    itemss.add(murid);
                }
                mAdapter = new AdapterNotifikasi(NotifikasiActivity.this, itemss);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
