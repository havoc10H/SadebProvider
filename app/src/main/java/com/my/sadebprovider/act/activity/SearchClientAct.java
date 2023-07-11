package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.my.sadebprovider.Fragment.HomeFragment;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;

import com.my.sadebprovider.act.model.user.SuccessResClients;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.category.CategoryRequest;
import com.my.sadebprovider.adapter.ClientsListAdapter;
import com.my.sadebprovider.adapter.HomeCompletedRecyclerViewAdapter;
import com.my.sadebprovider.databinding.ActivitySearchClientBinding;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchClientAct extends AppCompatActivity {

    private ClientsListAdapter mAdapter;
    private List<SuccessResClients.Result> list = new ArrayList<>();
    ActivitySearchClientBinding binding;
    private ResponseAuthentication model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search_client);

        model = SharedPrefsManager.getInstance().getObject(NetworkConstraint.type, ResponseAuthentication.class);

        binding.ivAdd.setOnClickListener(v ->
                {
                    startActivity(new Intent(SearchClientAct.this,AddClientAct.class));
                }
                );
        setAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        successResClients();
    }

    private void setAdapter() {
        mAdapter = new ClientsListAdapter(SearchClientAct.this);
        mAdapter.setList(list);
        binding.rvClient.setLayoutManager(new LinearLayoutManager(SearchClientAct.this));
        binding.rvClient.setAdapter(mAdapter);
    }

    private void successResClients() {
        String userId = model.getResult().getId();
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(CategoryRequest.class)
                .getClientData(userId)
                .enqueue(new Callback<SuccessResClients>() {
                    @Override
                    public void onResponse(Call<SuccessResClients> call, Response<SuccessResClients> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {
                            list.clear();
                            list.addAll(response.body().getResult());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessResClients> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                    }
                });
    }
}