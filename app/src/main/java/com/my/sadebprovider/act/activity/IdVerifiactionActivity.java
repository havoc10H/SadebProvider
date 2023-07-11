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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.idverification.IdVerificationReq;
import com.my.sadebprovider.act.network.profile.UpdateProfile;
import com.my.sadebprovider.databinding.ActivityIdVerifiactionBinding;
import com.my.sadebprovider.util.RealPathUtil;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdVerifiactionActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    public static final int CAPTURE_IMAGE = 3;
    final int PERMISSION_ALL = 100;
    final int PERMISSION_2 = 10;
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
     private File file,file2;
    private ActivityIdVerifiactionBinding binding;
    private boolean isfrontimg=true;
    private Bitmap bitmap;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdVerifiactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        binding.ivImage1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isfrontimg=true;
                if (!hasPermissions(IdVerifiactionActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(IdVerifiactionActivity.this, PERMISSIONS, PERMISSION_ALL);
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

        binding.ivImage2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isfrontimg=false;
                if (!hasPermissions(IdVerifiactionActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(IdVerifiactionActivity.this, PERMISSIONS, PERMISSION_2);
                } else {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE);
//                     Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                }

            }
        });

        binding.btnSave.setOnClickListener(v -> {
            SetUpUi();
        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void SetUpUi() {
        ResponseAuthentication   model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        String provider_id = getIntent().getStringExtra("provider_id");
        MultipartBody.Part image1 = null,image2= null;
        MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", provider_id);
        image1 = getPartFromFile("verification_front_image", file);
        image2 = getPartFromFile("verification_end_image", file2);

        if (file!=null&file2!=null) {
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(IdVerificationReq.class)
                    .getIdVerification( image1,image2,user_id)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);

                            Log.i("sfssfs", "onResponse: "+response.body());
                            if (response != null) {
                                if (response.isSuccessful()) {
                                    JsonObject object = response.body().getAsJsonObject();
                                    int status = object.get("status").getAsInt();
                                    if (status == 1) {
                                        Toast.makeText(IdVerifiactionActivity.this, getString(R.string.admin_will_be_approved_soon), Toast.LENGTH_SHORT).show();
                                       Intent intent=new Intent(IdVerifiactionActivity.this,WaitingActivity.class);
                                        startActivity(intent);
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
                            Log.i("sfssfs", "onResponse: "+t.getMessage());

                        }
                    });
        }
        else {
            Snackbar.make(findViewById(android.R.id.content), R.string.please_select_image, Snackbar.LENGTH_SHORT).show();

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


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (isfrontimg==true) {
                    file = new File(s);
                    Log.i("scss", "tr: "+isfrontimg);
                    binding.ivImage1.setImageBitmap(bitmap);
                } else if (isfrontimg==false) {
                    Log.i("scss", "fal: "+isfrontimg);
                    file2 = new File(s);
                    binding.ivImage2.setImageBitmap(bitmap);

                }
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