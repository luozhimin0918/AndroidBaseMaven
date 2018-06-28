package com.ums.android.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by JerryWang on 2016/3/31.
 */
public class RegUtils {

    public static boolean isIDNumber(String acc) {
        Pattern pattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        Matcher matcher = pattern.matcher(acc);
        return matcher.find();
    }
    /**
     * 判断是否为手机号码
     * @return
     */
    public static boolean isCellNumber(String acc) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(acc);
        return matcher.find();
    }
    /**
     * 判断是否为验证码
     * @return
     */
    public static boolean isOtpNumber(String acc) {
        Pattern pattern = Pattern.compile("\\d{6}$");
        Matcher matcher = pattern.matcher(acc);
        return matcher.find();
    }
    /**
     * 判断是否为身份证号码
     * @return
     */
    public static boolean idVerify(String str) throws PatternSyntaxException {
        String regEx = "^\\d{18}$|^\\d{17}(\\d|X|x|Y|y|Z|z)$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    /**
     * 判断是否为姓名
     * @return
     */
    public static boolean nameVerify(String str) throws PatternSyntaxException {
        String regEx = "[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    /**
     * 判断是否为银行卡号
     * @param acc
     * @return
     */
    public static boolean isBankCardNumber(String acc) {
        Pattern pattern = Pattern.compile("(^[0-9]{16}$)|(^[0-9]{17}$)|(^[0-9]{18}$)|(^[0-9]{19}$)");
        Matcher matcher = pattern.matcher(acc);
        return matcher.find();
    }

    /**
     * 判断是否为密码
     * @return
     */
    public static boolean isPswVerify(String acc) {
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
        Matcher matcher = pattern.matcher(acc);
        return matcher.find();
    }

    // 验证邮箱
    public static boolean isEmail(String acc) {
//        Pattern pattern = Pattern.compile("^([ums_navigation_0-zA-Z0-9_-])+@([ums_navigation_0-zA-Z0-9_-])+(.[ums_navigation_0-zA-Z0-9_-])+");
//        Pattern pattern = Pattern.compile("^[ums_navigation_0-z0-9]+([._\\\\-]*[ums_navigation_0-z0-9])*@([ums_navigation_0-z0-9]+[-ums_navigation_0-z0-9]*[ums_navigation_0-z0-9]+.){1,63}[ums_navigation_0-z0-9]+$");
//        Pattern pattern = Pattern.compile("^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$");
//        String str = "^([ums_navigation_0-zA-Z0-9]*[-_]?[ums_navigation_0-zA-Z0-9]+)*@([ums_navigation_0-zA-Z0-9]*[-_]?[ums_navigation_0-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        String str = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(acc);
        return matcher.find();
    }
}
