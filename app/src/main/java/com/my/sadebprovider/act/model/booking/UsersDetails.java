package com.my.sadebprovider.act.model.booking;

import com.google.gson.annotations.SerializedName;

public class UsersDetails{

	@SerializedName("image")
	private String image;

	@SerializedName("address")
	private String address;

	@SerializedName("gender")
	private String gender;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("description")
	private String description;

	@SerializedName("otp")
	private String otp;

	@SerializedName("lon")
	private String lon;

	@SerializedName("type")
	private String type;

	@SerializedName("register_id")
	private String registerId;

	@SerializedName("password")
	private String password;

	@SerializedName("social_id")
	private String socialId;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("lat")
	private String lat;

	@SerializedName("status")
	private String status;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
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

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setOtp(String otp){
		this.otp = otp;
	}

	public String getOtp(){
		return otp;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setRegisterId(String registerId){
		this.registerId = registerId;
	}

	public String getRegisterId(){
		return registerId;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setSocialId(String socialId){
		this.socialId = socialId;
	}

	public String getSocialId(){
		return socialId;
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

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"UsersDetails{" + 
			"image = '" + image + '\'' + 
			",address = '" + address + '\'' + 
			",gender = '" + gender + '\'' + 
			",user_name = '" + userName + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",description = '" + description + '\'' + 
			",otp = '" + otp + '\'' + 
			",lon = '" + lon + '\'' + 
			",type = '" + type + '\'' + 
			",register_id = '" + registerId + '\'' + 
			",password = '" + password + '\'' + 
			",social_id = '" + socialId + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",lat = '" + lat + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}