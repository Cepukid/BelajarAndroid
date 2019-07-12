package com.example.yukngaji.yukngaji;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yukngaji.BayarActivity;
import com.example.yukngaji.Pembayaran;
import com.example.yukngaji.R;
import com.example.yukngaji.setting.UserPreference;
import com.example.yukngaji.ui.Item.itemdaftar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataDiriFragment extends Fragment implements BlockingStep {

    EditText txt_nama,txt_email,txt_nohp;
    public DataDiriFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_data_diri, container, false);
        txt_email=view.findViewById(R.id.emaildiri);
        txt_nama=view.findViewById(R.id.namalengkap);
        txt_nohp=view.findViewById(R.id.nomorhp);
        return view;
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        if(txt_nama.getText().toString().equals("")){
            txt_nama.setError("Nama belum di isi");
        }else {
            if(!Cekemailvalid(txt_email.getText())){
                txt_email.setError("Email Salah");
            }else{
                if(txt_nohp.getText().toString().length()<10){
                    txt_nohp.setError("Masukkan Nomor");
                }else {
                    callback.complete();
                    showalert();
                }
            }
        }
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
    boolean Cekemailvalid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void showalert(){

                String dialogTitle, dialogMessage;
                dialogTitle = "Apakah anda Yakin?";


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setTitle(dialogTitle);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                savedata();

                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            public void savedata(){
                String getUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                UserPreference preference=new UserPreference(getActivity());
                preference.setNama(txt_nama.getText().toString());
                itemdaftar daftar=preference.getitem();
                daftar.setNama(txt_nama.getText().toString());
                daftar.setNohp(txt_nohp.getText().toString());
                daftar.setEmail(txt_email.getText().toString());
                daftar.setToken(FirebaseInstanceId.getInstance().getToken());
                DatabaseReference getReference;
                getReference = database.getReference();
                getReference.child("Murid").child(getUserID)
                        .setValue(daftar)
                        .addOnSuccessListener(getActivity(), new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                UserPreference preference=new UserPreference(getActivity());
                                preference.setDaftar(true);
                                Intent intent=new Intent(getActivity(), Pembayaran.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
            }
}
