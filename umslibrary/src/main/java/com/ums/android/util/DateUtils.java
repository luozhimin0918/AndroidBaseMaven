package com.ums.android.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created on 2018/1/22
 * @author wangjin
 * @email 156587036@qq.com
 */
public class DateUtils {
    private final static String TAG = "DateUtils";

    public static String getCurDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(c.getTime());
    }

    public static String getCurDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        return f.format(c.getTime());
    }

    /**
     * 计算两个日期的间隔天数
     */
    public static int daysBetween(String date1, String date2) {
        String bDate = date1;
        String eDate = date2;
        Log.i(TAG, "bDate: " + bDate);
        Log.i(TAG, "eDate: " + eDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long between_days = 0;
        try {
            cal.setTime(sdf.parse(bDate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(eDate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (Exception e) {

        }
        int days = Integer.parseInt(String.valueOf(between_days));
        if (days < 0) {
            days = 0;
        }
        return days;
    }

    public static String getFormatStr(String rate) {
        Double actRate = Double.parseDouble(rate);
        return String.format("%.01f", actRate);
    }

    public static String getFormatDate(String date) {
        if (date != null && !date.equalsIgnoreCase("") && !date.equalsIgnoreCase("null")) {
            String s1 = date.split("T")[0];
            return s1;
        } else {
            return "";
        }
    }

    public static String getFormat1Date(String date) {
        if (date != null && !date.equalsIgnoreCase("") && !date.equalsIgnoreCase("null")) {
            String s1 = date.split("T")[0];
            String[] dateArr = s1.split("-");
            String d1 = dateArr[0] + "/" + dateArr[1] + "/" + dateArr[2];
            return d1;
        } else {
            return "";
        }
    }

    public static String getFormatNumber1(String num) {
        BigDecimal bd = new BigDecimal(num);
        NumberFormat nf = new DecimalFormat(",###,###");
        return nf.format(bd);
    }

    public static String getFormatNumber2(String num) {
        BigDecimal bd = new BigDecimal(num);
        NumberFormat nf = new DecimalFormat(",###,###.00");
        return nf.format(bd);
    }

    public static String getFormatNumber3(String num) {
        if(!SystemUtils.isEmpty(num)){
            if (num.indexOf(".") > 0) {
                //正则表达
                num = num.replaceAll("0+?$", "");//去掉后面无用的零
                num = num.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
        }
        return num;
    }

}
