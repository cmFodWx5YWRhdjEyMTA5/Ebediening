package com.ebediening.Adapter_Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ebediening.R;

import java.util.ArrayList;

/**
 * Created by HP on 14-12-2017.
 */

public class Adapter_No_Table extends BaseAdapter {
    Context context;

    LayoutInflater inflater;
    ArrayList<String> no_of_table;

    public Adapter_No_Table(Context context, ArrayList<String> no_of_table) {
        this.context = context;
        this.no_of_table = no_of_table;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return no_of_table.size();
    }

    @Override
    public Object getItem(int i) {
        return no_of_table.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_textview, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.text.setText(no_of_table.get(i));
        return convertView;
    }

    private class MyViewHolder {
        TextView text;

        public MyViewHolder(View convertView) {
            text = (TextView) convertView.findViewById(R.id.text);

        }
    }
}
