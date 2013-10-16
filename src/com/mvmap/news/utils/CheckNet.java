package com.mvmap.news.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class CheckNet {
	final static int NET_WIFI = 1, NET_2G = 2, NET_3G = 3;
	Context context;

	public CheckNet(Context context) {
		this.context = context;
	}

	// 判断网络是否可用
	public static boolean checkNet(Context context) {
		ConnectivityManager connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connect.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			if (netInfo.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前的网络状态
	 * 
	 *  没有网络:    -1
	 *  WIFI网络:   1
	 *  wap网络:    2
	 *  net网络:    3
	 * 
	 * @param context
	 * @return
	 */
	public static int getAPNType(Context context) {
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int subType = telephonyManager.getNetworkType();
		//if (DEBUG)
			Log.d("TAG", "telephonyType=" + subType);
		if (networkInfo == null || !networkInfo.isAvailable()) {
			//if (DEBUG)
				//Log.d(TAG, "网络类型为空");
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			switch (subType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return NET_2G; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return NET_2G; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return NET_2G; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return NET_3G; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return NET_3G; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return NET_2G; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return NET_3G; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return NET_3G; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return NET_3G; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return NET_3G; // ~ 400-7000 kbps
				// NOT AVAILABLE YET IN API LEVEL 7
				// case Connectivity.NETWORK_TYPE_EHRPD:
				// return NET_3G; // ~ 1-2 Mbps
				// case Connectivity.NETWORK_TYPE_EVDO_B:
				// return NET_3G; // ~ 5 Mbps
				// case Connectivity.NETWORK_TYPE_HSPAP:
				// return NET_3G; // ~ 10-20 Mbps
				// case Connectivity.NETWORK_TYPE_IDEN:
				// return NET_2G; // ~25 kbps
				// case Connectivity.NETWORK_TYPE_LTE:
				// return NET_3G; // ~ 10+ Mbps
				// Unknown
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return NET_2G;
			default:
				return NET_2G;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NET_WIFI;
		}
		return netType;
	}
}