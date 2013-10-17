package com.mvmap.news.android.model;

import com.google.gson.annotations.SerializedName;

public class Category {


	@SerializedName("id")
	private int  			categoryId;

	@SerializedName("name")
	private String			categoryName;


	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
