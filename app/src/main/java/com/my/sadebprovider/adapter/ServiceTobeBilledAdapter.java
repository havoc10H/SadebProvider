package com.my.sadebprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.sadebprovider.Fragment.HomeFragment;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.booking.ResultItem;
import com.my.sadebprovider.databinding.ItemServiceToBilledBinding;
import com.my.sadebprovider.databinding.ItemServiceToBilledBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceTobeBilledAdapter extends RecyclerView.Adapter<ServiceTobeBilledAdapter.HomeHolder> {

    private final Context mContext;
//    private final Status status;
    public List<com.my.sadebprovider.act.model.service.ResultItem> list;

    private final ServicesListBillingAdapter.SelectedItem selecteditem;

    public ServiceTobeBilledAdapter(Context context/*, Status status*/,ServicesListBillingAdapter.SelectedItem selecteditem) {
        this.mContext = context;
        this.selecteditem = selecteditem;
//        this.list = list;
//        this.status = status;
    }

    public void setList(List<com.my.sadebprovider.act.model.service.ResultItem> list) {
        this.list = list;
    }

    public ServiceTobeBilledAdapter.HomeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemServiceToBilledBinding binding = ItemServiceToBilledBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ServiceTobeBilledAdapter.HomeHolder(binding);
    }

    public void onBindViewHolder(ServiceTobeBilledAdapter.HomeHolder holder, int position) {

        TextView provider_Name = holder.itemView.findViewById(R.id.provider_Name);
        TextView tvPrice = holder.itemView.findViewById(R.id.tvPrice);
        TextView tvTotal = holder.itemView.findViewById(R.id.tvTotal);

        ImageView ivBilled =  holder.itemView.findViewById(R.id.ivDelete);

        provider_Name.setText(list.get(position).getServiceName());
        tvPrice.setText(list.get(position).getServicePrice());
        tvTotal.setText(list.get(position).getServicePrice());

        ivBilled.setOnClickListener(v ->
                {
                    selecteditem.deleteItem(position);
                }
                );

    }

    public int getItemCount() {
        return this.list.size();
    }


    public interface Status {
        void acceptcontrol(int position, HomeFragment.DoneCallback callback);

        void declinetcontrol(int position, HomeFragment.DoneCallback callback);

    }



    public class HomeHolder extends RecyclerView.ViewHolder {
        ItemServiceToBilledBinding binding;

        public HomeHolder(final ItemServiceToBilledBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
