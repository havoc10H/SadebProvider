package com.my.sadebprovider.act.network.profile;

import com.google.gson.JsonElement;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.password.ChangePasswordResponse;
import com.my.sadebprovider.act.model.password.ForgetPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProfileRequet {

    @FormUrlEncoded
    @POST("forgot_password")
    Call<JsonElement> getForgetPass(@Field("email") String email);

    @FormUrlEncoded
    @POST("update_profile")
    Call<ResponseAuthentication> getProfileUpdate(@Field("user_name") String user_name,
                                                  @Field("surname") String surname,
                                                  @Field("email") String email,
                                                  @Field("mobile") String mobile,
                                                  @Field("user_id") String id,
                                                  @Field("gender") String gender
    );
    @FormUrlEncoded
    @POST("change_password")
    Call<JsonElement> changePassword(@Field("user_id") String id,
                                     @Field("old_password") String old_pass,
                                     @Field("new_password") String new_password
    );
}
