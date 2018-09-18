package com.ebediening.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ebediening.Adapter_Class.NotifiyAdapter;
import com.ebediening.R;
import com.ebediening.Response.NotifyResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class NotificationFragment extends Fragment {

    NotifiyAdapter notifiyAdapter;
    private OnFragmentInteractionListener mListener;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this,view);
        getNotify();
        return view;
    }

    private void getNotify() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvNotify.setLayoutManager(mLayoutManager);
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        ebedieningService.getNotification(Preferences.getInstance().getUserId()).
                enqueue(new Callback<NotifyResponse>() {
                    @Override
                    public void onResponse(Call<NotifyResponse> call, retrofit2.Response<NotifyResponse> response) {
                        if (response.body().getFlag() == 1) {
                            notifiyAdapter = new NotifiyAdapter(getActivity(), response.body().getNofifyData());
                            rvNotify.setAdapter(notifiyAdapter);

                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<NotifyResponse> call, Throwable t) {
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

    @BindView(R.id.rv_notify)
    RecyclerView rvNotify;
}
