package com.example.yukngaji;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.yukngaji.ui.Adapter.StoreAdapter;
import com.example.yukngaji.ui.Item.ItemStore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
