package com.mvmap.news.android.activity;

import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mvmap.news.R;

public class SplashActivity extends Activity implements Runnable{
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		ThemeManager.removeTheme(this);
		setTheme(ThemeManager.DIALOG_WHEN_LARGE | ThemeManager.NO_ACTION_BAR, false);
		super.onCreate(savedInstanceState);
		
		ImageView splashView = new ImageView(this);
		splashView.setScaleType(ScaleType.FIT_XY);
		splashView.setImageResource(R.drawable.splash);
		splashView.setAnimation(AnimationUtils.loadAnimation(this, 
				R.anim.fade_in));
		setContentView(splashView);
		
		
		Handler handle = new Handler();
		
		handle.postDelayed(this, 2000);
		
		
	}


	@Override
	public void run() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
	}
	
	
	
}
