package com.mvmap.news.android.activity;

import org.holoeverywhere.HoloEverywhere;
import org.holoeverywhere.HoloEverywhere.PreferenceImpl;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.app.Application;

import android.graphics.Bitmap.CompressFormat;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;

import com.mvmap.news.android.R;
import com.mvmap.news.android.images.ImageCacheManager;
import com.mvmap.news.android.images.ImageCacheManager.CacheType;
import com.mvmap.news.android.request.RequestManager;

public class MvmapApplication extends Application{
	
    private static final String PACKAGE = MvmapApplication.class.getPackage().getName();

    static {
        HoloEverywhere.DEBUG = true;
        HoloEverywhere.PREFERENCE_IMPL = PreferenceImpl.JSON;

        LayoutInflater.registerPackage(PACKAGE + ".widget");

        ThemeManager.setDefaultTheme(ThemeManager.LIGHT);

        // Android 2.* incorrect process FULLSCREEN flag when we are modify
        // DecorView of Window. This hack using HoloEverywhere Slider
        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
            ThemeManager.modify(ThemeManager.FULLSCREEN);
        }

        ThemeManager.map(ThemeManager.DARK,
                R.style.Holo_Demo_Theme);
        ThemeManager.map(ThemeManager.LIGHT,
                R.style.Holo_Demo_Theme_Light);
        ThemeManager.map(ThemeManager.MIXED,
                R.style.Holo_Demo_Theme_Light_DarkActionBar);

        ThemeManager.map(ThemeManager.DARK | ThemeManager.FULLSCREEN,
                R.style.Holo_Demo_Theme_Fullscreen);
        ThemeManager.map(ThemeManager.LIGHT | ThemeManager.FULLSCREEN,
                R.style.Holo_Demo_Theme_Light_Fullscreen);
        ThemeManager.map(ThemeManager.MIXED | ThemeManager.FULLSCREEN,
                R.style.Holo_Demo_Theme_Light_DarkActionBar_Fullscreen);
        
        
        
    }
    
    
    private static int DISK_IMAGECACHE_SIZE = 1024*1024*10;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.JPEG;
	
	//PNG is lossless so quality is ignored but must be provided
	private static int DISK_IMAGECACHE_QUALITY = 80;  
	
	@Override
	public void onCreate() {
		super.onCreate();

		_init();
	}

	/**
	 * Intialize the request manager and the image cache 
	 */
	private void _init() {
		RequestManager.init(this);
		createImageCache();
		Log.e("asdasdasd", this.getPackageCodePath());
	}
	
	/**
	 * Create the image cache. Uses Memory Cache by default. Change to Disk for a Disk based LRU implementation.  
	 */
	private void createImageCache(){
		ImageCacheManager.getInstance().init(this,
				"bitmap"
				, DISK_IMAGECACHE_SIZE
				, DISK_IMAGECACHE_COMPRESS_FORMAT
				, DISK_IMAGECACHE_QUALITY
				, CacheType.DISK);
	}


}
