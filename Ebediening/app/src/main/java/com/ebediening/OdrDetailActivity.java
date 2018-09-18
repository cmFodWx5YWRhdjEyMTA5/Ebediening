package com.ebediening;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ebediening.Adapter_Class.OrderItemAdapter;
import com.ebediening.Response.OdrHistoryResponse;
import com.ebediening.Utilites.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OdrDetailActivity extends BaseActivity {
    public static final String ORDER = "order";
    OrderItemAdapter orderItemAdapter;
    OdrHistoryResponse.HistoryData historyData;
    OdrHistoryResponse.HistoryData.KeyList keyList;
    List<OdrHistoryResponse.HistoryData.KeyList> keyLists=new ArrayList<>();
    HashMap<String, OdrHistoryResponse.HistoryData.KeyList> entityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odr_detail);
        ButterKnife.bind(this);
        historyData = (OdrHistoryResponse.HistoryData) getIntent().getParcelableExtra(ORDER);
        btHead.setText(historyData.getRestaurantName());
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("ORDER HISTORY");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

//        entityList = historyData.getData();
//        for (String mapKey :  entityList.keySet()) {
//            Log.d("Map","mapKey : "+mapKey+" , mapValue : "+entityList.get(mapKey));
//            keyList=entityList.get(mapKey);
//            keyLists.add(keyList);
//        }
//
//        if (keyLists.size()==0)
//        {}
//        else {
//            orderItemAdapter = new OrderItemAdapter(OdrDetailActivity.this, keyLists,historyData.getCurrencyType());
//            rvItems.setAdapter(orderItemAdapter);
//        }
    }







    @BindView(R.id.bt_head)
    Button btHead;

    @BindView(R.id.bt_print)
    Button btPrint;

    @BindView(R.id.rv_items)
    RecyclerView rvItems;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;
}
