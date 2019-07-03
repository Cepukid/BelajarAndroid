package com.example.yukngaji;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataNgajiFragment extends Fragment {
    TextView a;

    public DataNgajiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_data_ngaji, container, false);
        a=view.findViewById(R.id.tesesewea);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YukNgajiMurid yukNgajiMurid=(YukNgajiMurid) getActivity();
                //yukNgajiMurid.a.setText("janc7k");
            }
        });
        return view;
    }

}
