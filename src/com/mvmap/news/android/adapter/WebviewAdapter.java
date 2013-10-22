package com.mvmap.news.android.adapter;

import java.util.List;

import org.holoeverywhere.widget.ProgressBar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.mvmap.news.R;
import com.mvmap.news.android.activity.DetailActivity;
import com.mvmap.news.android.model.News;
import com.mvmap.news.android.request.MvmapNewsManager;

public class WebviewAdapter extends BaseAdapter{

	private static final String TAG = WebviewAdapter.class.getSimpleName();

	private	static final String mimeType = "text/html";  
	private	static final String encoding = "utf-8"; 

	private   DetailActivity		mActivity;
	private	  List<Integer>         newsIdList;

	public WebviewAdapter(DetailActivity mActivity, List<Integer> newsIdList) {
		this.mActivity = mActivity;
		this.newsIdList = newsIdList;
	}


	@Override
	public int getCount() {

		return newsIdList.size();

	}

	@Override
	public Object getItem(int position) {
		return newsIdList.get(position);	
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}


	public class ViewHolder {
		WebView  	mWebview;
		ProgressBar	mProgressBar;
		public 		News		mNews;
	}

	private String createContent(News  news){
		StringBuffer sb = new StringBuffer();
		sb.append("<p style=\"font-size:20px;\"><strong>");
		sb.append(news.getTitle());
		sb.append("<strong></p>");
		sb.append("<hr size=\"2\" color=\"#0099cc\" align=\"center noshade\">");
		sb.append(news.getContent());
		return sb.toString();
	}

	private Listener<News> createMyReqSuccessListener(final ViewHolder holder){

		return new Listener<News>(){
			@Override
			public void onResponse(News news) {
				holder.mNews = news;
				holder.mWebview.loadDataWithBaseURL(null, createContent(news), 
						mimeType, encoding, null);
				holder.mProgressBar.setVisibility(View.GONE);
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener(final ViewHolder holder) {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "data failed to load: "+error.getMessage());
				holder.mProgressBar.setVisibility(View.GONE);
			}
		};
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mActivity.getLayoutInflater().inflate(R.layout.detail_item, null);
			holder.mWebview = (WebView)convertView.findViewById(R.id.detail_webview);
			holder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.detail_loading);

			final WebSettings webSettings = holder.mWebview.getSettings();  
			webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			webSettings.setJavaScriptEnabled(false);  
			webSettings.setBuiltInZoomControls(false);
			webSettings.setSupportZoom(false);
			webSettings.setTextSize(TextSize.NORMAL);
			webSettings.setLoadsImagesAutomatically(true);
			webSettings.setBlockNetworkImage(true);
			holder.mWebview.setWebViewClient(new WebViewClient(){
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					webSettings.setBlockNetworkImage(false);
				}
			});
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		MvmapNewsManager.getInstance().getNewsItem(createMyReqSuccessListener(holder), 
				createMyReqErrorListener(holder), newsIdList.get(position));



		return convertView;
	}

}
