package com.mvmap.news.activity;

import com.mvmap.news.activity.MainActivity;
import com.mvmap.news.R;

import android.content.Intent;
import android.os.Bundle;

/*
 * 启动页面
 */
public class SplashActivity extends BaseActivity {

	private static final String TAG = "SplashActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		init();
	}

	private void init() {
		new Thread() {
			public void run() {
				try {
					// 启动画面显示3秒
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 打开主界面
				Intent intent = new Intent(mContext, MainActivity.class);
				mContext.startActivity(intent);
				// 销毁启动页面
				SplashActivity.this.finish();
			}
		}.start();
	}
}
