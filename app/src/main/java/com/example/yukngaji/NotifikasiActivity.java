package com.example.yukngaji;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yukngaji.ui.Adapter.AdapterNotifikasi;
import com.example.yukngaji.ui.Item.ItemNotif;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        Tampildata();
    }
    public void Tampildata(){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Promo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listNotif=new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ItemNotif murid = noteDataSnapshot.getValue(ItemNotif.class);
                    listNotif.add(murid);
                }
                mAdapter=new AdapterNotifikasi(NotifikasiActivity.this,listNotif);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
