package com.example.yukngaji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yukngaji.ui.Adapter.MyStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class YukNgajiMurid extends AppCompatActivity implements StepperLayout.StepperListener{
    public StepperLayout mStepperLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuk_ngaji_murid);
        mStepperLayout = findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), this));
    }

    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {
        finish();
    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }
    @Override
    public void onReturn() {
        finish();
    }

}
