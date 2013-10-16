package com.mvmap.news.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.mvmap.news.R;
import com.mvmap.news.activity.DetailActivity;
import com.mvmap.news.activity.MainActivity;
import com.mvmap.news.adapter.NewsListAdapter;
import com.mvmap.news.model.NewsCategory;
import com.mvmap.news.model.NewsItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/*
 * 一个Fragment只会创建一次，当Fragment不可见时，Fragment的view会销毁，再次显示时，view会重新创建（即重新调用onCreateView方法）
 */
public class NewsListFragment extends Fragment implements OnItemClickListener {
	
	private PullToRefreshListView titleListView;
	private NewsListAdapter titleAdapter;
	public NewsCategory cat;
	private List<NewsItem> titleData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 放在onCreate中只会执行一次
		getNewsListWithCategory(cat.id);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView cat : " + cat.id);
		View view = null;
		view = inflater.inflate(R.layout.fragment_news_list, null);
		
		titleListView = (PullToRefreshListView) view.findViewById(R.id.pull_to_refresh_listview);
		if (titleAdapter != null) {
			titleAdapter.updateListView(titleListView);
			titleListView.setAdapter(titleAdapter);
		}
		
		titleListView.setOnItemClickListener(this);
	    titleListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
	        @Override
	        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
	            getNewsListWithCategory(cat.id);
	        }
	        
	        
	    });
	    
	       
//	       

		return view;
	}
	
	// 数据加载完后，更新listView
	public void setData(List<NewsItem> data) {
		titleAdapter.setData(data);
		this.titleData = data;
	}
	
	private void getNewsListWithCategory(final int cat_id) {
		FinalHttp http = new FinalHttp();
	    // 取分类
	    http.get("http://api.mvmap.com/item?cat_id=" + cat_id + "&lang=zh", new AjaxCallBack<String> () {
	    	
	    	@Override
	    	public void onStart() {
	    		System.out.println("====> 开始取分类 ：" + cat_id);
	    	}

			@Override
			public void onSuccess(String t) {
				Gson g = new Gson();
        		titleData = g.fromJson(t, new TypeToken<ArrayList<NewsItem>>() {}.getType());
        		
        		titleAdapter = new NewsListAdapter(getActivity(), titleData, titleListView);
        		if (titleListView != null) {
        			titleListView.setAdapter(titleAdapter);
        			titleListView.onRefreshComplete();
				}
			}

			@Override
			public AjaxCallBack<String> progress(boolean progress, int rate) {
				// TODO Auto-generated method stub
				return super.progress(progress, rate);
			}
	    	
	    	
	    });
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		intent.putExtra("id", titleData.get(position - 1).id);
		getActivity().startActivity(intent);
	}

}
