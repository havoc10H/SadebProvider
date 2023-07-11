package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.my.sadebprovider.R;
import com.my.sadebprovider.databinding.ActivityInvoiceBinding;

public class InvoiceActivity extends AppCompatActivity {

    ActivityInvoiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_invoice);

        binding.ivBack.setOnClickListener(v -> finish());

    }
}