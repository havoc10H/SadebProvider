package com.my.sadebprovider.act.model.notification;

import com.google.gson.annotations.SerializedName;

public class ResultItem{

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("provider_id")
	private String providerId;



	@SerializedName("user_image")
	private String user_image;

	@Override
	public String toString() {
		return "ResultItem{" +
				"dateTime='" + dateTime + '\'' +
				", userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", providerId='" + providerId + '\'' +
				", user_image='" + user_image + '\'' +
				", comment='" + comment + '\'' +
				", id='" + id + '\'' +
				", providerName='" + providerName + '\'' +
				", requestId='" + requestId + '\'' +
				'}';
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private String id;

	@SerializedName("provider_name")
	private String providerName;

	@SerializedName("request_id")
	private String requestId;

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setProviderId(String providerId){
		this.providerId = providerId;
	}

	public String getProviderId(){
		return providerId;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setProviderName(String providerName){
		this.providerName = providerName;
	}

	public String getProviderName(){
		return providerName;
	}

	public void setRequestId(String requestId){
		this.requestId = requestId;
	}

	public String getRequestId(){
		return requestId;
	}

}