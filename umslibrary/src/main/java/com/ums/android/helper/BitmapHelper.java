package com.ums.android.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class BitmapHelper {
    private BitmapHelper() {
    }

	/**
	 * load image from Internet
	 * @param imgView
	 * @param url
	 * @param needRounded whether image need rounded bitmap or not.
	 * @param defualtDrawableId
	 */
	public static void loadImageFromUrl(ImageView imgView, String url, boolean needRounded,int defualtDrawableId) {
		ImageOptions imageLoadingOptions = new ImageOptions.Builder()
				.setLoadingDrawableId(defualtDrawableId)
				.setFailureDrawableId(defualtDrawableId)
				.setCircular(needRounded)
				.build();
		displayImageFromUrl(imgView, url, imageLoadingOptions);
	}

//	public static ImageOptions imageLoadingOptions = new ImageOptions.Builder()
//			.setLoadingDrawableId(R.drawable.ums_common_btn_pressed)
//			.setFailureDrawableId(R.drawable.ums_common_btn_pressed)
//			.setPlaceholderScaleType(ImageView.ScaleType.FIT_XY)
//			.build();


	public static ImageOptions circularOptions = new ImageOptions.Builder()
			.setCircular(true)
			.build();


	public static void displayImageFromUrl(ImageView view, String url){
		x.image().bind(view, url);
	}

	public static void displayImageFromUrl(ImageView view, String url, ImageOptions options){
		x.image().bind(view, url,options);
	}

	public static void displayImageFromUrl(ImageView view, String url, Callback.CommonCallback<Drawable> callback){
		x.image().bind(view, url,callback);
	}

	public static void displayImageFromUrl(ImageView view, String url, ImageOptions options,Callback.CommonCallback<Drawable> callback){
		x.image().bind(view, url,options,callback);
	}

	public static void LoadFileFromUrl(String url, Callback.CommonCallback<File> callback){
		x.image().loadFile(url, new ImageOptions.Builder().build(), callback);
	}

	public static void LoadDrawableFromUrl(String url,ImageOptions options, Callback.CommonCallback<Drawable> callback){
		x.image().loadDrawable(url,options, callback);
	}


//	/**
//	 * 将图片截取为圆角图片
//	 *
//	 * @param bitmap
//	 *            原图片
//	 * @param ratio
//	 *            截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
//	 * @return 圆角矩形图片
//	 */
//	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {
//
//		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//				bitmap.getHeight(), Config.ARGB_8888);
//		Canvas canvas = new Canvas(output);
//
//		final Paint paint = new Paint();
//		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		final RectF rectF = new RectF(rect);
//
//		paint.setAntiAlias(true);
//		canvas.drawARGB(0, 0, 0, 0);
//		canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
//				bitmap.getHeight() / ratio, paint);
//
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(bitmap, rect, rect, paint);
//		return output;
//	}
	
	public static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
		int i;
		int j;
		if (bitmap.getHeight() > bitmap.getWidth()) {
			i = bitmap.getWidth();
			j = bitmap.getWidth();
		} else {
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}

		Bitmap localBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);
		while (true) {
			localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0, 100, 100), null);
			if (paramBoolean) {
				bitmap.recycle();
			}

			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {
				// F.out(e);
			}
			i = bitmap.getHeight();
			j = bitmap.getHeight();
		}
	}
}
