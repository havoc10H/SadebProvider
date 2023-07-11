package com.my.sadebprovider.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.my.sadebprovider.Fragment.HomeFragment;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.ClientDetailAct;
import com.my.sadebprovider.act.activity.InvoiceActivity;
import com.my.sadebprovider.act.model.booking.ResultItem;
import com.my.sadebprovider.act.model.user.SuccessResClients;
import com.my.sadebprovider.databinding.ClientItemBinding;
import com.my.sadebprovider.databinding.ClientItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientsListAdapter extends RecyclerView.Adapter<ClientsListAdapter.HomeHolder> {

    private final Context mContext;

    public List<SuccessResClients.Result> list;

    public ClientsListAdapter(Context context) {
        this.mContext = context;
//        this.list = list;
    }

    public void setList(List<SuccessResClients.Result> list) {
        this.list = list;
    }

    public ClientsListAdapter.HomeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ClientItemBinding binding = ClientItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ClientsListAdapter.HomeHolder(binding);
    }

    public void onBindViewHolder(ClientsListAdapter.HomeHolder holder, int position) {

        holder.binding.llMain.setOnClickListener(v ->
                {
                    Intent intent = new Intent(mContext, ClientDetailAct.class);
                    intent.putExtra("id", list.get(position).getId());
                    mContext.startActivity(intent);
                }
                );

        holder.binding.providerName.setText(list.get(position).getClientCode());
        holder.binding.providerNo.setText(list.get(position).getFirstName());

        if(list.get(position).getImage()!=null && !list.get(position).getImage().equalsIgnoreCase(""))
        {
            Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.binding.ivProviderImage);
        }
    }

    public int getItemCount() {
        return this.list.size();
    }

    public interface Status {
        void acceptcontrol(int position, HomeFragment.DoneCallback callback);
        void declinetcontrol(int position, HomeFragment.DoneCallback callback);
    }

    public class HomeHolder extends RecyclerView.ViewHolder {
        ClientItemBinding binding;

        public HomeHolder(final ClientItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
