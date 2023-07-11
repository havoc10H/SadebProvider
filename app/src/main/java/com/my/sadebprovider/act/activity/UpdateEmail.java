package com.my.sadebprovider.act.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.profile.ProfileRequet;
import com.my.sadebprovider.databinding.ActivityUpdateEmailBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateEmail extends AppCompatActivity {

   private ActivityUpdateEmailBinding binding;
    private  ResponseAuthentication model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_update_email);

        SetupUI();

        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        binding.etEmaillogin.setText(model.getResult().getEmail());

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void SetupUI() {


        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                if (Utils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmaillogin.getText().toString().replace(" ", "")).matches()
                ){
                    Log.i("cscscs", "onClick: "+model.getResult().getId().toString());
                    Log.i("csvdsv", "onClick: "+binding.etEmaillogin.getText().toString());
                    RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                            .create(ProfileRequet.class)
                            .getProfileUpdate(model.getResult().getUserName(),model.getResult().getSurname(),binding.etEmaillogin.getText().toString(),
                                    model.getResult().getMobile(),model.getResult().getId(),
                                    model.getResult().getGender())
                            .enqueue(new Callback<ResponseAuthentication>() {
                                @Override
                                public void onResponse(Call<ResponseAuthentication> call, Response<ResponseAuthentication> response) {

                                    if (response.isSuccessful()){
                                        binding.loaderLayout.loader.setVisibility(View.GONE);
                                        SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider, response.body());
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseAuthentication> call, Throwable t) {
                                    binding.loaderLayout.loader.setVisibility(View.GONE);
                                    Log.i("dsgvdgvd", "onResponse: "+t.getMessage());

                                }
                            });
                }else {
                    Snackbar.make(findViewById(android.R.id.content),
                            R.string.text_register_correct_email,
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}