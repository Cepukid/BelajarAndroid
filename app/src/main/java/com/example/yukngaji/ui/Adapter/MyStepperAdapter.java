package com.example.yukngaji.ui.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.example.yukngaji.yukngaji.DataDiriFragment;
import com.example.yukngaji.yukngaji.DataMengajiFragment;
import com.example.yukngaji.yukngaji.LokasiMengajiFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class MyStepperAdapter extends AbstractFragmentStepAdapter {

    public MyStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
    }
    @Override
    public Step createStep(int position) {
        if(position==0){
            DataMengajiFragment dataMengajiFragment = new DataMengajiFragment();
            Bundle b = new Bundle();
            b.putInt("vvvvv", position);
            dataMengajiFragment.setArguments(b);
            return dataMengajiFragment;
        }else if(position==1){
            LokasiMengajiFragment lokasiMengajiFragment = new LokasiMengajiFragment();
            Bundle b = new Bundle();
            b.putInt("vvvvv", position);
            lokasiMengajiFragment.setArguments(b);
            return lokasiMengajiFragment;
        }else if(position==2){
            DataDiriFragment dataDiriFragment = new DataDiriFragment();
            Bundle b = new Bundle();
            b.putInt("vvvvv", position);
            dataDiriFragment.setArguments(b);
            return dataDiriFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        StepViewModel.Builder builder =new StepViewModel.Builder(context);
        switch (position) {
            case 0:
                builder.setTitle("Data Mengaji");
                builder.setBackButtonVisible(false);
                builder.setEndButtonLabel("Selanjutnya");
                break;
            case 1:
                builder.setTitle("Lokasi Mengaji");
                builder.setEndButtonLabel("Selanjutnya");
                builder.setBackButtonLabel("Kembali");
                break;
            case 2:
                builder.setTitle("Data Diri");
                builder.setBackButtonLabel("Kembali");
                builder.setEndButtonLabel("kirim");
                break;
        }
        return builder.create();
    }
}
