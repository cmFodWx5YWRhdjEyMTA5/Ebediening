package com.ebediening;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebediening.Utilites.BaseActivity;

public class Activity_Information extends BaseActivity {
    ImageView image_rest, back;
    TextView rest_name, text_timing, text_website, text_descrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        text_descrp = (TextView) findViewById(R.id.text_descrp);
        text_timing = (TextView) findViewById(R.id.text_timing);
        text_website = (TextView) findViewById(R.id.text_website);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Information.this.finish();
            }
        });
    }
}
