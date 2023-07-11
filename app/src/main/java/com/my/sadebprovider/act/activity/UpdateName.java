package com.my.sadebprovider.act.activity;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.profile.UpdateProfile;
import com.my.sadebprovider.databinding.ActivityUpdateNameBinding;
import com.my.sadebprovider.util.RealPathUtil;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateName extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int CAPTURE_IMAGE = 3;
    final int PERMISSION_ALL = 100;
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    private ActivityUpdateNameBinding binding;
    private ResponseAuthentication model;
    private Bitmap bitmap;
    private Uri uri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_name);


        SetupUI();
        init();

        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        if (model != null) {
            binding.etName.setText(model.getResult().getUserName());
            binding.etSurname.setText(model.getResult().getSurname());

            if(!model.getResult().getImage().equalsIgnoreCase(""))
            {
                Picasso.get().load(model.getResult().getImage()).placeholder(R.drawable.user_placeholder).into(binding.ivProfile);
            }

         }

        binding.ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void SetupUI() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MultipartBody.Part name = MultipartBody.Part.createFormData("user_name", binding.etName.getText().toString()+" "+binding.etLastName.getText().toString());
                MultipartBody.Part surname = MultipartBody.Part.createFormData("surname", binding.etSurname.getText().toString()+" "+binding.etLastName.getText().toString());
                MultipartBody.Part email = MultipartBody.Part.createFormData("email", model.getResult().getEmail());
                MultipartBody.Part no = MultipartBody.Part.createFormData("mobile", model.getResult().getMobile());
                MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", model.getResult().getId());
                MultipartBody.Part gender = MultipartBody.Part.createFormData("gender", model.getResult().getGender());
                MultipartBody.Part image = getPartFromFile("image", file);


                if (!TextUtils.isEmpty(binding.etName.getText().toString().replace(" ", ""))) {
                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                    RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                            .create(UpdateProfile.class)
                            .getEditUpdate(name,surname, email,
                                    no, user_id, gender, image)
                            .enqueue(new Callback<JsonElement>() {
                                @Override
                                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                                    if (response != null) {
                                        if (response.isSuccessful()) {
                                            JsonObject object = response.body().getAsJsonObject();
                                            int status = object.get("status").getAsInt();
                                            if (status == 1) {
                                                SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider, object);
                                                ResponseAuthentication authentication = new Gson().fromJson(object, ResponseAuthentication.class);
                                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                                SharedPrefsManager.getInstance().setObject(SharePrefrenceConstraint.provider, response.body());
                                                Log.i("dscscscs", "onResponse: " + authentication.toString());
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

                                }
                            });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), R.string.enter_Name, Snackbar.LENGTH_SHORT).show();

                }
            }
        });

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

    public void init() {
        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(UpdateName.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(UpdateName.this, PERMISSIONS, PERMISSION_ALL);
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