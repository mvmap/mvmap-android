package com.mvmap.news.android.model;

import com.google.gson.annotations.SerializedName;

public class News {


	private 	String			content;
	@SerializedName("view_count")
	private		int				viewCount;
	@SerializedName("fr")
	private		String			origin;
	private		String			dateline;
	private		String			img;
	private		String			title;
	@SerializedName("cat_id")
	private		int				catId;
	@SerializedName("feed_name")
	private		String			feedName;



	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public String getFeedName() {
		return feedName;
	}
	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

}
