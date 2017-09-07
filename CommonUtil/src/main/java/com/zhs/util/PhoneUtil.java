package com.zhs.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;


import java.io.File;
import java.util.UUID;

/**
 * Created by xuguoqiang on 2016/10/21.
 */

public class PhoneUtil {

    private static final long ERROR = 0;

    //查询android系统版本
    public static String getPhoneSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    //查询手机型号
    public static String getPhoneVersion() {
        return android.os.Build.MODEL;
    }

    //查询sdk版本号
    public static String getSdkVersion() {
        return android.os.Build.VERSION.SDK;
    }

    //查询手机品牌厂商
    public static String getPhoneBrand() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * SDCARD是否存在
     **/
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;//这里返回错误信息
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;//这里返回错误信息
        }
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (SecurityException e) {
            return UUID.randomUUID().toString();
        }
    }
}
