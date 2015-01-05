package com.mvmap.news.android.fragment;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mvmap.news.R;
import com.mvmap.news.android.activity.DetailActivity;
import com.mvmap.news.android.adapter.NewsListAdapter;
import com.mvmap.news.android.model.Tweet;
import com.mvmap.news.android.request.MvmapNewsManager;

public class MainFragment extends Fragment implements OnRefreshListener2<ListView>, OnItemClickListener{

	private final String TAG = getClass().getSimpleName();

	private int						catId;
	private String					title;
	private	int						start;
	private PullToRefreshListView 	mListView;
	private NewsListAdapter			mAdapter;
	private	ProgressBar				mProgressBar;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		title = args.getString("name");
		catId = args.getInt("id");
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(mListView == null){
			mListView = (PullToRefreshListView)getView().findViewById(R.id.pull_to_refresh_listview);
			mListView.setOnRefreshListener(this);
			mListView.setOnItemClickListener(this);
			mProgressBar = (ProgressBar)getView().findViewById(R.id.main_loading);
			mProgressBar.setVisibility(View.VISIBLE);
			MvmapNewsManager.getInstance().getNewsList(createMyReqSuccessListener(), 
					createMyReqErrorListener(), catId, start);
		}
		
	}
	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setSubtitle(title);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		start = 0;
		if(mAdapter != null){
			mAdapter.clear();		
		}
		MvmapNewsManager.getInstance().getNewsList(createMyReqSuccessListener(), 
				createMyReqErrorListener(), catId, 0);

	}


	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		MvmapNewsManager.getInstance().getNewsList(createMyReqSuccessListener(), 
				createMyReqErrorListener(), catId, start);
	}

	private Listener<List<Tweet>> createMyReqSuccessListener(){

		return new Listener<List<Tweet>>(){
			@Override
			public void onResponse(List<Tweet> list) {
				if(mAdapter == null){
					mAdapter = new NewsListAdapter(getActivity(), list);
					mListView.setAdapter(mAdapter);	
				}else{
					mAdapter.addList(list);
					mAdapter.notifyDataSetChanged();	
				}
				mListView.onRefreshComplete();
				mProgressBar.setVisibility(View.GONE);
				start+=10;
				mListView.setMode(Mode.BOTH);
			}
		};
	}

	private Response.ErrorListener createMyReqErrorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "data failed to load: "+error.getMessage());
				mListView.onRefreshComplete();
				mProgressBar.setVisibility(View.GONE);
				mListView.setMode(Mode.DISABLED);
			}
		};
	}


	@Override
	public void onItemClick(android.widget.AdapterView<?> arg0, View arg1,
			int position, long arg3) {
		Tweet tweet = (Tweet)arg0.getAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), DetailActivity.class);
		ArrayList<String> newsIdList = new ArrayList<String>();
		newsIdList.add(String.valueOf(tweet.getTweetId()));
		for (String idstr :  tweet.getRelatedIds().split(",")){
			newsIdList.add(idstr);
		}
		intent.putStringArrayListExtra("ids", newsIdList);
		startActivity(intent);
	}

}
