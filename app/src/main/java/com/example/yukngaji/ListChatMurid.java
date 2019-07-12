package com.example.yukngaji;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Adapter.AdapterMurid;
import com.example.yukngaji.ui.Item.itemmurid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        preference=new UserPreference(this);
        setTitle("List murid");
        Tampildata();
    }
    public void Tampildata(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("GuruNgaji").child(preference.getUid()).addValueEventListener(new ValueEventListener() {
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
