package com.mvmap.news.android;

import org.holoeverywhere.LayoutInflater;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.View;

public class ThemeActionProvider extends ActionProvider{
	
	private final Context mContext;

	public ThemeActionProvider(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	public View onCreateActionView() {
		
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		

		return null;
	}

}
