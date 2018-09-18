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
import com.ebediening.Response.NotifyResponse;

import java.util.List;

public class NotifiyAdapter extends RecyclerView.Adapter<NotifiyAdapter.ViewHolder> {
    private List<NotifyResponse.NotifyData> notifyData;;
    private Context context;

    public NotifiyAdapter(Context context, List<NotifyResponse.NotifyData> notifyData) {
        this.notifyData = notifyData;
        this.context = context;
    }

    @Override
    public NotifiyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notify_list, viewGroup, false);
        return new NotifiyAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final NotifiyAdapter.ViewHolder viewHolder, final int i) {
        final NotifyResponse.NotifyData notifyData1 =notifyData.get(i);
        viewHolder.tvDate.setText(notifyData1.getCreatedDate());
        viewHolder.tvTitle.setText(notifyData1.getContent());
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
        if (notifyData == null)
            return 0;
        else
            return  notifyData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDate,tvTitle;
        RelativeLayout rlBg;
        public ViewHolder(View view) {
            super(view);
            tvTitle= (TextView)view.findViewById(R.id.tv_title);
            tvDate= (TextView)view.findViewById(R.id.tv_date);
            rlBg= (RelativeLayout)view.findViewById(R.id.rl_bg);
        }
    }
}


