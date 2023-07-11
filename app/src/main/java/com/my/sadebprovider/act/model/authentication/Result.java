package com.my.sadebprovider.act.model.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("image")
    private String image;

    @SerializedName("address")
    private String address;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("surname")
    private String surname;

    @SerializedName("mobile")
    private String mobile;

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

    @SerializedName("gender")
    private String gender;

    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("lat")
    private String lat;

    @SerializedName("status")
    private String status;

    @SerializedName("business_name")
    private String business_name;

    @SerializedName("business_address")
    private String business_address;

    @SerializedName("business_cell_phone")
    private String business_cell_phone;

    @SerializedName("business_landline")
    private String business_landline;

    @SerializedName("offer_home_delivery")
    private String offer_home_delivery;

    @SerializedName("b_lat")
    private String b_lat;

    @SerializedName("b_lon")
    private String b_lon;

    @SerializedName("open_date")
    private String open_date;

    @SerializedName("close_date")
    private String close_date;

    @SerializedName("business_profile_image")
    private String business_profile_image;

    @SerializedName("image1")
    private String image1;

    @SerializedName("image2")
    private String image2;

    @SerializedName("image3")
    private String image3;

    @SerializedName("image4")
    private String image4;

    @SerializedName("image5")
    private String image5;

    @SerializedName("image6")
    private String image6;

    @SerializedName("image7")
    private String image7;

    @SerializedName("image8")
    private String image8;


    @SerializedName("image9")
    private String image9;


    @SerializedName("description")
    private String description;

    @SerializedName("weekly_close")
    private String weekly_close;

    @SerializedName("open_time_sunday")
    private String open_time_sunday;

    @SerializedName("close_time_sunday")
    private String close_time_sunday;

    @SerializedName("open_time_monday")
    private String open_time_monday;

    @SerializedName("close_time_monday")
    private String close_time_monday;

    @SerializedName("open_time_tuesday")
    private String open_time_tuesday;

    @SerializedName("close_time_tuesday")
    private String close_time_tuesday;

    @SerializedName("open_time_wednesday")
    private String open_time_wednesday;

    @SerializedName("close_time_wednesday")
    private String close_time_wednesday;

    @SerializedName("open_time_thursday")
    private String open_time_thursday;

    @SerializedName("close_time_thursday")
    private String close_time_thursday;

    @SerializedName("open_time_friday")
    private String open_time_friday;

    @SerializedName("close_time_friday")
    private String close_time_friday;

    @SerializedName("open_time_saturday")
    private String open_time_saturday;

    @SerializedName("close_time_saturday")
    private String close_time_saturday;

    @SerializedName("business_type")
    @Expose
    private String businessType;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    @SerializedName("service_image")
    @Expose
    private String serviceImage;
    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getBusinessTypeNameSp() {
        return businessTypeNameSp;
    }

    public void setBusinessTypeNameSp(String businessTypeNameSp) {
        this.businessTypeNameSp = businessTypeNameSp;
    }

    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("business_type_name")
    @Expose
    private String businessTypeName;
    @SerializedName("business_type_name_sp")
    @Expose
    private String businessTypeNameSp;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLon() {
        return lon;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getBusiness_cell_phone() {
        return business_cell_phone;
    }

    public void setBusiness_cell_phone(String business_cell_phone) {
        this.business_cell_phone = business_cell_phone;
    }

    public String getBusiness_landline() {
        return business_landline;
    }

    public void setBusiness_landline(String business_landline) {
        this.business_landline = business_landline;
    }

    public String getOffer_home_delivery() {
        return offer_home_delivery;
    }

    public void setOffer_home_delivery(String offer_home_delivery) {
        this.offer_home_delivery = offer_home_delivery;
    }

    public String getB_lat() {
        return b_lat;
    }

    public void setB_lat(String b_lat) {
        this.b_lat = b_lat;
    }

    public String getB_lon() {
        return b_lon;
    }

    public void setB_lon(String b_lon) {
        this.b_lon = b_lon;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getClose_date() {
        return close_date;
    }

    public void setClose_date(String close_date) {
        this.close_date = close_date;
    }

    public String getBusiness_profile_image() {
        return business_profile_image;
    }

    public void setBusiness_profile_image(String business_profile_image) {
        this.business_profile_image = business_profile_image;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    public String getImage9() {
        return image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeekly_close() {
        return weekly_close;
    }

    public void setWeekly_close(String weekly_close) {
        this.weekly_close = weekly_close;
    }

    public String getOpen_time_sunday() {
        return open_time_sunday;
    }

    public void setOpen_time_sunday(String open_time_sunday) {
        this.open_time_sunday = open_time_sunday;
    }

    public String getClose_time_sunday() {
        return close_time_sunday;
    }

    public void setClose_time_sunday(String close_time_sunday) {
        this.close_time_sunday = close_time_sunday;
    }

    public String getOpen_time_monday() {
        return open_time_monday;
    }

    public void setOpen_time_monday(String open_time_monday) {
        this.open_time_monday = open_time_monday;
    }

    public String getClose_time_monday() {
        return close_time_monday;
    }

    public void setClose_time_monday(String close_time_monday) {
        this.close_time_monday = close_time_monday;
    }

    public String getOpen_time_tuesday() {
        return open_time_tuesday;
    }

    public void setOpen_time_tuesday(String open_time_tuesday) {
        this.open_time_tuesday = open_time_tuesday;
    }

    public String getClose_time_tuesday() {
        return close_time_tuesday;
    }

    public void setClose_time_tuesday(String close_time_tuesday) {
        this.close_time_tuesday = close_time_tuesday;
    }
    public String getOpen_time_wednesday() {
        return open_time_wednesday;
    }

    public void setOpen_time_wednesday(String open_time_wednesday) {
        this.open_time_wednesday = open_time_wednesday;
    }

    public String getClose_time_wednesday() {
        return close_time_wednesday;
    }

    public void setClose_time_wednesday(String close_time_wednesday) {
        this.close_time_wednesday = close_time_wednesday;
    }

    public String getOpen_time_thursday() {
        return open_time_thursday;
    }

    public void setOpen_time_thursday(String open_time_thursday) {
        this.open_time_thursday = open_time_thursday;
    }

    public String getClose_time_thursday() {
        return close_time_thursday;
    }

    public void setClose_time_thursday(String close_time_thursday) {
        this.close_time_thursday = close_time_thursday;
    }

    public String getOpen_time_friday() {
        return open_time_friday;
    }

    public void setOpen_time_friday(String open_time_friday) {
        this.open_time_friday = open_time_friday;
    }

    public String getClose_time_friday() {
        return close_time_friday;
    }

    public void setClose_time_friday(String close_time_friday) {
        this.close_time_friday = close_time_friday;
    }

    public String getOpen_time_saturday() {
        return open_time_saturday;
    }

    public void setOpen_time_saturday(String open_time_saturday) {
        this.open_time_saturday = open_time_saturday;
    }

    public String getClose_time_saturday() {
        return close_time_saturday;
    }

    public void setClose_time_saturday(String close_time_saturday) {
        this.close_time_saturday = close_time_saturday;
    }

    @Override
    public String toString() {
        return
                "Result{" +
                        "image = '" + image + '\'' +
                        ",address = '" + address + '\'' +
                        ",user_name = '" + userName + '\'' +
                        ",surname = '" + surname + '\'' +
                        ",mobile = '" + mobile + '\'' +
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
                        ",business_name = '" + business_name + '\'' +
                        ",business_address = '" + business_address + '\'' +
                        ",business_cell_phone = '" + business_cell_phone + '\'' +
                        ",business_landline = '" + business_landline + '\'' +
                        ",offer_home_delivery = '" + offer_home_delivery + '\'' +
                        ",b_lat = '" + b_lat + '\'' +
                        ",b_lon = '" + b_lon + '\'' +
                        ",open_date = '" + open_date + '\'' +
                        ",close_date = '" + close_date + '\'' +
                        ",business_profile_image = '" + business_profile_image + '\'' +
                        ",image1 = '" + image1 + '\'' +
                        ",image2 = '" + image2 + '\'' +
                        ",image3 = '" + image3 + '\'' +
                        ",image4 = '" + image4 + '\'' +
                        ",image5 = '" + image5 + '\'' +
                        ",image6 = '" + image6 + '\'' +
                        ",image7 = '" + image7 + '\'' +
                        ",description = '" + description + '\'' +

                        ",weekly_close = '" + weekly_close + '\'' +
                        ",open_time_sunday = '" + open_time_sunday + '\'' +
                        ",close_time_sunday = '" + close_time_sunday + '\'' +
                        ",open_time_monday = '" + open_time_monday + '\'' +
                        ",close_time_monday = '" + close_time_monday + '\'' +
                        ",open_time_tuesday = '" + open_time_tuesday + '\'' +
                        ",close_time_tuesday = '" + close_time_tuesday + '\'' +
                        ",open_time_wednesday = '" + open_time_wednesday + '\'' +
                        ",close_time_wednesday = '" + close_time_wednesday + '\'' +
                        ",open_time_thursday = '" + open_time_thursday + '\'' +
                        ",close_time_thursday = '" + close_time_thursday + '\'' +
                        ",open_time_friday = '" + open_time_friday + '\'' +
                        ",close_time_friday = '" + close_time_friday + '\'' +
                        ",open_time_saturday = '" + open_time_saturday + '\'' +
                        ",close_time_saturday = '" + close_time_saturday + '\'' +

                        "}";
    }
}