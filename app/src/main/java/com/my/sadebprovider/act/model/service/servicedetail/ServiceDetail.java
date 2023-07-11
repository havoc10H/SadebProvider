package com.my.sadebprovider.act.model.service.servicedetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceDetail {

	@SerializedName("image5")
	private String image5;

	@SerializedName("service_time")
	private String serviceTime;

	@SerializedName("image6")
	private String image6;

	@SerializedName("image3")
	private String image3;

	@SerializedName("image4")
	private String image4;

	@SerializedName("service_user")
	private List<ServiceUserItem> serviceUser;


	@SerializedName("service_price")
	private String servicePrice;

	@SerializedName("customization")
	private String customization;

	@SerializedName("service_name")
	private String serviceName;

	@SerializedName("image7")
	private String image7;

	@SerializedName("provider_user_id")
	private String providerUserId;

	@SerializedName("description")
	private String description;

	@SerializedName("lon")
	private String lon;

	@SerializedName("image1")
	private String image1;

	@SerializedName("image2")
	private String image2;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("service_offer")
	private String serviceOffer;

	@SerializedName("estimate_time")
	private String estimateTime;

	@SerializedName("id")
	private String id;

	@SerializedName("lat")
	private String lat;

	public void setImage5(String image5){
		this.image5 = image5;
	}

	public String getImage5(){
		return image5;
	}

	public void setServiceTime(String serviceTime){
		this.serviceTime = serviceTime;
	}

	public String getServiceTime(){
		return serviceTime;
	}

	public void setImage6(String image6){
		this.image6 = image6;
	}

	public String getImage6(){
		return image6;
	}

	public void setImage3(String image3){
		this.image3 = image3;
	}

	public String getImage3(){
		return image3;
	}

	public void setImage4(String image4){
		this.image4 = image4;
	}

	public String getImage4(){
		return image4;
	}

	public void setServiceUser(List<ServiceUserItem> serviceUser){
		this.serviceUser = serviceUser;
	}

	public List<ServiceUserItem> getServiceUser(){
		return serviceUser;
	}

	public void setServicePrice(String servicePrice){
		this.servicePrice = servicePrice;
	}

	public String getServicePrice(){
		return servicePrice;
	}

	public void setCustomization(String customization){
		this.customization = customization;
	}

	public String getCustomization(){
		return customization;
	}

	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}

	public String getServiceName(){
		return serviceName;
	}

	public void setImage7(String image7){
		this.image7 = image7;
	}

	public String getImage7(){
		return image7;
	}

	public void setProviderUserId(String providerUserId){
		this.providerUserId = providerUserId;
	}

	public String getProviderUserId(){
		return providerUserId;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setImage1(String image1){
		this.image1 = image1;
	}

	public String getImage1(){
		return image1;
	}

	public void setImage2(String image2){
		this.image2 = image2;
	}

	public String getImage2(){
		return image2;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
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

	public void setServiceOffer(String serviceOffer){
		this.serviceOffer = serviceOffer;
	}

	public String getServiceOffer(){
		return serviceOffer;
	}

	public void setEstimateTime(String estimateTime){
		this.estimateTime = estimateTime;
	}

	public String getEstimateTime(){
		return estimateTime;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"image5 = '" + image5 + '\'' + 
			",service_time = '" + serviceTime + '\'' + 
			",image6 = '" + image6 + '\'' + 
			",image3 = '" + image3 + '\'' + 
			",image4 = '" + image4 + '\'' + 
			",service_user = '" + serviceUser + '\'' + 
			",service_price = '" + servicePrice + '\'' + 
			",customization = '" + customization + '\'' + 
			",service_name = '" + serviceName + '\'' + 
			",image7 = '" + image7 + '\'' + 
			",provider_user_id = '" + providerUserId + '\'' + 
			",description = '" + description + '\'' + 
			",lon = '" + lon + '\'' + 
			",image1 = '" + image1 + '\'' + 
			",image2 = '" + image2 + '\'' + 
			",category_id = '" + categoryId + '\'' + 
			",date_time = '" + dateTime + '\'' + 
			",user_id = '" + userId + '\'' + 
			",service_offer = '" + serviceOffer + '\'' + 
			",estimate_time = '" + estimateTime + '\'' + 
			",id = '" + id + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}