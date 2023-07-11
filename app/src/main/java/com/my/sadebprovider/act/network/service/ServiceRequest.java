package com.my.sadebprovider.act.network.service;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ServiceRequest {

    @Multipart
    @POST("add_service")
    Call<JsonElement> addService(@Part MultipartBody.Part user_id,
                                 @Part MultipartBody.Part service_name,
                                 @Part MultipartBody.Part service_price,
                                 @Part MultipartBody.Part service_offer,
                                 @Part MultipartBody.Part category_id,
                                 @Part MultipartBody.Part service_time,
                                 @Part MultipartBody.Part customization,
                                 @Part MultipartBody.Part description,
                                 @Part MultipartBody.Part image1,
                                 @Part MultipartBody.Part image2,
                                 @Part MultipartBody.Part image3,
                                 @Part MultipartBody.Part image4,
                                 @Part MultipartBody.Part image5,
                                 @Part MultipartBody.Part image6,
                                 @Part MultipartBody.Part image7,
                                 @Part MultipartBody.Part lat,
                                 @Part MultipartBody.Part lon,
                                 @Part MultipartBody.Part estimate_time,
                                 @Part MultipartBody.Part provider_user_id);

    @GET("get_service")
    Call<JsonElement> getmyServices(@Query("user_id") String user_id);

    @GET("search_service")
    Call<JsonElement> getSeachService(@Query("user_id") String user_id,@Query("Keyword") String searchSring);

    @POST("get_service_user")
    @FormUrlEncoded
    Call<JsonElement> getmyServicesdetail(@Field("service_id") String service_id);

    @Multipart
    @POST("update_service")
    Call<JsonElement> UpdateService(@Part MultipartBody.Part service_id,
                                    @Part MultipartBody.Part service_name,
                                    @Part MultipartBody.Part service_price,
                                    @Part MultipartBody.Part service_offer,
                                    @Part MultipartBody.Part category_id,
                                    @Part MultipartBody.Part service_time,
                                    @Part MultipartBody.Part customization,
                                    @Part MultipartBody.Part description,
                                    @Part MultipartBody.Part image1,
                                    @Part MultipartBody.Part image2,
                                    @Part MultipartBody.Part image3,
                                    @Part MultipartBody.Part image4,
                                    @Part MultipartBody.Part image5,
                                    @Part MultipartBody.Part image6,
                                    @Part MultipartBody.Part image7,
                                    @Part MultipartBody.Part lat,
                                    @Part MultipartBody.Part lon,
                                    @Part MultipartBody.Part estimate_time,
                                    @Part MultipartBody.Part provider_user_id
    );

    @POST("provider_service_time")
    @FormUrlEncoded
    Call<JsonElement> getAddTimeSlot(@Field("user_id") String user_id,
                                     @Field("weekly_close") String weekly_close,
                                     @Field("open_time_monday") String open_time_monday,
                                     @Field("open_time_tuesday") String open_time_tuesday,
                                     @Field("open_time_wednesday") String open_time_wednesday,
                                     @Field("open_time_thursday") String open_time_thursday,
                                     @Field("open_time_friday") String open_time_friday,
                                     @Field("open_time_saturday") String open_time_saturday,
                                     @Field("open_time_sunday") String open_time_sunday,
                                     @Field("close_time_monday") String close_time_monday,
                                     @Field("close_time_tuesday") String close_time_tuesday,
                                     @Field("close_time_wednesday") String close_time_wednesday,
                                     @Field("close_time_thursday") String close_time_thursday,
                                     @Field("close_time_friday") String close_time_friday,
                                     @Field("close_time_saturday") String close_time_saturday,
                                     @Field("close_time_sunday") String close_time_sunday);

    @Multipart
    @POST("add_user_invoice")
    Call<JsonElement> addInvoice(@Part MultipartBody.Part user_name,
                                 @Part MultipartBody.Part userRUC,
                                 @Part MultipartBody.Part user_id,
                                 @Part MultipartBody.Part provider_id,
                                 @Part MultipartBody.Part service_id,
                                 @Part MultipartBody.Part price,
                                 @Part MultipartBody.Part payment_method,
                                 @Part MultipartBody.Part discount,
                                 @Part MultipartBody.Part subTotal,
                                 @Part MultipartBody.Part tax,
                                 @Part MultipartBody.Part total,
                                 @Part MultipartBody.Part givenAmount,
                                 @Part MultipartBody.Part return_Amount,
                                 @Part MultipartBody.Part email);

}
