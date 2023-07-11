package com.my.sadebprovider.act.activity;

import static com.my.sadebprovider.R.string.enter_pass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.user.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.user.UserReq;
import com.my.sadebprovider.databinding.ActivityAddClientBinding;
import com.my.sadebprovider.util.RealPathUtil;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClientAct extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int CAPTURE_IMAGE = 3;
    final int PERMISSION_ALL = 100;
    private String called_from;
    ActivityAddClientBinding binding;
    
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    private Bitmap bitmap;
    private Uri uri;
    private File file;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_add_client);
       init();
       
    }

    private void init() {

        binding.ccp.setCountryForPhoneCode(57);

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(AddClientAct.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(AddClientAct.this, PERMISSIONS, PERMISSION_ALL);
                } else {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE);

//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                }

            }
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnSave.setOnClickListener(v -> {
            if (validate()) {
                SetupUi();
            }
        });
    }

    private void SetupUi() {
        int selectedId = binding.rgGender.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        Log.i("fsvscc", "SetupUi: " + radioButton.getText().toString());

        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        String businessType = model.getResult().getBusinessType();

        MultipartBody.Part user_id = MultipartBody.Part.createFormData("provider_id", model.getResult().getId());
        MultipartBody.Part business_id = MultipartBody.Part.createFormData("business_id", model.getResult().getBusinessType());
        MultipartBody.Part user_name = MultipartBody.Part.createFormData("first_name", binding.etName.getText().toString());
        MultipartBody.Part email = MultipartBody.Part.createFormData("email", binding.etEmail.getText().toString());
        MultipartBody.Part gender = MultipartBody.Part.createFormData("gender", radioButton.getText().toString());
        MultipartBody.Part mobile = MultipartBody.Part.createFormData("phone", binding.etNo.getText().toString());
        MultipartBody.Part dob = MultipartBody.Part.createFormData("dob", "");
        MultipartBody.Part clientCode = MultipartBody.Part.createFormData("client_code", binding.etClientCode.getText().toString());
        MultipartBody.Part agreement = MultipartBody.Part.createFormData("agreement", binding.etAgreement.getText().toString());
        MultipartBody.Part discount = MultipartBody.Part.createFormData("discount", binding.etDiscount.getText().toString());
        MultipartBody.Part ruc = MultipartBody.Part.createFormData("ruc_no", binding.etRuc.getText().toString());
        MultipartBody.Part observations = MultipartBody.Part.createFormData("observations", binding.etObservation.getText().toString());
        MultipartBody.Part image = getPartFromFile("image", file);

        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(UserReq.class)
                .addClient(user_id,business_id,user_name,email,mobile,gender,dob,clientCode,agreement,discount,observations,ruc,image)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        Log.i("sfssfs", "onResponse: " + response.body());
                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                if (status == 1) {
                                    Toast.makeText(AddClientAct.this, getString(R.string.user_successfully_added), Toast.LENGTH_SHORT).show();
//                                    SharedPrefsManager.getInstance().setString("ccp_user",binding.ccp.getSelectedCountryCode());
                                    finish();
                                } else {
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
                        Log.i("sfssfs", "onResponse: " + t.getMessage());

                    }
                });

    }

    private boolean validate() {
        if (TextUtils.isEmpty(binding.etName.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Name, Snackbar.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(binding.etEmail.getText().toString().replace(" ", ""))) {
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
        } else if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
            return false;
        }  if (binding.ccp.getSelectedCountryCode().equals("57")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
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
        }else if (TextUtils.isEmpty(binding.etRuc.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_ruc, Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(binding.etClientCode.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_client_code, Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(binding.etAgreement.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_agreement, Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(binding.etAgreement.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_discount, Snackbar.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(binding.etObservation.getText().toString().replace(" ", ""))) {
            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Observation, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.i("xzvcbc", "onActivityResult: " + 32);
            if (requestCode == PICK_IMAGE) {
                Log.i("xzvcbc", "onActivityResult: " + 327);
                Uri imageUri = data.getData();
                String s = RealPathUtil.getRealPath(this, imageUri);
                file = new File(s);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binding.ivProfile.setImageBitmap(bitmap);
            }
        }

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

    private MultipartBody.Part getPartFromFile(String name, File file) {
        if (file == null)
            return null;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part profile_image = MultipartBody.Part.createFormData(name, file.getPath(), requestBody);
        Log.i("sxzfdsf", "getPartFromFile: " + profile_image);
        return profile_image;
    }

}