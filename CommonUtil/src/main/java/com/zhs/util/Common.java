package com.zhs.util;

import android.content.Context;

/**
 * Created by Administrator on 2017/8/30.
 */

public class Common {
    private static Common instance;
    public static void init(Context context, Builder builder) {
        if (builder == null) {
            throw new IllegalArgumentException("configuration can not be null!");
        }
        Constant.isDebug=builder.isDebug;
        Constant.httpSpName=builder.httpSpName;
    }

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

    public static class Builder {
        private boolean isDebug;
        private String httpSpName;

        public Builder isDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder httpSpName(String httpSpName) {
            this.httpSpName = httpSpName;
            return this;
        }

        public Builder build() {
            return new Builder();
        }
    }

}
