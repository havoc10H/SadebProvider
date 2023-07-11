package com.my.sadebprovider.act.model.service.servicedetail;

import com.google.gson.annotations.SerializedName;

public class ServiceUserItem{

	@SerializedName("image")
	private String image;

	@SerializedName("gender")
	private String gender;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("booking_status")
	private String bookingStatus;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

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

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setBookingStatus(String bookingStatus){
		this.bookingStatus = bookingStatus;
	}

	public String getBookingStatus(){
		return bookingStatus;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"ServiceUserItem{" + 
			"image = '" + image + '\'' + 
			",gender = '" + gender + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",user_id = '" + userId + '\'' + 
			",user_name = '" + userName + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",booking_status = '" + bookingStatus + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}