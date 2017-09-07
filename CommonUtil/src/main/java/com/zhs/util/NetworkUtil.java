package com.zhs.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkUtil
 *
 * @author wlf(Andy)
 * @email 411086563@qq.com
 */
public class NetworkUtil {

    /**
     * is network available
     *
     * @param context Context
     * @return true means network is available
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isNetwork = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return isNetwork;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isNetwork = true;
                        break;
                    }
                }
            }
            return isNetwork;
        }
    }

    /**
     * 获取网络类型，-1没网络，1WiFi，2手机网络
     */
    public static int getNetWork(Context context) {
        // TODO 获取网络类型，-1没网络，1WiFi，2手机网络
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null) {// 没有任何网络
            return -1;
        } else if (ni.getType() == ConnectivityManager.TYPE_WIFI) {// 是否是wifi
            return 1;
        } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {// 是否是手机网络
            return 2;
        } else {
            return -1;
        }
    }
}


