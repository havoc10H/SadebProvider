package com.my.sadebprovider.act.model.booking;

import com.google.gson.annotations.SerializedName;

public class ProviderUserResponse {

    @SerializedName("image")
    private String image;

    @SerializedName("gender")
    private String gender;

    @SerializedName("date_time")
    private String dateTime;

    @Override
    public String toString() {
        return "ProviderUserResponse{" +
                "image='" + image + '\'' +
                ", gender='" + gender + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", booking_status='" + booking_status + '\'' +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    @SerializedName("user_id")
    private String userId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;


    @SerializedName("booking_status")
    private String booking_status;

}
