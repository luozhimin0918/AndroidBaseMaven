package com.ums.android.helper;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import org.xutils.common.Callback;

/**
 */
public class BitmapFadeInLoadCallBack implements Callback.CommonCallback<Drawable>{

    private final ImageView mImageView;
    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable();

    public BitmapFadeInLoadCallBack(ImageView imageView) {
        mImageView = imageView;
    }

    @Override
    public void onSuccess(Drawable result) {

        fadeInDisplay(mImageView, result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {


    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    private void fadeInDisplay(ImageView imageView, Drawable drawable) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        TRANSPARENT_DRAWABLE,
                        drawable
                });
        imageView.setImageDrawable(transitionDrawable);
//	    imageView.setBackgroundDrawable(transitionDrawable);
        transitionDrawable.startTransition(250);
    }
}
