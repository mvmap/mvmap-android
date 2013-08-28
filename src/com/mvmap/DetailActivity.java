package com.mvmap;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailActivity extends Activity implements OnClickListener {
	
	public static int MSG_UPDATE_CONTENT = 1;
	final String mimeType = "text/html";  
    final String encoding = "utf-8"; 
    private TextView mTitleTextView;
    private Button mButtonBack;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    
    private String contentString;
    private String titleString;
    
    private View mRootViewLayout;
    
    private Handler mHandler = new Handler() {
    	public void handleMessage(android.os.Message msg) {
    		switch (msg.what) {
			case 1:
				mTitleTextView.setText(titleString);
				try {  
					mWebView.loadDataWithBaseURL(null, contentString, mimeType, encoding, null);
		        } catch (Exception ex) {  
		            ex.printStackTrace();  
		        }  
				
				mRootViewLayout.invalidate();
				mProgressBar.setVisibility(View.INVISIBLE);
				break;

			default:
				break;
			}
    	};
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		mRootViewLayout = findViewById(R.id.root);
		
		initViews();
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", -1);
		startGetDetail(id);
	}
	
	protected void initViews() {
		mButtonBack = (Button) findViewById(R.id.btn_back);
		mButtonBack.setOnClickListener(this);
		
		mTitleTextView = (TextView) findViewById(R.id.txt_title);
		mProgressBar = (ProgressBar) findViewById(R.id.pb);
		
		mWebView = (WebView) findViewById(R.id.web_view);
		// 固定图片的大小,使适应webview的宽度
		WebSettings webSettings= mWebView.getSettings(); // webView: 类WebView的实例 
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
	}
	
	private void startGetDetail(int id) {
		mProgressBar.setVisibility(View.VISIBLE);
		
		FinalHttp http = new FinalHttp();
		System.out.println("site:" + "http://api.mvmap.com/item/" + id);
        // 取分类
        http.get("http://api.mvmap.com/item/" + id, new AjaxCallBack<String> () {
        	@Override
        	public void onStart() {
        		System.out.println("====> start ");
        	}
        	
        	@Override
        	public void onSuccess(String t) {
        		Gson g = new Gson();
        		ArrayList<Map<String, String>> newsArray = g.fromJson(t, new TypeToken<ArrayList<Map<String, String>>>() {}.getType());
        		System.err.println("title : " + newsArray.get(0).get("title"));
        		contentString = newsArray.get(0).get("content");
        		titleString = newsArray.get(0).get("title");
        		mHandler.sendEmptyMessage(1);
        		
        	}
        	
        	@Override
        	public void onFailure(Throwable t, int errorNo, String strMsg) {
        		System.out.println("failure");
        		mProgressBar.setVisibility(View.INVISIBLE);
        	}
        });
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;

		default:
			break;
		}
	}
}
