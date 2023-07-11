package com.my.sadebprovider.act.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.Model.HomeModel;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.service.DeleteServiceResponse;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.service.GetServiceResponse;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.service.DeleteServiceReq;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.ServicesListViewAdapter;
import com.my.sadebprovider.databinding.ActivityServicesListBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesListActivity extends AppCompatActivity {

    private final ArrayList<HomeModel> modelList = new ArrayList<>();
    private  List<ResultItem> myservicelist = new ArrayList();
    private ServicesListViewAdapter mAdapter;
    private ActivityServicesListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_services_list);

        myserviceresponse();
        binding.next.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.fbAdd.setOnClickListener(v -> {

            Intent intent=new Intent(this,AddSaloonPhoto.class);
            intent.putExtra("type", NetworkConstraint.TYPE.ADD);

            startActivity(intent);

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
                                             mAdapter = new ServicesListViewAdapter(ServicesListActivity.this, /*myservicelist,*/ new ServicesListViewAdapter.SelectedItem() {
                                                 @Override
                                                 public void deleteItem(int Position) {
                                                     DeleteService(Position);
                                                 }

                                                 @Override
                                                 public void updateItem(int Position) {
                                                     binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                                                     new Handler().postDelayed(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             Intent  intent =new Intent(ServicesListActivity.this,AddSaloonPhoto.class);
                                                             intent.putExtra("myservicelist", new Gson().toJson(myservicelist.get(Position)));
                                                             intent.putExtra("type", NetworkConstraint.TYPE.EDIT);
                                                             startActivity(intent);
                                                             binding.loaderLayout.loader.setVisibility(View.GONE);

                                                         }
                                                     },1000);

                                                 }
                                             });
                                             mAdapter.setMyservicelist(myservicelist);
//                        binding.recyclerServicesList.setHasFixedSize(true);
                                             binding.recyclerServicesList.setLayoutManager(new LinearLayoutManager(ServicesListActivity.this));

                                             binding.recyclerServicesList.setAdapter(mAdapter);
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

    private void DeleteService(int position) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(DeleteServiceReq.class)
                .getDeleteServiceReq(myservicelist.get(position).getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response!=null){
                            if (response.isSuccessful()){
                                JsonObject object= response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status==1){
                                    DeleteServiceResponse authentication = new Gson().fromJson(object, DeleteServiceResponse.class);
                                    Toast.makeText(ServicesListActivity.this, authentication.getResult(), Toast.LENGTH_SHORT).show();
                                    myservicelist.remove(position);
                                    mAdapter.notifyDataSetChanged();

                                }else {
                                    ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                    Snackbar.make(findViewById(android.R.id.content),
                                            authentication.getResult(),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                    }
                });
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mAdapter != null) {
//            mAdapter.saveStates(outState);
//        }
//    }

}