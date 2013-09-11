package com.mvmap.activity;

import com.mvmap.news.R;
import com.mvmap.news.R.layout;
import com.mvmap.news.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

}
