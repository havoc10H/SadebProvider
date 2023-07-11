package com.my.sadebprovider.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.service.servicedetail.ServiceUserItem;
import com.my.sadebprovider.databinding.UserlistBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserdetailAdapter extends RecyclerView.Adapter<UserdetailAdapter.UserdetailAdapter_View> {
    private final List<ServiceUserItem> list;

    public UserdetailAdapter(List<ServiceUserItem> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public UserdetailAdapter_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserlistBinding binding = UserlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserdetailAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserdetailAdapter_View holder, int position) {
        holder.binding.tvEmail.setText(list.get(position).getEmail());
        holder.binding.tvName.setText(list.get(position).getUserName());
        holder.binding.tvNo.setText(list.get(position).getMobile());
        Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ciImg);
         holder.binding.cvCheck.setVisibility(View.GONE);
         holder.binding.swipe.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UserdetailAdapter_View extends RecyclerView.ViewHolder {

        private final UserlistBinding binding;

        public UserdetailAdapter_View(@NonNull UserlistBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
