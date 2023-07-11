package com.my.sadebprovider.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.my.sadebprovider.Fragment.HomeFragment;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.booking.ResultItem;
import com.my.sadebprovider.databinding.ItemBillingBinding;
import com.my.sadebprovider.databinding.ItemHomeBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.HomeHolder> {

    private final Context mContext;
//    private final Status status;
    public List<ResultItem> list;

    public BillingAdapter(Context context/*, Status status*/) {
        this.mContext = context;
//        this.list = list;
//        this.status = status;

    }

    public void setList(List<ResultItem> list) {
        this.list = list;
    }

    public BillingAdapter.HomeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemBillingBinding binding = ItemBillingBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new BillingAdapter.HomeHolder(binding);
    }

    public void onBindViewHolder(BillingAdapter.HomeHolder holder, int position) {
        holder.binding.tvName.setText("$"+list.get(position).getService_details().getServicePrice());//ServiceName());//getUsersDetails().getUserName());
        holder.binding.tvStartTime.setText(String.format(mContext.getString(R.string.start), list.get(position).getStartTime()));
        holder.binding.tvEndTime.setText(String.format(mContext.getString(R.string.end), list.get(position).getEndTime()));
//        holder.binding.tvStartTime.setText("start :- " + list.get(position).getStartTime());
//        holder.binding.tvEndTime.setText("end :- " + list.get(position).getEndTime());
        holder.binding.tvReqType.setText("Invoice #"+list.get(position).getId());//list.get(position).getUsersDetails().getUserName());//getServiceName());
        holder.binding.tvNoOfBooking.setText(list.get(position).getDate());//getMobile());
        holder.binding.tvWant.setText(list.get(position).getService_details().getServiceName());//getUsersDetails().getEmail());
        holder.binding.tvEmail.setText("PAID");//list.get(position).getUsersDetails().getMobile());//getDate());

        holder.binding.providerEmail.setText(list.get(position).getProviderUserResponse().getEmail());
        holder.binding.providerName.setText(list.get(position).getProviderUserResponse().getUserName());
        holder.binding.providerNo.setText(list.get(position).getProviderUserResponse().getMobile());

        holder.binding.userEmail.setText(list.get(position).getUsersDetails().getEmail());
        holder.binding.userName.setText(list.get(position).getUsersDetails().getUserName());
        holder.binding.userNo.setText(list.get(position).getUsersDetails().getMobile());

        Picasso.get().load(list.get(position).getUsersDetails().getImage()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.binding.roundedUserImage);
        Picasso.get().load(list.get(position).getProviderUserResponse().getImage()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.binding.ivProviderImage);

//        if (list.get(position).getStatus().equals("Cancel")) {
//            holder.binding.tvStatus.setVisibility(View.VISIBLE);
//            holder.binding.tvStatus.setText(R.string.cancel);
//            holder.binding.tvStatus.setTextColor(Color.parseColor("#ED0000"));
//            holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_stroke_red);
//            holder.binding.llAction.setVisibility(View.GONE);
//
//        } else if (list.get(position).getStatus().equals("Accept")) {
//            holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_storke_green);
//            holder.binding.tvStatus.setVisibility(View.VISIBLE);
//            holder.binding.tvStatus.setText(R.string.accepted);
//            holder.binding.tvStatus.setTextColor(Color.parseColor("#10B234"));
//            holder.binding.llAction.setVisibility(View.GONE);
//        } else {
//            holder.binding.tvStatus.setVisibility(View.GONE);
//            holder.binding.llAction.setVisibility(View.VISIBLE);
//        }

//        holder.binding.tvAccept.setOnClickListener(v -> {
//            status.acceptcontrol(position, () -> {
//                holder.binding.tvStatus.setText(R.string.accepted);
//                holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_storke_green);
//                holder.binding.tvStatus.setTextColor(Color.parseColor("#10B234"));
//                holder.binding.tvStatus.setVisibility(View.VISIBLE);
//                holder.binding.tvDecline.setVisibility(View.GONE);
//                holder.binding.tvAccept.setVisibility(View.GONE);
//            });
//        });
//        holder.binding.tvDecline.setOnClickListener(v -> {
//            status.declinetcontrol(position, () -> {
//                holder.binding.tvStatus.setText(R.string.cancel);
//                holder.binding.tvStatus.setBackgroundResource(R.drawable.bg_stroke_red);
//                holder.binding.tvStatus.setTextColor(Color.parseColor("#ED0000"));
//                holder.binding.tvStatus.setVisibility(View.VISIBLE);
//                holder.binding.tvDecline.setVisibility(View.GONE);
//                holder.binding.tvAccept.setVisibility(View.GONE);
//            });
//        });


    }

    public int getItemCount() {
        return this.list.size();
    }


    public interface Status {
        void acceptcontrol(int position, HomeFragment.DoneCallback callback);

        void declinetcontrol(int position, HomeFragment.DoneCallback callback);

    }

    public class HomeHolder extends RecyclerView.ViewHolder {
        ItemBillingBinding binding;

        public HomeHolder(final ItemBillingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
