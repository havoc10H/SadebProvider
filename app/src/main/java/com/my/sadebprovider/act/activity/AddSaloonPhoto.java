package com.my.sadebprovider.act.activity;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.databinding.ActivityAddSaloonPhotoBinding;
import com.my.sadebprovider.util.RealPathUtil;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AddSaloonPhoto extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;
    final int PERMISSION_ALL = 100;
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    private final String[] strings = {null, null, null, null, null, null, null};
    private ActivityAddSaloonPhotoBinding binding;
    private Bitmap bitmap;
    private int currentIndex;
    private ResultItem item;
    private NetworkConstraint.TYPE type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(this, R.layout.activity_add_saloon_photo);
      item = new Gson().fromJson(getIntent().getStringExtra("myservicelist"), ResultItem.class);
      type = (NetworkConstraint.TYPE) getIntent().getSerializableExtra("type");

      init();

      setdata();

      String  provider_user_id=getIntent().getStringExtra("provider_user_id");
      Log.i("csvsbbd", "onCreate: "+provider_user_id);

        binding.continew.setOnClickListener(v -> {
            if (type == NetworkConstraint.TYPE.EDIT) {
                Intent intent = new Intent(this, AllUsersListActivity.class);
                intent.putExtra("images", strings);
                intent.putExtra("currentIndex", currentIndex);
                intent.putExtra("type", NetworkConstraint.TYPE.EDIT);
                intent.putExtra("myservicelist", new Gson().toJson(item));
                startActivity(intent);

            } else {
                binding.continew.setAlpha(0.5f);
                binding.continew.setEnabled(false);

                if (strings[0] == null) {
                  Snackbar.make(findViewById(android.R.id.content),
                            getString(R.string.one_image_must_be_select),
                            LENGTH_SHORT).show();

                } else {
                    binding.continew.setAlpha(1f);
                    binding.continew.setEnabled(true);
                    Intent intent = new Intent(this, AllUsersListActivity.class);
                    intent.putExtra("images", strings);
                    intent.putExtra("currentIndex", currentIndex);
                    intent.putExtra("type", NetworkConstraint.TYPE.ADD);
                    startActivity(intent);

                }
            }


        });

        binding.ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void setdata() {
        Log.i("scsvdvvdsc", "setdata: "+type);
        if (type == NetworkConstraint.TYPE.EDIT) {
            int Index = SharedPrefsManager.getInstance().getInt("index");

//            strings[0]=item.getImage1();
//            currentIndex=1;
             Log.i("scdv", "edit: "+currentIndex);
            binding.noSelectedPhoto.setText("(" + Index  + "/7)");
            Picasso.get().load(item.getImage1()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoOne);
            Picasso.get().load(item.getImage2()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoTwo);
            Picasso.get().load(item.getImage3()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoThree);
            Picasso.get().load(item.getImage4()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoFour);
            Picasso.get().load(item.getImage5()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoFive);
            Picasso.get().load(item.getImage6()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoSix);
            Picasso.get().load(item.getImage7()).placeholder(R.drawable.user_placeholder).into(binding.imgLogoSeven);

            Picasso.get().load(item.getServiceImage()).placeholder(R.drawable.user_placeholder).into(binding.imgSingle);


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri imageUri = data.getData();

                String s = RealPathUtil.getRealPath(this, imageUri);
                strings[currentIndex % 7] = s;

                binding.continew.setAlpha(1f);
                binding.continew.setEnabled(true);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (currentIndex % 7 == 0) {
                    binding.imgSingle.setImageBitmap(bitmap);
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
            }
        }

        Log.i("scdv", "onActivityResult: "+currentIndex);
    }

    public void init() {
        binding.imgCamera.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!hasPermissions(AddSaloonPhoto.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(AddSaloonPhoto.this, PERMISSIONS, PERMISSION_ALL);
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
                    if (b) {

                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, PICK_IMAGE);

//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                    }

                }
                return;
            }
        }
    }
}