package com.mvmap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.mvmap.news.R;

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
