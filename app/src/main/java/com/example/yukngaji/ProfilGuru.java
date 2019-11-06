package com.example.yukngaji;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.itemguru;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfilGuru extends AppCompatActivity {
    DatabaseReference reference;
    TextView Nama, JenisKelamin, alamat, telepon, lamabelajar;
    Button berhenti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guru);
        Nama = findViewById(R.id.namaguru);
        JenisKelamin = findViewById(R.id.JenisKelaminguru);
        alamat = findViewById(R.id.alamatguru);
        telepon = findViewById(R.id.teleponguru);
        berhenti = findViewById(R.id.berhentimengaji);
        lamabelajar = findViewById(R.id.lamabelajar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Tampildata();
        berhenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    public void Tampildata() {
        UserPreference preference = new UserPreference(this);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("GuruNgaji").child(preference.getUidGuru())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            itemguru item = dataSnapshot.getValue(itemguru.class);
                            Nama.setText(item.getNama());
                            JenisKelamin.setText(item.getJenisKelamin());
                            alamat.setText(item.getAlamat());
                            telepon.setText(item.getTelepon());
                            setTitle("Profil Guru " + item.getNama());
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

    private void showAlertDialog() {
        String dialogTitle, dialogMessage;
        dialogTitle = "Berhenti";
        dialogMessage = "Apakah anda ingin Berhenti?";

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserPreference preference = new UserPreference(ProfilGuru.this);
                        preference.setDaftar(false);
                        preference.setTunggu(false);
                        preference.setCekBayar(false);
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
