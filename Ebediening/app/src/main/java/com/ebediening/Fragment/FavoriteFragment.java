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

import com.ebediening.Adapter_Class.FavoriteAdapter;
import com.ebediening.R;
import com.ebediening.Response.FavResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;


public class FavoriteFragment extends Fragment {
    FavoriteAdapter favoriteAdapter;
    private OnFragmentInteractionListener mListener;

    public FavoriteFragment() {
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
        View view= inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this,view);
        getFav();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getFav() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rvFav.setLayoutManager(mLayoutManager);
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        ((BaseActivity) getActivity()).showProgressbar("Loading", "Please wait...");
        ebedieningService.favList(Preferences.getInstance().getUserId()).
                enqueue(new Callback<FavResponse>() {
                    @Override
                    public void onResponse(Call<FavResponse> call, retrofit2.Response<FavResponse> response) {
                        if (response.body().getFlag() == 1) {
                            favoriteAdapter = new FavoriteAdapter(getActivity(), response.body().getFavDataList(),FavoriteFragment.this);
                            rvFav.setAdapter(favoriteAdapter);

                        } else {

                        }
                        ((BaseActivity) getActivity()).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<FavResponse> call, Throwable t) {
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

    @BindView(R.id.rv_fav)
    RecyclerView rvFav;
}
