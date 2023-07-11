package com.my.sadebprovider.act.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.my.sadebprovider.MainActivity;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.authentication.ResponseAuthentication;
import com.my.sadebprovider.act.network.NetworkConstraint;
import com.my.sadebprovider.act.network.RetrofitClient;
import com.my.sadebprovider.act.network.service.ServiceRequest;
import com.my.sadebprovider.adapter.SpinnerCategoryAdapter;
import com.my.sadebprovider.adapter.SpinnerPaymentMethodAdapter;
import com.my.sadebprovider.databinding.ActivityConfirmBillingBinding;
import com.my.sadebprovider.util.SharedPrefsManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmBillingAct extends AppCompatActivity {

    private SpinnerPaymentMethodAdapter adapter;

    ActivityConfirmBillingBinding binding;

    private List<String> data = new ArrayList<String>();

    private String discount ="",subtotal="",itms="",total="",cash="",strReturn="";

    private String servicesTotal="",service,price="",services="",userName="",userRUC="";

    private ResponseAuthentication model;

    private double dTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_confirm_billing);

        binding.ivBack.setOnClickListener(v -> finish());

        data.add("Efectivo");
        data.add("Tarjeta debito");
        data.add("Tarjeta Credito Visa");
        data.add("Tarjeta De Credito American Express");
        data.add("Tarjeta De Credito Master Card");
        data.add("Yappy");
        data.add("Transferencia Bancaria");
        total="";

        model = SharedPrefsManager.getInstance().getObject(NetworkConstraint.type, ResponseAuthentication.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             price = extras.getString("price");
             services = extras.getString("services");
             total = extras.getString("total");
            userName = extras.getString("name");
            userRUC = extras.getString("ruc");
            //The key argument here must match that used in the other activity
        }

        binding.tvSubtotal.setText(total);

        DecimalFormat df = new DecimalFormat("#.##");

        double d = tenp(7);

        binding.tvITMB.setText(df.format(d));

        dTotal = Double.parseDouble(total) + d;

        binding.tvTotal.setText(df.format(dTotal));

        adapter = new SpinnerPaymentMethodAdapter(this, data);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout linearLayout = (LinearLayout) binding.spinner.getSelectedView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.tvCash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().equalsIgnoreCase(""))
                {
                    double myText = Double.parseDouble(s.toString());

                    double reurnText  = dTotal - myText;

                    String returnString = df.format(reurnText);

                    binding.tvReturn.setText(returnString.replace("-",""));
                }
                else
                {
                    binding.tvReturn.setText("");
                }

            }
        });

        binding.btnCheckin.setOnClickListener(v ->
                {
                    addInvoice();
                }
                );

    }

    public double tenp(float view1) {

        double amount = Double.parseDouble(total+"");
        double res = (amount / 100.0f) * view1;
//        Toast.makeText(ConfirmBillingAct.this, "" + res, Toast.LENGTH_SHORT).show();
        return  res;

    }

    private void addInvoice() {

        MultipartBody.Part username = MultipartBody.Part.createFormData("user_name", userName);
        MultipartBody.Part userruc = MultipartBody.Part.createFormData("user_id", "");
        MultipartBody.Part user_id = MultipartBody.Part.createFormData("ruc_number", userRUC);
        MultipartBody.Part portProviderId = MultipartBody.Part.createFormData("provider_id", model.getResult().getId());
        MultipartBody.Part serviceList = MultipartBody.Part.createFormData("service_id", services);
        MultipartBody.Part allPrice = MultipartBody.Part.createFormData("price", price);
        MultipartBody.Part paymentMeth = MultipartBody.Part.createFormData("payment_method", binding.spinner.getSelectedItem().toString());
        MultipartBody.Part disount = MultipartBody.Part.createFormData("discount", binding.tvDiscount.getText().toString());
        MultipartBody.Part subToal = MultipartBody.Part.createFormData("subtotal", binding.tvSubtotal.getText().toString());
        MultipartBody.Part tax = MultipartBody.Part.createFormData("tax", binding.tvITMB.getText().toString());
        MultipartBody.Part gTotal = MultipartBody.Part.createFormData("total", binding.tvTotal.getText().toString());
        MultipartBody.Part givenCash = MultipartBody.Part.createFormData("given_amount", binding.tvCash.getText().toString());
        MultipartBody.Part returnCash = MultipartBody.Part.createFormData("return_amount",binding.tvReturn.getText().toString());
        MultipartBody.Part email = MultipartBody.Part.createFormData("user_email","");

        RetrofitClient.getClient(NetworkConstraint.BASE_URL)
                .create(ServiceRequest.class)
                .addInvoice(username,userruc,user_id,
                        portProviderId,
                        serviceList,
                        allPrice,
                        paymentMeth,
                        disount,
                        subToal,
                        tax,
                        gTotal,
                        givenCash,
                        returnCash,
                        email
                        )
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        binding.loaderLayout.loader.setVisibility(View.GONE);

                        if (response != null) {
                            if (response.isSuccessful()) {
                                JsonObject object = response.body().getAsJsonObject();
                                int status = object.get("status").getAsInt();
                                Log.i("bcdkkxn", "stats: " + status);

                                if (status == 1) {
                                    startActivity(new Intent(ConfirmBillingAct.this, MainActivity.class));
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

}