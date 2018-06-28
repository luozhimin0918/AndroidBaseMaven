package com.ums.android.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by jerrywang on 2016/3/24.
 */
public class JSONUtils {
    /***
     * 判断一个字符串是json对象还是json数组,还是字符串
     * @param json
     * @return boolean
     */
    public static int getJsonType(String json){
        int type  = -1;
        Object obj = null;
        try {
            obj = new JSONTokener(json).nextValue();
            if(obj instanceof JSONObject){
                type = 0;
            }else if (obj instanceof JSONArray){
                type = 1;
            }else if(obj instanceof String){
                type = 2;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }

}
