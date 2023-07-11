package com.my.sadebprovider.act.network.category;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddCategory {
    @GET("add_category")
    Call<JsonElement> addCategory(@Query("category_name") String category_name,
                                  @Query("business_id") String business_ID);
}
