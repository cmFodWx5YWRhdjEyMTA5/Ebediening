package com.ebediening.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebediening.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Check_IN extends Fragment {


    public Fragment_Check_IN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_check_in, container, false);

        return v;
    }

}
