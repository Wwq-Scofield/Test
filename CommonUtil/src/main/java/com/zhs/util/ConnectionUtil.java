package com.zhs.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 单例模式，判断网络状态工具，必须在application中初始化
 * */
public class ConnectionUtil {
	public enum Type {
		WIFI, // WIFI
		MOBILE, // 移动网络
		NO// 无网络
	}

	private static ConnectionUtil connectionUtil;
	private ConnectivityManager manager;

	private ConnectionUtil() {
	}

	public static ConnectionUtil getInstance() {
		/*if (connectionUtil == null) {
			connectionUtil = new ConnectionUtil();
		}
		return connectionUtil;*/
		if (connectionUtil == null) {
			synchronized (ConnectionUtil.class) {
				if (connectionUtil == null) {
					connectionUtil = new ConnectionUtil();
				}
			}
		}
		return connectionUtil;
	}

	public void init(Context context) {
		manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	/*
	 * 获取手机当前的网络连接状态
	 */
	public Type getConnectedType() {
		if (manager != null) {
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				int type = info.getType();
				if (type == ConnectivityManager.TYPE_WIFI) {
					return Type.WIFI;
				} else if (type == ConnectivityManager.TYPE_MOBILE) {
					return Type.MOBILE;
				}
			}
		}
		return Type.NO;
	}
}
