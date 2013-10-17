package com.mvmap.news.android.common;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.widget.Toast;

public class DClickExit {

	private static final String DEFAULT_EXIT_TIP = "再按一次返回键退出极客新闻！";
	private static final int DEFAULT_EXIT_TIME  = 2000;
	private Activity mContext;
	private Boolean mIsExit = false;
	private Boolean mHasTask = false;

	/**
	 * 需要执行退出提示的Activity对象引用 
	 * @param context Activity的对象引用
	 */
	public DClickExit(Activity context) {
		this.mContext = context;
	}

	/**
	 * 在给定时间内双击某按钮退出程序
	 * @param keyCode 按钮ID
	 * @param tip 提示信息
	 * @param waitTime 双击检测时间
	 * @return false
	 */
	public boolean dClickExit(int keyCode, String tip, int waitTime) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mIsExit == false) {
				mIsExit = true;
				Toast.makeText(mContext, tip, Toast.LENGTH_SHORT).show();
				if (!mHasTask) {
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							mIsExit = false;
							mHasTask = true;
						}
					}, waitTime);
				}
			} else {
				try{
					Intent intent= new Intent(Intent.ACTION_MAIN);    
					intent.addCategory(Intent.CATEGORY_HOME);
					//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(intent);
				}catch(SecurityException e){
					
				}
				mContext.finish();
				System.exit(0);
//				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		return false;
	}

	/**
	 * 在给定时间内双击某按钮退出程序
	 * @param keyCode 按钮ID
	 * @param waitTime 双击检测时间
	 * @return false
	 */
	public boolean doubleClickExit(int keyCode,int waitTime){
		return dClickExit(keyCode, DEFAULT_EXIT_TIP, waitTime);
	}

	/**
	 * 在给定时间内双击某按钮退出程序
	 * @param keyCode 按钮ID
	 * @return false
	 */
	public boolean doubleClickExit(int keyCode){
		return dClickExit(keyCode, DEFAULT_EXIT_TIP, DEFAULT_EXIT_TIME);
	}

}
