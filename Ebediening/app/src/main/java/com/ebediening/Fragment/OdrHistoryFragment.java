package com.ebediening.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ebediening.Adapter_Class.OdrHistoryAdapter;
import com.ebediening.R;
import com.ebediening.Response.OdrHistoryResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class OdrHistoryFragment extends Fragment {
    OdrHistoryResponse.HistoryData.KeyList keyList;
    List<OdrHistoryResponse.HistoryData.KeyList> keyLists=new ArrayList<>();
    OdrHistoryAdapter odrHistoryAdapter;
    HashMap<String, OdrHistoryResponse.HistoryData.KeyList> entityList;
    private OnFragmentInteractionListener mListener;

    public OdrHistoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_odr_history, container, false);
        ButterKnife.bind(this,view);
        getOrders();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getOrders() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvOrder.setLayoutManager(mLayoutManager);
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        ebedieningService.orderHistory(Preferences.getInstance().getUserId()).
                enqueue(new Callback<OdrHistoryResponse>() {
                    @Override
                    public void onResponse(Call<OdrHistoryResponse> call, retrofit2.Response<OdrHistoryResponse> response) {
                        if (response.body().getFlag() == 1) {
                            odrHistoryAdapter = new OdrHistoryAdapter(getActivity(), response.body().getHistoryDataList(),response.body().getImageBasePath());
                            rvOrder.setAdapter(odrHistoryAdapter);

                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<OdrHistoryResponse> call, Throwable t) {
                        ((BaseActivity) getActivity()).hideProgressBar();
                        Toast.makeText(getContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
}
