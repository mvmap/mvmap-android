package com.mvmap.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mvmap.loader.AsyncImageLoader;
import com.mvmap.model.NewsItem;
import com.mvmap.news.R;

public class NewsListAdapter extends BaseAdapter {
	private ArrayList<NewsItem> data;
	private Context context;
	private LayoutInflater inflater;
	private PullToRefreshListView mListView;
//	private AsyncImageLoader asyncImageLoader;
	
	private FinalBitmap fb;
	
	public NewsListAdapter(Context context, ArrayList<NewsItem> data, PullToRefreshListView titleListView) {
		this.context = context;
		this.data = data;
		this.mListView = titleListView;
		inflater = LayoutInflater.from(context);
//		asyncImageLoader = new AsyncImageLoader();
//		mListView.setOnScrollListener(onScrollListener);
		fb = FinalBitmap.create(context);
//		loadImage();
	}
	
	public void clear(){
		if(data != null){
			data.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	static class ViewHolder {
		ImageView imageView;
		TextView titleTextView;
		TextView feedTextView;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		 ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.txt_title);
			holder.feedTextView = (TextView) convertView.findViewById(R.id.txt_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
//		convertView.setTag(position);
		holder.imageView = (ImageView) convertView.findViewById(R.id.img);
		holder.imageView.setImageDrawable(null);
		System.out.println("url : " + data.get(position).img);
		
		holder.titleTextView = (TextView) convertView.findViewById(R.id.txt_title);
		holder.titleTextView.setText(data.get(position).title);
		holder.feedTextView = (TextView) convertView.findViewById(R.id.txt_time);
		holder.feedTextView.setText(data.get(position).feed_name);
		
		fb.display(holder.imageView, data.get(position).img);
		//asyncImageLoader.loadImage(position, data.get(position).img, imageLoadListener);
		return convertView;
	}

	AsyncImageLoader.OnImageLoadListener imageLoadListener = new AsyncImageLoader.OnImageLoadListener(){  
		  
	    @Override  
	    public void onImageLoad(Integer t, Drawable drawable) {  
	        //BookModel model = (BookModel) getItem(t);  
	    	System.out.println("加载图片成功");
	        View view = mListView.findViewWithTag(t);  
	        if(view != null){  
	            ImageView iv = (ImageView) view.findViewById(R.id.img);  
//	            iv.setImageDrawable(drawable);
//	            iv.setBackground(drawable);
	            iv.setBackgroundDrawable(drawable);
//	            iv.setBackgroundDrawable(drawable);  
	        }  
	    }  
	    @Override  
	    public void onError(Integer t) {  
	        NewsItem model = (NewsItem) getItem(t);  
	        View view = mListView.findViewWithTag(model);  
	        if(view != null){  
	        	System.out.println("加载图片出错");
//	            ImageView iv = (ImageView) view.findViewById(R.id.sItemIcon);  
//	            iv.setBackgroundResource(R.drawable.rc_item_bg);  
	        }  
	    }  
	      
	};  
	
//	public void loadImage(){  
//	    int start = mListView.getFirstVisiblePosition();  
//	    int end = mListView.getLastVisiblePosition();  
//	    if(end >= getCount()){  
//	        end = getCount() -1;  
//	    }  
//	    asyncImageLoader.setLoadLimit(0, data.size());  
//	    asyncImageLoader.unlock();  
//	} 
	/*
	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {  
        
        @Override  
        public void onScrollStateChanged(AbsListView view, int scrollState) {  
            switch (scrollState) {  
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:  
                    asyncImageLoader.lock();  
                    break;  
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:  
                    loadImage();  
                    break;  
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:  
                    asyncImageLoader.lock();  
                    break;  
      
                default:  
                    break;  
            }  
              
        }  
          
        @Override  
        public void onScroll(AbsListView view, int firstVisibleItem,  
                int visibleItemCount, int totalItemCount) {  
            // TODO Auto-generated method stub  
              
        }  
    }; 
    */
}
