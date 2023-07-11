package com.my.sadebprovider.act.network.service;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeleteServiceReq {

    @GET("delete_service")
    Call<JsonElement> getDeleteServiceReq(@Query("service_id") String service_id);
}
