package com.mvmap.adapter;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mvmap.R;
import com.mvmap.loader.AsyncImageLoader;
import com.mvmap.model.NewsItem;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsListAdapter extends BaseAdapter {
	private ArrayList<NewsItem> data;
	private Context context;
	private LayoutInflater inflater;
	private PullToRefreshListView mListView;
	private AsyncImageLoader asyncImageLoader;
	
	public NewsListAdapter(Context context, ArrayList<NewsItem> data, PullToRefreshListView titleListView) {
		this.context = context;
		this.data = data;
		this.mListView = titleListView;
		inflater = LayoutInflater.from(context);
		asyncImageLoader = new AsyncImageLoader();
		mListView.setOnScrollListener(onScrollListener);
		loadImage();
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

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
		}
		convertView.setTag(position);
		ImageView imageView = (ImageView) convertView.findViewById(R.id.img);
		imageView.setImageDrawable(null);
		System.out.println("url : " + data.get(position).img);
//		AsyncImageLoader.setImageViewFromUrl(data.get(position).img, imageView);
		
		TextView titleTextView = (TextView) convertView.findViewById(R.id.txt_title);
		titleTextView.setText(data.get(position).title);
		TextView feedTextView = (TextView) convertView.findViewById(R.id.txt_time);
		feedTextView.setText(data.get(position).feed_name);
		
		asyncImageLoader.loadImage(position, data.get(position).img, imageLoadListener);
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
	
	public void loadImage(){  
//	    int start = mListView.getFirstVisiblePosition();  
//	    int end = mListView.getLastVisiblePosition();  
//	    if(end >= getCount()){  
//	        end = getCount() -1;  
//	    }  
	    asyncImageLoader.setLoadLimit(0, data.size());  
	    asyncImageLoader.unlock();  
	} 
	
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
}
