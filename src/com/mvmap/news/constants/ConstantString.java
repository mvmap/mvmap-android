package com.mvmap.news.constants;

import java.io.File;
import java.util.List;

import android.R.integer;

import com.mvmap.news.model.NewsItem;
import com.mvmap.news.utils.Utils;



public class ConstantString {
	public static final String APP_NAME = "美丽诊所";
	public static final String EXIT_APP = "exit.application.meili";
	public static String APP_KEY_SINA = "";// 新浪客户的appkey
	public static String APP_SECRET_SINA = "";// 新浪客户的app_secret
	public static String APP_KEY_WEIXIN = "";// 微信的appkey
	public static String APP_SECRET_WEIXIN = "";// 微信的的app_secret

	public static String EXTERNAL_DIR = Utils.getExternalStoragePath()
			+ File.separator + APP_NAME;
	public static String CACHE_DIR = EXTERNAL_DIR + File.separator + "cache";
	public static String FILES_DIR = EXTERNAL_DIR + File.separator + "files";
	public static String DATA_DIR = EXTERNAL_DIR + File.separator + "data";
	public static String LOG_DIR = EXTERNAL_DIR + File.separator + "log";
	
	/*
	private List<NewsItem> networkList;		// 互联网
	private List<NewsItem> designList;		// 设计
	private List<NewsItem> communityList;	// 社区
	private List<NewsItem> foreList;		// 前端
	private List<NewsItem> pythonList;		// Python
	private List<NewsItem> railsList;		// Rails
	private List<NewsItem> mobileList;		// Mobile
	private List<NewsItem> headList;		// 站长
	private List<NewsItem> dbaList;			// DBA
	private List<NewsItem> workList;		// 创业
	*/
	public static final int CATEGORY_ID_NETWORK = 1;
	public static final int CATEGORY_ID_DESIGN = 2;
	public static final int CATEGORY_ID_COMMUNITY = 3;
	public static final int CATEGORY_ID_FORE = 4;
	public static final int CATEGORY_ID_PYTHON = 5;
	public static final int CATEGORY_ID_RAILS = 6;
	public static final int CATEGORY_ID_MOBILE = 7;
	public static final int CATEGORY_ID_HEAD = 8;
	public static final int CATEGORY_ID_DBA = 9;
	public static final int CATEGORY_ID_WORK = 10;

}
