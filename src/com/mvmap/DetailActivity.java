package com.mvmap;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	
	final String mimeType = "text/html";  
    final String encoding = "utf-8"; 
    private TextView mTitleTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		String content = intent.getStringExtra("content");
		
		mTitleTextView = (TextView) findViewById(R.id.txt_title);
		mTitleTextView.setText(title);
		
		//过滤掉 img标签的width,height属性  
		content = content.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+","$1");  
		content = content.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+","$1");  
		
		WebView webView = (WebView) findViewById(R.id.web_view);
		// 固定图片的大小,使适应webview的宽度
		WebSettings webSettings= webView.getSettings(); // webView: 类WebView的实例 
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  //就是这句
		try {  
             webView.loadDataWithBaseURL(null, content, mimeType, encoding, null);
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
	}
}
