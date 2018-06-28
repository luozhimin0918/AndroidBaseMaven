package com.ums.android.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ums.android.R;
import com.ums.android.ui.CustomAlertDialog;
import com.ums.android.util.Log;

import java.io.File;
import java.util.Date;

/**
 * Created by JerryWang on 2017/5/12.
 */
public class UMSWebViewActivity extends UMSBaseActivity {
    private WebView mWebView;
    private UMSBaseActivity mContext;
    @Override
    public void setContentLayout() {
        setContentView(R.layout.ums_activity_webview);
        mContext = this;
    }

    @Override
    public void getViewReference() {
        mWebView = (WebView)findViewById(R.id.wv_webview);
        clearCache(this, 0);
        mWebView.clearCache(true);
        deleteDatabase("webview.db");
        deleteDatabase("webviewCache.db");
        initView();
    }

    @Override
    public void doEvent() {
        String url = this.getIntent().getStringExtra("url");
        Log.i(TAG, "AAA URL: " + url);
        mWebView.loadUrl(url);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @JavascriptInterface
    private void initView() {
        Log.i(TAG, "AAA init web View");
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        int currentVersion = android.os.Build.VERSION.SDK_INT;
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(false);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.setBackgroundColor(this.getResources().getColor(R.color.ums_colorPrimary));
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.addJavascriptInterface(new JavaScripdtObject(), "android");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
            // 重写此方法可以让webview处理https请求
                handler.proceed();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("TAG", "TITLE=" + title);
            }

            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG, "newProgress = " + newProgress);
                if (newProgress == 100) {
//                    mDlg.dismiss();
                }
//                else if (!getActivity().isFinishing()) {
//                    mDlg.show();
//                }
            }

            /**
             * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
             */
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                final CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(
                        view.getContext());

//                builder.setTitle(R.string.ums_const_tip_tip).setMessage(message)
//                        .setPositiveButton(R.string.ums_common_button_ok, null);
                Dialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
                return true;
                // return super.onJsAlert(view, url, message, result);
            }

            public boolean onJsBeforeUnload(WebView view, String url,
                                            String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

            /**
             * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
             */
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                final CustomAlertDialog.Builder builder = new CustomAlertDialog.Builder(
                        view.getContext());
//                builder.setTitle(R.string.ums_const_tip_tip)
//                        .setMessage(message)
//                        .setPositiveButton(R.string.ums_common_button_ok,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        result.confirm();
//                                    }
//                                })
//                        .setNegativeButton(R.string.ums_common_button_cancel,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        result.cancel();
//                                    }
//                                });

                Dialog dialog = builder.create();
                // 禁止响应按back键的事
                dialog.setCancelable(false);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        result.cancel();
                    }
                });
                dialog.show();
                return true;
                // return super.onJsConfirm(view, url, message, result);
            }
        });
    }


    public class JavaScripdtObject {
        // webview js 回调
        @JavascriptInterface
        public void onBack() {
            setResult(RESULT_OK);
            mContext.finish();
        }

        @JavascriptInterface
        public void onSignup() {
            setResult(RESULT_OK);
            mContext.finish();
//            if(CommonData.isLogin != 1){
//                Intent it = new Intent();
//                it.setClass(mContext, umsRegisterActivity.class);
//                startActivity(it);
//            }
        }

        @JavascriptInterface
        public void onLogin() {
            setResult(RESULT_OK);
            mContext.finish();
//            if(CommonData.isLogin != 1){
//                Intent it = new Intent();
//                it.setClass(mContext, umsLoginActivity.class);
//                startActivity(it);
//            }
        }

        @JavascriptInterface
        public String toString() { return "apk"; }
    }

    @Override
    public void onClick(View v) {

    }

    static int clearCacheFolder(final File dir, final int numDays) {
        int deletedFiles = 0;
        if (dir!= null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {
                    //first delete subdirectories recursively
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    //then delete the files and subdirectories in this dir
                    //only empty directories can be deleted, so subdirs have been done first
                    if (child.lastModified() < new Date().getTime() - numDays * DateUtils.DAY_IN_MILLIS) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
            catch(Exception e) {
                Log.e("WebView", String.format("Failed to clean the cache, error %s", e.getMessage()));
            }
        }
        return deletedFiles;
    }

    public static void clearCache(final Context context, final int numDays) {
        int numDeletedFiles = clearCacheFolder(context.getCacheDir(), numDays);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView != null){
            mWebView.clearCache(true);
        }
    }
}
