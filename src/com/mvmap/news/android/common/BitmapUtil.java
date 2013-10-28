package com.mvmap.news.android.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.text.TextUtils;
import android.util.Log;

public class BitmapUtil {


	private static byte[] getBytesFromInputStream(InputStream is){
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
		try{
			int ch;  
			while ((ch = is.read()) != -1) {  
				bytestream.write(ch);  
			}  
			byte imgdata[] = bytestream.toByteArray();
			bytestream.close();
			return imgdata;  	
		}catch (IOException e) {
			return null;
		}
	}


	public static byte[] getBytesFromUrl(String url){
		if(TextUtils.isEmpty(url)){
			return null;
		}
		final HttpClient client = AndroidHttpClient.newInstance("MVMAP_IMGLOADER");
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				return getBytesFromInputStream(entity.getContent());
			}
		}catch (OutOfMemoryError error) {
			getRequest.abort();
		} catch (IOException e) {
			getRequest.abort();
		} catch (IllegalStateException e) {
			getRequest.abort();
		} catch (Exception e) {
			getRequest.abort();
		} finally {
			if ((client instanceof AndroidHttpClient)) {
				((AndroidHttpClient) client).close();
			}
		}
		return null;	
	}


}
