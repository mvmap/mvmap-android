package com.mvmap.news.android.request;

import java.util.List;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.mvmap.news.android.model.Category;
import com.mvmap.news.android.model.News;
import com.mvmap.news.android.model.Tweet;


public class MvmapNewsManager {

	private final String TAG = getClass().getSimpleName();


	private static MvmapNewsManager mInstance;


	private MvmapNewsManager(){

	}


	public static MvmapNewsManager getInstance(){
		if(mInstance == null) {
			mInstance = new MvmapNewsManager();
		}

		return mInstance;
	}

	/**
	 * 获取 新闻类别
	 * @param listener
	 * @param errorListener
	 */
	public void getNewsCategory(Listener<List<Category>> listener, ErrorListener errorListener){
		String url = Uri.parse(HTTPREQ.NEWS_CATEGORY_REST.base).buildUpon().build().toString();
		GsonRequest<List<Category>> request = new GsonRequest<List<Category>>(
				HTTPREQ.NEWS_CATEGORY_REST.reqtype
				, url
				, new TypeToken<List<Category>>(){}.getType()
				, listener
				, errorListener);
		Log.e(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}


	/**
	 * 获取新闻列表
	 * @param listener
	 * @param errorListener
	 * @param catId
	 * @param start
	 */
	public void getNewsList(Listener<List<Tweet>> listener, ErrorListener errorListener, 
			int catId, int start){

		Uri.Builder uriBuilder = Uri.parse(HTTPREQ.NEWS_LIST_REST.base).buildUpon()
				.appendQueryParameter("lang", "zh")
				.appendQueryParameter("start", String.valueOf(start))
				.appendQueryParameter("cat_id", String.valueOf(catId));

		String url = uriBuilder.build().toString();
		GsonRequest<List<Tweet>> request = new GsonRequest<List<Tweet>>(
				HTTPREQ.NEWS_LIST_REST.reqtype
				, url
				, new TypeToken<List<Tweet>>(){}.getType()
				, listener
				, errorListener);
		request.setShouldCache(false);
		Log.e(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}


	public void getNewsItem(Listener<News> listener, ErrorListener errorListener, String newsId){
		Uri.Builder uriBuilder = Uri.parse(HTTPREQ.NEWS_ITEM_REST.base).buildUpon();
		String url = String.format(uriBuilder.build().toString(), newsId);

		GsonRequest<News> request = new GsonRequest<News>(
				HTTPREQ.NEWS_ITEM_REST.reqtype
				, url
				, new TypeToken<News>(){}.getType()
				, listener
				, errorListener);
		Log.e(TAG, request.toString());
		RequestManager.getRequestQueue().add(request);
	}




}
