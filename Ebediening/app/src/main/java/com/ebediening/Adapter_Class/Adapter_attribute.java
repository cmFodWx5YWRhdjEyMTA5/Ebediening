package com.ebediening.Adapter_Class;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.ebediening.Getter_Setter.Product_Attributes;
import com.ebediening.R;

import java.util.ArrayList;

/**
 * Created by HP on 14-12-2017.
 */

public class Adapter_attribute extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Product_Attributes> array_Attributes;
    String b;

    public Adapter_attribute(Context context, ArrayList<Product_Attributes> array_Attributes, String b) {
        this.context = context;
        this.array_Attributes = array_Attributes;
        this.b = b;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return array_Attributes.size();
    }

    @Override
    public Object getItem(int i) {
        return array_Attributes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_attribute_radiobutton, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        if (b.equalsIgnoreCase("2")) {
            mViewHolder.checkBox.setVisibility(View.GONE);
            mViewHolder.radioButton.setVisibility(View.VISIBLE);
            mViewHolder.radioButton.setText(array_Attributes.get(i).getAttribute_value());
        } else {
            mViewHolder.radioButton.setVisibility(View.GONE);
            mViewHolder.checkBox.setVisibility(View.VISIBLE);
            mViewHolder.checkBox.setText(array_Attributes.get(i).getAttribute_value());
        }

        return convertView;
    }

    private class MyViewHolder {
        RadioButton radioButton;
        CheckBox checkBox;

        public MyViewHolder(View convertView) {
            radioButton = (RadioButton) convertView.findViewById(R.id.radio);
            checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        }
    }
}
