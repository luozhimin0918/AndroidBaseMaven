package com.ums.android.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class IndicatorView extends HorizontalScrollView {
	private View ll_content;
	private int mScreenWitdh = 0;
	private Activity activity;
	
	public IndicatorView(Context context) {
		super(context);
	}

	public IndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IndicatorView(Context context, AttributeSet attrs,
						 int defStyle) {
		super(context, attrs, defStyle);
	}
	@Override
	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		// TODO Auto-generated method stub
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
	}
	public void setParam(Activity activity, int mScreenWitdh,View paramView){
		this.activity = activity;
		this.mScreenWitdh = mScreenWitdh;
		ll_content = paramView;
	}
}
