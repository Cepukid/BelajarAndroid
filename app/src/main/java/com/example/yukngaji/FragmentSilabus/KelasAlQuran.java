package com.example.yukngaji.FragmentSilabus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yukngaji.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelasAlQuran extends Fragment {


    public KelasAlQuran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kelas_al_quran, container, false);
    }

}
