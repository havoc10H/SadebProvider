package com.my.sadebprovider.act.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.user.AllUserListResponse;
import com.my.sadebprovider.act.model.user.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.user.UserReq;
import com.my.sadebprovider.adapter.AllUserListAdapter;
import com.my.sadebprovider.databinding.ActivityAllUsersListBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUsersListActivity extends AppCompatActivity {
    private ActivityAllUsersListBinding binding;
    private AllUserListAdapter adapter;
    private final List<ResultItem> list = new ArrayList<>();
    private Set<String> ids = new ArraySet<>();
    private NetworkConstraint.TYPE type;
    private com.my.sadebprovider.act.model.service.ResultItem item;
    private int currentIndex;
    private String[] images;
    private String finalS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUsersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {

        images = getIntent().getStringArrayExtra("images");
        item = new Gson().fromJson(getIntent().getStringExtra("myservicelist"), com.my.sadebprovider.act.model.service.ResultItem.class);
        type = (NetworkConstraint.TYPE) getIntent().getSerializableExtra("type");
        currentIndex = getIntent().getIntExtra("currentIndex", 0);
        getAllUser();
        SetUpUi();
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.ivAdd.setOnClickListener(v -> {
            Intent intent=new Intent(this, AddUserActivity.class);
            intent.putExtra("type", NetworkConstraint.TYPE.ADD);
            startActivity(intent);
        });

       binding.rvAllUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AllUserListAdapter(list, new AllUserListAdapter.SelectedPositon() {
            @Override
            public void selectedItem(int position, boolean isChecked) {
                String id = list.get(position).getId();
                if (isChecked)
                    ids.add(id);
                else {
                    ids.remove(id);
                }
                getIds();
            }

            @Override
            public void updateItem(int position) {
                Intent intent = new Intent(AllUsersListActivity.this, AddUserActivity.class);
                intent.putExtra("user", new Gson().toJson(list.get(position)));
                intent.putExtra("type", NetworkConstraint.TYPE.EDIT);
                startActivity(intent);
            }
            @Override
            public void deleteItem(int position) {
                getdeleteUser(position);
            }
        },item!=null?item.getProvider_user_id():null);
        binding.rvAllUsers.setAdapter(adapter);
    }

    private void SetUpUi() {

        if (item!=null){
            Log.i("csvssv", "SetUpUi: "+2324);
            Log.i("csvssv", "SetUpUi: "+item);
            Log.i("csvssv", "SetUpUi: "+item.getProvider_user_id());
            if (item.getProvider_user_id()!=null){
                finalS=item.getProvider_user_id();
            }
        }
        binding.tvNext.setOnClickListener(v -> {
            if (type== NetworkConstraint.TYPE.ADD){
                if (finalS !=null){
                    Log.i("csvssv", "adad: "+finalS);
                    Log.i("csvssv", "adad: "+type);

                    binding.tvNext.setAlpha(1f);
                    binding.tvNext.setEnabled(true);
                    Intent intent =new Intent(AllUsersListActivity.this,AddServices.class);
                    intent.putExtra("type", NetworkConstraint.TYPE.ADD);
                    intent.putExtra("provider_user_id", finalS);
                    intent.putExtra("myservicelist", new Gson().toJson(item));
                    intent.putExtra("images", images);
                    intent.putExtra("currentIndex", currentIndex);
                    Log.i("sfsvsvsv", "Add: "+finalS);
                    startActivity(intent);
                }else {
                    Snackbar.make(findViewById(android.R.id.content),
                            getString(R.string.please_select_at_least_one_user),
                            Snackbar.LENGTH_SHORT).show();
                }

            }else {
                Log.i("csvssv", "SetUpUi: "+finalS);

                if (finalS !=null){
                    binding.tvNext.setAlpha(1f);
                    binding.tvNext.setEnabled(true);
                    Intent intent =new Intent(AllUsersListActivity.this,AddServices.class);
                    intent.putExtra("provider_user_id", finalS);
                    intent.putExtra("myservicelist", new Gson().toJson(item));
                    intent.putExtra("type", NetworkConstraint.TYPE.EDIT);
                    intent.putExtra("images", images);
                    intent.putExtra("currentIndex", currentIndex);
                    startActivity(intent);
                    Log.i("sfsvsvsv", "edit: "+finalS);

                }else {
                    Snackbar.make(findViewById(android.R.id.content),
                            getString(R.string.please_select_at_least_one_user),
                            Snackbar.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void getdeleteUser(int position) {
        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(UserReq.class)
                .getDeleteUser(list.get(position).getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("sfssfs", "onResponse: " + response.body());
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                     list.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(AllUsersListActivity.this, getString(R.string.user_removed_successfully), Toast.LENGTH_SHORT).show();

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
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("sfssfs", "onResponse: " + t.getMessage());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllUser();
    }

    private void getAllUser() {
        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(UserReq.class)
                .getAllUsers(model.getResult().getId())
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    AllUserListResponse authentication = new Gson().fromJson(object, AllUserListResponse.class);
                                    list.clear();
                                    list.addAll(authentication.getResult());
                                    adapter.notifyDataSetChanged();

                                  if (item!=null){
                                      if(item.getProvider_user_id()!=null){
                                          ids = new ArraySet<>(Arrays.asList(item.getProvider_user_id().split(",")));
                                      }
                                  }

                                    getIds();

                                    if (list.size()>0){
                                        binding.tvNoUserFound.setVisibility(View.GONE);
                                    }
                                } else {
                                    if (list.size()==0){
                                        binding.tvNoUserFound.setVisibility(View.VISIBLE);
                                        binding.tvNext.setAlpha(0.5f);
                                        binding.tvNext.setEnabled(false);
                                    }
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
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("sfssfs", "onResponse: " + t.getMessage());
                    }
                });
    }

    void getIds(){
        String s = "";
        for (String id : ids) {
            s = s +","+id;
        }
        if(s.length()>0){
            s = s.substring(1);
        }
        finalS = s;
        Log.i("jgjgjb", "ids: "+ids);
        Log.i("jgjgjb", "getIds: "+finalS);
        if (finalS==""){
            binding.tvNext.setAlpha(0.5f);
            binding.tvNext.setEnabled(false);
        }else {
            binding.tvNext.setAlpha(1f);
            binding.tvNext.setEnabled(true);
        }
    }

}