package com.example.yukngaji;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.ui.Adapter.HajiAdapter;
import com.example.yukngaji.ui.Item.ItemHaji;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HajiActivity extends AppCompatActivity {
    DatabaseReference reference;
    private ArrayList<ItemHaji> listhaji;
    private RecyclerView rvView;
    HajiAdapter hajiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haji);
        rvView = findViewById(R.id.rv_haji);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("List Travel Haji");
        Tampildata();
    }
    public void Tampildata(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Travel").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listhaji=new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    ItemHaji store = noteDataSnapshot.getValue(ItemHaji.class);
                    listhaji.add(store);
                }
                hajiAdapter=new HajiAdapter(HajiActivity.this,listhaji);
                rvView.setAdapter(hajiAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
