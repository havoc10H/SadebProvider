package com.my.sadebprovider.act.model.booking;

import com.google.gson.annotations.SerializedName;
import com.my.sadebprovider.act.model.service.servicedetail.ServiceDetail;
import com.my.sadebprovider.act.model.service.servicedetail.ServiceDetailResponse;

public class ResultItem{

	@SerializedName("date")
	private String date;

	@SerializedName("service_name")
	private String serviceName;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("end_time")
	private String endTime;

	@SerializedName("lon")
	private String lon;

	@SerializedName("users_details")
	private UsersDetails usersDetails;

	@SerializedName("service_details")
	private ServiceDetail service_details;

	public ServiceDetail getService_details() {
		return service_details;
	}

	public void setService_details(ServiceDetail service_details) {
		this.service_details = service_details;
	}

	@Override
	public String toString() {
		return "ResultItem{" +
				"date='" + date + '\'' +
				", serviceName='" + serviceName + '\'' +
				", mobile='" + mobile + '\'' +
				", endTime='" + endTime + '\'' +
				", lon='" + lon + '\'' +
				", usersDetails=" + usersDetails +
				", service_details=" + service_details +
				", ProviderUserResponse=" + ProviderUserResponse +
				", startTime='" + startTime + '\'' +
				", dateTime='" + dateTime + '\'' +
				", userId='" + userId + '\'' +
				", serviceId='" + serviceId + '\'' +
				", providerId='" + providerId + '\'' +
				", id='" + id + '\'' +
				", email='" + email + '\'' +
				", lat='" + lat + '\'' +
				", status='" + status + '\'' +
				'}';
	}

	public com.my.sadebprovider.act.model.booking.ProviderUserResponse getProviderUserResponse() {
		return ProviderUserResponse;
	}

	public void setProviderUserResponse(com.my.sadebprovider.act.model.booking.ProviderUserResponse providerUserResponse) {
		ProviderUserResponse = providerUserResponse;
	}

	@SerializedName("provider_user")
	private ProviderUserResponse ProviderUserResponse;

	@SerializedName("start_time")
	private String startTime;

	@SerializedName("date_time")
	private String dateTime;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("service_id")
	private String serviceId;

	@SerializedName("provider_id")
	private String providerId;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("lat")
	private String lat;

	@SerializedName("status")
	private String status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}

	public String getServiceName(){
		return serviceName;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setEndTime(String endTime){
		this.endTime = endTime;
	}

	public String getEndTime(){
		return endTime;
	}

	public void setLon(String lon){
		this.lon = lon;
	}

	public String getLon(){
		return lon;
	}

	public void setUsersDetails(UsersDetails usersDetails){
		this.usersDetails = usersDetails;
	}

	public UsersDetails getUsersDetails(){
		return usersDetails;
	}

	public void setStartTime(String startTime){
		this.startTime = startTime;
	}

	public String getStartTime(){
		return startTime;
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

	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}

	public String getServiceId(){
		return serviceId;
	}

	public void setProviderId(String providerId){
		this.providerId = providerId;
	}

	public String getProviderId(){
		return providerId;
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

}