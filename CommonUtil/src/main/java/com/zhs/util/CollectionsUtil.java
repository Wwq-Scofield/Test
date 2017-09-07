package com.zhs.util;

import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/3/20.
 */

public class CollectionsUtil {
    public static boolean isListEmpty(List list) {
        if (list == null) {
            return true;
        } else {
            Common.init(GlobalContext.getAppContext(),new Common.Builder()
                        .isDebug(true)
                        .httpSpName(""));
            return list.isEmpty();
        }
    }
    public static boolean isMapEmpty(Map map) {
        if (map == null) {
            return true;
        } else {
            return map.isEmpty();
        }
    }
}
