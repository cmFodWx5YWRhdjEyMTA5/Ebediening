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

import com.ebediening.OdrDetailActivity;
import com.ebediening.R;
import com.ebediening.Response.OdrHistoryResponse;

import java.util.List;

public class OdrHistoryAdapter extends RecyclerView.Adapter<OdrHistoryAdapter.ViewHolder> {
    private List<OdrHistoryResponse.HistoryData> historyData;;
    private Context context;
    String imagePath;

    public OdrHistoryAdapter(Context context, List<OdrHistoryResponse.HistoryData> historyData,String imagePath) {
        this.historyData = historyData;
        this.context = context;
        this.imagePath=imagePath;
    }

    @Override
    public OdrHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_list, viewGroup, false);
        return new OdrHistoryAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final OdrHistoryAdapter.ViewHolder viewHolder, final int i) {
        final OdrHistoryResponse.HistoryData historyData1 =historyData.get(i);
        viewHolder.tvInvoice.setText(historyData1.getInvoiceNumber());
        viewHolder.tvItems.setText(historyData1.getItemsCounter());
        viewHolder.tvDate.setText(historyData1.getOrderDate());
        viewHolder.tvPrice.setText(historyData1.getCurrencyType()+" "+historyData1.getTotal());
        viewHolder.tvName.setText(historyData1.getRestaurantName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                OdrHistoryResponse.HistoryData historyData2 = (OdrHistoryResponse.HistoryData) historyData.get(i);
                Intent intent = new Intent(activity,OdrDetailActivity.class);
                intent.putExtra(OdrDetailActivity.ORDER, historyData2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                //activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

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
        if (historyData == null)
            return 0;
        else
            return  historyData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvInvoice,tvName,tvItems,tvDate,tvPrice;
        RelativeLayout rlBg;LinearLayout llTrack;
        public ViewHolder(View view) {
            super(view);
            tvInvoice= (TextView)view.findViewById(R.id.tv_invoice);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            tvItems= (TextView)view.findViewById(R.id.tv_items);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            rlBg= (RelativeLayout)view.findViewById(R.id.rl_bg);
        }
    }
}

