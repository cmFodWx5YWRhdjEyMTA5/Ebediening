package com.ebediening;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.ebediening.Adapter_Class.Adapter_Reviews;
import com.ebediening.Utilites.BaseActivity;

import java.util.ArrayList;

public class Activity_Review extends BaseActivity {
    ListView review_list;
    ImageView back;
    ArrayList<String> array_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__review);
        review_list = (ListView) findViewById(R.id.review_list);
        array_review = new ArrayList<>();
        array_review.add("Rakinder");
        array_review.add("Rakinder");
        array_review.add("Rakinder");
        array_review.add("Rakinder");

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Review.this.finish();
            }
        });

        Adapter_Reviews adapter_reviews = new Adapter_Reviews(Activity_Review.this, array_review);
        review_list.setAdapter(adapter_reviews);
    }
}
