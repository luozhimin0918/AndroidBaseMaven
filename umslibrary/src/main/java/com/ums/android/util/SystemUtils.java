package com.ums.android.util;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.ums.android.R;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Created on 2017/9/15
 * @author wangjin
 * @email 156587036@qq.com
 */

public class SystemUtils {

    private final static String TAG = "SystemUtils";
    private static Toast mToast;

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || "null".equalsIgnoreCase(str) || "".equalsIgnoreCase(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPackageVersion(Context cxt) {
        String ver = "";
        PackageManager packageManager = cxt.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(cxt.getPackageName(), 0);
            ver = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return ver;
    }


    /**
     * 隐藏软键盘
     * @param context
     * @param edt     从哪个里面隐藏，如EditText等有焦点的View
     */
    public static void hideKeyBord(Context context, View edt) {
        InputMethodManager inmanager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inmanager.hideSoftInputFromWindow(edt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     * @param context
     * @param edt
     */

    public static void showKeyBord(Context context, View edt) {
        InputMethodManager inmanager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inmanager.showSoftInput(edt, 0);
    }

    /***
     * 实现leftPad功能, 对字符串实现左填充*
     * @param str    被填充字符串: 5*
     * @param ch     填充字符: #*
     * @param length 填充以后的长度: 8* @return "#######5"
     */
    public static String leftPad(String str, int length, char ch) {
        if (str.length() == length) {
            return str;
        }
        char[] chs = new char[length];
        Arrays.fill(chs, ch);
        System.arraycopy(str.toCharArray(), 0, chs, length - str.length(), str.length());
        return new String(chs);
    }

    public static String string2Unicode(String msg) {
        StringBuffer buf = new StringBuffer();
        for (char c : msg.toCharArray()) {
            buf.append("\\u" + leftPad(Integer.toHexString(c), 4, '0'));
        }
        return buf.toString();
    }


    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取网络状态，wifi,wap,2g,3g.
     * @param context 上下文
     */
    public static String getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        String netWorkType = "";
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();

            if (type.equalsIgnoreCase("WIFI")) {
                netWorkType = "WIFI";
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                netWorkType = TextUtils.isEmpty(proxyHost) ? (SystemUtils.isFastMobileNetwork(context) ? "Mobile3G" : "Mobile2G") : "MobileWap";
            }
        } else {
            netWorkType = "NULL";
        }
        return netWorkType;
    }






    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }

    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }


    /*******************
     * 下载文件  start
     ********************************/

    static Context mContext;
    static final int DOWNLOAD_MSG_LISTENER = 102;
    static final int STEP_APP_LISTENER = 103;
    static DownloadManager manager;
    static long downloadId;
    private final static String ums_DOWNLOAD_PATH = "/ums/dowload";

    public static void downloadFile(Context context, String url) {
        Log.i(TAG, "AAA SDK: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 9) {
            downloadVersion(context, url);
        } else {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }



    @TargetApi(9)
    private static void downloadVersion(Context context, String url) {
        Log.i(TAG, "AAA download,url: " + url);
        mContext = context;
        // DownloadManager manager = (DownloadManager) context
        // .getSystemService(Context.DOWNLOAD_SERVICE);
        manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        // 允许下载网络模式
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        down.setShowRunningNotification(true);
        down.setVisibleInDownloadsUi(true);

        String fileName = url.substring(url.lastIndexOf("/") + 1);
        down.setTitle(fileName);
        File downloadFile = produceDownloadFile(context);
        if (downloadFile == null) {
            Toast.makeText(context, R.string.toast_sdcard_error, Toast.LENGTH_SHORT).show();
            return;
        } else {
            // down.setDestinationInExternalFilesDir(context,
            // downloadFile.getAbsolutePath(), fileName);
            down.setDestinationUri(Uri.fromFile(new File(downloadFile.getAbsolutePath(), fileName)));
        }
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);// 标示下载完成后通知栏显示通知
        /**
         * 若有用户删除了自身手机中的下载功能，当更新时会调用到下载模块，如果用户已经将其删除，导致程序崩溃 若删除后加入try
         * catch防止程序崩溃，并跳转到浏览器下载
         */
        try {
            // 将下载请求放入队列
            downloadId = manager.enqueue(down);
        } catch (Exception e) {
            Uri uri = Uri.parse(url);
            Log.e(TAG, "uri=" + uri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            context.startActivity(intent);
            e.printStackTrace();
        }
    }

    public static File produceDownloadFile(Context context) {
        String downloadPath = "";
        File downloadFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            downloadFile = context.getExternalFilesDir(null);
            if (downloadFile == null) {
                downloadPath = context.getFilesDir().getAbsolutePath() + ums_DOWNLOAD_PATH;
                downloadFile = new File(downloadPath);
            }
        } else {
            downloadPath = context.getFilesDir().getAbsolutePath() + ums_DOWNLOAD_PATH;
            downloadFile = new File(downloadPath);
        }
        if (!downloadFile.exists()) {
            downloadFile.mkdirs();
        }
        return downloadFile;
    }


    public static int getVersionCode(Context context) {
        // 获取packagemanager的实例
        int versionCode = 0;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断是否是快速点击
     */
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return xmlUTF8;
    }

}