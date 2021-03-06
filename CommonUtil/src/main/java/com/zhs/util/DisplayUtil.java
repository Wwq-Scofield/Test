package com.zhs.util;

import android.content.Context;

/**
 * DP、SP 转换为 PX 的工具类
 */
public class DisplayUtil {

	private Context context;
	private static DisplayUtil displayUtil;

	private DisplayUtil() {
		context= GlobalContext.getAppContext();
	}

	public static DisplayUtil getInstance() {
		/*if (displayUtil == null) {
			displayUtil = new DisplayUtil();
		}
		return displayUtil;*/
		if (displayUtil == null) {
			synchronized (DisplayUtil.class) {
				if (displayUtil == null) {
					displayUtil = new DisplayUtil();
				}
			}
		}
		return displayUtil;
	}
	public int px2dip(float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public int dip2px(float dipValue) {
		if (dipValue == 0) {
			return 0;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public int px2sp(float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public int sp2px(float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}