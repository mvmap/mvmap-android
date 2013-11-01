package com.mvmap.news.android.activity;

import java.util.List;

import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.AddonSlider.AddonSliderA;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Activity.Addons;
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
import com.mvmap.news.R;
import com.mvmap.news.android.common.DClickExit;
import com.mvmap.news.android.fragment.MainFragment;
import com.mvmap.news.android.model.Category;
import com.mvmap.news.android.request.MvmapNewsManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

@Addons(Activity.ADDON_SLIDER)
public class MainActivity extends Activity{

	private final String TAG = getClass().getSimpleName();


	private SliderMenu 					mSliderMenu;
	private	DClickExit					mDbclickExit;

	public AddonSliderA addonSlider() {
		return addon(AddonSlider.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UmengUpdateAgent.update(this);
		getSupportActionBar().setTitle(R.string.app_name);
		mSliderMenu = addonSlider().obtainDefaultSliderMenu(R.menu.main_left_menu);

		MvmapNewsManager.getInstance().getNewsCategory(createMyReqSuccessListener(), 
				createMyReqErrorListener());

	}


	private Listener<List<Category>> createMyReqSuccessListener(){

		return new Listener<List<Category>>(){
			@Override
			public void onResponse(List<Category> list) {
				for(Category category : list){
					Bundle fragmentArguments = new Bundle();
					fragmentArguments.putString("name", category.getCategoryName());
					fragmentArguments.putInt("id", category.getCategoryId());

					mSliderMenu.add(category.getCategoryName(), MainFragment.class, 
							fragmentArguments, SliderMenu.BLUE);
				}
				mSliderMenu.setCurrentPage(0);
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
		switch (item.getItemId()) {
		case R.id.skin_light:
			if(ThemeManager.getDefaultTheme() != ThemeManager.LIGHT){
				ThemeManager.setDefaultTheme(ThemeManager.LIGHT);
				ThemeManager.restart(this, false);
			}
			return true;

		case R.id.skin_dark:
			if(ThemeManager.getDefaultTheme() != ThemeManager.DARK){
				ThemeManager.setDefaultTheme(ThemeManager.DARK);
				ThemeManager.restart(this, false);
			}
			return true;
		case R.id.skin_mixed:
			if(ThemeManager.getDefaultTheme() != ThemeManager.MIXED){
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
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}


}
