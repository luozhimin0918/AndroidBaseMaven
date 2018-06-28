package com.ums.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class CustomWebView extends WebView {

	private long preTouchTime = 0;

	public CustomWebView(Context context) {
		super(context);
	}

	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 屏蔽双击事件
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {
			long currentTouchTime = System.currentTimeMillis();
			// ViewConfiguration.getDoubleTapTimeout()
			if (currentTouchTime - preTouchTime <= 500) {
				preTouchTime = currentTouchTime;
				return true;
			}
			preTouchTime = currentTouchTime;
		}
		return super.dispatchTouchEvent(ev);
	}

}
