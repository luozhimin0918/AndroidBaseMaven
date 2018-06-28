package com.ums.android.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;
import com.ums.android.R;
import com.ums.android.activity.UMSBaseActivity;
import com.ums.android.ui.WaitingDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;

/**
 * Http wrapper class. wrap xutils
 * Created by jerrywang 156587036@qq.com on 2015/12/11.
 */
public class HttpFetcher {
    public static final String TAG = "HttpFetcher";
    public static final String NETWORK_NOT_AVAILABLE = "1";
    public static final String SERVER_ERR = "2";
    public static final String TIME_OUT = "3";
    public final static int NETWORK_STATUS_NOT_AVAILABLE = 0;
    public final static int NETWORK_STATUS_IS_WIFI = 1;
    public final static int NETWORK_STATUS_IS_GPRS = 2;
    public final static int CONNECT_TIME_OUT = 1000 * 60;

    Context mContext;
    RequestParams mRequestParams;
    Callback.CommonCallback mCommonCallback;

    HttpFetcher(Context context) {
        mContext = context;
    }

    public void setmRequestParams(RequestParams mRequestParams) {
        this.mRequestParams = mRequestParams;
    }

    public void setmCommonCallback(Callback.CommonCallback mCommonCallback) {
        this.mCommonCallback = mCommonCallback;
    }

    public Callback.Cancelable fetch() {
        if (mRequestParams == null) {
            throw new NullPointerException("RequestParams argument is not set");
        }
        if (mCommonCallback == null) {
            throw new NullPointerException("Callback.CommonCallback argument is not set");
        }
        return x.http().post(mRequestParams, mCommonCallback);
    }

    public Callback.Cancelable getFetch() {
        if (mRequestParams == null) {
            throw new NullPointerException("RequestParams argument is not set");
        }
        if (mCommonCallback == null) {
            throw new NullPointerException("Callback.CommonCallback argument is not set");
        }
        return x.http().get(mRequestParams, mCommonCallback);
    }

    public Callback.Cancelable putFetch() {
        if (mRequestParams == null) {
            throw new NullPointerException("RequestParams argument is not set");
        }
        if (mCommonCallback == null) {
            throw new NullPointerException("Callback.CommonCallback argument is not set");
        }
        return x.http().request(HttpMethod.PUT, mRequestParams, mCommonCallback);
    }
    public Callback.Cancelable deleteFetch() {
        if (mRequestParams == null) {
            throw new NullPointerException("RequestParams argument is not set");
        }
        if (mCommonCallback == null) {
            throw new NullPointerException("Callback.CommonCallback argument is not set");
        }
        return x.http().request(HttpMethod.DELETE, mRequestParams, mCommonCallback);
    }

    public static Builder with(Context context, HttpFetchListener listener) {
        return new Builder(context, listener);
    }

    public static Builder with(Context context) {
        return new Builder(context, null);
    }


    /**
     * Convenience Builder class
     */
    public static class Builder {
        HttpFetcher mFetcher;
        HttpFetchListener listener;
        Context mContext;
        Boolean mIsShow = false;
        WaitingDialog mProgressDlg;

        public Builder(Context context, HttpFetchListener listener) {
            mFetcher = new HttpFetcher(context);
            this.listener = listener;
            mContext = context;
        }

//        public RequestParams getRequestParams() {
//            preCheckNotNull();
//            return mFetcher.mRequestParams;
//        }

        /**
         * must be followed by creator method
         * @param url http url
         * @return Builder
         */
        public Builder setUrl(String url) {
            preCheckNotNull();
            mFetcher.mRequestParams = new RequestParams(url);
            mFetcher.mRequestParams.setConnectTimeout(CONNECT_TIME_OUT);
            return this;
        }

        /**
         * @param proxy java.net.Proxy
         *              <li>DIRECT</li>
         *              <li>HTTP</li>
         *              <li>SOCKS</li>
         * @return Builder
         */
        public Builder setProxy(Proxy proxy) {
            preCheckNotNull();
            mFetcher.mRequestParams.setProxy(proxy);
            return this;
        }

        /**
         * this header will replace exist key's value
         *
         * @param key   header name
         * @param value header value
         * @return Builder
         */
        public Builder setHeader(String key, String value) {
            preCheckNotNull();
            mFetcher.mRequestParams.setHeader(key, value);
            return this;
        }

        /**
         * this header won't replace exist key's value
         *
         * @param key   header name
         * @param value header value
         * @return Builder
         */
        public Builder addHeader(String key, String value) {
            preCheckNotNull();
            mFetcher.mRequestParams.addHeader(key, value);
            return this;
        }

        /**
         * http body
         * @param json JSON data
         * @return Builder
         */
        public Builder setBodyContent(String json) {
            preCheckNotNull();
            mFetcher.mRequestParams.setBodyContent(json);
            return this;
        }

        /**
         * @return Builder
         */
        public Builder setUploadParams(String fileName) {
            preCheckNotNull();
            mFetcher.mRequestParams.setMethod(HttpMethod.POST);
            mFetcher.mRequestParams.setMultipart(true);
            mFetcher.mRequestParams.addBodyParameter("uploadFile", new File(fileName), null);
            return this;
        }
        /**
         * @return Builder
         */
        public Builder setUploadFileParams(String fileName,int item) {
            preCheckNotNull();
            mFetcher.mRequestParams.setMethod(HttpMethod.POST);
            mFetcher.mRequestParams.setMultipart(true);
            mFetcher.mRequestParams.addBodyParameter("file", new File(fileName), null);
            mFetcher.mRequestParams.addBodyParameter("item", item, null);
            return this;
        }
        public Builder setQueryStringMaps(Map<String, String> maps) {
            preCheckNotNull();
            if (maps != null) {
                Iterator it = maps.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    mFetcher.mRequestParams.addQueryStringParameter(key, value);
                }
            }
            return this;
        }

        public Builder setShowProgressDialog(Boolean isShow) {
            mIsShow = isShow;
            return this;
        }

        public Callback.Cancelable fetch(MyHandleResultListener listener) {
            preCheckNotNull();
            showProgressDlg();
            if (mFetcher.mCommonCallback == null) {
                mFetcher.mCommonCallback = createCommonCallback(mContext, mProgressDlg, listener);
            }
            return mFetcher.fetch();
        }

        public Callback.Cancelable getFetch(MyHandleResultListener listener) {
            preCheckNotNull();
            showProgressDlg();
            if (mFetcher.mCommonCallback == null) {
                mFetcher.mCommonCallback = createCommonCallback(mContext, mProgressDlg, listener);
            }
            return mFetcher.getFetch();
        }

        public Callback.Cancelable putFetch(MyHandleResultListener listener) {
            preCheckNotNull();
            showProgressDlg();
            if (mFetcher.mCommonCallback == null) {
                mFetcher.mCommonCallback = createCommonCallback(mContext, mProgressDlg, listener);
            }
            return mFetcher.putFetch();
        }

        public Callback.Cancelable deleteFetch(MyHandleResultListener listener) {
            preCheckNotNull();
            showProgressDlg();
            if (mFetcher.mCommonCallback == null) {
                mFetcher.mCommonCallback = createCommonCallback(mContext, mProgressDlg, listener);
            }
            return mFetcher.deleteFetch();
        }
        private void preCheckNotNull() {
            if (mFetcher == null) {
                throw new NullPointerException("mFetcher is null, you should call Builder creator firstly!");
            }
        }

        private void showProgressDlg() {
            if (mIsShow) {
                if (mProgressDlg == null) {
                    mProgressDlg = new WaitingDialog(mContext);
                    mProgressDlg.setDialogWindowStyle();
                    mProgressDlg.setCanceledOnTouchOutside(false);
                }
                mProgressDlg.show();
            }
        }

        @NonNull
        private MyHandleResultListenerAdapter getListener(final int protocolId, final int arg1, final Bundle data) {
            return new MyHandleResultListenerAdapter() {
                @Override
                public void handleData(JSONObject json) {
                    if (listener != null) {
                        String result = json.toString();
                        Message msg = new Message();
                        msg.what = protocolId;
                        msg.arg1 = arg1;
                        msg.obj = "";
                        msg.setData(data);
                        if (result != null) {
                            msg.obj = result;
                        }
                        listener.onFetchResult(msg);
                    }
                }

                @Override
                public void handleData(JSONArray json) {
                    if (listener != null) {
                        String result = json.toString();
                        Message msg = new Message();
                        msg.what = protocolId;
                        msg.arg1 = arg1;
                        msg.obj = "";
                        msg.setData(data);
                        if (result != null) {
                            msg.obj = result;
                        }
                        listener.onFetchResult(msg);
                    }
                }

                @Override
                public int onError() {
                    if (listener != null) {
                        Message msg = new Message();
                        msg.what = protocolId;
                        msg.setData(data);
                        msg.obj = "";
                        listener.onFetchResult(msg);
                    }
                    return super.onError();
                }

                @Override
                public void onNeedLogin(JSONObject json) {
                    if (listener != null) {
                        String result = json.toString();
                        Message msg = new Message();
                        msg.what = protocolId;
                        msg.arg1 = arg1;
                        msg.obj = "";
                        msg.setData(data);
                        if (result != null) {
                            msg.obj = result;
                        }
                        listener.onFetchResult(msg);
                    }
                }
            };
        }

        /**
         * @param url
         * @param json
         * @param listener
         */
        public Callback.Cancelable postRequest(String url, String json, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            setBodyContent(json);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return fetch(listener);
        }

        public Callback.Cancelable postAddHeaderRequest(String url, String json, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            setBodyContent(json);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return fetch(listener);
        }

        public Callback.Cancelable getRequest(String url, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return getFetch(listener);
        }

        public Callback.Cancelable getAddHeaderRequest(String url, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return getFetch(listener);
        }

        public Callback.Cancelable putRequest(String url, String json, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            setBodyContent(json);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return putFetch(listener);
        }

        public Callback.Cancelable putAddHeaderRequest(String url, String json, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            setBodyContent(json);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return putFetch(listener);
        }
        public Callback.Cancelable deleteAddHeaderRequest(String url, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            return deleteFetch(listener);
        }

        /**
         * @param url
         * @param fileName
         * @param listener
         */
        public Callback.Cancelable uploadRequest(String url,String fileName, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            setUploadParams(fileName);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            addHeader(CONST.HEADER_CONTENT_TYPE_KEY,CONST.HEADER_CONTENT_TYPE_VALUE);
            return fetch(listener);
        }

        /**
         * 上传文件
         * @param url
         * @param fileName
         * @param listener
         */
        public Callback.Cancelable uploadFileRequest(String url,String fileName,int item, MyHandleResultListener listener) {
            preCheckNotNull();
            setUrl(url);
            setUploadFileParams(fileName,item);
            addHeader(CONST.HEADER_X_VERSION_KEY, String.valueOf(SystemUtils.getVersionCode(mContext)));
            addHeader(CONST.CHANNEL_KEY, getChannel());
            addHeader(CONST.HEADER_USERAGENT_KEY, CONST.HEADER_USERAGENT_VALUE);
            addHeader(CONST.HEADER_CONTENT_TYPE_KEY,CONST.HEADER_CONTENT_TYPE_VALUE);
            return fetch(listener);
        }

        private String getChannel(){
            ApplicationInfo appInfo = null;
            String channel = "";
            try {
                appInfo = mContext.getPackageManager()
                        .getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
                channel = appInfo.metaData.getString("UMENG_CHANNEL");
                Log.i(TAG, " AAA channel : " + channel);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return channel;
        }
    }



    /**
     * 回调接口
     */
    public interface HttpFetchListener {
        void onFetchResult(Message msg);
    }

    /**
     * Base MyHandleResultListener
     */
    public static abstract class MyHandleResultListener {
        /**
         * directory parsing data segment JSONObject
         *
         * @param jsonObject all json object
         */
        public abstract void handleData(JSONObject jsonObject);

        public void handleData(JSONArray jsonArray) {

        }

        public void handleData(String str) {

        }

        public abstract int onEmpty();

        public abstract int onError();

        protected  void onError(String str){

        }


        public int onCustomMessage(JSONObject obj) {
            LogUtil.i(obj.optString("message"));
            return 0;
        }

        public abstract void onCancelled(Callback.CancelledException cex);

        public abstract void onFinished();

        /**
         * 为了兼容自定义的 ProgressBar dismiss 回调
         */
        public abstract void onDismissProgressBar();

        /**
         * @param json 为了兼容老版本的代码，回传json进去，新版代码可以不用解析，只需调用登录方法。
         */
        public abstract void onNeedLogin(JSONObject json);

    }

    /**
     * convenience adapted class for MyHandleResultListener
     */
    public static abstract class MyHandleResultListenerAdapter extends MyHandleResultListener {
        @Override
        public int onEmpty() {
            return 0;
        }

        @Override
        public int onError() {
            return 0;
        }
        @Override
        public void onError(String str) {
            Log.i(TAG, "AAA   onError  RESULT: " + str);
        }

        public int onTimoutToken(UMSBaseActivity context, String str) {
            try {
                Log.i(TAG,"AAA onTimoutToken str: " +str);
                // 超时处理
                if (str.contains("401")) {
                    // 清空登录状态

                }

            } catch (Exception e) {

            }

            return 0;
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            //stub
        }

        @Override
        public void onFinished() {
            //stub
        }

        @Override
        public void onNeedLogin(JSONObject json) {
            //stub
        }

        @Override
        public void onDismissProgressBar() {
            //stub
        }
    }


    public static Callback.CommonCallback createCommonCallback(final Context context, final WaitingDialog dlg, final MyHandleResultListener listener) {
        return new Callback.CommonCallback<String>() {
            private Toast mToast;

            /**
             * toast提示
             *
             * @param text toast text
             */
            public void showToast(String text) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(text);
                }
                mToast.show();
            }

            /**
             * toast提示
             *
             * @param resId resource id
             */
            public void showToast(int resId) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(resId);
                }
                mToast.show();
            }

            private void dismissProgressDialog() {
                if (dlg != null) {
                    dlg.dismiss();
                }
                if (listener != null) {
                    listener.onDismissProgressBar();
                }
            }

            @Override
            public void onSuccess(String response) {
                String json = response;
                if (!TextUtils.isEmpty(json)) {
                    JSONArray arr = null;
                    JSONObject obj = null;
                    try {
//                        json = json.replace("(", "").replace(")", "");
                        if (JSONUtils.getJsonType(json) == 1) {
                            arr = new JSONArray(json);
                            listener.handleData(arr);
                        } else if (JSONUtils.getJsonType(json) == 0) {
                            obj = new JSONObject(json);
                            listener.handleData(obj);
                        } else if (JSONUtils.getJsonType(json) == 2) {
                            listener.handleData(json);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else { //json is empty
                    if (listener != null) {
                        int resId = listener.onEmpty();
                        if (resId > 0) {
                            showToast(resId);
                        } else if (resId == 0) {
                            showToast(R.string.request_data_error);
                        }
                    } else {
                        showToast(R.string.request_data_error);
                    }
                    Log.e(TAG, "result is null or empty!");
                }
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "AAA 630 onError result: " + ex.toString());
                if (listener != null) {
                    listener.onError(ex.toString());
                } else {
                    showToast(R.string.toast_common_net_error);
                }
                dismissProgressDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (listener != null) {
                    listener.onCancelled(cex);
                }
                dismissProgressDialog();
            }

            @Override
            public void onFinished() {
                if (listener != null) {
                    listener.onFinished();
                }
                dismissProgressDialog();
            }

        };
    }

    /**
     * 获取当前网络状态
     * @param context
     * @return
     */
    public static int getNetworkStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                return NETWORK_STATUS_IS_WIFI;
            } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
                if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                        .isConnected()) {
                    return NETWORK_STATUS_IS_GPRS; // 2G/2.5G/3G
                }
            }
        }
        return NETWORK_STATUS_NOT_AVAILABLE;
    }
}
