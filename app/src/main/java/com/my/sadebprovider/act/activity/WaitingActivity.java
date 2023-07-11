package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.my.sadebprovider.databinding.ActivityWaitingBinding;
import com.my.sadebprovider.util.Utils;

public class WaitingActivity extends AppCompatActivity {

    private ActivityWaitingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWaitingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }
    private void init(){
        binding.loginID.setOnClickListener(v -> {
             Intent intent=new Intent(this,Login.class);
            intent.putExtra("type", Utils.type.ID);
            startActivity(intent);
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }


}