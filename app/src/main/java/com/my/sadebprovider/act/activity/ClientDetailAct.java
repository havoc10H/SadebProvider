package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.model.authentication.SuccessResDeleteClient;
import com.my.sadebprovider.act.model.category.CategoryResponse;
import com.my.sadebprovider.act.model.client.SuccessResGetClient;
import com.my.sadebprovider.act.model.password.ForgetPasswordResponse;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.category.CategoryRequest;
import com.my.sadebprovider.databinding.ActivityClientDetailBinding;
import com.my.sadebprovider.util.SharePrefrenceConstraint;
import com.my.sadebprovider.util.SharedPrefsManager;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailAct extends AppCompatActivity {

    ActivityClientDetailBinding binding;

    private ResponseAuthentication model;

    private SuccessResGetClient.Result clientData;

    private String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_client_detail);

        model = SharedPrefsManager.getInstance().getObject(SharePrefrenceConstraint.provider, ResponseAuthentication.class);

        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getString("id");

        clientResponse();

        binding.ivDelete.setOnClickListener(v ->
                {
                    new AlertDialog.Builder(ClientDetailAct.this)
                            .setTitle("Eliminar cliente")
                            .setMessage("¿Está seguro de que desea eliminar este cliente?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                    deleteClient();

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                );

    }



    private void deleteClient() {
        String user_id = model.getResult().getId();
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(CategoryRequest.class)
                .deleteClient(clientId,user_id)
                .enqueue(new Callback<SuccessResDeleteClient>() {
                    @Override
                    public void onResponse(Call<SuccessResDeleteClient> call, Response<SuccessResDeleteClient> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {

                            finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessResDeleteClient> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                    }
                });
    }


    private void clientResponse() {
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        String user_id = model.getResult().getId();

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(CategoryRequest.class)
                .getClient(user_id,clientId)
                .enqueue(new Callback<SuccessResGetClient>() {
                    @Override
                    public void onResponse(Call<SuccessResGetClient> call, Response<SuccessResGetClient> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                        if (response != null) {

                            SuccessResGetClient data = response.body();

                            String status = data.status;

                            if (status.equalsIgnoreCase("1")){

                                clientData = data.getResult();

                                setData();

                            }else {

                                Toast.makeText(ClientDetailAct.this, ""+data.message, Toast.LENGTH_SHORT).show();

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessResGetClient> call, Throwable t) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);
                    }
                });
    }

    public void setData()
    {

        if(!clientData.getImage().equalsIgnoreCase(""))

        {
            Picasso.get().load(clientData.getImage()).placeholder(R.drawable.user_placeholder).into(binding.ivProfile);
        }

        binding.etName.setText(clientData.getFirstName());
        binding.etEmail.setText(clientData.getEmail());
        binding.etGender.setText(clientData.getGender());
        binding.etNo.setText(clientData.getPhone());

        binding.etRuc.setText(clientData.getRucNo());
        binding.etClientCode.setText(clientData.getClientCode());
        binding.etAgreement.setText(clientData.getAgreement());
        binding.etDiscount.setText(clientData.getDiscount());
        binding.etObservation.setText(clientData.getObservations());

    }

}