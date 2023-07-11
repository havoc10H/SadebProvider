package com.my.sadebprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.my.sadebprovider.R;
import com.my.sadebprovider.act.model.notification.ResultItem;
import com.my.sadebprovider.databinding.NotificationBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationAdapter_View> {

    private List<ResultItem> results;

    Context  context;
    public NotificationAdapter(/*List<ResultItem> results,*/Context context) {
//        this.results=results;
        this.context=context;
    }

    public void setResults(List<ResultItem> results) {
        this.results = results;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationAdapter_View onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        @NonNull NotificationBinding binding = NotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationAdapter_View(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.NotificationAdapter_View holder, int position) {

        String  s =results.get(position).getComment();

        String newString = s.substring(4);

        Picasso.get().load(results.get(position).getUser_image()).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder).into(holder.binding.ivImage);
        holder.binding.tvDescription.setText( results.get(position).getUserName()+" "+s.substring(4));
        String time =results.get(position).getDateTime();
        holder.binding.tvTime.setText(time.substring(10));
        holder.binding.tvReadMore.setText(String.format(context.getString(R.string.user_name), results.get(position).getUserName()));
//      holder.binding.tvReadMore.setText("User Name:- "+ results.get(position).getUserName());

     }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class NotificationAdapter_View extends RecyclerView.ViewHolder {
       private NotificationBinding binding;
        public NotificationAdapter_View(@NonNull @NotNull NotificationBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
         }
    }
}
