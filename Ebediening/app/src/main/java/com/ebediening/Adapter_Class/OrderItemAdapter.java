package com.ebediening.Adapter_Class;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebediening.R;
import com.ebediening.Response.OdrHistoryResponse;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {
    private List<OdrHistoryResponse.HistoryData.KeyList> historyData;;
    private Context context;
    String currencyType;

    public OrderItemAdapter(Context context, List<OdrHistoryResponse.HistoryData.KeyList> historyData,String currencyType) {
        this.historyData = historyData;
        this.context = context;
        this.currencyType=currencyType;
    }

    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        return new OrderItemAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final OrderItemAdapter.ViewHolder viewHolder, final int i) {
        final OdrHistoryResponse.HistoryData.KeyList keyList =historyData.get(i);
        viewHolder.tvPrice.setText(currencyType+" "+keyList.getTotal());
        viewHolder.tvName.setText(keyList.getItemName());
        viewHolder.tvQuantity.setText("Quantity: "+keyList.getQuantity());
        viewHolder.tvDescrip.setText(keyList.getDescription());
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
        private TextView tvQuantity,tvName,tvDescrip,tvPrice;
        RelativeLayout rlBg;
        public ViewHolder(View view) {
            super(view);
            tvQuantity= (TextView)view.findViewById(R.id.tv_quantity);
            tvPrice= (TextView)view.findViewById(R.id.tv_price);
            tvDescrip= (TextView)view.findViewById(R.id.tv_descrip);
            tvName= (TextView)view.findViewById(R.id.tv_name);
            rlBg= (RelativeLayout)view.findViewById(R.id.rl_bg);
        }
    }
}
