package com.mvmap.news.android.activity;



import java.util.List;

import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.CheckedTextView;
import org.holoeverywhere.widget.ImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.BaseAdapter;

import com.mvmap.news.R;
import com.mvmap.news.android.adapter.WebviewAdapter;
import com.mvmap.news.android.adapter.WebviewAdapter.ViewHolder;
import com.mvmap.news.android.common.BitmapUtil;
import com.mvmap.news.android.common.ConstWeixin;
import com.mvmap.news.android.model.News;
import com.mvmap.news.android.view.ViewFlow;
import com.mvmap.news.android.view.ViewFlow.ViewSwitchListener;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends Activity implements OnClickListener, OnMenuItemClickListener
, ViewSwitchListener, IWXAPIEventHandler{

	private static final String TAG = DetailActivity.class.getSimpleName();


	private 	ImageButton 		mButtonBack;
	private		ImageButton			mButtonFullScreen;
	private		ImageButton			mButtonTextsize;
	private		CheckedTextView		mCheckTextView;
	private		PopupMenu 			mPopupMenu;
	private		ViewFlow 			mViewFlow;


	private		News				mCurNews;
	private		WebSettings			mCurWebSettings;

	private		IWXAPI 				wxapi;


	@Override
	protected void onCreate(Bundle sSavedInstanceState) {

		ThemeManager.removeTheme(this);
		setTheme(ThemeManager.DIALOG_WHEN_LARGE | ThemeManager.NO_ACTION_BAR, false);
		super.onCreate(sSavedInstanceState);
		setContentView(R.layout.activity_detail);

		wxapi = WXAPIFactory.createWXAPI(this, ConstWeixin.APP_ID, false);
		wxapi.handleIntent(getIntent(), this);

		mButtonBack = (ImageButton) findViewById(R.id.detail_btn_back);
		mButtonBack.setOnClickListener(this);
		mButtonFullScreen = (ImageButton)findViewById(R.id.detail_btn_fullscreen);
		mButtonFullScreen.setOnClickListener(this);
		mCheckTextView = (CheckedTextView)findViewById(R.id.detail_btn_fav);
		mCheckTextView.setOnClickListener(this);
		mButtonTextsize = (ImageButton)findViewById(R.id.detail_btn_textsize);
		mButtonTextsize.setOnClickListener(this);

		List<String> newsIdList = getIntent().getStringArrayListExtra("ids");
		mViewFlow= (ViewFlow) findViewById(R.id.detail_content);
		BaseAdapter mAdapter = new WebviewAdapter(this, newsIdList);
		mViewFlow.setAdapter(mAdapter);
		mViewFlow.setOnViewSwitchListener(this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		wxapi.handleIntent(intent, this);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.detail_btn_back:
			finish();
			break;
		case R.id.detail_btn_fullscreen:
			//			if(mCurNews != null){
			//				Uri uri = Uri.parse(mCurNews.getOrigin());   
			//				Intent intent = new Intent(Intent.ACTION_VIEW,uri);  
			//				startActivity(intent);				
			//			}
			if(mCurNews == null) return;
			wxapi.registerApp(ConstWeixin.APP_ID);
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = mCurNews.getOrigin();
			WXMediaMessage msg = new WXMediaMessage(webpage);
			msg.title = mCurNews.getTitle();
			//msg.description = ;
			msg.thumbData = BitmapUtil.getBytesFromUrl(mCurNews.getOrigin());

			SendMessageToWX.Req req = new SendMessageToWX.Req();
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
			req.transaction = "webpage"+System.currentTimeMillis();
			req.message = msg;
			wxapi.sendReq(req);
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
		if(mCurWebSettings == null) return false;
		item.setChecked(true);
		switch (item.getItemId()) {
		case R.id.detail_ts_largest:
			mCurWebSettings.setTextSize(TextSize.LARGEST);
			break;
		case R.id.detail_ts_larger:
			mCurWebSettings.setTextSize(TextSize.LARGER);
			break;
		case R.id.detail_ts_normal:
			mCurWebSettings.setTextSize(TextSize.NORMAL);
			break;
		case R.id.detail_ts_smaller:
			mCurWebSettings.setTextSize(TextSize.SMALLER);
			break;
		case R.id.detail_ts_smallest:
			mCurWebSettings.setTextSize(TextSize.SMALLEST);
			break;
		default:
			break;
		}
		return false;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onSwitched(View view, int position) {
		Object viewTag = view.getTag();
		if(viewTag != null){
			mCurNews = ((ViewHolder)viewTag).mNews;
		}
		mCurWebSettings = ((WebView)view.findViewById(R.id.detail_webview)).getSettings();
	}

	@Override
	public void onReq(BaseReq arg0) {

	}

	@Override
	public void onResp(BaseResp arg0) {

	}

}
