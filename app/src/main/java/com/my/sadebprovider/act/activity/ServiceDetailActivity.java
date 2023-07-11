package com.my.sadebprovider.act.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.category.CategoryResponse;
import com.my.sadebprovider.act.model.service.servicedetail.ResultItem;
import com.my.sadebprovider.act.model.service.servicedetail.ServiceDetailResponse;
import com.my.sadebprovider.act.model.service.servicedetail.ServiceUserItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.category.CategoryRequest;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.UserdetailAdapter;
import com.my.sadebprovider.databinding.ActivityServiceDetailBinding;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceDetailActivity extends AppCompatActivity {

    private final List<ServiceUserItem> list = new ArrayList<>();
    private final List<com.my.sadebprovider.act.model.category.ResultItem> catlist = new ArrayList<>();
    private final int ALL_API_COUNT = 2;
    private ActivityServiceDetailBinding binding;
    private UserdetailAdapter adapter;
    private String catName;
    private int API_COUNT;

    private ResponseAuthentication model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = SharedPrefsManager.getInstance().getObject(NetworkConstraint.type, ResponseAuthentication.class);

        init();

    }

    private void init() {
        CategoryResponse();
        binding.rvAllUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserdetailAdapter(list);
        binding.rvAllUsers.setAdapter(adapter);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void getAllUser() {
        String service_id = getIntent().getStringExtra("service_id");
        binding.scrollView.setVisibility(View.GONE);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .getmyServicesdetail(service_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        ++API_COUNT;
                        if (API_COUNT == ALL_API_COUNT) {
                            binding.scrollView.setVisibility(View.VISIBLE);
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                        }
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    ServiceDetailResponse authentication = new Gson().fromJson(object, ServiceDetailResponse.class);
                                    list.clear();
                                    list.addAll(authentication.getResult().getServiceUser());
                                    adapter.notifyDataSetChanged();
                                    SetData(authentication.getResult());
                                } else {
                                    ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                    Log.i("dscbhs", "onResponse: " + authentication);
                                    Snackbar.make(findViewById(android.R.id.content),
                                            authentication.getResult(),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        ++API_COUNT;
                        if (API_COUNT == ALL_API_COUNT) {
                            Log.i("scvdbx", "sta: " + t.getMessage());
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void SetData(ResultItem result) {
        binding.tvServiceName.setText(result.getServiceName());
        binding.tvServicePrice.setText(result.getServicePrice());
        binding.tvServiceOffer.setText(result.getServiceOffer());
        binding.tvServiceTime.setText(result.getServiceTime());
        Picasso.get().load(result.getImage1()).placeholder(R.drawable.user_placeholder).into(binding.imgCamera);

        String catName = null;
        Log.i("csacaa", "SetData: "+catlist.toString());
        for (int i = 0; i < catlist.size(); i++) {
            Log.i("csacaa", "SetData: "+Integer.valueOf(catlist.get(i).getId()));
            if (Integer.valueOf(catlist.get(i).getId()) == Integer.valueOf(result.getCategoryId())) {
                Log.i("csacaa", "SetData: " + 12);
                catName = catlist.get(i).getCategoryName();
                break;
            }
            Log.i("csacaa", "cat: " + catName);

        }
        binding.tvCategory.setText(catName);

        Log.i("csacaa", "SetData: " + Integer.valueOf(result.getCategoryId()));
        binding.tvEstimateTime.setText(result.getEstimateTime());
        binding.tvCustomization.setText(result.getCustomization());
        binding.tvDescription.setText(result.getDescription());
    }

    private void CategoryResponse() {

        String language= SharedPrefsManager.getInstance().getString("language");

        binding.scrollView.setVisibility(View.GONE);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);

        String businessType = model.getResult().getBusinessType();

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(CategoryRequest.class)
                .getCategory(businessType,language)
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                        ++API_COUNT;
                        if (API_COUNT == ALL_API_COUNT) {
                            binding.scrollView.setVisibility(View.VISIBLE);
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                        }
                        if (response != null) {
                            if (response.isSuccessful())
                                catlist.addAll(response.body().getResult());
                            getAllUser();
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {
                        ++API_COUNT;
                        if (API_COUNT == ALL_API_COUNT) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                        }
                    }
                });
    }
}