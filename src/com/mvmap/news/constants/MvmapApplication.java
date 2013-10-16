package com.mvmap.news.constants;

import java.io.IOException;

import com.mvmap.news.utils.CheckNet;

import android.app.Application;
import android.util.Log;

public class MvmapApplication extends Application {
	private static final String TAG = "MeiLiApplication";
	private static MvmapApplication mApplication;
	public boolean isConnectNet = false;

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
		init();
	}

	// TODO init path,db.....
	private void init() {
		if (CheckNet.getAPNType(mApplication) > 0) {
			isConnectNet = true;
		} else {
			isConnectNet = false;
		}
	}

	public static MvmapApplication getContext() {
		return mApplication;
	}
}
