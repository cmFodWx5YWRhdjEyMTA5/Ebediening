package com.ebediening.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebediening.Adapter_Class.ReservationAdapter;
import com.ebediening.R;
import com.ebediening.Response.BookingResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationFragment extends Fragment {
    TextView text_no_record;
    ListView list_reservation;
    ReservationAdapter reservationAdapter;


    public ReservationFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        ButterKnife.bind(this,view);
        getBookings();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getBookings() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvBooking.setLayoutManager(mLayoutManager);
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        ebedieningService.getBookings(Preferences.getInstance().getUserId()).
                enqueue(new Callback<BookingResponse>() {
                    @Override
                    public void onResponse(Call<BookingResponse> call, retrofit2.Response<BookingResponse> response) {
                        if (response.body().getFlag() == 1) {
                            reservationAdapter = new ReservationAdapter(getContext(), response.body().getBookDataList(),response.body().getImageBasePath());
                            rvBooking.setAdapter(reservationAdapter);

                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<BookingResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @BindView(R.id.rv_booking)
    RecyclerView rvBooking;

}
