package com.mvmap.news.activity;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mvmap.news.R;
import com.mvmap.news.adapter.CategoryFragmentAdapter;
import com.mvmap.news.model.NewsCategory;
import com.viewpagerindicator.TabPageIndicator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends BaseActivity implements OnPageChangeListener, OnClickListener {
	
	CategoryFragmentAdapter mAdapter;
	private SlidingMenu menu;
	private ArrayList<NewsCategory> categories = new ArrayList<NewsCategory>();
	
	private Button mMenuButton;
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		NewsCategory cat = new NewsCategory();
		cat.id = 1;
		cat.name = "互联网";
		categories.add(cat);
		
		NewsCategory cat2 = new NewsCategory();
		cat2.id = 2;
		cat2.name = "设计";
		categories.add(cat2);
		
		NewsCategory cat3 = new NewsCategory();
		cat3.id = 3;
		cat3.name = "社区";
		categories.add(cat3);
		
		NewsCategory cat4 = new NewsCategory();
		cat4.id = 4;
		cat4.name = "前端";
		categories.add(cat4);
		
		NewsCategory cat5 = new NewsCategory();
		cat5.id = 5;
		cat5.name = "Python";
		categories.add(cat5);
		
		NewsCategory cat6 = new NewsCategory();
		cat6.id = 6;
		cat6.name = "Rails";
		categories.add(cat6);
		
		NewsCategory cat7 = new NewsCategory();
		cat7.id = 7;
		cat7.name = "Mobile";
		categories.add(cat7);
		
		NewsCategory cat8 = new NewsCategory();
		cat8.id = 8;
		cat8.name = "站长";
		categories.add(cat8);
		
		NewsCategory cat9 = new NewsCategory();
		cat9.id = 9;
		cat9.name = "DBA";
		categories.add(cat9);
		
		NewsCategory cat10 = new NewsCategory();
		cat10.id = 10;
		cat10.name = "创业";
		categories.add(cat10);
		
		mPager = (ViewPager)findViewById(R.id.pager);
		mAdapter = new CategoryFragmentAdapter(getSupportFragmentManager(), categories, mPager);
		mPager.setAdapter(mAdapter);
		
		initViews();
	}
	
	protected void initViews() {
   	   // configure the SlidingMenu
       menu = new SlidingMenu(this);
       menu.setMode(SlidingMenu.LEFT);
       menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
       menu.setFadeDegree(0.35f);
       menu.setBehindWidth(200);
       menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
       menu.setMenu(R.layout.menu);
       
       TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
       indicator.setViewPager(mPager);
       indicator.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		System.out.println("当前滑动到第" + position + "页");
		if (position == 0) {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onClick(View view) {
		
	}
	

}
