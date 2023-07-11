package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.material.snackbar.Snackbar;
import com.my.sadebprovider.R;
import com.my.sadebprovider.databinding.ActivityOtpVerificationBinding;

public class OtpVerificationActivity extends AppCompatActivity {

    private ActivityOtpVerificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
    }
    private void init(){
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        binding.loginID.setOnClickListener(v -> {
            startActivity(new Intent(this,Login.class));
            finish();
        });
    }
    private boolean validate() {
        if (TextUtils.isEmpty(binding.pvOtp.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.please_enter_otp), Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (binding.pvOtp.getText().toString().replace(" ", "").length() != 6) {
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.please_enter_4_digit_otp),
                    Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}