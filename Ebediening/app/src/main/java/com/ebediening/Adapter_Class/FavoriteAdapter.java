package com.ebediening.Adapter_Class;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebediening.Fragment.FavoriteFragment;
import com.ebediening.R;
import com.ebediening.Response.FavResponse;
import com.ebediening.Response.UserResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<FavResponse.FavData> favData;;
    private Context context;
    FavoriteFragment favoriteFragment;

    public FavoriteAdapter(Context context, List<FavResponse.FavData> favData, FavoriteFragment favoriteFragment) {
        this.favData = favData;
        this.context = context;
        this.favoriteFragment=favoriteFragment;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_list, viewGroup, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final FavoriteAdapter.ViewHolder viewHolder, final int i) {
        final FavResponse.FavData favData1 =favData.get(i);
        viewHolder.tvAddress.setText(favData1.getRestaurantAddress());
        viewHolder.tvName.setText(favData1.getRestaurantName());
        viewHolder.llLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               unFavorite(favData1);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Activity activity = (Activity) context;
//                OdrHistoryResponse.HistoryData historyData2 = (OdrHistoryResponse.HistoryData) historyData.get(i);
//                Intent intent = new Intent(activity,OdrDetailActivity.class);
//                intent.putExtra(OdrDetailActivity.ORDER, historyData2);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivity(intent);
//                //activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void unFavorite(FavResponse.FavData favData1) {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        ((BaseActivity) context).showProgressbar("Loading", "Please wait...");
        ebedieningService.unFavorite(Preferences.getInstance().getUserId(),favData1.getRestaurantId()).
                enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                        if (response.body().getFlag() == 1) {
                            favoriteFragment.getFav();
                        } else {

                        }
                        ((BaseActivity) context).hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        ((BaseActivity) context).hideProgressBar();
                        Toast.makeText(context, Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (favData == null)
            return 0;
        else
            return  favData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAddress,tvName;
        LinearLayout llLove;

        public ViewHolder(View view) {
            super(view);
            tvAddress= (TextView)view.findViewById(R.id.tv_address);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            llLove= (LinearLayout)view.findViewById(R.id.ll_love);
        }
    }
}


