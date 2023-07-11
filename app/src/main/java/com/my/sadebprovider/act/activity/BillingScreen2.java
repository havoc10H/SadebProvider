package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.service.GetServiceResponse;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.ServiceTobeBilledAdapter;
import com.my.sadebprovider.adapter.ServicesListBillingAdapter;
import com.my.sadebprovider.databinding.ActivityBillingScreen2Binding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingScreen2 extends AppCompatActivity {

    ActivityBillingScreen2Binding binding;
    private List<ResultItem> myservicelist = new ArrayList();
    private String name="",ruc="";
    private ServicesListBillingAdapter mAdapter;
    private ServiceTobeBilledAdapter serviceTobeBilledAdapter;
    private  List<com.my.sadebprovider.act.model.service.ResultItem> myAddedServiceList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_billing_screen2);

        name = getIntent().getExtras().getString("name");
        name = getIntent().getExtras().getString("ruc");

        binding.ivBack.setOnClickListener(v -> finish());

        binding.tvClear.setOnClickListener(v ->
                {
                    myserviceresponse();
                }
        );

        binding.etVendor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchServiceResponse(binding.etVendor.getText().toString());
                    return true;
                }
                return false;
            }
        });

                binding.tvNext.setOnClickListener(v ->
                {
                    if(myAddedServiceList.size()>0)
                    {

                        String services = "";
                        String prices = "";
                        float total = 0;

                        for (com.my.sadebprovider.act.model.service.ResultItem resultItem:myAddedServiceList)
                        {
                            services = services+resultItem.getId()+",";
                            prices = prices+resultItem.getServicePrice()+",";
                            total =total+ Float.parseFloat(resultItem.getServicePrice());
                        }

                        prices = prices.substring(0, prices.length() -1);
                        services = services.substring(0, services.length() -1);

                        startActivity(new Intent(BillingScreen2.this, ConfirmBillingAct.class).putExtra("price",prices)
                                .putExtra("services",services)
                                .putExtra("total",total+"")
                                .putExtra("name",name)
                                .putExtra("ruc",ruc)
                        );
                    }
                    else
                    {
                        Toast.makeText(BillingScreen2.this, ""+getString(R.string.please_select_service), Toast.LENGTH_SHORT).show();
                    }
                }
                );


    }

    @Override
    public void onResume() {
        super.onResume();
        myserviceresponse();
    }


    public void searchServiceResponse(String text) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .getSeachService(model.getResult().getId(),text)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response!=null){
                            if (response.isSuccessful()){
                                JsonObject object = response.body().getAsJsonObject();
                                int status =object.get("status").getAsInt();
                                Log.i("sfdvxc", "status: " +status );
                                if (status==1){
                                    myservicelist=new ArrayList<>();
                                    GetServiceResponse authentication = new Gson().fromJson(object,GetServiceResponse.class);
                                    myservicelist.addAll(authentication.getResult());

                                    mAdapter = new ServicesListBillingAdapter(BillingScreen2.this, new ServicesListBillingAdapter.SelectedItem() {

                                        @Override
                                        public void deleteItem(int position) {
                                            updateSelectedinvoiceItems(position);
                                        }

                                        @Override
                                        public void updateItem(int Position) {

                                        }
                                    });

                                    mAdapter.setMyservicelist(myservicelist);
//                        binding.recyclerServicesList.setHasFixedSize(true);
                                    binding.rvServices.setLayoutManager(new LinearLayoutManager(BillingScreen2.this));

                                    binding.rvServices.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();

                                }else {

                                }
                            }
                        }

                        Log.i("sfdvxc", "onResponse: " + response.body());
                        Log.i("sfdvxc", "onResponse: " + response.toString());

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("sfdvxc", "onFailure: " + t.getMessage());
                    }
                });
    }


    public void myserviceresponse() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .getmyServices(model.getResult().getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response!=null){
                            if (response.isSuccessful()){
                                JsonObject object = response.body().getAsJsonObject();
                                int status =object.get("status").getAsInt();
                                Log.i("sfdvxc", "status: " +status );
                                if (status==1){
                                    myservicelist=new ArrayList<>();
                                    GetServiceResponse authentication = new Gson().fromJson(object,GetServiceResponse.class);
                                    myservicelist.addAll(authentication.getResult());

                                    mAdapter = new ServicesListBillingAdapter(BillingScreen2.this, new ServicesListBillingAdapter.SelectedItem() {
                                        @Override
                                        public void deleteItem(int position) {

                                            updateSelectedinvoiceItems(position);

                                        }

                                        @Override
                                        public void updateItem(int Position) {

                                        }
                                    });

                                    mAdapter.setMyservicelist(myservicelist);
//                        binding.recyclerServicesList.setHasFixedSize(true);
                                    binding.rvServices.setLayoutManager(new LinearLayoutManager(BillingScreen2.this));

                                    binding.rvServices.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();

                                }else {

                                }
                            }
                        }

                        Log.i("sfdvxc", "onResponse: " + response.body());
                        Log.i("sfdvxc", "onResponse: " + response.toString());

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("sfdvxc", "onFailure: " + t.getMessage());
                    }
                });
    }


    public void updateSelectedinvoiceItems(int position)
    {

        myAddedServiceList.add(myservicelist.get(position));
        binding.rvServicesToBeBilled.setHasFixedSize(true);
        binding.rvServicesToBeBilled.setLayoutManager(new LinearLayoutManager(BillingScreen2.this));

        serviceTobeBilledAdapter = new ServiceTobeBilledAdapter(BillingScreen2.this, new ServicesListBillingAdapter.SelectedItem() {
            @Override
            public void deleteItem(int pos) {

                myAddedServiceList.remove(pos);
                serviceTobeBilledAdapter.notifyDataSetChanged();

            }

            @Override
            public void updateItem(int Position) {

            }
        });

        serviceTobeBilledAdapter.setList(myAddedServiceList);

        binding.rvServicesToBeBilled.setAdapter(serviceTobeBilledAdapter);

        serviceTobeBilledAdapter.notifyDataSetChanged();

    }


}