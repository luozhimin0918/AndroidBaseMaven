package com.ums.android.activity;

import android.view.View;
import android.widget.ImageView;

import com.ums.android.R;
import com.ums.android.util.Log;

/**
 * Created by JerryWang on 2016/4/29.
 */
public class UMSImageFullScreenActivity extends UMSBaseActivity {
    private ImageView mZvFullScreen;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.ums_activity_image_fullscreen);
    }

    @Override
    public void getViewReference() {
        mZvFullScreen = (ImageView) findViewById(R.id.iv_fullscren);
        mZvFullScreen.setOnClickListener(this);
    }

    @Override
    public void doEvent() {
        String url = this.getIntent().getStringExtra("url");
        Log.i(TAG, "AAA url: " + url);
        asyncLoadImageView(mZvFullScreen, url);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_fullscren) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
