package com.example.yukngaji.yukngaji;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataMengajiFragment extends Fragment implements BlockingStep {
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText txt_tgl;
    Spinner jenjang,PreferensiGuru,PaketMengaji;
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
        txt_tgl = view.findViewById(R.id.txt_tgl);
        jenjang = view.findViewById(R.id.jenjang);
        PreferensiGuru=view.findViewById(R.id.preferensiguru);
        PaketMengaji=view.findViewById(R.id.paketmengaji);
        myCalendar = Calendar.getInstance();
        progressBar = new ProgressDialog(view.getContext());
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        isidatajenjang();
        isidataPaketMengaji();
        isidataPreferensiGuru();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        txt_tgl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txt_tgl.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if(jenjang.getSelectedItem().toString().equals("")){
            ((TextView)jenjang.getSelectedView()).setError("Error message");
        }else {
            if(PreferensiGuru.getSelectedItem().toString().equals("")){
                ((TextView)PreferensiGuru.getSelectedView()).setError("Error message");
            }else{
                if(PaketMengaji.getSelectedItem().toString().equals("")){
                    ((TextView)PaketMengaji.getSelectedView()).setError("Error message");
                }else {
                    if(txt_tgl.getText().toString().equals("")){
                        txt_tgl.setError("Masukkan Tanngal");
                    }else {
                        UserPreference preference=new UserPreference(getActivity());
                        preference.setDataMengaji(jenjang.getSelectedItem().toString(),txt_tgl.getText().toString(),PreferensiGuru.getSelectedItem().toString(),PaketMengaji.getSelectedItem().toString());
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
    public void isidataPaketMengaji(){
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("YukNgaji").child("PaketMengaji").addValueEventListener(new ValueEventListener() {
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
                PaketMengaji.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
