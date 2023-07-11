package com.my.sadebprovider.act.model.service;

import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("open_time_wednesday")
	private String openTimeWednesday;

	@SerializedName("customization")
	private String customization;

	@SerializedName("close_time_friday")
	private String closeTimeFriday;

	@SerializedName("description")
	private String description;

	@SerializedName("open_time_sunday")
	private String openTimeSunday;

	@SerializedName("weekly_close")
	private String weeklyClose;

	@SerializedName("close_time_sunday")
	private String closeTimeSunday;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("service_offer")
	private String serviceOffer;

	@SerializedName("close_time_saturday")
	private String closeTimeSaturday;

	@SerializedName("id")
	private String id;

	@SerializedName("lat")
	private String lat;
	@SerializedName("lon")
	private String lon;

	@Override
	public String toString() {
		return "Result{" +
				"openTimeWednesday='" + openTimeWednesday + '\'' +
				", customization='" + customization + '\'' +
				", closeTimeFriday='" + closeTimeFriday + '\'' +
				", description='" + description + '\'' +
				", openTimeSunday='" + openTimeSunday + '\'' +
				", weeklyClose='" + weeklyClose + '\'' +
				", closeTimeSunday='" + closeTimeSunday + '\'' +
				", categoryId='" + categoryId + '\'' +
				", dateTime='" + dateTime + '\'' +
				", serviceOffer='" + serviceOffer + '\'' +
				", closeTimeSaturday='" + closeTimeSaturday + '\'' +
				", id='" + id + '\'' +
				", lat='" + lat + '\'' +
				", lon='" + lon + '\'' +
				", openTimeSaturday='" + openTimeSaturday + '\'' +
				", image5='" + image5 + '\'' +
				", closeTimeWednesday='" + closeTimeWednesday + '\'' +
				", serviceTime='" + serviceTime + '\'' +
				", image6='" + image6 + '\'' +
				", closeTimeTuesday='" + closeTimeTuesday + '\'' +
				", image3='" + image3 + '\'' +
				", image4='" + image4 + '\'' +
				", servicePrice='" + servicePrice + '\'' +
				", openTimeTuesday='" + openTimeTuesday + '\'' +
				", serviceName='" + serviceName + '\'' +
				", image7='" + image7 + '\'' +
				", image1='" + image1 + '\'' +
				", closeTimeMonday='" + closeTimeMonday + '\'' +
				", image2='" + image2 + '\'' +
				", closeTimeThursday='" + closeTimeThursday + '\'' +
				", userId='" + userId + '\'' +
				", openTimeThursday='" + openTimeThursday + '\'' +
				", openTimeFriday='" + openTimeFriday + '\'' +
				", openTimeMonday='" + openTimeMonday + '\'' +
				'}';
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	@SerializedName("open_time_saturday")
	private String openTimeSaturday;

	@SerializedName("image5")
	private String image5;

	@SerializedName("close_time_wednesday")
	private String closeTimeWednesday;

	@SerializedName("service_time")
	private String serviceTime;

	@SerializedName("image6")
	private String image6;

	@SerializedName("close_time_tuesday")
	private String closeTimeTuesday;

	@SerializedName("image3")
	private String image3;

	@SerializedName("image4")
	private String image4;

	@SerializedName("service_price")
	private String servicePrice;

	@SerializedName("open_time_tuesday")
	private String openTimeTuesday;

	@SerializedName("service_name")
	private String serviceName;

	@SerializedName("image7")
	private String image7;

	@SerializedName("image1")
	private String image1;

	@SerializedName("close_time_monday")
	private String closeTimeMonday;

	@SerializedName("image2")
	private String image2;

	@SerializedName("close_time_thursday")
	private String closeTimeThursday;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("open_time_thursday")
	private String openTimeThursday;

	@SerializedName("open_time_friday")
	private String openTimeFriday;

	@SerializedName("open_time_monday")
	private String openTimeMonday;

	public void setOpenTimeWednesday(String openTimeWednesday){
		this.openTimeWednesday = openTimeWednesday;
	}

	public String getOpenTimeWednesday(){
		return openTimeWednesday;
	}

	public void setCustomization(String customization){
		this.customization = customization;
	}

	public String getCustomization(){
		return customization;
	}

	public void setCloseTimeFriday(String closeTimeFriday){
		this.closeTimeFriday = closeTimeFriday;
	}

	public String getCloseTimeFriday(){
		return closeTimeFriday;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setOpenTimeSunday(String openTimeSunday){
		this.openTimeSunday = openTimeSunday;
	}

	public String getOpenTimeSunday(){
		return openTimeSunday;
	}

	public void setWeeklyClose(String weeklyClose){
		this.weeklyClose = weeklyClose;
	}

	public String getWeeklyClose(){
		return weeklyClose;
	}

	public void setCloseTimeSunday(String closeTimeSunday){
		this.closeTimeSunday = closeTimeSunday;
	}

	public String getCloseTimeSunday(){
		return closeTimeSunday;
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

	public void setServiceOffer(String serviceOffer){
		this.serviceOffer = serviceOffer;
	}

	public String getServiceOffer(){
		return serviceOffer;
	}

	public void setCloseTimeSaturday(String closeTimeSaturday){
		this.closeTimeSaturday = closeTimeSaturday;
	}

	public String getCloseTimeSaturday(){
		return closeTimeSaturday;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setOpenTimeSaturday(String openTimeSaturday){
		this.openTimeSaturday = openTimeSaturday;
	}

	public String getOpenTimeSaturday(){
		return openTimeSaturday;
	}

	public void setImage5(String image5){
		this.image5 = image5;
	}

	public String getImage5(){
		return image5;
	}

	public void setCloseTimeWednesday(String closeTimeWednesday){
		this.closeTimeWednesday = closeTimeWednesday;
	}

	public String getCloseTimeWednesday(){
		return closeTimeWednesday;
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

	public void setCloseTimeTuesday(String closeTimeTuesday){
		this.closeTimeTuesday = closeTimeTuesday;
	}

	public String getCloseTimeTuesday(){
		return closeTimeTuesday;
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

	public void setServicePrice(String servicePrice){
		this.servicePrice = servicePrice;
	}

	public String getServicePrice(){
		return servicePrice;
	}

	public void setOpenTimeTuesday(String openTimeTuesday){
		this.openTimeTuesday = openTimeTuesday;
	}

	public String getOpenTimeTuesday(){
		return openTimeTuesday;
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

	public void setImage1(String image1){
		this.image1 = image1;
	}

	public String getImage1(){
		return image1;
	}

	public void setCloseTimeMonday(String closeTimeMonday){
		this.closeTimeMonday = closeTimeMonday;
	}

	public String getCloseTimeMonday(){
		return closeTimeMonday;
	}

	public void setImage2(String image2){
		this.image2 = image2;
	}

	public String getImage2(){
		return image2;
	}

	public void setCloseTimeThursday(String closeTimeThursday){
		this.closeTimeThursday = closeTimeThursday;
	}

	public String getCloseTimeThursday(){
		return closeTimeThursday;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setOpenTimeThursday(String openTimeThursday){
		this.openTimeThursday = openTimeThursday;
	}

	public String getOpenTimeThursday(){
		return openTimeThursday;
	}

	public void setOpenTimeFriday(String openTimeFriday){
		this.openTimeFriday = openTimeFriday;
	}

	public String getOpenTimeFriday(){
		return openTimeFriday;
	}

	public void setOpenTimeMonday(String openTimeMonday){
		this.openTimeMonday = openTimeMonday;
	}

	public String getOpenTimeMonday(){
		return openTimeMonday;
	}

}