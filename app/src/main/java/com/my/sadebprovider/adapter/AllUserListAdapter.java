package com.my.sadebprovider.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.user.ResultItem;
import com.my.sadebprovider.databinding.UserlistBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
public class AllUserListAdapter extends RecyclerView.Adapter<AllUserListAdapter.AllUserListAdapter_View> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private final List<ResultItem> list;
    private final String provider_user_id;
    private SelectedPositon  selectedPositon;

    public AllUserListAdapter(List<ResultItem> list, SelectedPositon selectedPositon, String provider_user_id) {
        this.list=list;
        this.selectedPositon=selectedPositon;
        this.provider_user_id=provider_user_id;
    }

    @NonNull
    @Override
    public AllUserListAdapter_View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserlistBinding binding = UserlistBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AllUserListAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllUserListAdapter_View holder, int position) {
        viewBinderHelper.bind(holder.binding.swipeRevealLayout, list.get(position).getId());

        holder.binding.cvCheck.setOnClickListener(v -> {
            selectedPositon.selectedItem(position,holder.binding.cvCheck.isChecked());
        });

        if (provider_user_id!=null){
            String s[] = provider_user_id.split(",");
            checkUser(s,list.get(position).getId(),isAvailable -> holder.binding.cvCheck.setChecked(isAvailable));
        }
        holder.binding.update.setOnClickListener(v -> {
            selectedPositon.updateItem(position);
        });

        holder.binding.delete.setOnClickListener(v -> {
            selectedPositon.deleteItem(position);
         });
        holder.binding.tvEmail.setText(list.get(position).getEmail());
        holder.binding.tvName.setText(list.get(position).getUserName());
        holder.binding.tvNo.setText(list.get(position).getMobile());
        Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.user_placeholder).into(holder.binding.ciImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AllUserListAdapter_View extends RecyclerView.ViewHolder {
        private final UserlistBinding binding;

        public AllUserListAdapter_View(@NonNull UserlistBinding binding) {
            super(binding.getRoot());

           this.binding=binding;
        }
    }

    public interface SelectedPositon{
        void selectedItem(int position,boolean isChecked);
        void updateItem(int position);
        void deleteItem(int position);
    }

    interface CheckUser{
        void check(boolean isAvailable);
    }

    private void checkUser(String ids[],String id,CheckUser callback){
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equals(id)){
                callback.check(true);
                return;
            }
        }
        callback.check(false);
    }

}
