package com.example.yukngaji;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.ui.Adapter.StoreAdapter;
import com.example.yukngaji.ui.Item.ItemStore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StoreActivity extends AppCompatActivity {
    DatabaseReference reference;
    private ArrayList<ItemStore> liststore;
    private RecyclerView rvView;
    StoreAdapter storeAdapter;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setTitle("Store");
        progressBar = new ProgressDialog(this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        rvView = findViewById(R.id.rv_store);
        Tampildata();
    }
    public void Tampildata(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Store").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        liststore=new ArrayList<>();
                        for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                            ItemStore store = noteDataSnapshot.getValue(ItemStore.class);
                            liststore.add(store);
                        }
                        storeAdapter=new StoreAdapter(StoreActivity.this,liststore);
                        rvView.setAdapter(storeAdapter);
                        progressBar.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
