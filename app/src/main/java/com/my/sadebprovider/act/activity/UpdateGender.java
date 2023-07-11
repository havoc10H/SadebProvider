package com.my.sadebprovider.act.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.profile.ProfileRequet;
import com.my.sadebprovider.adapter.SpinnerGender;
import com.my.sadebprovider.databinding.ActivityUpdateGenderBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateGender extends AppCompatActivity {

   private ActivityUpdateGenderBinding binding;
    private SpinnerGender adapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_update_gender);

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

         setAdapter();
        SetupUI();


    }

    private void setAdapter() {
        String[] s = {"Male", "Female", "Other"};
        adapter = new SpinnerGender(this, s);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) binding.spinner.getSelectedView();
                  textView = linearLayout.findViewById(R.id.text);


                if (textView.getText().equals("Female")) {

                    binding.tvFemale.setTextColor(Color.parseColor("#0053B4"));
                    binding.tvOther.setTextColor(Color.parseColor("#9098B1"));
                    binding.tvMale.setTextColor(Color.parseColor("#9098B1"));

                } else if (textView.getText().equals("Male")) {
                    binding.tvFemale.setTextColor(Color.parseColor("#9098B1"));
                    binding.tvOther.setTextColor(Color.parseColor("#9098B1"));
                    binding.tvMale.setTextColor(Color.parseColor("#0053B4"));

                } else if (textView.getText().equals("Other")) {
                    binding.tvFemale.setTextColor(Color.parseColor("#9098B1"));
                    binding.tvOther.setTextColor(Color.parseColor("#0053B4"));
                    binding.tvMale.setTextColor(Color.parseColor("#9098B1"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void SetupUI() {
        ResponseAuthentication  model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);

        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                        .create(ProfileRequet.class)
                        .getProfileUpdate(model.getResult().getUserName(),model.getResult().getSurname(),model.getResult().getEmail(),
                               model.getResult().getMobile(),model.getResult().getId()
                                , textView.getText().toString())
                        .enqueue(new Callback<ResponseAuthentication>() {
                            @Override
                            public void onResponse(Call<ResponseAuthentication> call, Response<ResponseAuthentication> response) {
                                Log.i("sfsdfd", "onResponse: "+response.toString());
                                if (response.isSuccessful()){
                                    binding.loaderLayout.loader.setVisibility(View.GONE);
                                    SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider, response.body());
                                    ResponseAuthentication  model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);

                                    Log.i("zfdgdxv", "onResponse: "+response.body());
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseAuthentication> call, Throwable t) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);

                            }
                        });

            }
        });
    }


}