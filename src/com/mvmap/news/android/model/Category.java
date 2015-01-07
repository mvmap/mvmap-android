package com.mvmap.news.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable{

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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private Category(Parcel parcel){
		categoryId = parcel.readInt();
		categoryName = parcel.readString();
	}

	@Override
	public void writeToParcel(Parcel des, int arg1) {
		des.writeInt(categoryId);
		des.writeString(categoryName);
	}

	public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() { 
		public Category createFromParcel(Parcel parcel) {
			return new Category(parcel);	
		};
		
		public Category[] newArray(int size) {
			
			return new Category[size];
			
		};
	};



}
