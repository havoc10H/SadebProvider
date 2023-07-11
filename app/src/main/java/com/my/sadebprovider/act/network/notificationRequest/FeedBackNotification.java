package com.my.sadebprovider.act.network.notificationRequest;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FeedBackNotification {

    @GET("get_feedback_notifi")
    Call<JsonElement> getFeedbackNotification(@Query("user_id") String user_id,
                                 @Query("type") String type,
                                              @Query("lang") String lang
     );

}

