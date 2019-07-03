package com.example.yukngaji.yukngaji;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yukngaji.R;
import com.example.yukngaji.YukNgajiMurid;
import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.ItemStore;
import com.example.yukngaji.ui.Item.itemspinner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LokasiMengajiFragment extends Fragment implements BlockingStep {

    EditText txt_alamat;
    Spinner Provinsi,kota,kecamatan;
    DatabaseReference reference;
    List<String> item;
    private ProgressDialog progressBar;
    public LokasiMengajiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lokasi_mengaji, container, false);
        Provinsi=view.findViewById(R.id.Provinsi);
        kota=view.findViewById(R.id.Kota);
        kecamatan=view.findViewById(R.id.Kecamatan);
        txt_alamat=view.findViewById(R.id.alamatlengkap);
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        isidataprovinsi();
        isidatakota();
        isidataKecmatan();
        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if(Provinsi.getSelectedItem().toString().equals("")){
            ((TextView)Provinsi.getSelectedView()).setError("Error message");
        }else {
            if(kota.getSelectedItem().toString().equals("")){
                ((TextView)kota.getSelectedView()).setError("Error message");
            }else{
                if(kota.getSelectedItem().toString().equals("")){
                    ((TextView)kota.getSelectedView()).setError("Error message");
                }else {
                    if(txt_alamat.getText().toString().equals("")){
                        txt_alamat.setError("Masukkan Alamat");
                    }else {
                        UserPreference preference=new UserPreference(getActivity());
                        preference.setLokasiMengaji(Provinsi.getSelectedItem().toString(),kota.getSelectedItem().toString(),kecamatan.getSelectedItem().toString(),txt_alamat.getText().toString());
                        callback.goToNextStep();
                    }
                }
            }
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
    public void isidataprovinsi(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("YukNgaji").child("Provinsi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itemspinner itemspin = noteDataSnapshot.getValue(itemspinner.class);
                    item.add(itemspin.getNama());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,
                        item);
                Provinsi.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void isidatakota(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("YukNgaji").child("Kota").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itemspinner itemspin = noteDataSnapshot.getValue(itemspinner.class);
                    item.add(itemspin.getNama());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,
                        item);
                kota.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void isidataKecmatan(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("YukNgaji").child("Kecamatan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itemspinner itemspin = noteDataSnapshot.getValue(itemspinner.class);
                    item.add(itemspin.getNama());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,
                        item);
                kecamatan.setAdapter(adapter);
                progressBar.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
