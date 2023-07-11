package com.my.sadebprovider.act.network.authentication;

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

public interface RequestLAuthentication {

    @GET("login")
    Call<JsonElement> getlogIn(@Query("email") String email,
                               @Query("password") String password,
                               @Query("type") String type,
                               @Query("register_id") String register_id
    );

    @Multipart
    @POST("signup")
    Call<JsonElement> updateBusiness(
                                     @Part MultipartBody.Part date,
                                     @Part MultipartBody.Part businessCategory,
                                     @Part MultipartBody.Part user_name,
                                     @Part MultipartBody.Part surname,
                                     @Part MultipartBody.Part password,
                                     @Part MultipartBody.Part email,
                                     @Part MultipartBody.Part type,
                                     @Part MultipartBody.Part mobile,
                                     @Part MultipartBody.Part register_id,
                                     @Part MultipartBody.Part description,
                                     @Part MultipartBody.Part business_name,
                                     @Part MultipartBody.Part business_address,
                                     @Part MultipartBody.Part b_lat,
                                     @Part MultipartBody.Part b_lon,
                                     @Part MultipartBody.Part business_cell_phone,
                                     @Part MultipartBody.Part business_landline,
//                                     @Part MultipartBody.Part image3,
//                                     @Part MultipartBody.Part image4,
//                                     @Part MultipartBody.Part image5,
//                                     @Part MultipartBody.Part image6,
                                     @Part MultipartBody.Part business_profile_image,
                                     @Part MultipartBody.Part offer_home_delivery
    );

    @FormUrlEncoded
    @POST("signup")
    Call<JsonElement> getSignUp(@Field("user_name") String user_name,
                                @Field("surname") String surname,
                                @Field("password") String password,
                                @Field("email") String email,
                                @Field("type") String type,
                                @Field("mobile") String mobile,
                                @Field("register_id") String register_id,
                                @Field("description") String description,
                                @Field("business_name") String business_name,
                                @Field("business_address") String business_address,
                                @Field("b_lat") String b_lat,
                                @Field("b_lon") String b_lon,
                                @Field("business_cell_phone") String business_cell_phone,
                                @Field("business_landline") String business_landline,
                                @Field("offer_home_delivery") String offer_home_delivery);

}
