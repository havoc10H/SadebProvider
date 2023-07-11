package com.my.sadebprovider.act.activity;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.databinding.ActivityAddTimeSlotBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTimeSlot extends AppCompatActivity {


    private final String[] startTime = {"", "", "", "", "", "", ""};
    private final String[] closeTime = {"", "", "", "", "", "", ""};
    private ActivityAddTimeSlotBinding binding;
    private String[] images;
    private String W_close = "";
    private int currentIndex;
    private NetworkConstraint.TYPE type;
//    private ResultItem item;
    private ResponseAuthentication model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_time_slot);
//        item = new Gson().fromJson(getIntent().getStringExtra("myservicelist"), ResultItem.class);
        type = (NetworkConstraint.TYPE) getIntent().getSerializableExtra("type");
        images = getIntent().getStringArrayExtra("images");
        currentIndex = getIntent().getIntExtra("currentIndex", 0);
        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });

        setdata(model);

        setUpCloseDayListeners();
        setUpTimeListeners();

        binding.tvNext.setOnClickListener(v -> {
            if (validate()){
                SetUpUi();
            }
        });
    }

    private void SetUpUi() {
        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .getAddTimeSlot(model.getResult().getId(),W_close,startTime[0],startTime[1],startTime[2],startTime[3]
                ,startTime[4],startTime[5],startTime[6],closeTime[0],
                        closeTime[1],closeTime[2]
                ,closeTime[3],closeTime[4],closeTime[5],closeTime[6])
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response!=null){
                            if (response.isSuccessful()){
                                JsonObject object =response.body().getAsJsonObject();
                                int status=object.get("status").getAsInt();
                                if (status==1){
                                    Toast.makeText(AddTimeSlot.this, getString(R.string.time_slot_added_successfully), Toast.LENGTH_SHORT).show();
                                    SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider,object);
                                    if(getIntent().getStringExtra("from").equals("profile")){
                                     finish();
                                    }else {
                                        Intent intent = new Intent(AddTimeSlot.this, AddSaloonPhoto.class);
                                        intent.putExtra("type", NetworkConstraint.TYPE.ADD);
                                        startActivity(intent);

                                    }
                                }else {
                                    ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                    Snackbar.make(findViewById(android.R.id.content),
                                            authentication.getResult(),
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

    private void setdata(ResponseAuthentication item) {
//        if (type == NetworkConstraint.TYPE.EDIT) {

            closeTime[0] = (item.getResult().getClose_time_monday());//getCloseTimeMonday());
            closeTime[1] = (item.getResult().getClose_time_tuesday());//getCloseTimeTuesday());
            closeTime[2] = (item.getResult().getClose_time_wednesday());//getCloseTimeWednesday());
            closeTime[3] = (item.getResult().getClose_time_thursday());//getCloseTimeThursday());
            closeTime[4] = (item.getResult().getClose_time_friday());//getCloseTimeFriday());
            closeTime[5] = (item.getResult().getClose_time_saturday());//getCloseTimeSaturday());
            closeTime[6] = (item.getResult().getClose_time_sunday());//getCloseTimeSunday());
            binding.monCTime.setText(item.getResult().getClose_time_monday());//getCloseTimeMonday());
            binding.tuesCTime.setText(item.getResult().getClose_time_tuesday());//getCloseTimeTuesday());
        binding.wedCTime.setText(item.getResult().getClose_time_wednesday());//getCloseTimeTuesday());
        binding.thurCTime.setText(item.getResult().getClose_time_thursday());//getCloseTimeThursday());
            binding.friCTime.setText(item.getResult().getClose_time_friday());//getCloseTimeFriday());
            binding.satCTime.setText(item.getResult().getClose_time_saturday());//getCloseTimeSaturday());
            binding.sunCTime.setText(item.getResult().getClose_time_sunday());//getCloseTimeSunday());

            startTime[0] = (item.getResult().getOpen_time_monday());//getOpenTimeMonday());
            startTime[1] = (item.getResult().getOpen_time_tuesday());//.getOpenTimeTuesday());
            startTime[2] = (item.getResult().getOpen_time_wednesday());//.getOpenTimeWednesday());
            startTime[3] = (item.getResult().getOpen_time_thursday());//.getOpenTimeThursday());
            startTime[4] = (item.getResult().getOpen_time_friday());//.getOpenTimeFriday());
            startTime[5] = (item.getResult().getOpen_time_saturday());//.getOpenTimeSaturday());
            startTime[6] = (item.getResult().getOpen_time_sunday());//.getOpenTimeSunday());
            binding.mondayOTime.setText(item.getResult().getOpen_time_monday());//.getOpenTimeMonday());
            binding.tuesTime.setText(item.getResult().getOpen_time_tuesday());//.getOpenTimeTuesday());
            binding.wedTime.setText(item.getResult().getOpen_time_wednesday());//.getOpenTimeWednesday());
            binding.thurTime.setText(item.getResult().getOpen_time_thursday());//.getOpenTimeThursday());
            binding.friTime.setText(item.getResult().getOpen_time_friday());//.getOpenTimeFriday());
            binding.satTime.setText(item.getResult().getOpen_time_saturday());//.getOpenTimeSaturday());
            binding.satTime.setText(item.getResult().getOpen_time_sunday());//.getOpenTimeSunday());

            if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Monday")) {
                W_close = "Monday";
                binding.oMonday.setClickable(false);
                binding.oMonday.setAlpha(0.5f);

                binding.cMonday.setEnabled(false);
                binding.cMonday.setAlpha(0.5f);

                binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));

            } else if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Tuesday")) {
                W_close = "Tuesday";
                binding.oTuesday.setClickable(false);
                binding.oTuesday.setAlpha(0.5f);

                binding.cTuesday.setEnabled(false);
                binding.cTuesday.setAlpha(0.5f);

                binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            } else if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Wednesday")) {
                W_close = "Wednesday";
                binding.oWednesday.setClickable(false);
                binding.oWednesday.setAlpha(0.5f);

                binding.cWednesday.setEnabled(false);
                binding.cWednesday.setAlpha(0.5f);

                binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            } else if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Thursday")) {
                W_close = "Thursday";
                binding.oThursday.setClickable(false);
                binding.oThursday.setAlpha(0.5f);

                binding.cThursday.setEnabled(false);
                binding.cThursday.setAlpha(0.5f);

                binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            } else if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Friday")) {
                W_close = "Friday";
                binding.oFriday.setClickable(false);
                binding.oFriday.setAlpha(0.5f);

                binding.cFriday.setEnabled(false);
                binding.cFriday.setAlpha(0.5f);

                binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            } else if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Saturday")) {
                W_close = "Saturday";
                binding.oSaturday.setClickable(false);
                binding.oSaturday.setAlpha(0.5f);

                binding.cSaturday.setEnabled(false);
                binding.cSaturday.setAlpha(0.5f);

                binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            } else if (item.getResult().getWeekly_close()./*getWeeklyClose().*/equals("Sunday")) {
                W_close = "Sunday";
                binding.oSunday.setClickable(false);
                binding.oSunday.setAlpha(0.5f);

                binding.cSunday.setEnabled(false);
                binding.cSunday.setAlpha(0.5f);

                binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            }


//        }
    }

    private Boolean validate() {
        for (int i = 0; i < startTime.length; i++) {


            if (W_close.equals("Monday") && i == 0
                    || W_close.equals("Tuesday") && i == 1 ||
                    W_close.equals("Wednesday") && i == 2 ||
                    W_close.equals("Thursday") && i == 3 ||
                    W_close.equals("Friday") && i == 4 ||
                    W_close.equals("Saturday") && i == 5 ||
                    W_close.equals("Sunday") && i == 6) continue;



            if (startTime[i].equals("")) {
                switch (i) {
                    case 0: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.monday_opening,
                                LENGTH_SHORT).show();
                        return false;
                    }
                    case 1: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_opening_tuesday,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 2: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_opening_Wednesday,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 3: {
                        Snackbar.make(findViewById(android.R.id.content), R.string.text_opening_Thursday, LENGTH_SHORT).show();
                        return false;
                    }


                    case 4: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_Opening_Friday,
                                LENGTH_SHORT).show();
                        return false;
                    }
                    case 5: {
                        Snackbar.make(findViewById(android.R.id.content), R.string.text_opening_Saturday, LENGTH_SHORT).show();
                        return false;
                    }
                    case 6: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_opening_Sunday,
                                LENGTH_SHORT).show();
                        return false;
                    }
                }
            }

        }

        for (int i = 0; i < closeTime.length; i++) {
            if (closeTime[i].equals("")) {

                if (W_close.equals("Monday") && i == 0
                        || W_close.equals("Tuesday") && i == 1 ||
                        W_close.equals("Wednesday") && i == 2 ||
                        W_close.equals("Thursday") && i == 3 ||
                        W_close.equals("Friday") && i == 4 ||
                        W_close.equals("Saturday") && i == 5 ||
                        W_close.equals("Sunday") && i == 6) continue;

                switch (i) {
                    case 0: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_close_Monday,
                                LENGTH_SHORT).show();
                        return false;
                    }
                    case 1: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_close_tues,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 2: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_close_Wednesday,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 3: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.test_close_Thursday,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 4: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.test_close_Friday,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 5: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_close_Saturday,
                                LENGTH_SHORT).show();
                        return false;
                    }

                    case 6: {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.text_close_Sunday,
                                LENGTH_SHORT).show();
                        return false;
                    }
                }
            }

        }

        if (W_close.equals("")) {
            Snackbar.make(findViewById(android.R.id.content), R.string.weeklyoff, LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setUpCloseDayListeners() {
        binding.wcMonday.setOnClickListener(v -> {
            W_close = "Monday";
            binding.oMonday.setClickable(false);
            binding.oMonday.setAlpha(0.5f);

            binding.cMonday.setEnabled(false);
            binding.cMonday.setAlpha(0.5f);

            binding.oTuesday.setClickable(true);
            binding.oTuesday.setAlpha(1f);

            binding.cTuesday.setEnabled(true);
            binding.cTuesday.setAlpha(1f);

            binding.oWednesday.setClickable(true);
            binding.oWednesday.setAlpha(1f);

            binding.cWednesday.setEnabled(true);
            binding.cWednesday.setAlpha(1f);

            binding.oThursday.setClickable(true);
            binding.oThursday.setAlpha(1f);

            binding.cThursday.setEnabled(true);
            binding.cThursday.setAlpha(1f);

            binding.oFriday.setClickable(true);
            binding.oFriday.setAlpha(1f);

            binding.cFriday.setEnabled(true);
            binding.cFriday.setAlpha(1f);

            binding.oSaturday.setClickable(true);
            binding.oSaturday.setAlpha(1f);

            binding.cSaturday.setEnabled(true);
            binding.cSaturday.setAlpha(1f);

            binding.oSunday.setClickable(true);
            binding.oSunday.setAlpha(1f);

            binding.cSunday.setEnabled(true);
            binding.cSunday.setAlpha(1f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
        });
        binding.wcTuesday.setOnClickListener(v -> {
            W_close = "Tuesday";
            binding.oMonday.setClickable(true);
            binding.oMonday.setAlpha(1f);

            binding.cMonday.setEnabled(true);
            binding.cMonday.setAlpha(1f);


            binding.oWednesday.setClickable(true);
            binding.oWednesday.setAlpha(1f);

            binding.cWednesday.setEnabled(true);
            binding.cWednesday.setAlpha(1f);

            binding.oThursday.setClickable(true);
            binding.oThursday.setAlpha(1f);

            binding.cThursday.setEnabled(true);
            binding.cThursday.setAlpha(1f);

            binding.oFriday.setClickable(true);
            binding.oFriday.setAlpha(1f);

            binding.cFriday.setEnabled(true);
            binding.cFriday.setAlpha(1f);

            binding.oSaturday.setClickable(true);
            binding.oSaturday.setAlpha(1f);

            binding.cSaturday.setEnabled(true);
            binding.cSaturday.setAlpha(1f);

            binding.oSunday.setClickable(true);
            binding.oSunday.setAlpha(1f);

            binding.cSunday.setEnabled(true);
            binding.cSunday.setAlpha(1f);

            binding.oTuesday.setClickable(false);
            binding.oTuesday.setAlpha(0.5f);

            binding.cTuesday.setEnabled(false);
            binding.cTuesday.setAlpha(0.5f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));

        });
        binding.wcWednesday.setOnClickListener(v -> {
            W_close = "Wednesday";

            binding.oMonday.setClickable(true);
            binding.oMonday.setAlpha(1f);

            binding.cMonday.setEnabled(true);
            binding.cMonday.setAlpha(1f);

            binding.oTuesday.setClickable(true);
            binding.oTuesday.setAlpha(1f);

            binding.cTuesday.setEnabled(true);
            binding.cTuesday.setAlpha(1f);


            binding.oThursday.setClickable(true);
            binding.oThursday.setAlpha(1f);

            binding.cThursday.setEnabled(true);
            binding.cThursday.setAlpha(1f);

            binding.oFriday.setClickable(true);
            binding.oFriday.setAlpha(1f);

            binding.cFriday.setEnabled(true);
            binding.cFriday.setAlpha(1f);

            binding.oSaturday.setClickable(true);
            binding.oSaturday.setAlpha(1f);

            binding.cSaturday.setEnabled(true);
            binding.cSaturday.setAlpha(1f);

            binding.oSunday.setClickable(true);
            binding.oSunday.setAlpha(1f);

            binding.cSunday.setEnabled(true);
            binding.cSunday.setAlpha(1f);


            binding.oWednesday.setClickable(false);
            binding.oWednesday.setAlpha(0.5f);

            binding.cWednesday.setEnabled(false);
            binding.cWednesday.setAlpha(0.5f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));

        });
        binding.wcThursday.setOnClickListener(v -> {
            W_close = "Thursday";

            binding.oMonday.setClickable(true);
            binding.oMonday.setAlpha(1f);

            binding.cMonday.setEnabled(true);
            binding.cMonday.setAlpha(1f);


            binding.oTuesday.setClickable(true);
            binding.oTuesday.setAlpha(1f);

            binding.cTuesday.setEnabled(true);
            binding.cTuesday.setAlpha(1f);

            binding.oWednesday.setClickable(true);
            binding.oWednesday.setAlpha(1f);

            binding.cWednesday.setEnabled(true);
            binding.cWednesday.setAlpha(1f);


            binding.oFriday.setClickable(true);
            binding.oFriday.setAlpha(1f);

            binding.cFriday.setEnabled(true);
            binding.cFriday.setAlpha(1f);

            binding.oSaturday.setClickable(true);
            binding.oSaturday.setAlpha(1f);

            binding.cSaturday.setEnabled(true);
            binding.cSaturday.setAlpha(1f);

            binding.oSunday.setClickable(true);
            binding.oSunday.setAlpha(1f);

            binding.cSunday.setEnabled(true);
            binding.cSunday.setAlpha(1f);

            binding.oThursday.setClickable(false);
            binding.oThursday.setAlpha(0.5f);

            binding.cThursday.setEnabled(false);
            binding.cThursday.setAlpha(0.5f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));

        });
        binding.wcFriday.setOnClickListener(v -> {
            W_close = "Friday";

            binding.oMonday.setClickable(true);
            binding.oMonday.setAlpha(1f);

            binding.cMonday.setEnabled(true);
            binding.cMonday.setAlpha(1f);

            binding.oTuesday.setClickable(true);
            binding.oTuesday.setAlpha(1f);

            binding.cTuesday.setEnabled(true);
            binding.cTuesday.setAlpha(1f);

            binding.oWednesday.setClickable(true);
            binding.oWednesday.setAlpha(1f);

            binding.cWednesday.setEnabled(true);
            binding.cWednesday.setAlpha(1f);

            binding.oThursday.setClickable(true);
            binding.oThursday.setAlpha(1f);

            binding.cThursday.setEnabled(true);
            binding.cThursday.setAlpha(1f);


            binding.oSaturday.setClickable(true);
            binding.oSaturday.setAlpha(1f);

            binding.cSaturday.setEnabled(true);
            binding.cSaturday.setAlpha(1f);

            binding.oSunday.setClickable(true);
            binding.oSunday.setAlpha(1f);

            binding.cSunday.setEnabled(true);
            binding.cSunday.setAlpha(1f);

            binding.oFriday.setClickable(false);
            binding.oFriday.setAlpha(0.5f);

            binding.cFriday.setEnabled(false);
            binding.cFriday.setAlpha(0.5f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));

        });
        binding.wcSatday.setOnClickListener(v -> {
            W_close = "Saturday";

            binding.oMonday.setClickable(true);
            binding.oMonday.setAlpha(1f);

            binding.cMonday.setEnabled(true);
            binding.cMonday.setAlpha(1f);

            binding.oTuesday.setClickable(true);
            binding.oTuesday.setAlpha(1f);

            binding.cTuesday.setEnabled(true);
            binding.cTuesday.setAlpha(1f);

            binding.oWednesday.setClickable(true);
            binding.oWednesday.setAlpha(1f);

            binding.cWednesday.setEnabled(true);
            binding.cWednesday.setAlpha(1f);

            binding.oThursday.setClickable(true);
            binding.oThursday.setAlpha(1f);

            binding.cThursday.setEnabled(true);
            binding.cThursday.setAlpha(1f);

            binding.oFriday.setClickable(true);
            binding.oFriday.setAlpha(1f);

            binding.cFriday.setEnabled(true);
            binding.cFriday.setAlpha(1f);


            binding.oSunday.setClickable(true);
            binding.oSunday.setAlpha(1f);

            binding.cSunday.setEnabled(true);
            binding.cSunday.setAlpha(1f);

            binding.oSaturday.setClickable(false);
            binding.oSaturday.setAlpha(0.5f);

            binding.cSaturday.setEnabled(false);
            binding.cSaturday.setAlpha(0.5f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));

        });
        binding.wcSunday.setOnClickListener(v -> {
            W_close = "Sunday";

            binding.oMonday.setClickable(true);
            binding.oMonday.setAlpha(1f);

            binding.cMonday.setEnabled(true);
            binding.cMonday.setAlpha(1f);

            binding.oTuesday.setClickable(true);
            binding.oTuesday.setAlpha(1f);

            binding.cTuesday.setEnabled(true);
            binding.cTuesday.setAlpha(1f);

            binding.oWednesday.setClickable(true);
            binding.oWednesday.setAlpha(1f);

            binding.cWednesday.setEnabled(true);
            binding.cWednesday.setAlpha(1f);

            binding.oThursday.setClickable(true);
            binding.oThursday.setAlpha(1f);

            binding.cThursday.setEnabled(true);
            binding.cThursday.setAlpha(1f);

            binding.oFriday.setClickable(true);
            binding.oFriday.setAlpha(1f);

            binding.cFriday.setEnabled(true);
            binding.cFriday.setAlpha(1f);

            binding.oSaturday.setClickable(true);
            binding.oSaturday.setAlpha(1f);

            binding.cSaturday.setEnabled(true);
            binding.cSaturday.setAlpha(1f);


            binding.oSunday.setClickable(false);
            binding.oSunday.setAlpha(0.5f);

            binding.cSunday.setEnabled(false);
            binding.cSunday.setAlpha(0.5f);

            binding.wcMonday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcTuesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcWednesday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcThursday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcFriday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSatday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gray));
            binding.wcSunday.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dark_gray));

        });
    }

    private void setUpTimeListeners() {
        binding.oMonday.setOnClickListener(v -> {
            timepick(binding.mondayOTime, 0, true);
        });
        binding.oTuesday.setOnClickListener(v -> {
            timepick(binding.tuesTime, 1, true);
        });
        binding.oWednesday.setOnClickListener(v -> {
            timepick(binding.wedTime, 2, true);
        });
        binding.oThursday.setOnClickListener(v -> {
            timepick(binding.thurTime, 3, true);
        });
        binding.oFriday.setOnClickListener(v -> {
            timepick(binding.friTime, 4, true);
        });
        binding.oSaturday.setOnClickListener(v -> {
            timepick(binding.satTime, 5, true);
        });
        binding.oSunday.setOnClickListener(v -> {
            timepick(binding.sunTime, 6, true);
        });
        binding.cMonday.setOnClickListener(v -> {
            timepick(binding.monCTime, 0, false);
        });
        binding.cTuesday.setOnClickListener(v -> {
            timepick(binding.tuesCTime, 1, false);
        });
        binding.cWednesday.setOnClickListener(v -> {
            timepick(binding.wedCTime, 2, false);
        });
        binding.cThursday.setOnClickListener(v -> {
            timepick(binding.thurCTime, 3, false);
        });
        binding.cFriday.setOnClickListener(v -> {
            timepick(binding.friCTime, 4, false);
        });
        binding.cSaturday.setOnClickListener(v -> {
            timepick(binding.satCTime, 5, false);
        });
        binding.cSunday.setOnClickListener(v -> {
            timepick(binding.sunCTime, 6, false);
        });
    }

    private void timepick(TextView textView, int index, boolean isStart) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .build();
        picker.addOnPositiveButtonClickListener(v -> {
            Calendar selectedDateTime = Calendar.getInstance();
            selectedDateTime.set(Calendar.HOUR_OF_DAY, picker.getHour());
            selectedDateTime.set(Calendar.MINUTE, picker.getMinute());
            if (isStart) {
                startTime[index] = new SimpleDateFormat("HH:mm").format(selectedDateTime.getTime());
                textView.setText(startTime[index]);
            } else {
                closeTime[index] = new SimpleDateFormat("HH:mm").format(selectedDateTime.getTime());
                textView.setText(closeTime[index]);
            }
        });
        picker.show(getSupportFragmentManager(), "sdfs");
    }
}