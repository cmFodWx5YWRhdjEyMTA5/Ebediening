package com.ebediening.Fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ebediening.Activity_Check_IN;
import com.ebediening.Activity_NearBy;
import com.ebediening.Activity_Offers;
import com.ebediening.Utilites.GPSTracker;
import com.ebediening.MainActivity;
import com.ebediening.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment implements View.OnClickListener {
    LinearLayout linear_nearby, linear_checkin, linear_reservation, linear_offers;
    GPSTracker tracker;

    public Fragment_Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        tracker = new GPSTracker(getActivity());
        linear_nearby = (LinearLayout) v.findViewById(R.id.linear_nearby);
        linear_nearby.setOnClickListener(this);
        linear_checkin = (LinearLayout) v.findViewById(R.id.linear_checkin);
        linear_checkin.setOnClickListener(this);
        linear_reservation = (LinearLayout) v.findViewById(R.id.linear_reservation);
        linear_reservation.setOnClickListener(this);
        linear_offers = (LinearLayout) v.findViewById(R.id.linear_offers);
        linear_offers.setOnClickListener(this);
        if (tracker.getIsGPSTrackingEnabled()) {
            check_permission_location();
        } else {
            showSettingsAlert();
        }
        return v;
    }

    private void check_permission_location() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == 0) {
            //perform action
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            12);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_nearby:
                Intent intent = new Intent(getActivity(), Activity_NearBy.class);
                getActivity().startActivity(intent);
                break;

            case R.id.linear_checkin:

//                MainActivity.image_heading.setVisibility(View.GONE);
//                MainActivity.text_heading.setVisibility(View.VISIBLE);
//                MainActivity.text_heading.setText("QR Code Scan");
//                FragmentTransaction fragmentTransaction_scan = getFragmentManager().beginTransaction();
//                Fragment_Scanner frag_scan = new Fragment_Scanner();
//                fragmentTransaction_scan.replace(R.id.main_content, frag_scan);
//                fragmentTransaction_scan.addToBackStack(null);
//                fragmentTransaction_scan.commit();
//                Intent i = new Intent(getActivity(), Activity_Scanner_Checkin.class);
//                getActivity().startActivity(i);

                Intent i = new Intent(getActivity(), Activity_Check_IN.class);
                getActivity().startActivity(i);
                break;

            case R.id.linear_reservation:
                MainActivity.image_heading.setVisibility(View.GONE);
                MainActivity.text_heading.setVisibility(View.VISIBLE);
                MainActivity.text_heading.setText("Reservation");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ReservationFragment frag_home = new ReservationFragment();
                fragmentTransaction.replace(R.id.main_content, frag_home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;

            case R.id.linear_offers:
                Intent i_OFFERS = new Intent(getActivity(), Activity_Offers.class);
                getActivity().startActivity(i_OFFERS);
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        //Setting Dialog Title
        alertDialog.setTitle("Alert");

        //Setting Dialog Message
        alertDialog.setMessage("Check GPS connection");

        //On Pressing Setting button
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1);
                dialog.dismiss();
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            check_permission_location();
        }
    }
}
