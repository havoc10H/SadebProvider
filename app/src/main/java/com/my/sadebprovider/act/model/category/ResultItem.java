package com.my.sadebprovider.act.model.category;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("image")
	private String image;

	@SerializedName("category_name")
	private String categoryName;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("id")
	private String id;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

//	@Override
// 	public String toString(){
//		return
//			"ResultItem{" +
//			"image = '" + image + '\'' +
//			",category_name = '" + categoryName + '\'' +
//			",date_time = '" + dateTime + '\'' +
//			",id = '" + id + '\'' +
//			"}";
//		}
}