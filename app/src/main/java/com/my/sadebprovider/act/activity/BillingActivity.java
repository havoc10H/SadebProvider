package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.my.sadebprovider.Fragment.HomeFragment;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.booking.BookingAppointmentResponse;
import com.my.sadebprovider.act.model.booking.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.booking.BookingRequest;
import com.my.sadebprovider.adapter.BillingAdapter;
import com.my.sadebprovider.adapter.HomeRecyclerViewAdapter;
import com.my.sadebprovider.databinding.ActivityBillingBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingActivity extends AppCompatActivity {
    private ResponseAuthentication model;
//    private int API_COUNT, ALL_API_COUNT = 1;
     LineGraphSeries<DataPoint> series;
     private List<ResultItem> list = new ArrayList<>();
     private BookingAppointmentResponse authentication;

    private BillingAdapter mAdapter;
    ActivityBillingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setAdapter();

//        // on below line we are adding data to our graph view.
//        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                // on below line we are adding
//                // each point on our x and y axis.
//                new DataPoint(0, 1),
//                new DataPoint(1, 3),
//                new DataPoint(2, 4),
//                new DataPoint(3, 9),
//                new DataPoint(4, 6),
//                new DataPoint(5, 3),
//                new DataPoint(6, 6),
//                new DataPoint(7, 1),
//                new DataPoint(8, 2)
//        });

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        binding.idGraphView.setTitle(getString(R.string.billing_graph_view));

        // on below line we are setting
        // text color to our graph view.
        binding.idGraphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        binding.idGraphView.setTitleTextSize(18);

        // on below line we are adding
        // data series to our graph view.
//        binding.idGraphView.addSeries(series);

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

                                        for(int i=0;i<authentication.getResult().size();i++){
                                            if(authentication.getResult().get(i).getStatus().equals("Accept")){
                                                list.add(authentication.getResult().get(i));
                                            }
                                        }
                                        DataPoint dataPoint[]=  new DataPoint[list.size()];
                                        for(int i=0;i<list.size();i++){
                                            dataPoint[i]=new DataPoint(i, Double.parseDouble(list.get(i).getService_details().getServicePrice()));
                                        }
                                        series = new LineGraphSeries<DataPoint>(dataPoint);
                                        binding.idGraphView.addSeries(series);

//                                        list.addAll(authentication.getResult());
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
                                        Toast.makeText(BillingActivity.this, authentication.getResult(), Toast.LENGTH_SHORT).show();

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
        mAdapter = new BillingAdapter(this/*, new BillingAdapter.Status() {
            @Override
            public void acceptcontrol(int position, BillingAdapter.DoneCallback callback) {
//                acceptResponse(position, callback);
            }

            @Override
            public void declinetcontrol(int position, BillingAdapter.DoneCallback callback) {
//                declineResponce(position, callback);
            }
        }*/);
        mAdapter.setList(list);
        binding.recyclerReequuest.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerReequuest.setAdapter(mAdapter);


    }
//    public interface DoneCallback {
//        void done();
//    }
}
