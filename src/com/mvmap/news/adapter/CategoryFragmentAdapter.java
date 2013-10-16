package com.mvmap.news.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mvmap.news.fragment.NewsListFragment;
import com.mvmap.news.model.NewsCategory;
import com.mvmap.news.model.NewsItem;

import android.R.integer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class CategoryFragmentAdapter extends FragmentPagerAdapter {
	private ArrayList<NewsCategory> titles;
	private ViewPager mViewPager;
	

	public CategoryFragmentAdapter(FragmentManager fm, ArrayList<NewsCategory> titles, ViewPager pager) {
		super(fm);
		this.titles = titles;
		this.mViewPager = pager;
	}

	// 此方法调用次数固定，有几个item就调用几次，不会重复调用。
	@Override
	public Fragment getItem(int position) {
		NewsListFragment fragment = new NewsListFragment();
		fragment.cat = titles.get(position);
		
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titles.size();
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).name;
    }

}
