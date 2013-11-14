package com.mvmap.news.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.mvmap.news.R;
import com.mvmap.news.android.images.ImageCacheManager;
import com.mvmap.news.android.model.Tweet;

public class NewsListAdapter extends BaseAdapter{

	private 	List<Tweet>		dataList;
	private 	LayoutInflater 	inflater;

	public NewsListAdapter(Context context, List<Tweet> dataList) {
		this.inflater = LayoutInflater.from(context);
		this.dataList = dataList;
	}


	public void addList(List<Tweet> newlist){
		dataList.addAll(newlist);
	}
	public List<Tweet> getAllList(){
		return dataList;
	}

	public void clear(){
		if(dataList != null){
			dataList.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		if(dataList == null){
			return 0;
		}
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	class ViewHolder {
		NetworkImageView imageView;
		TextView titleTextView;
		TextView originTextView;
		TextView timeTextView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.imageView = (NetworkImageView) convertView.findViewById(R.id.img);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.txt_title);
			holder.originTextView = (TextView)convertView.findViewById(R.id.txt_origin);
			holder.timeTextView = (TextView) convertView.findViewById(R.id.txt_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}

		Tweet tweet = dataList.get(position);
		holder.imageView.setImageUrl(tweet.getImg(), 
				ImageCacheManager.getInstance().getImageLoader());
		holder.titleTextView.setText(tweet.getTitle());
		holder.originTextView.setText(tweet.getFeedName());
		holder.timeTextView.setText(tweet.getDateLine());

		return convertView;
	}

}
