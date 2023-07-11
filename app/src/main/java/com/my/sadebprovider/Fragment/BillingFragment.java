package com.my.sadebprovider.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.Model.HomeModel;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.AddSaloonPhoto;
import com.my.sadebprovider.act.activity.BillingScreen2;
import com.my.sadebprovider.act.activity.ConfirmBillingAct;
import com.my.sadebprovider.act.activity.ServicesListActivity;
import com.my.sadebprovider.act.activity.StatusResponse;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.booking.BookingAppointmentResponse;
import com.my.sadebprovider.act.model.booking.ResultItem;
import com.my.sadebprovider.act.model.service.GetServiceResponse;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.booking.BookingRequest;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.HomeCompletedRecyclerViewAdapter;
import com.my.sadebprovider.adapter.HomeRecyclerViewAdapter;
import com.my.sadebprovider.adapter.ServiceTobeBilledAdapter;
import com.my.sadebprovider.adapter.ServicesListBillingAdapter;
import com.my.sadebprovider.adapter.ServicesListViewAdapter;
import com.my.sadebprovider.databinding.FragmentBillingBinding;
import com.my.sadebprovider.databinding.FragmentBillingDemoBinding;
import com.my.sadebprovider.databinding.FragmentHomeBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillingFragment extends Fragment {

    FragmentBillingDemoBinding binding;

    private  List<com.my.sadebprovider.act.model.service.ResultItem> myservicelist = new ArrayList();

    private  List<com.my.sadebprovider.act.model.service.ResultItem> myAddedServiceList = new ArrayList();

    private List<ResultItem> list = new ArrayList<>();
    private BookingAppointmentResponse authentication;
    private ResponseAuthentication model;
//    private int API_COUNT, ALL_API_COUNT = 1;

    private ServicesListBillingAdapter mAdapter;
    private final ArrayList<HomeModel> modelList = new ArrayList<>();

    private ServiceTobeBilledAdapter serviceTobeBilledAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BillingFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static BillingFragment newInstance(String param1, String param2) {
        BillingFragment fragment = new BillingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_billing_demo, container, false);
//        setAdapter();

        String date = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault()).format(new Date());

        binding.tvDate.setText(getString(R.string.date)+" "+date);

//        binding.tvClear.setOnClickListener(v ->
//                {
//                    myserviceresponse();
//                }
//                );

//        binding.tvNext.setOnClickListener(v ->
//                {
//                    if(!binding.etSearchClient.getText().toString().equals(""))
//                    {
//
//                        if(!binding.etRUC.getText().toString().equals(""))
//                        {
//                            if(myAddedServiceList.size()>0)
//                            {
//
//                                String services = "";
//                                String prices = "";
//                                float total = 0;
//
//                                for (com.my.sadebprovider.act.model.service.ResultItem resultItem:myAddedServiceList)
//                                {
//                                    services = services+resultItem.getId()+",";
//                                    prices = prices+resultItem.getServicePrice()+",";
//                                    total =total+ Float.parseFloat(resultItem.getServicePrice());
//                                }
//
//                                prices = prices.substring(0, prices.length() -1);
//                                services = services.substring(0, services.length() -1);
//
//                                startActivity(new Intent(getActivity(), BillingScreen2.class).putExtra("price",prices)
//                                        .putExtra("services",services)
//                                        .putExtra("total",total+"")
//                                        .putExtra("name",binding.etSearchClient.getText().toString())
//                                        .putExtra("ruc",binding.etRUC.getText().toString())
//                                );
//                            }
//                            else
//                            {
//                                Toast.makeText(getActivity(), "Por favor agregue servicios", Toast.LENGTH_SHORT).show();
//                            }
//                        }else
//                        {
//                            Toast.makeText(getActivity(), "Ingresar RUC", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(), "Por favor ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                );


        binding.tvNext.setOnClickListener(v ->
                {
                    if(!binding.etSearchClient.getText().toString().equals(""))
                    {

                        if(!binding.etRUC.getText().toString().equals(""))
                        {

                            startActivity(new Intent(getActivity(), BillingScreen2.class)
                                    .putExtra("name",binding.etSearchClient.getText().toString())
                                    .putExtra("ruc",binding.etRUC.getText().toString())
                            );
//
//                            if(myAddedServiceList.size()>0)
//                            {
//
//                                String services = "";
//                                String prices = "";
//                                float total = 0;
//
//                                for (com.my.sadebprovider.act.model.service.ResultItem resultItem:myAddedServiceList)
//                                {
//                                    services = services+resultItem.getId()+",";
//                                    prices = prices+resultItem.getServicePrice()+",";
//                                    total =total+ Float.parseFloat(resultItem.getServicePrice());
//                                }
//
//                                prices = prices.substring(0, prices.length() -1);
//                                services = services.substring(0, services.length() -1);
//
//                                startActivity(new Intent(getActivity(), BillingScreen2.class).putExtra("price",prices)
//                                        .putExtra("services",services)
//                                        .putExtra("total",total+"")
//                                        .putExtra("name",binding.etSearchClient.getText().toString())
//                                        .putExtra("ruc",binding.etRUC.getText().toString())
//                                );
//                            }
//                            else
//                            {
//                                Toast.makeText(getActivity(), "Por favor agregue servicios", Toast.LENGTH_SHORT).show();
//                            }
                        }else
                        {
                            Toast.makeText(getActivity(), "Ingresar RUC", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Por favor ingrese el nombre del cliente", Toast.LENGTH_SHORT).show();
                    }
                }
        );

//        binding.etVendor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    searchServiceResponse(binding.etVendor.getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
//        myserviceresponse();
    }

//    public void searchServiceResponse(String text) {
//        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
//        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
//
//        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
//                .create(ServiceRequest.class)
//                .getSeachService(model.getResult().getId(),text)
//                .enqueue(new Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//
//                        binding.loaderLayout.loader.setVisibility(View.GONE);
//
//                        if (response!=null){
//                            if (response.isSuccessful()){
//                                JsonObject object = response.body().getAsJsonObject();
//                                int status =object.get("status").getAsInt();
//                                Log.i("sfdvxc", "status: " +status );
//                                if (status==1){
//                                    myservicelist=new ArrayList<>();
//                                    GetServiceResponse authentication = new Gson().fromJson(object,GetServiceResponse.class);
//                                    myservicelist.addAll(authentication.getResult());
//
//                                    mAdapter = new ServicesListBillingAdapter(getActivity(), new ServicesListBillingAdapter.SelectedItem() {
//                                        @Override
//                                        public void deleteItem(int position) {
//
//                                            updateSelectedinvoiceItems(position);
//
//                                        }
//
//                                        @Override
//                                        public void updateItem(int Position) {
//
//                                        }
//                                    });
//
//                                    mAdapter.setMyservicelist(myservicelist);
////                        binding.recyclerServicesList.setHasFixedSize(true);
//                                    binding.rvServices.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                                    binding.rvServices.setAdapter(mAdapter);
//                                    mAdapter.notifyDataSetChanged();
//
//                                }else {
//
//                                }
//                            }
//                        }
//
//                        Log.i("sfdvxc", "onResponse: " + response.body());
//                        Log.i("sfdvxc", "onResponse: " + response.toString());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//                        binding.loaderLayout.loader.setVisibility(View.GONE);
//                        Log.i("sfdvxc", "onFailure: " + t.getMessage());
//                    }
//                });
//    }


//    public void myserviceresponse() {
//        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
//        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
//
//        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
//                .create(ServiceRequest.class)
//                .getmyServices(model.getResult().getId())
//                .enqueue(new Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//
//                        binding.loaderLayout.loader.setVisibility(View.GONE);
//
//                        if (response!=null){
//                            if (response.isSuccessful()){
//                                JsonObject object = response.body().getAsJsonObject();
//                                int status =object.get("status").getAsInt();
//                                Log.i("sfdvxc", "status: " +status );
//                                if (status==1){
//                                    myservicelist=new ArrayList<>();
//                                    GetServiceResponse authentication = new Gson().fromJson(object,GetServiceResponse.class);
//                                    myservicelist.addAll(authentication.getResult());
//
//                                    mAdapter = new ServicesListBillingAdapter(getActivity(), new ServicesListBillingAdapter.SelectedItem() {
//                                        @Override
//                                        public void deleteItem(int position) {
//
//                                            updateSelectedinvoiceItems(position);
//
//                                        }
//
//                                        @Override
//                                        public void updateItem(int Position) {
//
//                                        }
//                                    });
//
//                                    mAdapter.setMyservicelist(myservicelist);
////                        binding.recyclerServicesList.setHasFixedSize(true);
//                                    binding.rvServices.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                                    binding.rvServices.setAdapter(mAdapter);
//                                    mAdapter.notifyDataSetChanged();
//
//                                }else {
//
//                                }
//                            }
//                        }
//
//                        Log.i("sfdvxc", "onResponse: " + response.body());
//                        Log.i("sfdvxc", "onResponse: " + response.toString());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//                        binding.loaderLayout.loader.setVisibility(View.GONE);
//                        Log.i("sfdvxc", "onFailure: " + t.getMessage());
//                    }
//                });
//    }
//
//
//    public void updateSelectedinvoiceItems(int position)
//    {
//
//        myAddedServiceList.add(myservicelist.get(position));
//        binding.rvServicesToBeBilled.setHasFixedSize(true);
//        binding.rvServicesToBeBilled.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        serviceTobeBilledAdapter = new ServiceTobeBilledAdapter(getActivity(), new ServicesListBillingAdapter.SelectedItem() {
//            @Override
//            public void deleteItem(int pos) {
//
//                myAddedServiceList.remove(pos);
//                serviceTobeBilledAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void updateItem(int Position) {
//
//            }
//        });
//
//        serviceTobeBilledAdapter.setList(myAddedServiceList);
//
//        binding.rvServicesToBeBilled.setAdapter(serviceTobeBilledAdapter);
//
//        serviceTobeBilledAdapter.notifyDataSetChanged();
//
//    }



}