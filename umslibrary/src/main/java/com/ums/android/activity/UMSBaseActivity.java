package com.ums.android.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.ums.android.helper.BitmapHelper;
import com.ums.android.ui.WaitingDialog;
import com.ums.android.util.Log;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;
import java.util.List;

public abstract class UMSBaseActivity extends AppCompatActivity implements OnClickListener {
    public static final String TAG = "UMSBaseActivity";
    protected UMSBaseActivity mContext;
    private Toast mToast;
    protected WaitingDialog mDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentLayout();
        getViewReference();
        doEvent();
    }

    public abstract void setContentLayout();

    public abstract void getViewReference();

    public abstract void doEvent();


    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            // 记录当前已经进入后台
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns ums_navigation_0 list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 异步加载网络图片
     * @param iv
     * @param imgUrl
     */
    protected void asyncLoadImageView(ImageView iv, String imgUrl) {
        BitmapHelper.displayImageFromUrl(iv, imgUrl, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 下载文件
     * @param url
     * @param filePath
     */
    protected void downloadfile(String url, final String filePath) {
        downLoadFile(url, filePath, new MyCallBack<File>() {
            @Override
            public void onSuccess(File result) {
                super.onSuccess(result);
                Log.i(TAG, "AAA file dowlloaded path: " + result.getAbsoluteFile());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);

            }
        });
    }

    protected <T> Callback.Cancelable downLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }



    protected class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

        @Override
        public void onSuccess(ResultType result) {
            //请求成功的逻辑处理
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            //请求网络失败的逻辑处理
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }


//    /** 判断触摸时间派发间隔 */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (SystemUtils.isFastDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }



    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    protected String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return  version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * toast提示
     * @param text
     */
    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    /**
     * toast提示
     * @param resId
     */
    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    /**
     * 加载等待提示
     */
    public void startWaitDialog() {
        if (mDlg == null) {
            mDlg = new WaitingDialog(this);
            mDlg.show();
            mDlg.setDialogWindowStyle();
        }
    }

    /**
     *  移除等待提示框
     */
    public void removeWaitDialog() {
        if (mDlg != null) {
            mDlg.dismiss();
            mDlg = null;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
