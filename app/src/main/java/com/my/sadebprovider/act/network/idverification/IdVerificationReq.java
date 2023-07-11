package com.my.sadebprovider.act.network.idverification;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IdVerificationReq {
    @Multipart
    @POST("upload_verification_image")
    Call<JsonElement> getIdVerification(@Part MultipartBody.Part verification_front_image,
                                    @Part MultipartBody.Part verification_end_image,
                                    @Part MultipartBody.Part user_id);
}
