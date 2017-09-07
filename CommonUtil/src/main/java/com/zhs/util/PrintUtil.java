package com.zhs.util;

import android.text.TextUtils;


import java.util.Map;

/**
 * 打印工具类
 * Created by pengyan on 2017/1/6.
 */

public class PrintUtil {

    /**
     * 将hashMap打印出来
     * 主要是将Post的请求参数打印出来
     *
     * @param hashMap 打印的HashMap
     */
    public static void printHashMap(Map<String, String> hashMap) {
        StringBuilder builder = new StringBuilder("");
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        String parames = builder.toString();
        if (!TextUtils.isEmpty(parames)) {
            StringBuilder sb = new StringBuilder(parames);
            parames = sb.replace(parames.length() - 1, parames.length(), "").toString();
        }
        LogCatUtil.DLog("wwq", parames);
    }
}
