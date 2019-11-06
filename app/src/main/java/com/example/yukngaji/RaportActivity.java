package com.example.yukngaji;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yukngaji.ui.Adapter.AdapterRaport;
import com.example.yukngaji.ui.Item.itemraport;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RaportActivity extends AppCompatActivity {
    TextView nama, kelas;
    Button save;
    DatabaseReference reference;
    private RecyclerView mRecyclerView;
    private AdapterRaport adapterRaport;
    private ArrayList<itemraport> dataraport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);
        nama = findViewById(R.id.namamurid);
        kelas = findViewById(R.id.namakelas);
        save = findViewById(R.id.saveraport);
        setTitle("Raport");
        mRecyclerView = findViewById(R.id.rv_raport);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        if (getIntent().getExtras() != null) {
            String uid = getIntent().getStringExtra("uid");
            Tampildata(uid);
        } else {
            Tampildata(FirebaseAuth.getInstance().getCurrentUser().getUid());
            save.setVisibility(View.INVISIBLE);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void Tampildata(final String uid) {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Murid").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            nama.setText(dataSnapshot.child("nama").getValue().toString());
                            kelas.setText(dataSnapshot.child("paket").getValue().toString());
                            Tampildataraport(uid);
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

    public void Tampildataraport(String uid) {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Murid").child(uid).child("raport")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            dataraport = new ArrayList<>();
                            int no = 1;
                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                String nama = noteDataSnapshot.child("Nama").getValue().toString();
                                String nilai = noteDataSnapshot.child("Nilai").getValue().toString();
                                dataraport.add(new itemraport(nama, nilai, String.valueOf(no)));
                                no++;
                            }
                            adapterRaport = new AdapterRaport(RaportActivity.this, dataraport);
                            mRecyclerView.setAdapter(adapterRaport);
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
