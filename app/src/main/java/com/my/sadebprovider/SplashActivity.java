package com.my.sadebprovider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


import com.my.sadebprovider.act.activity.ChooseLanguageActivity;
import com.my.sadebprovider.act.activity.ChooseLoginActivity;
import com.my.sadebprovider.act.activity.IdVerifiactionActivity;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.util.Currency;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    private ImageView iv_Logo;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance(locale);
        String symbol = currency.getSymbol().replaceAll("\\w", "");


//
//
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//       Currency currency1= Currency.getInstance(new Locale("", telephonyManager.getNetworkCountryIso()));

        Log.d("TAG", "onCreate: Currency "+symbol+" Currency : "+currency);


        if(SharedPrefsManager.getInstance().getString("language").equals("")){
            SharedPrefsManager.getInstance().setString("language","en");
        }
        setLocale(SharedPrefsManager.getInstance().getString("language"));


        finds();
    }

    public static String getCurrencyCode(String countryCode) {
        return Currency.getInstance(new Locale("", countryCode)).getCurrencyCode();
    }

    public static String getCurrencySymbol(String countryCode) {
        return Currency.getInstance(new Locale("", countryCode)).getSymbol();
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

    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void finds() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
                  if (model!=null){
                     Log.i("sfsfdd", "run: "+12);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else {
                     Log.i("sfsfdd", "run: "+12342);
                     Intent i = new Intent(SplashActivity.this, ChooseLanguageActivity.class);
                      i.putExtra("from","splash");

                      startActivity(i);
                }
                finish();
            }
        }, 3000);
    }
}