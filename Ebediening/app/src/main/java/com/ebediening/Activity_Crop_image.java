package com.ebediening;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;

import com.ebediening.Utilites.BaseActivity;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;

import java.io.ByteArrayOutputStream;

public class Activity_Crop_image extends BaseActivity implements View.OnClickListener {
    CropImageView mCropView;
    Uri uri_image, saveUri;
    String image_uri = "";
    LinearLayout linear_crop;
    private Bitmap.CompressFormat mCompressFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        linear_crop = (LinearLayout) findViewById(R.id.linear_crop);
        linear_crop.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("uri")) {
            image_uri = extras.getString("uri");
        }

        mCropView.setOverlayColor(0xAA1C1C1C);
        mCropView.setMinFrameSizeInDp(80);
        mCompressFormat = Bitmap.CompressFormat.JPEG;
        mCropView.setOutputWidth(400);
        mCropView.setOutputHeight(400);
        mCropView.setCompressQuality(10);
        uri_image = Uri.parse(image_uri);
        mCropView.load(uri_image).execute(new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_crop:
                final ProgressDialog dialog = new ProgressDialog(Activity_Crop_image.this);
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);
                dialog.show();
                mCropView.crop(uri_image).execute(new CropCallback() {
                    @Override
                    public void onSuccess(Bitmap cropped) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        cropped.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] b = baos.toByteArray();
                        String temp = Base64.encodeToString(b, Base64.DEFAULT);

                        Intent intent = new Intent();
                        intent.putExtra("bitmap", temp);
                        setResult(RESULT_OK, intent);
                        Activity_Crop_image.this.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
                break;
        }
    }

    private Uri createSaveUri() {
        return createNewUri(Activity_Crop_image.this, mCompressFormat);
    }

    private Uri createNewUri(Activity_Crop_image activity_crop_image, Bitmap.CompressFormat mCompressFormat) {
        return null;
    }
}
