package com.mvmap.news.android.activity;



import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.CheckedTextView;
import org.holoeverywhere.widget.ImageButton;
import org.holoeverywhere.widget.ProgressBar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.mvmap.news.android.R;
import com.mvmap.news.android.model.News;
import com.mvmap.news.android.request.MvmapNewsManager;

public class DetailActivity extends Activity implements OnClickListener, OnMenuItemClickListener{

	private static final String TAG = DetailActivity.class.getSimpleName();

	private	static final String mimeType = "text/html";  
	private	static final String encoding = "utf-8"; 

	private 	TextView 			mTitleTextView;
	private 	ImageButton 		mButtonBack;
	private		ImageButton			mButtonFullScreen;
	private		ImageButton			mButtonTextsize;
	private 	WebView 			mContentView;
	private		ProgressBar			mProgressBar;
	private		CheckedTextView		mCheckTextView;
	private		PopupMenu 			mPopupMenu;
	private		ScrollView			mScrollView;			
	private 	News				mNews;

	private		WebSettings 		webSettings;


	@Override
	protected void onCreate(Bundle sSavedInstanceState) {

		ThemeManager.removeTheme(this);
		setTheme(ThemeManager.DIALOG_WHEN_LARGE | ThemeManager.NO_ACTION_BAR, false);
		super.onCreate(sSavedInstanceState);
		setContentView(R.layout.activity_detail);

		mButtonBack = (ImageButton) findViewById(R.id.detail_btn_back);
		mButtonBack.setOnClickListener(this);
		mButtonFullScreen = (ImageButton)findViewById(R.id.detail_btn_fullscreen);
		mButtonFullScreen.setOnClickListener(this);
		mCheckTextView = (CheckedTextView)findViewById(R.id.detail_btn_fav);
		mCheckTextView.setOnClickListener(this);
		mButtonTextsize = (ImageButton)findViewById(R.id.detail_btn_textsize);
		mButtonTextsize.setOnClickListener(this);

		mTitleTextView = (TextView) findViewById(R.id.detail_title);

		mScrollView = (ScrollView)findViewById(R.id.detail_scrollview);

		mContentView = (WebView) findViewById(R.id.detail_content);
		webSettings = mContentView.getSettings();  
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setJavaScriptEnabled(false);  
		webSettings.setBuiltInZoomControls(false);
		webSettings.setSupportZoom(false);
		webSettings.setTextSize(TextSize.NORMAL);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setBlockNetworkImage(true);
		mContentView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webSettings.setBlockNetworkImage(false);
			}
		});
		

		mProgressBar = (ProgressBar)findViewById(R.id.detail_loading);
		mProgressBar.setVisibility(View.VISIBLE);

		int newsId = getIntent().getIntExtra("id", 0);
		MvmapNewsManager.getInstance().getNewsItem(createMyReqSuccessListener(), 
				createMyReqErrorListener(), newsId);
	}



	private Listener<News> createMyReqSuccessListener(){

		return new Listener<News>(){
			@Override
			public void onResponse(News news) {
				mTitleTextView.setText(news.getTitle());
				mContentView.loadDataWithBaseURL(null, news.getContent(), 
						mimeType, encoding, null);
				mProgressBar.setVisibility(View.GONE);
				mNews = news;
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "data failed to load: "+error.getMessage());
				mProgressBar.setVisibility(View.GONE);
			}
		};
	}



	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.detail_btn_back:
			finish();
			break;
		case R.id.detail_btn_fullscreen:
			if(mNews != null){
				Uri uri = Uri.parse(mNews.getOrigin());   
				Intent intent = new Intent(Intent.ACTION_VIEW,uri);  
				startActivity(intent);				
			}
			break;
		case R.id.detail_btn_fav:
			((CheckedTextView)view).toggle();
			break;
		case R.id.detail_btn_textsize:
			if(mPopupMenu == null){
				mPopupMenu = new PopupMenu(this, view);
				mPopupMenu.getMenuInflater().inflate(R.menu.detail_menu, 
						mPopupMenu.getMenu());
				mPopupMenu.setOnMenuItemClickListener(this);
			}
			mPopupMenu.show();

		default:
			break;
		}
	}



	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.detail_ts_largest:
			webSettings.setTextSize(TextSize.LARGEST);
			item.setChecked(true);
			
			break;
		case R.id.detail_ts_larger:
			webSettings.setTextSize(TextSize.LARGER);
			item.setChecked(true);
			
			
			break;
		case R.id.detail_ts_normal:
			webSettings.setTextSize(TextSize.NORMAL);
			item.setChecked(true);
//			findViewById(R.id.detail_linearlayout).invalidate();
			
			break;
		case R.id.detail_ts_smaller:
			webSettings.setTextSize(TextSize.SMALLER);
			item.setChecked(true);
			
			
			break;
		case R.id.detail_ts_smallest:
			webSettings.setTextSize(TextSize.SMALLEST);
			item.setChecked(true);
			
			
			break;
		default:
			break;
		}
		return false;
	}

}
