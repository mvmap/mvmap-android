package com.mvmap.news.activity;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mvmap.news.constants.ConstantString;
import com.mvmap.news.constants.MvmapApplication;
import com.mvmap.news.utils.CheckNet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends SherlockFragmentActivity {
	public BaseActivity mContext = null;

	protected boolean isSdcard;
	protected boolean isConn = true;
	protected ViewPager mPager;

	BroadcastReceiver baseReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConstantString.EXIT_APP)) {
				mContext.finish();// 检测到退出广播则finish掉所有Activity
			} else {
				if (CheckNet.getAPNType(mContext) > 0) {
					MvmapApplication.getContext().isConnectNet = true;
				} else {
					MvmapApplication.getContext().isConnectNet = false;
				}
			}

		}
	};

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSomeBroadcast();
		mContext = this;
	}

	private void initSomeBroadcast() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConstantString.EXIT_APP);
		//filter.addAction(Intent.ACTION_BOOT_COMPLETED);
		filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		filter.addAction("android.net.conn.LINK_STATE_CHANGED");
		registerReceiver(baseReceiver, filter);

	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(baseReceiver);
		super.onDestroy();
	}

	

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
