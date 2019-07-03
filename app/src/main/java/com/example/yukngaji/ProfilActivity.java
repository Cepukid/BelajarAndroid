package com.example.yukngaji;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yukngaji.ui.Item.ItemProfil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilActivity extends AppCompatActivity {
    EditText Nama,JenisKelamin,Provinsi,Kota,Kecamatan,Alamat,KodePos;
    Button saveprofil;
    DatabaseReference reference;
    Boolean edit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Nama=findViewById(R.id.namalengkap);
        JenisKelamin=findViewById(R.id.JenisKelamin);
        Provinsi=findViewById(R.id.Provinsi);
        Kota=findViewById(R.id.Kabupaten);
        Kecamatan=findViewById(R.id.Kecamatan);
        Alamat=findViewById(R.id.Alamat);
        KodePos=findViewById(R.id.KodePos);
        Tampildata();
        disableEditText();
        saveprofil=findViewById(R.id.saveprofil);
        saveprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit){
                    editdata();
                    saveprofil.setText("Update Profil");
                    disableEditText();
                    edit=false;
                }
                else{
                    EnableEditText();
                    saveprofil.setText("Simpan");
                    edit=true;
                }
            }
        });
    }
    public void disableEditText(){
        Nama.setEnabled(false);
        JenisKelamin.setEnabled(false);
        Provinsi.setEnabled(false);
        Kota.setEnabled(false);
        Kecamatan.setEnabled(false);
        Alamat.setEnabled(false);
        KodePos.setEnabled(false);
    }
    public void EnableEditText(){
        Nama.setEnabled(true);
        JenisKelamin.setEnabled(true);
        Provinsi.setEnabled(true);
        Kota.setEnabled(true);
        Kecamatan.setEnabled(true);
        Alamat.setEnabled(true);
        KodePos.setEnabled(true);
    }
    public void Tampildata(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Profil").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ItemProfil itemprofil = dataSnapshot.getValue(ItemProfil.class);
                            Nama.setText(itemprofil.getNama());
                            JenisKelamin.setText(itemprofil.getJenisKelamin());
                            Provinsi.setText(itemprofil.getProvinsi());
                            Kota.setText(itemprofil.getKota());
                            Kecamatan.setText(itemprofil.getKecamatan());
                            Alamat.setText(itemprofil.getAlamat());
                            KodePos.setText(itemprofil.getKodePos());
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
    public void editdata(){
        String getUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getReference;
        String nama=Nama.getText().toString();
        String jeniskelamin=JenisKelamin.getText().toString();
        String provinsi=Provinsi.getText().toString();
        String kota=Kota.getText().toString();
        String kecamatan=Kecamatan.getText().toString();
        String alamat=Alamat.getText().toString();
        String kodepos=KodePos.getText().toString();
        getReference = database.getReference();
        getReference.child("Profil").child(getUserID)
                .setValue(new ItemProfil(nama,jeniskelamin,provinsi,kota,kecamatan,alamat,kodepos))
                .addOnSuccessListener(ProfilActivity.this, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ProfilActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                        Tampildata();
                    }
                });
    }
}
