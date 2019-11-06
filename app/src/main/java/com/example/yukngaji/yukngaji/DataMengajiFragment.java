package com.example.yukngaji.yukngaji;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yukngaji.R;
import com.example.yukngaji.setting.UserPreference;
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
public class DataMengajiFragment extends Fragment implements BlockingStep {
    Spinner jenjang, PreferensiGuru;
    DatabaseReference reference;
    List<String> item;
    private ProgressDialog progressBar;


    public DataMengajiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_data_mengaji, container, false);
        jenjang = view.findViewById(R.id.jenjang);
        PreferensiGuru=view.findViewById(R.id.preferensiguru);
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        isidatajenjang();
        isidataPreferensiGuru();

        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if(jenjang.getSelectedItem().toString().equals("")){
            ((TextView)jenjang.getSelectedView()).setError("Error message");
        }else {
            if(PreferensiGuru.getSelectedItem().toString().equals("")){
                ((TextView)PreferensiGuru.getSelectedView()).setError("Error message");
            }else{
                        UserPreference preference=new UserPreference(getActivity());
                preference.setDataMengaji(jenjang.getSelectedItem().toString(), PreferensiGuru.getSelectedItem().toString());
                        callback.goToNextStep();
                    }
                }
            }


    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        getActivity().finish();
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
    public void isidatajenjang(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("YukNgaji").child("Jenjang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itemspinner itemspin = noteDataSnapshot.getValue(itemspinner.class);
                    item.add(itemspin.getNama());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,
                        item);
                jenjang.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void isidataPreferensiGuru(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("YukNgaji").child("PreferensiGuru").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                item = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    itemspinner itemspin = noteDataSnapshot.getValue(itemspinner.class);
                    item.add(itemspin.getNama());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,
                        item);
                //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                PreferensiGuru.setAdapter(adapter);
                progressBar.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
