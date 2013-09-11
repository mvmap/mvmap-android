package com.mvmap.model;

public class NewsItem {
	public int cat_id;
	public int id;
	public String img;
	public String title;
	public String content;
	public String feed_name;
	
	public String getString() {
		return "[ title : " + title + "] [ id : " + id + " ]";
	}
}
