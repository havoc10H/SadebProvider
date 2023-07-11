package com.my.sadebprovider.act.model.client;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuccessResGetClient implements Serializable {

    @SerializedName("result")
    @Expose
    public Result result;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public String status;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public class BusinessDetails implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("name_sp")
        @Expose
        public String nameSp;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("date_time")
        @Expose
        public String dateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameSp() {
            return nameSp;
        }

        public void setNameSp(String nameSp) {
            this.nameSp = nameSp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

    }

    public class ProviderDetails implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("surname")
        @Expose
        public String surname;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("register_id")
        @Expose
        public String registerId;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("date_time")
        @Expose
        public String dateTime;
        @SerializedName("social_id")
        @Expose
        public String socialId;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("otp")
        @Expose
        public String otp;
        @SerializedName("lon")
        @Expose
        public String lon;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("verification_front_image")
        @Expose
        public String verificationFrontImage;
        @SerializedName("verification_end_image")
        @Expose
        public String verificationEndImage;
        @SerializedName("weekly_close")
        @Expose
        public String weeklyClose;
        @SerializedName("open_time_sunday")
        @Expose
        public String openTimeSunday;
        @SerializedName("close_time_sunday")
        @Expose
        public String closeTimeSunday;
        @SerializedName("open_time_monday")
        @Expose
        public String openTimeMonday;
        @SerializedName("close_time_monday")
        @Expose
        public String closeTimeMonday;
        @SerializedName("open_time_tuesday")
        @Expose
        public String openTimeTuesday;
        @SerializedName("close_time_tuesday")
        @Expose
        public String closeTimeTuesday;
        @SerializedName("open_time_wednesday")
        @Expose
        public String openTimeWednesday;
        @SerializedName("close_time_wednesday")
        @Expose
        public String closeTimeWednesday;
        @SerializedName("open_time_thursday")
        @Expose
        public String openTimeThursday;
        @SerializedName("close_time_thursday")
        @Expose
        public String closeTimeThursday;
        @SerializedName("open_time_friday")
        @Expose
        public String openTimeFriday;
        @SerializedName("close_time_friday")
        @Expose
        public String closeTimeFriday;
        @SerializedName("open_time_saturday")
        @Expose
        public String openTimeSaturday;
        @SerializedName("close_time_saturday")
        @Expose
        public String closeTimeSaturday;
        @SerializedName("booking_status")
        @Expose
        public String bookingStatus;
        @SerializedName("business_name")
        @Expose
        public String businessName;
        @SerializedName("business_address")
        @Expose
        public String businessAddress;
        @SerializedName("business_cell_phone")
        @Expose
        public String businessCellPhone;
        @SerializedName("business_landline")
        @Expose
        public String businessLandline;
        @SerializedName("offer_home_delivery")
        @Expose
        public String offerHomeDelivery;
        @SerializedName("b_lat")
        @Expose
        public String bLat;
        @SerializedName("b_lon")
        @Expose
        public String bLon;
        @SerializedName("open_date")
        @Expose
        public String openDate;
        @SerializedName("close_date")
        @Expose
        public String closeDate;
        @SerializedName("business_profile_image")
        @Expose
        public String businessProfileImage;
        @SerializedName("image1")
        @Expose
        public String image1;
        @SerializedName("image2")
        @Expose
        public String image2;
        @SerializedName("image3")
        @Expose
        public String image3;
        @SerializedName("image4")
        @Expose
        public String image4;
        @SerializedName("image5")
        @Expose
        public String image5;
        @SerializedName("image6")
        @Expose
        public String image6;
        @SerializedName("image7")
        @Expose
        public String image7;
        @SerializedName("email_code")
        @Expose
        public String emailCode;
        @SerializedName("business_type")
        @Expose
        public String businessType;
        @SerializedName("registration_date")
        @Expose
        public String registrationDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRegisterId() {
            return registerId;
        }

        public void setRegisterId(String registerId) {
            this.registerId = registerId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getSocialId() {
            return socialId;
        }

        public void setSocialId(String socialId) {
            this.socialId = socialId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVerificationFrontImage() {
            return verificationFrontImage;
        }

        public void setVerificationFrontImage(String verificationFrontImage) {
            this.verificationFrontImage = verificationFrontImage;
        }

        public String getVerificationEndImage() {
            return verificationEndImage;
        }

        public void setVerificationEndImage(String verificationEndImage) {
            this.verificationEndImage = verificationEndImage;
        }

        public String getWeeklyClose() {
            return weeklyClose;
        }

        public void setWeeklyClose(String weeklyClose) {
            this.weeklyClose = weeklyClose;
        }

        public String getOpenTimeSunday() {
            return openTimeSunday;
        }

        public void setOpenTimeSunday(String openTimeSunday) {
            this.openTimeSunday = openTimeSunday;
        }

        public String getCloseTimeSunday() {
            return closeTimeSunday;
        }

        public void setCloseTimeSunday(String closeTimeSunday) {
            this.closeTimeSunday = closeTimeSunday;
        }

        public String getOpenTimeMonday() {
            return openTimeMonday;
        }

        public void setOpenTimeMonday(String openTimeMonday) {
            this.openTimeMonday = openTimeMonday;
        }

        public String getCloseTimeMonday() {
            return closeTimeMonday;
        }

        public void setCloseTimeMonday(String closeTimeMonday) {
            this.closeTimeMonday = closeTimeMonday;
        }

        public String getOpenTimeTuesday() {
            return openTimeTuesday;
        }

        public void setOpenTimeTuesday(String openTimeTuesday) {
            this.openTimeTuesday = openTimeTuesday;
        }

        public String getCloseTimeTuesday() {
            return closeTimeTuesday;
        }

        public void setCloseTimeTuesday(String closeTimeTuesday) {
            this.closeTimeTuesday = closeTimeTuesday;
        }

        public String getOpenTimeWednesday() {
            return openTimeWednesday;
        }

        public void setOpenTimeWednesday(String openTimeWednesday) {
            this.openTimeWednesday = openTimeWednesday;
        }

        public String getCloseTimeWednesday() {
            return closeTimeWednesday;
        }

        public void setCloseTimeWednesday(String closeTimeWednesday) {
            this.closeTimeWednesday = closeTimeWednesday;
        }

        public String getOpenTimeThursday() {
            return openTimeThursday;
        }

        public void setOpenTimeThursday(String openTimeThursday) {
            this.openTimeThursday = openTimeThursday;
        }

        public String getCloseTimeThursday() {
            return closeTimeThursday;
        }

        public void setCloseTimeThursday(String closeTimeThursday) {
            this.closeTimeThursday = closeTimeThursday;
        }

        public String getOpenTimeFriday() {
            return openTimeFriday;
        }

        public void setOpenTimeFriday(String openTimeFriday) {
            this.openTimeFriday = openTimeFriday;
        }

        public String getCloseTimeFriday() {
            return closeTimeFriday;
        }

        public void setCloseTimeFriday(String closeTimeFriday) {
            this.closeTimeFriday = closeTimeFriday;
        }

        public String getOpenTimeSaturday() {
            return openTimeSaturday;
        }

        public void setOpenTimeSaturday(String openTimeSaturday) {
            this.openTimeSaturday = openTimeSaturday;
        }

        public String getCloseTimeSaturday() {
            return closeTimeSaturday;
        }

        public void setCloseTimeSaturday(String closeTimeSaturday) {
            this.closeTimeSaturday = closeTimeSaturday;
        }

        public String getBookingStatus() {
            return bookingStatus;
        }

        public void setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getBusinessAddress() {
            return businessAddress;
        }

        public void setBusinessAddress(String businessAddress) {
            this.businessAddress = businessAddress;
        }

        public String getBusinessCellPhone() {
            return businessCellPhone;
        }

        public void setBusinessCellPhone(String businessCellPhone) {
            this.businessCellPhone = businessCellPhone;
        }

        public String getBusinessLandline() {
            return businessLandline;
        }

        public void setBusinessLandline(String businessLandline) {
            this.businessLandline = businessLandline;
        }

        public String getOfferHomeDelivery() {
            return offerHomeDelivery;
        }

        public void setOfferHomeDelivery(String offerHomeDelivery) {
            this.offerHomeDelivery = offerHomeDelivery;
        }

        public String getbLat() {
            return bLat;
        }

        public void setbLat(String bLat) {
            this.bLat = bLat;
        }

        public String getbLon() {
            return bLon;
        }

        public void setbLon(String bLon) {
            this.bLon = bLon;
        }

        public String getOpenDate() {
            return openDate;
        }

        public void setOpenDate(String openDate) {
            this.openDate = openDate;
        }

        public String getCloseDate() {
            return closeDate;
        }

        public void setCloseDate(String closeDate) {
            this.closeDate = closeDate;
        }

        public String getBusinessProfileImage() {
            return businessProfileImage;
        }

        public void setBusinessProfileImage(String businessProfileImage) {
            this.businessProfileImage = businessProfileImage;
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

        public String getEmailCode() {
            return emailCode;
        }

        public void setEmailCode(String emailCode) {
            this.emailCode = emailCode;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }

    }

    public class Result implements Serializable {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("provider_id")
        @Expose
        public String providerId;
        @SerializedName("business_id")
        @Expose
        public String businessId;
        @SerializedName("ruc_no")
        @Expose
        public String rucNo;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("phone")
        @Expose
        public String phone;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("client_code")
        @Expose
        public String clientCode;
        @SerializedName("agreement")
        @Expose
        public String agreement;
        @SerializedName("discount")
        @Expose
        public String discount;
        @SerializedName("observations")
        @Expose
        public String observations;
        @SerializedName("staus")
        @Expose
        public String staus;
        @SerializedName("date_time")
        @Expose
        public String dateTime;
        @SerializedName("business_details")
        @Expose
        public BusinessDetails businessDetails;
        @SerializedName("provider_details")
        @Expose
        public ProviderDetails providerDetails;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public String getRucNo() {
            return rucNo;
        }

        public void setRucNo(String rucNo) {
            this.rucNo = rucNo;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getClientCode() {
            return clientCode;
        }

        public void setClientCode(String clientCode) {
            this.clientCode = clientCode;
        }

        public String getAgreement() {
            return agreement;
        }

        public void setAgreement(String agreement) {
            this.agreement = agreement;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getObservations() {
            return observations;
        }

        public void setObservations(String observations) {
            this.observations = observations;
        }

        public String getStaus() {
            return staus;
        }

        public void setStaus(String staus) {
            this.staus = staus;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public BusinessDetails getBusinessDetails() {
            return businessDetails;
        }

        public void setBusinessDetails(BusinessDetails businessDetails) {
            this.businessDetails = businessDetails;
        }

        public ProviderDetails getProviderDetails() {
            return providerDetails;
        }

        public void setProviderDetails(ProviderDetails providerDetails) {
            this.providerDetails = providerDetails;
        }

    }


}
