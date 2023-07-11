
package com.my.sadebprovider.act.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.password.ForgetPasswordResponse;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.profile.ProfileRequet;
import com.my.sadebprovider.databinding.ActivityForogotPasswordBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForogotPassword extends AppCompatActivity {

   private ActivityForogotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forogot_password);

        SetupUI();
        init();

    }
    private void init(){
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }
    private void SetupUI() {
         binding.loginID.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if ( validate()){
                     binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                     RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                             .create(ProfileRequet.class)
                             .getForgetPass(binding.etEmail.getText().toString())
                             .enqueue(new Callback<JsonElement>() {
                                 @Override
                                 public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                     binding.loaderLayout.loader.setVisibility(View.GONE);
                                     if (response.isSuccessful()){
                                         if(response.body()!=null){
                                             JsonObject object = response.body().getAsJsonObject();
                                             int status = object.get("status").getAsInt();
                                             if(status==1){
                                                  ForgetPasswordResponse authentication = new Gson().fromJson(object,ForgetPasswordResponse.class);
                                                 Log.i("dscbhs", "onResponse: "+object.toString());
                                                 Toast.makeText(ForogotPassword.this,String.format(getString(R.string.password_sent_to_email), authentication.getMessage()) /*"Password "+authentication.getMessage()+" sent to your registered email"*/, Toast.LENGTH_SHORT).show();
                                                 startActivity(new Intent(ForogotPassword.this, OtpVerificationActivity.class));
                                                 finish();
                                             }
                                             else {
                                                 ResponseAuthError authentication = new Gson().fromJson(object,ResponseAuthError.class);
                                                 Log.i("dscbhs", "onResponse: "+authentication);
                                                 Snackbar.make(findViewById(android.R.id.content),
                                                         authentication.getResult(),
                                                         Snackbar.LENGTH_SHORT).show();
                                             }
                                         }

                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<JsonElement> call, Throwable t) {
                                     Log.i("cxvdsgdgg", "onResponse:" +t.getMessage());
                                     binding.loaderLayout.loader.setVisibility(View.GONE);

                                 }
                             });
                 }
             }
         });
    }

    public boolean validate(){
        if (!Utils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmail.getText().toString().replace(" ", "")).matches()
        ) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_correct_email,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}