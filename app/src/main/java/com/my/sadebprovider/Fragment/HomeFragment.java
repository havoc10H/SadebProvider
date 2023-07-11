package com.my.sadebprovider.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.Model.HomeModel;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.StatusResponse;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.booking.BookingAppointmentResponse;
import com.my.sadebprovider.act.model.booking.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.booking.BookingRequest;
import com.my.sadebprovider.adapter.HomeRecyclerViewAdapter;
import com.my.sadebprovider.databinding.FragmentHomeBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private List<ResultItem> list = new ArrayList<>();
    private FragmentHomeBinding binding;
    private BookingAppointmentResponse authentication;
    private ResponseAuthentication model;
//  private int API_COUNT, ALL_API_COUNT = 1;
    private HomeRecyclerViewAdapter mAdapter;
    private final ArrayList<HomeModel> modelList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setAdapter();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getBookingRequest();
    }

    private void getBookingRequest() {
        Log.i("cscc", "getBookingRequest: " + 12);
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        binding.llMain.setVisibility(View.GONE);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        if (model != null) {
            String User_ID = model.getResult().getId();
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(BookingRequest.class)
                    .getBookingAppointment(User_ID)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                            ++API_COUNT;
//                            if (API_COUNT == ALL_API_COUNT) {
                                binding.llMain.setVisibility(View.VISIBLE);
                                binding.loaderLayout.loader.setVisibility(View.GONE);
//                            }
                            Log.i("scvdbx", "onResponse: " + response.toString());
                            if (response != null) {
                                if (response.isSuccessful()) {
                                    JsonObject object = response.body().getAsJsonObject();
                                    int status = object.get("status").getAsInt();
                                    Log.i("scvdbx", "sta: " + status);
                                    if (status == 1) {
                                        list=new ArrayList<>();
                                        authentication = new Gson().fromJson(object, BookingAppointmentResponse.class);
                                        list.addAll(authentication.getResult());
                                        mAdapter.setList(list);
                                        mAdapter.notifyDataSetChanged();
                                        Log.i("dvdvvxv", "onResponse: " + list.toString());
                                        if (list.size() > 0) {
                                            binding.tvNoProductFound.setVisibility(View.GONE);
                                        }else {
                                            binding.tvNoProductFound.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                        Toast.makeText(getContext(), authentication.getResult(), Toast.LENGTH_SHORT).show();
                                        if (list.size() > 0) {
                                            binding.tvNoProductFound.setVisibility(View.GONE);
                                        }else {
                                            binding.tvNoProductFound.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
//                            ++API_COUNT;
//                            if (API_COUNT == ALL_API_COUNT) {
                                Log.i("scvdbx", "sta: " + t.getMessage());
                                binding.loaderLayout.loader.setVisibility(View.GONE);
//                            }
                        }
                    });
        }
    }
    private void setAdapter() {
        mAdapter = new HomeRecyclerViewAdapter(getContext(), new HomeRecyclerViewAdapter.Status() {
            @Override
            public void acceptcontrol(int position, DoneCallback callback) {
                acceptResponse(position, callback);
            }
            @Override
            public void declinetcontrol(int position, DoneCallback callback) {
                declineResponce(position, callback);
            }
        });
        mAdapter.setList(list);
        binding.recyclerReequuest.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerReequuest.setAdapter(mAdapter);
    }

    private void declineResponce(int position, DoneCallback callback) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(BookingRequest.class)
                .makestatus(getString(R.string.cancel), authentication.getResult().get(position).getId()
                        , model.getResult().getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    callback.done();
                                    StatusResponse statusResponse = new Gson().fromJson(object, StatusResponse.class);
                                    Log.i("scdvddvv", "onResponse: " + statusResponse.getResult());
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

    private void acceptResponse(int position, DoneCallback callback) {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(BookingRequest.class)
                .makestatus(getString(R.string.accept), authentication.getResult().get(position).getId()
                        , model.getResult().getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("scvdbx", "onResponse: " + response.toString());

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    callback.done();
                                    StatusResponse statusResponse = new Gson().fromJson(object, StatusResponse.class);
                                    Log.i("scdvddvv", "onResponse: " + statusResponse.getResult());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);


                        Log.i("cxvxv", "onFailure: " + t.getMessage());
                    }
                });

    }

    public interface DoneCallback {
        void done();
    }
}

