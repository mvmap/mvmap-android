package com.mvmap.activity;

import com.mvmap.news.R;
import com.mvmap.news.R.layout;
import com.mvmap.news.R.menu;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable(){
				 
	        @Override
	        public void run() {
	             Intent intent = new Intent(SplashActivity.this,MainActivity.class);
	             SplashActivity.this.startActivity(intent);
	             SplashActivity.this.finish();
	        }
             
        }, SPLASH_DISPLAY_LENGHT); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
