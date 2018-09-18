package com.ebediening.Adapter_Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebediening.R;
import com.ebediening.Response.OrdrResponse;
import com.ebediening.TrackActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrdrResponse.OrderData> orderData;;
    private Context context;
    String currencyType;

    public OrderAdapter(Context context, List<OrdrResponse.OrderData> orderData,String currencyType) {
        this.orderData = orderData;
        this.context = context;
        this.currencyType=currencyType;
    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_list, viewGroup, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final OrderAdapter.ViewHolder viewHolder, final int i) {
        final OrdrResponse.OrderData orderData1 =orderData.get(i);
        viewHolder.tvOdr.setText(orderData1.getId());
        viewHolder.tvItems.setText(orderData1.getTotalItems());
        viewHolder.tvAmt.setText(currencyType+" "+orderData1.getTotal());
        viewHolder.llTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                OrdrResponse.OrderData orderData2 = (OrdrResponse.OrderData) orderData.get(i);
                Intent intent = new Intent(activity,TrackActivity.class);
                intent.putExtra(TrackActivity.TRACK, orderData2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                //activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        if (orderData1.getDeliveryType().equalsIgnoreCase("dineout"))
        {
            viewHolder.tvType.setText("Takeaway");
        }
        else {
            viewHolder.tvType.setText("Delivery");
        }
        if (i%2==0)
        {
            viewHolder.rlBg.setBackgroundResource(R.drawable.line_bg);

        }
        else {
            viewHolder.rlBg.setBackgroundResource(R.drawable.line_gray_bg);
        }

    }

    @Override
    public int getItemCount() {
        if (orderData == null)
            return 0;
        else
            return  orderData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOdr,tvItems,tvAmt,tvType;
        RelativeLayout rlBg;LinearLayout llTrack;
        public ViewHolder(View view) {
            super(view);
            tvOdr= (TextView)view.findViewById(R.id.tv_odrid);
            tvItems= (TextView)view.findViewById(R.id.tv_items);
            tvAmt= (TextView)view.findViewById(R.id.tv_amt);
            tvType= (TextView)view.findViewById(R.id.tv_type);
            rlBg= (RelativeLayout)view.findViewById(R.id.rl_bg);
            llTrack= (LinearLayout)view.findViewById(R.id.ll_track);
        }
    }
}
