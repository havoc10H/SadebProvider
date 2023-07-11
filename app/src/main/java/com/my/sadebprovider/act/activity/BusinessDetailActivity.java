package com.my.sadebprovider.act.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthError;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.businesscategory.BusinessType;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.category.CategoryRequest;
import com.my.sadebprovider.act.network.profile.UpdateProfile;
import com.my.sadebprovider.adapter.SpinnerBusinessTypeAdapter;
import com.my.sadebprovider.databinding.ActivityBusinessDetailBinding;
import com.my.sadebprovider.util.RealPathUtil;
import com.my.sadebprovider.util.ScalingUtilities;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.my.sadebprovider.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.my.sadebprovider.R.string.enter_conf;
import static com.my.sadebprovider.R.string.enter_pass;

public class BusinessDetailActivity extends AppCompatActivity {

    private final List<BusinessType.Result> list = new ArrayList<>();

    ActivityBusinessDetailBinding binding;
    private ResponseAuthentication model;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_IMAGE_MORE = 2;
    private final File[] files = {null, null, null, null, null, null, null, null, null};
    private SpinnerBusinessTypeAdapter adapter;
    private final String[] strings = {null, null, null, null, null, null, null, null, null};
    final int PERMISSION_ALL = 100;
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    String latitude = "", longitude = "";
    private Bitmap bitmap;
    private File file;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_business_detail);

        binding.ivBack.setOnClickListener(v -> finish());

        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        binding.etBusinessName.setText(model.getResult().getBusiness_name());
        binding.etBusinessAddress.setText(model.getResult().getBusiness_address());
        latitude = model.getResult().getB_lat();
        longitude = model.getResult().getB_lon();
        binding.etNo.setText(model.getResult().getBusiness_cell_phone());
        binding.etNoLl.setText(model.getResult().getBusiness_landline());
        if (model.getResult().getOffer_home_delivery().equals("true")) {
            binding.offerHomeSwitch.setChecked(true);
        } else {
            binding.offerHomeSwitch.setChecked(false);
        }
        binding.etBusinessOpenDate.setText(model.getResult().getOpen_date());
        binding.etBusinessCloseDate.setText(model.getResult().getClose_date());
        binding.etDescription.setText(model.getResult().getDescription());

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

        binding.ccp.setCountryForPhoneCode(57);

        if (!model.getResult().getBusiness_profile_image().equals("")) {
            Picasso.get().load(model.getResult().getBusiness_profile_image()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(binding.ivProfile);
        }

        int Index = SharedPrefsManager.getInstance().getInt("index");

        binding.noSelectedPhoto.setText("(" + Index + "/7)");
        if (!model.getResult().getImage1().equals("")) {
            Picasso.get().load(model.getResult().getImage1()).placeholder(R.drawable.place_holder).into(binding.imgLogoOne);
        }
        if (!model.getResult().getImage2().equals("")) {
            Picasso.get().load(model.getResult().getImage2()).placeholder(R.drawable.place_holder).into(binding.imgLogoTwo);
        }
        if (!model.getResult().getImage3().equals("")) {
            Picasso.get().load(model.getResult().getImage3()).placeholder(R.drawable.place_holder).into(binding.imgLogoThree);
        }
        if (!model.getResult().getImage4().equals("")) {
            Picasso.get().load(model.getResult().getImage4()).placeholder(R.drawable.place_holder).into(binding.imgLogoFour);
        }
        if (!model.getResult().getImage5().equals("")) {
            Picasso.get().load(model.getResult().getImage5()).placeholder(R.drawable.place_holder).into(binding.imgLogoFive);
        }
        if (!model.getResult().getImage6().equals("")) {
            Picasso.get().load(model.getResult().getImage6()).placeholder(R.drawable.place_holder).into(binding.imgLogoSix);
        }
        if (!model.getResult().getImage7().equals("")) {
            Picasso.get().load(model.getResult().getImage7()).placeholder(R.drawable.place_holder).into(binding.imgLogoSeven);
        }
        if (!model.getResult().getImage8().equals("")) {
            Picasso.get().load(model.getResult().getImage8()).placeholder(R.drawable.place_holder).into(binding.imgLogoEight);
        }
        if (!model.getResult().getImage9().equals("")) {
            Picasso.get().load(model.getResult().getImage9()).placeholder(R.drawable.place_holder).into(binding.imgLogoNine);
        }

        Places.initialize(getApplicationContext(), NetworkConstraint.GOOGLE_API_KEY);

        binding.etBusinessAddress.setFocusable(false);
        binding.etBusinessAddress.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .build(BusinessDetailActivity.this);
            startActivityForResult(intent, 100);
        });

        binding.etBusinessOpenDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BusinessDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.etBusinessOpenDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        binding.etBusinessCloseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BusinessDetailActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                binding.etBusinessCloseDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
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
        SetupUI();
        init();
        CategoryResponse();
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

                            setSpinnerPosition();

//                            SetData(item);
                        }
                    }

                    @Override
                    public void onFailure(Call<BusinessType> call, Throwable t) {

                    }
                });
    }



    public void setSpinnerPosition()
    {
        int i=0;
        for(BusinessType.Result result:list)
        {
            if(result.getId().equalsIgnoreCase(model.getResult().getBusinessType()))
            {
                break;
            }
            i++;
        }

        binding.spinner.setSelection(i);

    }


    public void init() {
        binding.imgCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_MORE);
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                }
            }
        });

        binding.imgLogoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 11);
                }
            }
        });

        binding.imgLogoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 12);
                }
            }
        });

        binding.imgLogoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 13);
                }
            }
        });

        binding.imgLogoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 14);
                }
            }
        });

        binding.imgLogoFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 15);
                }
            }
        });

        binding.imgLogoSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 16);
                }
            }
        });

        binding.imgLogoSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 17);
                }
            }
        });

        binding.imgLogoEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 18);
                }
            }
        });

        binding.imgLogoNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions(BusinessDetailActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BusinessDetailActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 19);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissionsList, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissionsList, grantResults);
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (grantResults.length > 0) {
                    boolean b = true;
                    for (int per : grantResults) {
                        if (per == PackageManager.PERMISSION_DENIED) {
                            b = false;
                        }
                    }
//                    if (b) {

//                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(pickPhoto, PICK_IMAGE);

////                        Intent intent = new Intent();
////                        intent.setType("image/*");
////                        intent.setAction(Intent.ACTION_GET_CONTENT);
////                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
//
//                    }

                }
                return;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    binding.etBusinessAddress.setText(place.getAddress());
                    LatLng latLng = place.getLatLng();
                    latitude = String.valueOf(latLng.latitude);
                    longitude = String.valueOf(latLng.longitude);
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
                case 11:
                    try {
                    Uri imageUri11 = data.getData();
                    Bitmap bitmapNew11 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri11);

                    Bitmap bitmap11 = BITMAP_RESIZER(bitmapNew11, bitmapNew11.getWidth(), bitmapNew11.getHeight());
                    Uri tempUri11 = getImageUri(this, bitmap11);
                    String s11 = RealPathUtil.getRealPath(this,tempUri11);// imageUri11);
                    files[0] = new File(s11);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri11);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoOne.setImageBitmap(bitmap);
                    uploadimage1(0);
                    break;
                case 12:
                    try {
                    Uri imageUri12 = data.getData();
                    Bitmap bitmapNew12 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri12);

                    Bitmap bitmap12 = BITMAP_RESIZER(bitmapNew12, bitmapNew12.getWidth(), bitmapNew12.getHeight());
                    Uri tempUri12 = getImageUri(this, bitmap12);
                    String s12 = RealPathUtil.getRealPath(this, tempUri12);//imageUri12);
                    files[1] = new File(s12);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri12);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoTwo.setImageBitmap(bitmap);
                    uploadimage1(1);
                    break;
                case 13:
                    try {
                    Uri imageUri13 = data.getData();
                    Bitmap bitmapNew13 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri13);

                    Bitmap bitmap13 = BITMAP_RESIZER(bitmapNew13, bitmapNew13.getWidth(), bitmapNew13.getHeight());
                    Uri tempUri13 = getImageUri(this, bitmap13);
                    String s13 = RealPathUtil.getRealPath(this,tempUri13);// imageUri13);
                    files[2] = new File(s13);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri13);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoThree.setImageBitmap(bitmap);
                    uploadimage1(2);
                    break;
                case 14:
                    try {
                    Uri imageUri14 = data.getData();
                    Bitmap bitmapNew14  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri14);

                    Bitmap bitmap14 = BITMAP_RESIZER(bitmapNew14, bitmapNew14.getWidth(), bitmapNew14.getHeight());
                    Uri tempUri14 = getImageUri(this, bitmap14);
                    String s14 = RealPathUtil.getRealPath(this, tempUri14);//imageUri14);
                    files[3] = new File(s14);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri14);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }} catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoFour.setImageBitmap(bitmap);
                    uploadimage1(3);
                    break;
                case 15:
                    try {
                    Uri imageUri15 = data.getData();
                    Bitmap bitmapNew15= MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri15);

                    Bitmap bitmap15 = BITMAP_RESIZER(bitmapNew15, bitmapNew15.getWidth(), bitmapNew15.getHeight());
                    Uri tempUri15 = getImageUri(this, bitmap15);
                    String s15 = RealPathUtil.getRealPath(this, tempUri15);//imageUri15);
                    files[4] = new File(s15);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri15);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoFive.setImageBitmap(bitmap);
                    uploadimage1(4);
                    break;
                case 16:
                    try {
                    Uri imageUri16 = data.getData();
                    Bitmap bitmapNew16 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri16);

                    Bitmap bitmap16 = BITMAP_RESIZER(bitmapNew16, bitmapNew16.getWidth(), bitmapNew16.getHeight());
                    Uri tempUri16 = getImageUri(this, bitmap16);
                    String s16 = RealPathUtil.getRealPath(this, tempUri16);//imageUri16);
                    files[5] = new File(s16);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri16);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoSix.setImageBitmap(bitmap);
                    uploadimage1(5);
                    break;
                case 17:
                    try {
                    Uri imageUri17 = data.getData();
                    Bitmap bitmapNew17  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri17);

                    Bitmap bitmap17 = BITMAP_RESIZER(bitmapNew17, bitmapNew17.getWidth(), bitmapNew17.getHeight());
                    Uri tempUri17 = getImageUri(this, bitmap17);
//                    String imag = RealPathUtil.getRealPath(this, tempUri);
//                    filePaths = imag;
                    String s17 = RealPathUtil.getRealPath(this, tempUri17);//imageUri17);

                    files[6] = new File(s17);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri17);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoSeven.setImageBitmap(bitmap);
                    uploadimage1(6);
                    break;

                case 18:
                    try {
                        Uri imageUri18 = data.getData();
                        Bitmap bitmapNew18  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri18);

                        Bitmap bitmap18 = BITMAP_RESIZER(bitmapNew18, bitmapNew18.getWidth(), bitmapNew18.getHeight());
                        Uri tempUri18 = getImageUri(this, bitmap18);
//                    String imag = RealPathUtil.getRealPath(this, tempUri);
//                    filePaths = imag;
                        String s18 = RealPathUtil.getRealPath(this, tempUri18);//imageUri18);

                        files[7] = new File(s18);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri18);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoEight.setImageBitmap(bitmap);
                    uploadimage1(7);
                    break;

                case 19:
                    try {
                        Uri imageUri19 = data.getData();
                        Bitmap bitmapNew19  = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri19);

                        Bitmap bitmap19 = BITMAP_RESIZER(bitmapNew19, bitmapNew19.getWidth(), bitmapNew19.getHeight());
                        Uri tempUri19 = getImageUri(this, bitmap19);
//                    String imag = RealPathUtil.getRealPath(this, tempUri);
//                    filePaths = imag;
                        String s19 = RealPathUtil.getRealPath(this, tempUri19);//imageUri18);

                        files[8] = new File(s19);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri19);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    binding.imgLogoNine.setImageBitmap(bitmap);
                    uploadimage1(8);
                    break;

                case PICK_IMAGE_MORE:
                    Uri imageUri1 = data.getData();

                    String ss = RealPathUtil.getRealPath(this, imageUri1);
                    strings[currentIndex % 7] = ss;
                    files[currentIndex % 7] = new File(ss);
//                    binding.btSubmit.setAlpha(1f);
//                    binding.btSubmit.setEnabled(true);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (currentIndex % 7 == 0) {
                        binding.imgLogoOne.setImageBitmap(bitmap);
                    } else if (currentIndex % 7 == 1) {
                        binding.imgLogoTwo.setImageBitmap(bitmap);
                    } else if (currentIndex % 7 == 2) {
                        binding.imgLogoThree.setImageBitmap(bitmap);

                    } else if (currentIndex % 7 == 3) {
                        binding.imgLogoFour.setImageBitmap(bitmap);

                    } else if (currentIndex % 7 == 4) {
                        binding.imgLogoFive.setImageBitmap(bitmap);

                    } else if (currentIndex % 7 == 5) {
                        binding.imgLogoSix.setImageBitmap(bitmap);

                    } else if (currentIndex % 7 == 6) {
                        binding.imgLogoSeven.setImageBitmap(bitmap);
                    }
                    if (currentIndex == 0) {
                        binding.noSelectedPhoto.setText("(1/7)");
                    } else if (currentIndex == 1) {
                        binding.noSelectedPhoto.setText("(2/7)");
                    } else if (currentIndex == 2) {
                        binding.noSelectedPhoto.setText("(3/7)");
                    } else if (currentIndex == 3) {
                        binding.noSelectedPhoto.setText("(4/7)");

                    } else if (currentIndex == 4) {
                        binding.noSelectedPhoto.setText("(5/7)");

                    } else if (currentIndex == 5) {
                        binding.noSelectedPhoto.setText("(6/7)");
                    } else if (currentIndex >= 6) {
                        binding.noSelectedPhoto.setText("(7/7)");
                    }
                    currentIndex++;
                    break;
            }
        }
    }

    private void SetupUI() {
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", model.getResult().getId());
                MultipartBody.Part category_id = MultipartBody.Part.createFormData("business_type", list.get(binding.spinner.getSelectedItemPosition()).getId());
                MultipartBody.Part business_name = MultipartBody.Part.createFormData("business_name", binding.etBusinessName.getText().toString());
                MultipartBody.Part business_address = MultipartBody.Part.createFormData("business_address", binding.etBusinessAddress.getText().toString());
                MultipartBody.Part b_lat = MultipartBody.Part.createFormData("b_lat", latitude);
                MultipartBody.Part b_lon = MultipartBody.Part.createFormData("b_lon", longitude);
                MultipartBody.Part business_cell_phone = MultipartBody.Part.createFormData("business_cell_phone", binding.etNo.getText().toString());
                MultipartBody.Part business_landline = MultipartBody.Part.createFormData("business_landline", binding.etNoLl.getText().toString());
                MultipartBody.Part offer_home_delivery = MultipartBody.Part.createFormData("offer_home_delivery", binding.offerHomeSwitch.isChecked() ? "true" : "false");
                MultipartBody.Part open_date = MultipartBody.Part.createFormData("open_date", binding.etBusinessOpenDate.getText().toString());
                MultipartBody.Part close_date = MultipartBody.Part.createFormData("close_date", binding.etBusinessCloseDate.getText().toString());
                MultipartBody.Part business_profile_image = getPartFromFile("business_profile_image", file);
                MultipartBody.Part image1 = getPartFromFile("image1", files[0]);
                MultipartBody.Part image2 = getPartFromFile("image2", files[1]);
                MultipartBody.Part image3 = getPartFromFile("image3", files[2]);
                MultipartBody.Part image4 = getPartFromFile("image4", files[3]);
                MultipartBody.Part image5 = getPartFromFile("image5", files[4]);
                MultipartBody.Part image6 = getPartFromFile("image6", files[5]);
                MultipartBody.Part image7 = getPartFromFile("image7", files[6]);
                MultipartBody.Part description = MultipartBody.Part.createFormData("description", binding.etDescription.getText().toString());

                if (validate()) {
                    binding.loaderLayout.loader.setVisibility(View.VISIBLE);
                    RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                            .create(UpdateProfile.class)
                            .updateBusiness(user_id,category_id, business_name, business_address, b_lat, b_lon, business_cell_phone, business_landline, offer_home_delivery, open_date, close_date, business_profile_image, /*image1, image2, image3, image4, image5, image6, image7,*/ description)
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
                                                binding.loaderLayout.loader.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonElement> call, Throwable t) {
                                    binding.loaderLayout.loader.setVisibility(View.GONE);
                                    Snackbar.make(findViewById(android.R.id.content),
                                            t.getMessage(),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

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
/*
    private String decodeFile(String path,int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);

            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return path;
            }

            // Store to tmp file

            String extr = Environment.getExternalStorageDirectory().toString();
            File mFolder = new File(extr + "/TMMFOLDER");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }

            String s = "tmp.png";

            File f = new File(mFolder.getAbsolutePath(), s);

            strMyImagePath = f.getAbsolutePath();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }
*/

    private void uploadimage1(int position) {
        MultipartBody.Part user_id = MultipartBody.Part.createFormData("user_id", model.getResult().getId());
//        MultipartBody.Part business_name = MultipartBody.Part.createFormData("business_name", binding.etBusinessName.getText().toString());
//        MultipartBody.Part business_address = MultipartBody.Part.createFormData("business_address", binding.etBusinessAddress.getText().toString());
//        MultipartBody.Part b_lat = MultipartBody.Part.createFormData("b_lat", latitude);
//        MultipartBody.Part b_lon = MultipartBody.Part.createFormData("b_lon", longitude);
//        MultipartBody.Part business_cell_phone = MultipartBody.Part.createFormData("business_cell_phone", binding.etNo.getText().toString());
//        MultipartBody.Part business_landline = MultipartBody.Part.createFormData("business_landline", binding.etNoLl.getText().toString());
//        MultipartBody.Part offer_home_delivery = MultipartBody.Part.createFormData("offer_home_delivery", binding.offerHomeSwitch.isChecked() ? "true" : "false");
//        MultipartBody.Part open_date = MultipartBody.Part.createFormData("open_date", binding.etBusinessOpenDate.getText().toString());
//        MultipartBody.Part close_date = MultipartBody.Part.createFormData("close_date", binding.etBusinessCloseDate.getText().toString());
//        MultipartBody.Part business_profile_image = getPartFromFile("business_profile_image", file);
//        MultipartBody.Part image1 = getPartFromFile("image1", files[0]);
//        MultipartBody.Part image2 = getPartFromFile("image2", files[1]);
//        MultipartBody.Part image3 = getPartFromFile("image3", files[2]);
//        MultipartBody.Part image4 = getPartFromFile("image4", files[3]);
//        MultipartBody.Part image5 = getPartFromFile("image5", files[4]);
//        MultipartBody.Part image6 = getPartFromFile("image6", files[5]);
//        MultipartBody.Part image7 = getPartFromFile("image7", files[6]);
//        MultipartBody.Part description = MultipartBody.Part.createFormData("description", binding.etDescription.getText().toString());

        MultipartBody.Part image1 = null;
        if (position == 0) {
            image1 = getPartFromFile("image1", files[0]);
        }
        if (position == 1) {
            image1 = getPartFromFile("image2", files[1]);
        }
        if (position == 2) {
            image1 = getPartFromFile("image3", files[2]);
        }
        if (position == 3) {
            image1 = getPartFromFile("image4", files[3]);
        }
        if (position == 4) {
            image1 = getPartFromFile("image5", files[4]);
        }
        if (position == 5) {
            image1 = getPartFromFile("image6", files[5]);
        }
        if (position == 6) {
            image1 = getPartFromFile("image7", files[6]);
        }
        if (position == 7) {
            image1 = getPartFromFile("image8", files[7]);
        }
        if (position == 8) {
            image1 = getPartFromFile("image9", files[8]);
        }

        if (validate()) {
            binding.loaderLayout.loader.setVisibility(View.VISIBLE);
            RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                    .create(UpdateProfile.class)
                    .updateBusinessImage1(user_id, /*business_name, business_address, b_lat, b_lon, business_cell_phone, business_landline, offer_home_delivery, open_date, close_date, business_profile_image,*/ image1/*, image2, image3, image4, image5, image6, image7, description*/)
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
//                                        finish();
                                    } else {
                                        ResponseAuthError authentication = new Gson().fromJson(object, ResponseAuthError.class);
                                        Log.i("dscbhs", "onResponse: " + authentication);
                                        Snackbar.make(findViewById(android.R.id.content),
                                                authentication.getResult(),
                                                Snackbar.LENGTH_SHORT).show();
                                        binding.loaderLayout.loader.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            binding.loaderLayout.loader.setVisibility(View.GONE);
                            Snackbar.make(findViewById(android.R.id.content),
                                    t.getMessage(),
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validate() {
//        if (TextUtils.isEmpty(binding.etFullName.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_Name, Snackbar.LENGTH_SHORT).show();
//            return false;
//        }else if (TextUtils.isEmpty(binding.etSurName.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_surname, Snackbar.LENGTH_SHORT).show();
//            return false;
//        }else if (TextUtils.isEmpty(binding.etEmail.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.enter_email,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        } else if (!Utils.EMAIL_ADDRESS_PATTERN.matcher(binding.etEmail.getText().toString().replace(" ", "")).matches()
//        ) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_correct_email,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (TextUtils.isEmpty(binding.etPassword.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    enter_pass,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }else if (binding.etPassword.getText().toString().replace(" ", "").length() < 6) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_password,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//
//        }
//        else if (TextUtils.isEmpty(binding.etConfirmPassword.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    enter_conf,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (!binding.etConfirmPassword.getText().toString().equals(binding.etPassword.getText().toString())) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_not_matched,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//
//        } else if (binding.etConfirmPassword.getText().toString().replace(" ", "").length() < 6) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_password,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        } else  if (TextUtils.isEmpty(binding.etBusinessName.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_business_name, Snackbar.LENGTH_SHORT).show();
//            return false;
//
//        } else  if (TextUtils.isEmpty(binding.etBusinessAddress.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.enter_business_address, Snackbar.LENGTH_SHORT).show();
//            return false;
//
//        } else if (TextUtils.isEmpty(binding.etNo.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content), R.string.text_register_no, Snackbar.LENGTH_SHORT).show();
//            return false;
//        }   else if (binding.ccp.getSelectedCountryCode().equals("57")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//
//        else if (binding.ccp.getSelectedCountryCode().equals("507")&&binding.etNo.getText().toString().replace(" ", "").length()!=8) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (binding.ccp.getSelectedCountryCode().equals("56")&&binding.etNo.getText().toString().replace(" ", "").length()!=11) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (binding.ccp.getSelectedCountryCode().equals("1")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (binding.ccp.getSelectedCountryCode().equals("91")&&binding.etNo.getText().toString().replace(" ", "").length()!=10) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.text_register_right_no,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }
//        else if (TextUtils.isEmpty(binding.etDescription.getText().toString().replace(" ", ""))) {
//            Snackbar.make(findViewById(android.R.id.content),
//                    R.string.please_enter_description,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//        }else if (!binding.cbBtn.isChecked()) {
//            Snackbar.make(binding.getRoot(),
//                    R.string.text_register_term,
//                    Snackbar.LENGTH_SHORT).show();
//            return false;
//
//        } else {
        return true;
//        }
    }


}