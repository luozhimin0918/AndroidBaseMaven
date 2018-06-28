package com.ums.android.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * fix showing all child as it can, when listView inset in scrollView.
 */
public class ListView extends android.widget.ListView {

	public ListView(Context context) {
		super(context);
	}

	public ListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.setFocusable(false);
	}
}