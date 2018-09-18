package com.ebediening.Adapter_Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebediening.Getter_Setter.Offers;
import com.ebediening.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP on 14-12-2017.
 */

public class Adapter_offers extends BaseAdapter {
    Context context;

    LayoutInflater inflater;
    ArrayList<Offers> array_offers;

    public Adapter_offers(Context context, ArrayList<Offers> array_offers) {
        this.context = context;
        this.array_offers = array_offers;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return array_offers.size();
    }

    @Override
    public Object getItem(int i) {
        return array_offers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_row_offers, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.rest_name.setText(array_offers.get(i).getRestaurent_name());
        mViewHolder.more_offers.setText(array_offers.get(i).getTotal_offers()+" "+"More Offers");
        mViewHolder.title.setText(array_offers.get(i).getTitle());
        Picasso.with(context).load(array_offers.get(i).getImage_url()).placeholder(R.drawable.profileicon).into(mViewHolder.rest_image);
        return convertView;
    }

    private class MyViewHolder {
        TextView rest_name, more_offers, title;//, no_of_person, status;
        ImageView rest_image;

        public MyViewHolder(View convertView) {
            rest_name = (TextView) convertView.findViewById(R.id.rest_name);
            more_offers = (TextView) convertView.findViewById(R.id.more_offers);
            title = (TextView) convertView.findViewById(R.id.title);
            rest_image = (ImageView) convertView.findViewById(R.id.rest_image);
        }
    }
}
