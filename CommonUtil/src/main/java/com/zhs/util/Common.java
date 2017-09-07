package com.zhs.util;

/**
 * Created by Administrator on 2017/8/30.
 */

public class Common {
    private static Common instance;
    private boolean isDebug;
    private String httpSpName;

    public Common() {

    }

    public static Common getInstance() {
        if (instance == null) {
            synchronized (Common.class) {
                if (instance == null) {
                    instance = new Common();
                }
            }
        }

        return instance;
    }

    /**
     * true: open log
     * false: close log
     *
     * @param flag
     */
    public void setDebugMode(boolean flag) {
        this.isDebug = flag;
    }

    /**
     * @param return true: open log
     *               false: close log
     */
    public boolean isDebug() {
        return isDebug;
    }

    public void setHttpSpName(String name) {
        this.httpSpName = name;
    }
    public String getHttpSpName() {
        return httpSpName == null ? "pre_http_sp" : httpSpName;
    }

}
