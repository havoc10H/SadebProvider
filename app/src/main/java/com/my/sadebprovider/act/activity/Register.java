package com.my.sadebprovider.act.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.businesscategory.BusinessType;
import com.my.sadebprovider.act.model.category.CategoryResponse;
import com.my.sadebprovider.act.model.category.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.authentication.RequestLAuthentication;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.category.CategoryRequest;
import com.my.sadebprovider.adapter.SpinnerBusinessTypeAdapter;
import com.my.sadebprovider.adapter.SpinnerCategoryAdapter;
import com.my.sadebprovider.databinding.ActivityRegisterBinding;
import com.my.sadebprovider.util.RealPathUtil;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.my.sadebprovider.R.string.enter_conf;
import static com.my.sadebprovider.R.string.enter_pass;

public class Register extends AppCompatActivity {

    private final List<BusinessType.Result> list = new ArrayList<>();
    private String date;
    private SpinnerBusinessTypeAdapter adapter;
    private ActivityRegisterBinding binding;
    private String called_from;
    public static final int PICK_IMAGE_MORE = 2;
    public static final int PICK_IMAGE = 1;
    final int PERMISSION_ALL = 100;
    String latitude="",longitude="";
    private Bitmap bitmap;
    private File file;
//  String register_id="";

final String[] PERMISSIONS = {
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =    DataBindingUtil.setContentView(this, R.layout.activity_register);
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
        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.tvSignIn.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
        });

        if (called_from != null && called_from.equalsIgnoreCase("add"))
            binding.ccp.registerPhoneNumberTextView(binding.etNo);
        binding.ccp.setCountryForPhoneCode(57);
        SetupUI();

        Places.initialize(getApplicationContext(), NetworkConstraint.GOOGLE_API_KEY);

        binding.etBusinessAddress.setFocusable(false);
        binding.etBusinessAddress.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .build(Register.this);
            startActivityForResult(intent, 100);
        });

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        binding.tvBusinessDate.setText(date);

        adapter = new SpinnerBusinessTypeAdapter(this, list);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) binding.spinner.getSelectedView();
//                textView = linearLayout.findViewById(R.id.text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(Register.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(Register.this, PERMISSIONS, PERMISSION_ALL);
                } else {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE);

                }

            }
        });
        CategoryResponse();
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void CategoryResponse() {

        String language= SharedPrefsManager.getInstance().getString("language");

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(CategoryRequest.class)
                .getBusinesType(language)
                .enqueue(new Callback<BusinessType>() {
                    @Override
                    public void onResponse(Call<BusinessType> call, Response<BusinessType> response) {
                        if (response != null) {
                            list.addAll(response.body().getResult());
                            adapter.notifyDataSetChanged();
//                            SetData(item);
                        }
                    }

                    @Override
                    public void onFailure(Call<BusinessType> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.etBusinessAddress.setText(place.getAddress());
                    LatLng latLng = place.getLatLng();
                    latitude = String.valueOf(latLng.latitude);
                    longitude = String.valueOf(latLng.longitude);
                }
                break;
            case PICK_IMAGE:
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmapNew  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    Bitmap bitmap_ = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());
                    Uri tempUri_ = getImageUri(this, bitmap_);
                    String s = RealPathUtil.getRealPath(this, tempUri_);//imageUri);
                    file = new File(s);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binding.ivProfile.setImageBitmap(bitmap);
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title_" + System.currentTimeMillis(), null);
        return Uri.parse(path);
    }
    public Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;

    }

    public void getFirebaseRegisterId() {
        try {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();
//                PrefManager.setString(Constant.REGISTER_ID, token);
//                register_id=token;
                SharedPrefsManager.getInstance().setString(SharePrefrenceConstraint.register_id,token);

                Log.d("TAG-token", token);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void SetupUI() {
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    getFirebaseRegisterId();

                    MultipartBody.Part user_name = MultipartBody.Part.createFormData("user_name",binding.etFullName.getText().toString());
                    MultipartBody.Part surname = MultipartBody.Part.createFormData("surname", binding.etSurName.getText().toString());
                    MultipartBody.Part password = MultipartBody.Part.createFormData("password", binding.etPassword.getText().toString());
                    MultipartBody.Part email = MultipartBody.Part.createFormData("email",  binding.etEmail.getText().toString());
                    MultipartBody.Part type = MultipartBody.Part.createFormData("type", NetworkConstraint.type);
                    MultipartBody.Part mobile = MultipartBody.Part.createFormData("mobile", binding.etNo.getText().toString());
                    MultipartBody.Part register_id = MultipartBody.Part.createFormData("register_id",  SharedPrefsManager.getInstance().getString(SharePrefrenceConstraint.register_id));
                    MultipartBody.Part description = MultipartBody.Part.createFormData("description",binding.etDescription.getText().toString());
                    MultipartBody.Part business_name = MultipartBody.Part.createFormData("business_name",   binding.etBusinessName.getText().toString());
                    MultipartBody.Part business_address = MultipartBody.Part.createFormData("business_address",   binding.etBusinessAddress.getText().toString());
                    MultipartBody.Part b_lat = MultipartBody.Part.createFormData("b_lat",latitude);
                    MultipartBody.Part b_lon =  MultipartBody.Part.createFormData("b_lon", longitude);
                    MultipartBody.Part business_cell_phone =  MultipartBody.Part.createFormData("business_cell_phone", binding.etNo.getText().toString());
                    MultipartBody.Part business_landline =  MultipartBody.Part.createFormData("business_landline", binding.etNoLl.getText().toString());
                    MultipartBody.Part business_profile_image =  getPartFromFile("business_profile_image", file);
                    MultipartBody.Part offer_home_delivery =  MultipartBody.Part.createFormData("offer_home_delivery",binding.offerHomeSwitch.isChecked()?"true":"false");
                    MultipartBody.Part myDate =  MultipartBody.Part.createFormData("registration_date",date);

                    MultipartBody.Part category_id = MultipartBody.Part.createFormData("business_type", list.get(binding.spinner.getSelectedItemPosition()).getId());

                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);

                    RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                            .create(RequestLAuthentication.class)
                            .updateBusiness(myDate,category_id,user_name,
                                    surname,
                                    password,
                                    email,
                                    type,
                                    mobile,
                                    register_id,
                                    description,
                                    business_name,
                                    business_address,
                                    b_lat,
                                    b_lon,
                                    business_cell_phone,
                                    business_landline,
                                    business_profile_image,
                                    offer_home_delivery)
                            .enqueue(new Callback<JsonElement>() {
                                @Override
                                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                    if (response.isSuccessful()) {
                                        binding.loaderLayout.loader.setVisibility(View.GONE);

                                        if (response.body() != null) {
                                            JsonObject object = response.body().getAsJsonObject();
                                            int status = object.get("status").getAsInt();
                                            if (status == 1) {
                                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                                SharedPrefsManager.getInstance().setString("ccp",binding.ccp.getSelectedCountryCode());

//                                                SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider, object);
                                                ResponseAuthentication authentication = new Gson().fromJson(object, ResponseAuthentication.class);
                                                Log.i("dscbhs", "onResponse: " + authentication);
                                                Intent intent=new Intent(Register.this, IdVerifiactionActivity.class);
                                                intent.putExtra("provider_id",authentication.getResult().getId());
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                                ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                                Log.i("dscbhs", "onResponse: " + authentication);
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

                                    Log.i("acsxgvdgf", "onFailure: " + t.getMessage());
                                }
                            });

//                    RetrofitClient.getClient(NetworkConstraint.BASE_URL)
//                            .create(RequestLAuthentication.class)
//                            .getSignUp(binding.etFullName.getText().toString(),
//                                    binding.etSurName.getText().toString(),
//                                    binding.etPassword.getText().toString(),
//                                    binding.etEmail.getText().toString(),
//                                    NetworkConstraint.type,
//                                    binding.etNo.getText().toString(),
//                                    SharedPrefsManager.getInstance().getString(SharePrefrenceConstraint.register_id),
//                                    binding.etDescription.getText().toString(),
//                                    binding.etBusinessName.getText().toString(),
//                                    binding.etBusinessAddress.getText().toString(),
//                                    latitude,
//                                    longitude,
//                                    binding.etNo.getText().toString(),
//                                    binding.etNoLl.getText().toString(),
//                                    binding.offerHomeSwitch.isChecked()?"true":"false")
//                            .enqueue(new Callback<JsonElement>() {
//                                @Override
//                                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                                    if (response.isSuccessful()) {
//                                        binding.loaderLayout.loader.setVisibility(View.GONE);
//
//                                        if (response.body() != null) {
//                                            JsonObject object = response.body().getAsJsonObject();
//                                            int status = object.get("status").getAsInt();
//                                            if (status == 1) {
//                                                binding.loaderLayout.loader.setVisibility(View.GONE);
//                                                SharedPrefsManager.getInstance().setString("ccp",binding.ccp.getSelectedCountryCode());
//
////                                                SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider, object);
//                                                ResponseAuthentication authentication = new Gson().fromJson(object, ResponseAuthentication.class);
//                                                Log.i("dscbhs", "onResponse: " + authentication);
//                                                Intent intent=new Intent(Register.this, IdVerifiactionActivity.class);
//                                                intent.putExtra("provider_id",authentication.getResult().getId());
//                                                startActivity(intent);
//                                                finish();
//                                            } else {
//                                                binding.loaderLayout.loader.setVisibility(View.GONE);
//                                                ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
//                                                Log.i("dscbhs", "onResponse: " + authentication);
//                                                Snackbar.make(findViewById(android.R.id.content),
//                                                        authentication.getResult(),
//                                                        Snackbar.LENGTH_SHORT).show();
//                                            }
//                                        }
//
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<JsonElement> call, Throwable t) {
//                                    binding.loaderLayout.loader.setVisibility(View.GONE);
//
//
//                                    Log.i("acsxgvdgf", "onFailure: " + t.getMessage());
//                                }
//                            });

                }
            }
        });

    }

    private MultipartBody.Part getPartFromFile(String name, File file) {
        if (file == null)
            return null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part profile_image = MultipartBody.Part.createFormData(name, file.getPath(), requestBody);
        Log.i("sxzfdsf", "getPartFromFile: " + profile_image);
        return profile_image;
    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etFullName.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Name, Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(binding.etSurName.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_surname, Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(binding.etEmail.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.enter_email,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (!Utils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmail.getText().toString().replace(" ", "")).matches()
        ) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_correct_email,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(binding.etPassword.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content),
                   enter_pass,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (binding.etPassword.getText().toString().replace(" ", "").length() < 6) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_password,
                    Snackbar.LENGTH_SHORT).show();
            return false;

        }
        else if (TextUtils.isEmpty(binding.etConfirmPassword.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content),
                    enter_conf,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (!binding.etConfirmPassword.getText().toString().equals(binding.etPassword.getText().toString())) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_not_matched,
                    Snackbar.LENGTH_SHORT).show();
            return false;

        } else if (binding.etConfirmPassword.getText().toString().replace(" ", "").length() < 6) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_password,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        } else  if (TextUtils.isEmpty(binding.etBusinessName.getText().toString().replace(" ", ""))) {
                Snackbar.make(findViewById(android.R.id.content), R.string.enter_business_name, Snackbar.LENGTH_SHORT).show();
                return false;

        } else  if (TextUtils.isEmpty(binding.etBusinessAddress.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_business_address, Snackbar.LENGTH_SHORT).show();
            return false;

        } else if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
            return false;
        }   else if (binding.ccp.getSelectedCountryCode().equals("57")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_right_no,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }

        else if (binding.ccp.getSelectedCountryCode().equals("507")&&binding.etNo.getText().toString().replace(" ", "").length()!=8) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_right_no,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.ccp.getSelectedCountryCode().equals("56")&&binding.etNo.getText().toString().replace(" ", "").length()!=11) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_right_no,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.ccp.getSelectedCountryCode().equals("1")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_right_no,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (binding.ccp.getSelectedCountryCode().equals("91")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.text_register_right_no,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else if (TextUtils.isEmpty(binding.etDescription.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.please_enter_description,
                    Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (!binding.cbBtn.isChecked()) {
            Snackbar.make(binding.getRoot(),
                    R.string.text_register_term,
                    Snackbar.LENGTH_SHORT).show();
            return false;

        } else {
            return true;
        }
    }

}