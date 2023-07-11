package com.my.sadebprovider.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PushNotificationModel implements Serializable {

    @SerializedName("key")
    @Expose
    String key;

    @SerializedName("message")
    @Expose
    String message;

//    @SerializedName("image")
//    @Expose
//    String image;

    @SerializedName("provider_name")
    @Expose
    String provider_name;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("technician_id")
    @Expose
    String technician_id;

    @SerializedName("job_id")
    @Expose
    String job_id;

    @SerializedName("type")
    @Expose
    String type;

    @SerializedName("date")
    @Expose
    String date;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTechnician_id() {
        return technician_id;
    }

    public void setTechnician_id(String technician_id) {
        this.technician_id = technician_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
