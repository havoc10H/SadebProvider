package com.my.sadebprovider.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.my.sadebprovider.Model.HomeModel;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.ServiceDetailActivity;
import com.my.sadebprovider.act.model.service.AddServiceResponse;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServicesListViewAdapter extends RecyclerView.Adapter<ServicesListViewAdapter.ServicesListViewAdapter_View> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private final SelectedItem selecteditem;
    private final Context mContext;
    public List<ResultItem> myservicelist;

    public ServicesListViewAdapter(Context context/*, List<ResultItem> myservicelist*/, SelectedItem selecteditem) {
        this.mContext = context;
//        this.myservicelist = myservicelist;
        this.selecteditem = selecteditem;
    }

    public void setMyservicelist(List<ResultItem> myservicelist) {
        this.myservicelist = myservicelist;
    }

    public ServicesListViewAdapter_View onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ServicesListViewAdapter_View(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesListViewAdapter_View holder, int position) {

        viewBinderHelper.bind(holder.swipeRevealLayout, myservicelist.get(position).getId());

        Picasso.get().load(myservicelist.get(position).getServiceImage()).placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.iv_service_img);
        holder.tv_service_name.setText(myservicelist.get(position).getServiceName());
        holder.tv_service_price.setText("$ "+myservicelist.get(position).getServicePrice());
        holder.tv_service_time.setText(myservicelist.get(position).getEstimate_time()+" min");//ServiceTime());
        holder.update.setOnClickListener(v -> {
            selecteditem.updateItem(position);
        });

        holder.delete.setOnClickListener(v -> {
            selecteditem.deleteItem(position);
        });

        holder.ll_main.setOnClickListener(v -> {
            Intent intent=new Intent(holder.ll_main.getContext(), ServiceDetailActivity.class);
            intent.putExtra("service_id",myservicelist.get(position).getId());
            holder.ll_main.getContext().startActivity(intent);
        });
    }

    public int getItemCount() {
        return this.myservicelist.size();
    }

    public class ServicesListViewAdapter_View extends RecyclerView.ViewHolder {

        private ImageView iv_service_img;
        private TextView tv_service_name, tv_service_price, tv_service_time;
        private SwipeRevealLayout swipeRevealLayout;
        private CardView delete,update;
        private LinearLayout ll_main;


        public ServicesListViewAdapter_View(final View itemView) {
            super(itemView);
            tv_service_name = itemView.findViewById(R.id.tv_service_name);
            tv_service_price = itemView.findViewById(R.id.tv_service_price);
            tv_service_time = itemView.findViewById(R.id.tv_service_time);
            iv_service_img = itemView.findViewById(R.id.iv_service_img);
            update = itemView.findViewById(R.id.update);
            delete = itemView.findViewById(R.id.delete);
            ll_main = itemView.findViewById(R.id.ll_main);
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);

        }
    }

    public interface SelectedItem{
        void deleteItem(int Position);
        void updateItem(int Position);
    }

    public void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    public void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }
}
