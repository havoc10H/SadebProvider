package com.my.sadebprovider;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.my.sadebprovider.Fragment.AccountFragment;
import com.my.sadebprovider.Fragment.BillingFragment;
import com.my.sadebprovider.Fragment.HomeFragment;
import com.my.sadebprovider.Fragment.NotificationFragment;
import com.my.sadebprovider.Fragment.ServiceFragment;
import com.my.sadebprovider.databinding.ActivityMainBinding;
import com.my.sadebprovider.util.Utils;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private ActivityMainBinding binding;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Utils.type type = (Utils.type) getIntent().getSerializableExtra("type");

        if (type == Utils.type.ID) {
            fragment = new ServiceFragment();
            loadFragment(fragment);
        } else {
            fragment = new HomeFragment();
            loadFragment(fragment);
        }

        binding.RRHome.setOnClickListener(view -> {
            fragment = new HomeFragment();
            loadFragment(fragment);
        });

        binding.RRProfile.setOnClickListener(view -> {
            fragment = new AccountFragment();
            loadFragment(fragment);
        });

        binding.RRSerVice.setOnClickListener(view -> {
            fragment = new ServiceFragment();
            loadFragment(fragment);
        });

        binding.RRNotification.setOnClickListener(view -> {
            fragment = new NotificationFragment();
            loadFragment(fragment);
        });

        binding.RRBilling.setOnClickListener(view -> {
            fragment = new BillingFragment();
            loadFragment(fragment);
        });

    }

    public void finishAct() {
        finish();
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                finish();
                return;
            } else {
                backToast = Toast.makeText(MainActivity.this, R.string.press_once_again_to_exit, Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }
}