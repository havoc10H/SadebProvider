package com.my.sadebprovider.act.network.profile;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdateProfile {


    @Multipart
    @POST("update_profile")
    Call<JsonElement> getEditUpdate(@Part MultipartBody.Part user_name,
                                    @Part MultipartBody.Part surname,
                                    @Part MultipartBody.Part email,
                                    @Part MultipartBody.Part mobile,
                                    @Part MultipartBody.Part user_id,
                                    @Part MultipartBody.Part gender,
                                    @Part MultipartBody.Part image);


    @Multipart
    @POST("update_business")
    Call<JsonElement> updateBusiness(@Part MultipartBody.Part user_id,
                                     @Part MultipartBody.Part business_type,
                                     @Part MultipartBody.Part business_name,
                                     @Part MultipartBody.Part business_address,
                                     @Part MultipartBody.Part b_lat,
                                     @Part MultipartBody.Part b_lon,
                                     @Part MultipartBody.Part business_cell_phone,
                                     @Part MultipartBody.Part business_landline,
                                     @Part MultipartBody.Part offer_home_delivery,
                                     @Part MultipartBody.Part open_date,
                                     @Part MultipartBody.Part close_date,
                                     @Part MultipartBody.Part business_profile_image,
//                                     @Part MultipartBody.Part image1,
//                                     @Part MultipartBody.Part image2,
//                                     @Part MultipartBody.Part image3,
//                                     @Part MultipartBody.Part image4,
//                                     @Part MultipartBody.Part image5,
//                                     @Part MultipartBody.Part image6,
//                                     @Part MultipartBody.Part image7,
                                     @Part MultipartBody.Part description
    );

    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage1(@Part MultipartBody.Part user_id,
                                          @Part MultipartBody.Part image1);

    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage2(@Part MultipartBody.Part user_id,
                                           @Part MultipartBody.Part image2);

    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage3(@Part MultipartBody.Part user_id,
                                           @Part MultipartBody.Part image3);

    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage4(@Part MultipartBody.Part user_id,
                                           @Part MultipartBody.Part image4);

    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage5(@Part MultipartBody.Part user_id,
                                           @Part MultipartBody.Part image5);
    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage6(@Part MultipartBody.Part user_id,
                                           @Part MultipartBody.Part image6);

    @Multipart
    @POST("update_business_image")
    Call<JsonElement> updateBusinessImage7(@Part MultipartBody.Part user_id,
                                           @Part MultipartBody.Part image7);

}
