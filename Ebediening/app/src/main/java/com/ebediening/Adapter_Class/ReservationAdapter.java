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
import android.widget.TextView;

import com.ebediening.BookTableActivity;
import com.ebediening.R;
import com.ebediening.Response.BookingResponse;

import java.util.List;

/**
 * Created by HP on 14-12-2017.
 */

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<BookingResponse.BookData> bookData;;
    private Context context;
    String imagePath;

    public ReservationAdapter(Context context, List<BookingResponse.BookData> bookData,String imagePath) {
        this.bookData = bookData;
        this.context = context;
        this.imagePath = imagePath;
    }

    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_reservation_single_row, viewGroup, false);
        return new ReservationAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final ReservationAdapter.ViewHolder viewHolder, final int i) {
        final BookingResponse.BookData bookData1 = bookData.get(i);
        viewHolder.restName.setText(bookData1.getRestaurantName());
        viewHolder.tvDate.setText(bookData1.getBookingDate());
        viewHolder.tvTime.setText(bookData1.getBookingTime());
        viewHolder.tvPerson.setText(bookData1.getNoOfPersons());
        viewHolder.tvStatus.setText(bookData1.getBookingStatus());
        if (bookData1.getIsConfirm()==null){
            viewHolder.tvStatus.setText("Pending");
            viewHolder.llLine.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            viewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        }
        else {
            if (bookData1.getIsConfirm().equalsIgnoreCase("confirmed")) {
                viewHolder.tvStatus.setText("Confirmed");
                viewHolder.llLine.setBackgroundColor(context.getResources().getColor(R.color.green_d));
                viewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.green_d));
            } else if (bookData1.getIsConfirm().equalsIgnoreCase("Pending")) {
                viewHolder.tvStatus.setText("Pending");
                viewHolder.llLine.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                viewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow));
            } else if (bookData1.getIsConfirm().equalsIgnoreCase("cancelled")) {
                viewHolder.tvStatus.setText("cancelled");
                viewHolder.llLine.setBackgroundColor(context.getResources().getColor(R.color.red));
                viewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                viewHolder.tvStatus.setText("Pending");
                viewHolder.llLine.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                viewHolder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow));
            }
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                BookingResponse.BookData bookData2 = (BookingResponse.BookData) bookData.get(i);
                Intent intent = new Intent(activity,BookTableActivity.class);
                intent.putExtra(BookTableActivity.BOOK, bookData2);
                intent.putExtra("imagePath",imagePath);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (bookData == null)
            return 0;
        else
            return  bookData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView restName, tvDate, tvTime, tvPerson, tvStatus;
        LinearLayout llLine;

        public ViewHolder(View convertView) {
            super(convertView);
            restName = (TextView) convertView.findViewById(R.id.rest_name);
            tvDate = (TextView) convertView.findViewById(R.id.text_date);
            tvTime = (TextView) convertView.findViewById(R.id.text_time);
            tvPerson = (TextView) convertView.findViewById(R.id.no_of_person);
            tvStatus = (TextView) convertView.findViewById(R.id.status);
            llLine= (LinearLayout) convertView.findViewById(R.id.ll_line);
        }

    }
}

