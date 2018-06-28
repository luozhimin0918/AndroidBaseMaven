package com.ums.android.util;

import android.content.Context;

public class ProtocolUtils {

    public ProtocolUtils(Context ctx) {
    }

    // 开发环境0 ，测试环境1，staging 2 , 生产环境3
    public static final int UNSTABLE = 0;
    public static final int QA = 1;
    public static final int STAGING = 2;
    public static final int PRODUCT = 3;
    public static  int CUR_ENVI = UNSTABLE;

    private static final String PREFIX_UNSTABLE = "https://unstable";
    private static final String PREFIX_STAGING = "https://staging";
    private static final String PREFIX_QA = "https://qa";
    private static final String PREFIX_PRODUCT = "https://www";
    private static final String PREFIX_H5_UNSTABLE = "https://unstable-m";
    private static final String PREFIX_H5_STAGING = "https://staging-m";
    private static final String PREFIX_H5_QA = "https://qa-m";
    private static final String PREFIX_H5_PRODUCT = "https://m";
    private static final String UMS_DOMAIN = ".chinaums.com";

    public static String BASE_USER_URL = "";

    static {
        if (CUR_ENVI == UNSTABLE) {
        } else if (CUR_ENVI == QA) {
        }else if (CUR_ENVI == STAGING) {
        }  else if (CUR_ENVI == PRODUCT) {
        }
    }


}

