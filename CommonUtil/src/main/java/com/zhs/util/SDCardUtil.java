package com.zhs.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by Lenovo on 2017/4/19.
 */
public class SDCardUtil {
    public static boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public static long getSDFreeSize(){
        File path = Environment.getExternalStorageDirectory();
        StatFs statfs = new StatFs(path.getPath());
        //获得单个数据块的大小
        long blocksize = statfs.getBlockSize();
        //获得空闲数据块的个数
        long freeblock = statfs.getAvailableBlocks();
        return (freeblock*blocksize)/1024/1024; //单位MB
    }
}
