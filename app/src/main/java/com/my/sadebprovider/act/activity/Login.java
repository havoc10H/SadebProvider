package com.my.sadebprovider.act.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.authentication.RequestLAuthentication;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.databinding.ActivityLoginBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.my.sadebprovider.R.string.enter_email;
import static com.my.sadebprovider.R.string.enter_pass;


public class Login extends AppCompatActivity {

   private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        try {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();
                SharedPrefsManager.getInstance().setString(SharePrefrenceConstraint.register_id,token);

                Log.d("TAG-token", token);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        SetupUI();
    }

    private void SetupUI() {
      Utils.type  type= (Utils.type) getIntent().getSerializableExtra("type");
        binding.buttonFotgotAction.setOnClickListener(v -> {
            startActivity(new Intent(this, ForogotPassword.class));
        });

        binding.loginID.setOnClickListener(v -> {
            if (validate()){
                 binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                        .create(RequestLAuthentication.class)
                        .getlogIn(binding.etEmail.getText().toString(),
                                binding.etPassword.getText().toString(),
                                NetworkConstraint.type,
                                SharedPrefsManager.getInstance().getString(SharePrefrenceConstraint.register_id))
                        .enqueue(new Callback<JsonElement>() {
                            @Override
                            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                if (response.isSuccessful()){
                                    if(response.body()!=null){
                                        binding.loaderLayout.loader.setVisibility(View.GONE);

                                        JsonObject object = response.body().getAsJsonObject();
                                        int status = object.get("status").getAsInt();
                                        if(status==1){
                                            binding.loaderLayout.loader.setVisibility(View.GONE);
                                            SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider,object);
                                            Log.i("xamc", "onResponse: "+object.toString());
                                            ResponseAuthentication authentication = new Gson().fromJson(object,ResponseAuthentication.class);


                                            Log.i("sffsfsf", "onResponse: "+type);
//                                            if (type== Utils.type.ID){
                                            if(SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getWeekly_close().equals("")//||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_sunday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_monday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_tuesday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_wednesday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_thursday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_friday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getOpen_time_saturday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_sunday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_monday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_tuesday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_wednesday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_thursday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_friday().equals("")||
//                                                    SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class).getResult().getClose_time_saturday().equals("")
                                            ){
                                                startActivity(new Intent(Login.this, AddTimeSlot.class).putExtra("from",""));

                                            }else {
                                                startActivity(new Intent(Login.this, MainActivity.class));
                                            }
                                            finish();

                                        }
                                        else {
                                            binding.loaderLayout.loader.setVisibility(View.GONE);
//                                            ResponseAuthError authentication = new Gson().fromJson(object,ResponseAuthError.class);
                                             Snackbar.make(findViewById(android.R.id.content),
                                                     object.get("message").toString(),//authentication.getResult(),
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
//            else {
//                Snackbar.make(findViewById(android.R.id.content),
//                        R.string.exist,
//                        Snackbar.LENGTH_SHORT).show();
//            }
        });

        binding.reg.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
        });
    }

    private boolean validate()
   {
          if (TextUtils.isEmpty(binding.etEmail.getText().toString().replace(" ", ""))) {
       Snackbar.make(findViewById(android.R.id.content),
               enter_email,
               Snackbar.LENGTH_SHORT).show();
       return false;
   }
          else if (!Utils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmail.getText().toString().replace(" ", "")).matches()
        ) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_correct_email,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
       if (TextUtils.isEmpty(binding.etPassword.getText().toString().replace(" ", ""))) {
           Snackbar.make(findViewById(android.R.id.content),
                   enter_pass,
                   Snackbar.LENGTH_SHORT).show();
           return false;
       }else if (binding.etPassword.getText().toString().replace(" ", "").length() < 5) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_password,
                    Snackbar.LENGTH_SHORT).show();
            return false;

        }
        else {
            return true;
        }
    }

}