package com.my.sadebprovider.act.network.booking;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingRequest {

    @FormUrlEncoded
    @POST("get_booking_appointment")
    Call<JsonElement> getBookingAppointment(
            @Field("provider_id") String provider_id);

    @FormUrlEncoded
    @POST("get_complete_appointment")
    Call<JsonElement> getCompletedAppointment(
            @Field("provider_id") String provider_id);



    @GET("provider_accept_and_Cancel_request")
    Call<JsonElement> makestatus(@Query("status") String status,
                               @Query("request_id") String request,
                               @Query("provider_id") String provider_id
    );
}
