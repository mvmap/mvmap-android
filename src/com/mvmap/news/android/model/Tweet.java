package com.mvmap.news.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Tweet implements Parcelable{

	/*
	 * "view_count": 0,
      "img": "http://www.mvmap.com/static/mini/2f2a/6ab3/2f2a6ab3771dd84c375973aa284c0c12.jpg",
      "title": "微软研究替代Cookie的追踪技术：将能够横跨多种设备",
      "cat_id": 1,
      "feed_name": "WPDang",
      "dateline": "9 小时前",
      "id": 20161
	 */

	@SerializedName("id")
	private 	int			tweetId;
	@SerializedName("dateline")
	private		String		dateLine;
	@SerializedName("feed_name")
	private		String 		feedName;
	@SerializedName("cat_id")
	private		int			categoryId;
	@SerializedName("title")
	private		String		title;
	@SerializedName("img")
	private		String		img;
	@SerializedName("view_count")
	private		int			viewCount;
	@SerializedName("ids")
	private		String			relatedIds;
	
	
	
	public int getTweetId() {
		return tweetId;
	}
	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}
	public String getDateLine() {
		return dateLine;
	}
	public void setDateLine(String dateLine) {
		this.dateLine = dateLine;
	}
	public String getFeedName() {
		return feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getRelatedIds() {
		return relatedIds;
	}
	public void setRelatedIds(String relatedIds) {
		this.relatedIds = relatedIds;
	}
	
	private Tweet(Parcel parcel){
		tweetId = parcel.readInt();
		categoryId = parcel.readInt();
		viewCount = parcel.readInt();
		dateLine = parcel.readString();
		feedName = parcel.readString();
		title = parcel.readString();
		img = parcel.readString();
		relatedIds = parcel.readString();
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel des, int arg1) {
		des.writeInt(tweetId);
		des.writeInt(categoryId);
		des.writeInt(viewCount);
		des.writeString(dateLine);
		des.writeString(feedName);
		des.writeString(title);
		des.writeString(img);
		des.writeString(relatedIds);
	}
	
	public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
		
		public Tweet createFromParcel(Parcel parcel) {
			return new Tweet(parcel);
		};
		
		public Tweet[] newArray(int size) {
			return new Tweet[size];
		};
	};
	
}
