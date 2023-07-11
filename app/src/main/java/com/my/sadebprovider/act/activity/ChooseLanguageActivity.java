package com.my.sadebprovider.act.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.R;
import com.my.sadebprovider.databinding.ActivityChooseLanguageBinding;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.Locale;

public class ChooseLanguageActivity extends AppCompatActivity {

    ActivityChooseLanguageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose_language);
        binding.txtSingIn.setOnClickListener(v -> {
            setLocale("en");
        });
        binding.txtSingup.setOnClickListener(v -> {
            setLocale("es");
        });
    }

    public void setLocale(String lang) {
        // Create a new Locale object
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        // Create a new configuration object
        Configuration config = new Configuration();
        // Set the locale of the new configuration
        config.locale = locale;
        // Update the configuration of the Accplication context
        getResources().updateConfiguration(
                config,
                getResources().getDisplayMetrics()
        );
        SharedPrefsManager.getInstance().setString("language",lang);
        if(getIntent().getStringExtra("from").equals("Account")){
            Intent i = new Intent(ChooseLanguageActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }else {
            Intent i = new Intent(ChooseLanguageActivity.this, ChooseLoginActivity.class);
            startActivity(i);
            finish();
        }

    }
}