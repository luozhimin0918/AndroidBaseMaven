package com.ums.android.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by JerryWang on 2016/4/8.
 */

public class CustomViewPager extends ViewPager {

    private Context context;
    private boolean isCanScroll = true;

    public CustomViewPager(Context context) {
        super(context);
        this.context = context;
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if(isCanScroll){
            return super.onInterceptTouchEvent(arg0);
        }else{
            //false  不能左右滑动
            return false;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }
}
