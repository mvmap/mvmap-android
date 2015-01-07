package com.mvmap.news.android.activity;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.AddonSlider.AddonSliderA;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.slider.SliderMenu;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.mvmap.news.R;
import com.mvmap.news.android.common.DClickExit;
import com.mvmap.news.android.fragment.MainFragment;
import com.mvmap.news.android.model.Category;
import com.mvmap.news.android.request.MvmapNewsManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;
import com.tencent.stat.common.StatLogger;

@Addons(AddonSlider.class)
public class MainActivity extends Activity{

	private final String TAG = getClass().getSimpleName();
	
	private static StatLogger logger = new StatLogger("MTADemon");

	private SliderMenu 					mSliderMenu;
	private	DClickExit					mDbclickExit;

	private ArrayList<Category>				categoryList;

	public AddonSliderA addonSlider() {
		return addon(AddonSlider.class);
	}

	static StatLogger getLogger() {
		return logger;
	}
	
	private void initMTAConfig(boolean isDebugMode) {
		logger.d("isDebugMode:" + isDebugMode);
		if (isDebugMode) {
			StatConfig.setDebugEnable(true);
			StatConfig.setStatSendStrategy(StatReportStrategy.INSTANT);
		} else { 
			StatConfig.setDebugEnable(false);
			StatConfig.setAutoExceptionCaught(true);
			StatConfig.setStatSendStrategy(StatReportStrategy.APP_LAUNCH);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		VolleyLog.v(TAG+" %s", "onCreate");
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		
        initMTAConfig(true);
		String appkey = "Aqc1101244287";
		StatService.startStatService(this, appkey, com.tencent.stat.common.StatConstants.VERSION);
		 
		getSupportActionBar().setTitle(R.string.app_name);
		mSliderMenu = addonSlider().obtainDefaultSliderMenu(R.layout.main_left_menu);
		
		if(savedInstanceState != null){
			categoryList = savedInstanceState.getParcelableArrayList("categoryList");
			createSliderMenu(categoryList, false);
		}else{
			Bundle bundle = getIntent().getBundleExtra(ThemeManager.KEY_INSTANCE_STATE);
			if(bundle!=null){
				categoryList = bundle.getParcelableArrayList("categoryList");
				createSliderMenu(categoryList, false);
			}else{
				categoryList = new ArrayList<Category>();
				MvmapNewsManager.getInstance().getNewsCategory(createMyReqSuccessListener(), 
						createMyReqErrorListener());	
			}
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelableArrayList("categoryList", categoryList);	
		super.onSaveInstanceState(outState);
	}

	
	private void createSliderMenu(List<Category> list, boolean isFirstLoad){
		if(list == null || list.size() == 0) return;
		
		for(Category category : list){
			if(isFirstLoad){
				categoryList.add(category);	
			}
			Bundle fragmentArguments = new Bundle();
			fragmentArguments.putString("name", category.getCategoryName());
			fragmentArguments.putInt("id", category.getCategoryId());

			mSliderMenu.add(category.getCategoryName(), MainFragment.class, 
					fragmentArguments, SliderMenu.BLUE);
		}
		if(isFirstLoad){
			mSliderMenu.setCurrentPage(0);	
		}
		
	}

	private Listener<List<Category>> createMyReqSuccessListener(){

		return new Listener<List<Category>>(){
			@Override
			public void onResponse(List<Category> list) {
				createSliderMenu(list, true);
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "data failed to load: "+error.getMessage());
			}
		};
	}

	public boolean onKeyDown(int keyCode, KeyEvent event){
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(mDbclickExit == null){
				mDbclickExit = new DClickExit(this);
			}
			mDbclickExit.doubleClickExit(keyCode);
			return true;
		default:
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.skin_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPreferences mPerferences = getDefaultSharedPreferences(); 
		SharedPreferences.Editor mEditor = mPerferences.edit();
		switch (item.getItemId()) {
		case R.id.skin_light:
			if(ThemeManager.getDefaultTheme() != ThemeManager.LIGHT){
				mEditor.putInt("theme", ThemeManager.LIGHT);
				mEditor.commit();
				ThemeManager.setDefaultTheme(ThemeManager.LIGHT);
				ThemeManager.restart(this, false);
			}
			return true;

		case R.id.skin_dark:
			if(ThemeManager.getDefaultTheme() != ThemeManager.DARK){
				mEditor.putInt("theme", ThemeManager.DARK);
				mEditor.commit();
				ThemeManager.setDefaultTheme(ThemeManager.DARK);
				ThemeManager.restart(this, false);
			}
			return true;
		case R.id.skin_mixed:
			if(ThemeManager.getDefaultTheme() != ThemeManager.MIXED){
				mEditor.putInt("theme", ThemeManager.MIXED);
				mEditor.commit();
				ThemeManager.setDefaultTheme(ThemeManager.MIXED);
				ThemeManager.restart(this, false);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		StatService.onPause(this);
	}


}
