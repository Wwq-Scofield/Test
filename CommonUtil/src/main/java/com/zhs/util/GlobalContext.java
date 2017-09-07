package com.zhs.util;

import android.content.Context;
import android.widget.ListView;

import java.util.List;


public class GlobalContext {
    private GlobalContext() {
    }

    private static Context sAppContext;

    public static final void setAppContext(Context appContext) {
        if (appContext != null) {
            sAppContext = appContext.getApplicationContext();
        }
    }

    public static final Context getAppContext() {
        assert sAppContext != null;
        return sAppContext;
    }


}
