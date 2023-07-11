package com.my.sadebprovider.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.Model.HomeModel;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.AddSaloonPhoto;
import com.my.sadebprovider.act.model.service.DeleteServiceResponse;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.service.GetServiceResponse;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.service.DeleteServiceReq;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.ServicesListViewAdapter;
import com.my.sadebprovider.databinding.FragmentServiceBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceFragment extends Fragment {

    private FragmentServiceBinding binding;
    private final ArrayList<HomeModel> modelList = new ArrayList<>();
    private List<ResultItem> myservicelist = new ArrayList();
    private ServicesListViewAdapter mAdapter;
    AlertDialog alertDialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false);
        init();

        mAdapter = new ServicesListViewAdapter(getContext()/*, myservicelist*/, new ServicesListViewAdapter.SelectedItem() {
            @Override
            public void deleteItem(int Position) {
                alertDialog = new AlertDialog.Builder(getActivity())

                .setMessage(R.string.are_you_sure_you_wanted_to_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        DeleteService(Position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .show();
            }

            @Override
            public void updateItem(int Position) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), AddSaloonPhoto.class);
                        intent.putExtra("myservicelist", new Gson().toJson(myservicelist.get(Position)));
                        intent.putExtra("type", NetworkConstraint.TYPE.EDIT);
                        startActivity(intent);
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });

        mAdapter.setMyservicelist(myservicelist);
        binding.recyclerServicesList.setHasFixedSize(true);
        binding.recyclerServicesList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerServicesList.setAdapter(mAdapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        myserviceresponse();
    }

    private void init() {
        binding.fbAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddSaloonPhoto.class);
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

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                Log.i("sfdvxc", "status: " + status);
                                if (status == 1) {
                                    myservicelist = new ArrayList<>();
                                    GetServiceResponse authentication = new Gson().fromJson(object, GetServiceResponse.class);
                                    myservicelist.addAll(authentication.getResult());
                                    mAdapter.setMyservicelist(myservicelist);

                                    mAdapter.notifyDataSetChanged();
                                } else {

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
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    DeleteServiceResponse authentication = new Gson().fromJson(object, DeleteServiceResponse.class);
                                    Toast.makeText(getContext(), authentication.getResult(), Toast.LENGTH_SHORT).show();
                                    myservicelist.remove(position);
                                    mAdapter.notifyDataSetChanged();

                                } else {

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
}