package com.my.sadebprovider.util;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class BaseClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsManager.initialize(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //to disable dark mode
    }
}
