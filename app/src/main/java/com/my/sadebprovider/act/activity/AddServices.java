package com.my.sadebprovider.act.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.category.CategoryResponse;
import com.my.sadebprovider.act.model.category.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.category.AddCategory;
import com.my.sadebprovider.act.network.category.CategoryRequest;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.SpinnerCategoryAdapter;
import com.my.sadebprovider.databinding.ActivityAddServicesBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class AddServices extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    private final List<ResultItem> list = new ArrayList<>();
    private final Calendar selectedDateTime = Calendar.getInstance();
    private final File[] files = {null, null, null, null, null, null, null};
    private ActivityAddServicesBinding binding;
    private SpinnerCategoryAdapter adapter;
    private ResponseAuthentication model;
    private String W_close;
    private NetworkConstraint.TYPE type;
    private TextView textView;
    private MaterialTimePicker picker;
    private int currentIndex;
    private String[] images;
    private com.my.sadebprovider.act.model.service.ResultItem item;
    private String provider_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_services);
        type = (NetworkConstraint.TYPE) getIntent().getSerializableExtra("type");
        item = new Gson().fromJson(getIntent().getStringExtra("myservicelist"), com.my.sadebprovider.act.model.service.ResultItem.class);
        images = getIntent().getStringArrayExtra("images");
        currentIndex = getIntent().getIntExtra("currentIndex", 0);
        provider_user_id = getIntent().getStringExtra("provider_user_id");

        Log.i("szfdfvdvd", "provider_user_id: " + provider_user_id);
        if (currentIndex >= 8) {
            binding.noSelectedPhoto.setText("(" + "7" + "/7)" + "");
        } else
            binding.noSelectedPhoto.setText("(" + currentIndex + "/7)" + "");
        Log.i("dsffc", "onCreate: " + currentIndex);
        for (int i = 0; i < images.length; i++) {
            if (images[i] != null)
                files[i] = new File(images[i]);
        }

        SharedPrefsManager.getInstance().setInt("index", currentIndex);
        SetupUI();
        model = SharedPrefsManager.getInstance().getObject(NetworkConstraint.type, ResponseAuthentication.class);
        init();
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .build();

        CategoryResponse();
        binding.etServiceTime.setOnClickListener(v -> {
            picker.show(getSupportFragmentManager(), "tag");
        });

        picker.addOnPositiveButtonClickListener(v -> {
            selectedDateTime.set(Calendar.HOUR_OF_DAY, picker.getHour());
            selectedDateTime.set(Calendar.MINUTE, picker.getMinute());
            binding.etServiceTime.setText(new SimpleDateFormat("HH:mm").format(selectedDateTime.getTime()));
        });

        adapter = new SpinnerCategoryAdapter(this, list);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) binding.spinner.getSelectedView();
                textView = linearLayout.findViewById(R.id.text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvAddNewCategory.setOnClickListener(v -> showAddCategoryDialog());
    }

    private void showAddCategoryDialog() {

        DialogPlus dialogPlus =  DialogPlus.newDialog(AddServices.this)
                .setContentHolder(new ViewHolder(R.layout.category_dialog))
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setMargin(Utils.dpToPx(AddServices.this,20),Utils.dpToPx(AddServices.this,50),Utils.dpToPx(AddServices.this,20),Utils.dpToPx(AddServices.this,50))
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();

        View holderView = (LinearLayout)dialogPlus.getHolderView();

        TextView tv_Yes = holderView.findViewById(R.id.tv_Yes);
        TextView tv_No = holderView.findViewById(R.id.tv_No);
//        RatingBar rb_Main = holderView.findViewById(R.id.rb_Main);
        EditText ev_name = holderView.findViewById(R.id.ev_name);

//        if(!status){
//            rb_Main.setRating(Float.parseFloat(order.getRating()));
//            ev_Review.setText(order.getComment());
//        }

        tv_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPlus.dismiss();
            }
        });

        tv_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidEditTextInput(ev_name)){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
//                            ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, ResponseAuthentication.class);
                            addReview(ev_name.getText().toString());
                            dialogPlus.dismiss();
//                            Log.i("scsfcdcdc", "run: "+Provider_id);
//                            Log.i("scsfcdcdc", "run: "+model.getResult().getId());
//                            Log.i("scsfcdcdc", "run: "+model.getResult().getUserName());
//                            Log.i("scsfcdcdc", "run: "+ev_Review.getText().toString());
//                            Log.i("scsfcdcdc", "run: "+Float.toString(rb_Main.getRating()));

                        }
                    },500);
                }
            }
        });
        dialogPlus.show();
    }
    public static boolean isValidEditTextInput(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            editText.setError(editText.getContext().getString(R.string.field_required));
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public void addReview(String name){

        String businessType = model.getResult().getBusinessType();

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(AddCategory.class)
                .addCategory(name,businessType)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response.body() != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    Toast.makeText(AddServices.this, getString(R.string.category_added_successfully), Toast.LENGTH_SHORT).show();
                                    CategoryResponse();
//                                    ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.user, ResponseAuthentication.class);
//                                    AddReviewResponse authentication = new Gson().fromJson(object, AddReviewResponse.class);
//                                    ResultItem item=new ResultItem(authentication.getResult());
//                                    item.setProviderImage(provider_img);
//                                    item.setProviderName(provider_Name);
//                                    item.setUserName(model.getResult().getUserName());
//                                    item.setUserImage(model.getResult().getImage());
//                                    list.add(item);
//                                    adapter.notifyDataSetChanged();
//                                    Log.i("svfdvdcdc", "onResponse: "+authentication.toString());
                                } else {
                                    ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                    Log.i("dscbhs", "onResponse: " + authentication);
                                    Toast.makeText(AddServices.this, authentication.getResult(), Toast.LENGTH_SHORT).show();
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

    private void SetData(com.my.sadebprovider.act.model.service.ResultItem item) {
        if (type == NetworkConstraint.TYPE.EDIT) {
            if (currentIndex >= 8) {
                binding.noSelectedPhoto.setText("(" + "7" + "/7)" + "");
            } else
                binding.noSelectedPhoto.setText("(" + currentIndex + "/7)" + "");
            binding.etServiceName.setText(item.getServiceName());
            binding.etServicePrice.setText(item.getServicePrice());
            binding.etServiceOffer.setText(item.getServiceOffer());
            binding.spinner.setSelection(Integer.valueOf(item.getCategoryId()));
            binding.etServiceTime.setText(item.getServiceTime());
            binding.etEstimateTime.setText(item.getEstimate_time());
            binding.etCustomization.setText(item.getCustomization());
            binding.etDescription.setText(item.getDescription());
        }
    }

    private void CategoryResponse() {

        String language= SharedPrefsManager.getInstance().getString("language");

        String businessType = model.getResult().getBusinessType();

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(CategoryRequest.class)
                .getCategory(businessType,language)
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                        if (response != null) {
                            list.addAll(response.body().getResult());
                            adapter.notifyDataSetChanged();
                            SetData(item);
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryResponse> call, Throwable t) {

                    }
                });
    }

    private void SetupUI() {
        binding.tvNext.setOnClickListener(v -> {
            if (validate()) {
                binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                if (type == NetworkConstraint.TYPE.ADD) {
                    service();
                } else {
                    EditService();
                }
            }
        });

    }

    private void EditService() {
        MultipartBody.Part image1 = getPartFromFile("image1", files[0]);
        MultipartBody.Part image2 = getPartFromFile("image2", files[1]);
        MultipartBody.Part image3 = getPartFromFile("image3", files[2]);
        MultipartBody.Part image4 = getPartFromFile("image4", files[3]);
        MultipartBody.Part image5 = getPartFromFile("image5", files[4]);
        MultipartBody.Part image6 = getPartFromFile("image6", files[5]);
        MultipartBody.Part image7 = getPartFromFile("image7", files[6]);

        MultipartBody.Part service_id = MultipartBody.Part.createFormData("service_id", item.getId());
        MultipartBody.Part service_name = MultipartBody.Part.createFormData("service_name", binding.etServiceName.getText().toString());
        MultipartBody.Part service_price = MultipartBody.Part.createFormData("service_price", binding.etServicePrice.getText().toString());
        MultipartBody.Part service_offer = MultipartBody.Part.createFormData("service_offer", binding.etServiceOffer.getText().toString());
        MultipartBody.Part category_id = MultipartBody.Part.createFormData("category_id", list.get(binding.spinner.getSelectedItemPosition()).getId());
        MultipartBody.Part service_time = MultipartBody.Part.createFormData("service_time", binding.etServiceTime.getText().toString());
        MultipartBody.Part customization = MultipartBody.Part.createFormData("customization", binding.etCustomization.getText().toString());
        MultipartBody.Part description = MultipartBody.Part.createFormData("description", binding.etDescription.getText().toString());
        MultipartBody.Part provider_usr_id = MultipartBody.Part.createFormData("provider_user_id", provider_user_id);
        MultipartBody.Part lat = MultipartBody.Part.createFormData("lat", "22.7196° N");
        MultipartBody.Part longitude = MultipartBody.Part.createFormData("lon", "75.8577° E");
        MultipartBody.Part estimate_time = MultipartBody.Part.createFormData("estimate_time", binding.etEstimateTime.getText().toString());
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .UpdateService(service_id,
                        service_name,
                        service_price,
                        service_offer,
                        category_id,
                        service_time,
                        customization, description, image1, image2, image3, image4, image5,
                        image6, image7, lat, longitude, estimate_time, provider_usr_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("fsfvdgdvdev", "onResponse: " + response.body());
                        Log.i("fsfvdgdvdev", "onResponse: " + provider_user_id);
                        Log.i("fsfvdgdvdev", "onResponse: " + service_id);
                        Log.i("fsfvdgdvdev", "onResponse: " + model.getResult().getId());

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    Toast.makeText(AddServices.this, "Your service successfully updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddServices.this, MainActivity.class);
                                    intent.putExtra("type", Utils.type.ID);

                                    startActivity(intent);


                                } else {
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

                        Log.i("scxsvc", "onFailure: " + t.getMessage());
                    }
                });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etServiceName.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Service_Name, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etServicePrice.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Service_Price, Snackbar.LENGTH_SHORT).show();
            return false;
//        } else if (TextUtils.isEmpty(binding.etServiceOffer.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Service_Offer, Snackbar.LENGTH_SHORT).show();
//            return false;
        }
//        else if (TextUtils.isEmpty(binding.etCategory.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.choose_category, Snackbar.LENGTH_SHORT).show();
//            return false;
//        }

        else if (TextUtils.isEmpty(binding.etEstimateTime.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.please_enter_estimate_time, Snackbar.LENGTH_SHORT).show();
            return false;
//        } else if (TextUtils.isEmpty(binding.etServiceTime.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Service_time, Snackbar.LENGTH_SHORT).show();
//            return false;
//        } else if (TextUtils.isEmpty(binding.etCustomization.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Service_customization, Snackbar.LENGTH_SHORT).show();
//            return false;
        } else if (TextUtils.isEmpty(binding.etDescription.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Service_description, Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void service() {

//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part profile_image = MultipartBody.Part.createFormData("image1", file.getPath(), requestBody);


        MultipartBody.Part image1 = getPartFromFile("image1", files[0]);
        MultipartBody.Part image2 = getPartFromFile("image2", files[1]);
        MultipartBody.Part image3 = getPartFromFile("image3", files[2]);
        MultipartBody.Part image4 = getPartFromFile("image4", files[3]);
        MultipartBody.Part image5 = getPartFromFile("image5", files[4]);
        MultipartBody.Part image6 = getPartFromFile("image6", files[5]);
        MultipartBody.Part image7 = getPartFromFile("image7", files[6]);


        Log.i("sdfsfs", "service: " + model.getResult().getId());
        Log.i("sdfsfs", "service: " + list.get(binding.spinner.getSelectedItemPosition()).getId());
        MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", model.getResult().getId());
        MultipartBody.Part service_name = MultipartBody.Part.createFormData("service_name", binding.etServiceName.getText().toString());
        MultipartBody.Part service_price = MultipartBody.Part.createFormData("service_price", binding.etServicePrice.getText().toString());
        MultipartBody.Part service_offer = MultipartBody.Part.createFormData("service_offer", binding.etServiceOffer.getText().toString());
        MultipartBody.Part category_id = MultipartBody.Part.createFormData("category_id", list.get(binding.spinner.getSelectedItemPosition()).getId());
        MultipartBody.Part service_time = MultipartBody.Part.createFormData("service_time", binding.etServiceTime.getText().toString());
        MultipartBody.Part customization = MultipartBody.Part.createFormData("customization", binding.etCustomization.getText().toString());
        MultipartBody.Part description = MultipartBody.Part.createFormData("description", binding.etDescription.getText().toString());
        MultipartBody.Part provider_usr_id = MultipartBody.Part.createFormData("provider_user_id", provider_user_id);
        MultipartBody.Part lat = MultipartBody.Part.createFormData("lat", "22.7196° N");
        MultipartBody.Part longitude = MultipartBody.Part.createFormData("lon", "75.8577° E");
        MultipartBody.Part estimate_time = MultipartBody.Part.createFormData("estimate_time", binding.etEstimateTime.getText().toString());


        Log.i("svsvsvsv", "service: " + provider_usr_id);
        Log.i("svsvsvsv", "service: " + provider_user_id);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .addService(user_id,
                        service_name,
                        service_price,
                        service_offer,
                        category_id,
                        service_time,
                        customization, description, image1, image2, image3, image4, image5,
                        image6, image7, lat, longitude, estimate_time, provider_usr_id)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        Log.i("bcdkkxn", "out: " + response.body());
                        Log.i("bcdkkxn", "out: " + response.toString());

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                Log.i("bcdkkxn", "stats: " + status);

                                if (status == 1) {
                                    startActivity(new Intent(AddServices.this, MainActivity.class));
                                    Log.i("bcdkkxn", "onResponse: " + response.body());
                                    Log.i("bcdkkxn", "onResponse: " + response.toString());
                                    Log.i("bcdkkxn", "onResponse: " + user_id.toString());
                                    Log.i("bcdkkxn", "onResponse: " + category_id.toString());

                                } else {

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("scxsvc", "onFailure: " + t.getMessage());
                    }
                });
    }

    private MultipartBody.Part getPartFromFile(String name, File file) {
        if (file == null)
            return null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part profile_image = MultipartBody.Part.createFormData(name, file.getPath(), requestBody);
        return profile_image;
    }


    public void init() {
//        binding.imgCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//
//            }
//        });
        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

}



