package com.ebediening.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ebediening.MainActivity;
import com.ebediening.R;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Scanner extends Fragment implements BarcodeReader.BarcodeReaderListener {
    private BarcodeReader barcodeReader;

    public Fragment_Scanner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scanner, container, false);
        barcodeReader = (BarcodeReader) getFragmentManager().findFragmentById(R.id.barcode_fragment);
        return v;
    }

    @Override
    public void onScanned(Barcode barcode) {
        // play beep sound
        barcodeReader.playBeep();
        MainActivity.image_heading.setVisibility(View.GONE);
        MainActivity.text_heading.setVisibility(View.VISIBLE);
        MainActivity.text_heading.setText("CHECK-IN");
//        MainActivity.share.setVisibility(View.VISIBLE);
//        MainActivity.logout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction_scan = getFragmentManager().beginTransaction();
        Fragment_Check_IN frag_scan = new Fragment_Check_IN();
        fragmentTransaction_scan.replace(R.id.main_content, frag_scan);
        fragmentTransaction_scan.addToBackStack(null);
        fragmentTransaction_scan.commit();

    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {
        barcodeReader.playBeep();
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        barcodeReader.playBeep();
    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
