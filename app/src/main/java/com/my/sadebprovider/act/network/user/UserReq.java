package com.my.sadebprovider.act.network.user;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserReq {


    @POST("add_provider_client")
    @Multipart
    Call<JsonElement> addClient(@Part MultipartBody.Part user_id,
                                @Part MultipartBody.Part business_id,
                                @Part MultipartBody.Part user_name,
                                @Part MultipartBody.Part email,
                                 @Part MultipartBody.Part mobile,
                                 @Part MultipartBody.Part gender,
                                @Part MultipartBody.Part dob,
                                @Part MultipartBody.Part clientCode,
                                @Part MultipartBody.Part agreement,
                                @Part MultipartBody.Part discount,
                                 @Part MultipartBody.Part observation,
                                @Part MultipartBody.Part ruc,
                                @Part MultipartBody.Part image
    );


    @POST("add_user")
    @Multipart
    Call<JsonElement> getAddUser(@Part MultipartBody.Part user_id,
                                 @Part MultipartBody.Part email,
                                 @Part MultipartBody.Part mobile,
                                 @Part MultipartBody.Part gender,
                                 @Part MultipartBody.Part user_name,
                                 @Part MultipartBody.Part image,
                                 @Part MultipartBody.Part password
    );

    @POST("get_users")
    @FormUrlEncoded
    Call<JsonElement> getAllUsers(@Field("user_id") String user_id);

    @POST("edit_user")
    @Multipart
    Call<JsonElement> getUpdateUser(@Part MultipartBody.Part id,
                                    @Part MultipartBody.Part email,
                                    @Part MultipartBody.Part mobile,
                                    @Part MultipartBody.Part gender,
                                    @Part MultipartBody.Part user_name,
                                    @Part MultipartBody.Part image,
                                    @Part MultipartBody.Part password

    );

    @POST("delete_user")
    @FormUrlEncoded
    Call<JsonElement> getDeleteUser(@Field("id") String id);

}

