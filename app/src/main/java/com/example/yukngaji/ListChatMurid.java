package com.example.yukngaji;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Adapter.AdapterMurid;
import com.example.yukngaji.ui.Item.itemmurid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ListChatMurid extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private AdapterMurid mAdapter;
    private ArrayList<itemmurid> listmurid;
    UserPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat_murid);
        recyclerView =  findViewById(R.id.rv_murid);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        preference=new UserPreference(this);
        setTitle("List murid");
        Tampildata();
    }
    public void Tampildata(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("GuruNgaji").child(preference.getUid()).child("Murid").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listmurid=new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itemmurid murid = noteDataSnapshot.getValue(itemmurid.class);
                    listmurid.add(murid);
                }
                mAdapter=new AdapterMurid(ListChatMurid.this,listmurid);
                recyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
