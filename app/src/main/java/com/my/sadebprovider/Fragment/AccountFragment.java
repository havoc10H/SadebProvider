package com.my.sadebprovider.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.AddTimeSlot;
import com.my.sadebprovider.act.activity.BillingActivity;
import com.my.sadebprovider.act.activity.BusinessDetailActivity;
import com.my.sadebprovider.act.activity.ChooseLanguageActivity;
import com.my.sadebprovider.act.activity.InviteFriends;
import com.my.sadebprovider.act.activity.Login;
import com.my.sadebprovider.act.activity.SearchClientAct;
import com.my.sadebprovider.act.activity.UpdateGender;
import com.my.sadebprovider.act.activity.UpdateName;
import com.my.sadebprovider.act.activity.UpdatePassword;
import com.my.sadebprovider.act.activity.UpdatePhoneNumber;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.databinding.FragmentAccountBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    private Fragment fragment;
    private FragmentAccountBinding binding;
    public static final int PICK_IMAGE = 1;
    private Bitmap bitmap;
    AlertDialog alertDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);

        setProfileData();
        init();

        binding.RRLogout.setOnClickListener(v -> {
            alertDialog = new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.are_you_sure_you_want_to_logout)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    SharedPrefsManager.getInstance().clearPrefs();
                    startActivity(new Intent(getContext(), Login.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    ((MainActivity)getActivity()).finishAct();
                }
            })
            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            }).show();
        });

        return binding.getRoot();
    }

    private void init() {
        binding.llMain.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), UpdateName.class));
        });

        binding.RREmail.setOnClickListener(v -> {

//            startActivity(new Intent(getContext(), UpdateEmail.class));
//            startActivity(new Intent(getContext(), ServicesListActivity.class));
        });
        binding.RRMyService.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), ServicesListActivity.class));
        });

        binding.businessDetail.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BusinessDetailActivity.class));
        });

        binding.RRGender.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), UpdateGender.class));
        });

        binding.RRPhoneNumber.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), UpdatePhoneNumber.class));
        });

        binding.RRchangePassword.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), UpdatePassword.class));
        });
        binding.RRInvite.setOnClickListener(v -> {

            String link = "https://play.google.com/store/apps/";

            Uri uri = Uri.parse(link);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, link.toString());

            startActivity(Intent.createChooser(intent, "Share Link"));

//            startActivity(new Intent(getContext(), InviteFriends.class));
        });

        binding.clientCreation.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SearchClientAct.class));
        });

        binding.timeSlote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddTimeSlot.class).putExtra("from","profile"));

            }
        });
        binding.billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BillingActivity.class));

            }
        });

        binding.language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChooseLanguageActivity.class).putExtra("from","Account"));

            }
        });
    }


    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setProfileData();
    }

    private void setProfileData() {
        ResponseAuthentication model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);
        if (model!=null){
            if(!model.getResult().getImage().equals("")){
                Picasso.get().load(model.getResult().getImage()).placeholder(R.drawable.user_placeholder).into(binding.ivUser);
            }
            binding.tvStatus.setText(model.getResult().getBusiness_name());
            binding.tvEmail.setText(model.getResult().getBusiness_name());
            binding.tvName.setText(model.getResult().getUserName()+" "+model.getResult().getSurname());
            binding.tvNo.setText(model.getResult().getMobile());
            binding.tvGender.setText(model.getResult().getGender());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                binding.ivUser.setImageBitmap(bitmap);
            }
        }
    }
}