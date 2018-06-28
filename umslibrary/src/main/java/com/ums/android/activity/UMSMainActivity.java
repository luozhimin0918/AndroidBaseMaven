package com.ums.android.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import com.ums.android.R;

public class UMSMainActivity extends UMSBaseActivity implements View.OnClickListener {
    private final static String TAG = "UMSMainActivity";

    @Override
    public void setContentLayout() {
        mContext = this;
        setContentView(R.layout.ums_activity_main);
    }

    @Override
    public void getViewReference() {

    }
    @Override
    public void doEvent() {

    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    }

//    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // UMSMainActivity  应用主界面， 在此点返回键，应用退到后台。
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}