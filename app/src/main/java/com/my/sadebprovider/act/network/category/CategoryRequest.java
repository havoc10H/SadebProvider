package com.my.sadebprovider.act.network.category;

import com.my.sadebprovider.act.model.authentication.SuccessResDeleteClient;
import com.my.sadebprovider.act.model.businesscategory.BusinessType;
import com.my.sadebprovider.act.model.category.CategoryResponse;
import com.my.sadebprovider.act.model.client.SuccessResGetClient;
import com.my.sadebprovider.act.model.user.SuccessResClients;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryRequest {

    @GET("get_business_services")
    Call<CategoryResponse> getCategory(
            @Query("business_id") String business_type,
            @Query("lang") String provider_name
    );

    @GET("get_business_type")
    Call<BusinessType> getBusinesType(@Query("lang") String provider_name);

    @GET("get_provider_client")
    Call<SuccessResClients> getClientData(@Query("provider_id") String provider_name);

    @GET("get_provider_client_profile")
    Call<SuccessResGetClient> getClient(
            @Query("provider_id") String provider_id,
            @Query("client_id") String client_id
    );


    @GET("remove_provider_client")
    Call<SuccessResDeleteClient> deleteClient(
            @Query("client_id") String business_type,
            @Query("provider_id") String provider_name
    );


}
//https://servista.co.uk/servista_new/webservice/add_service
