package com.my.sadebprovider.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.my.sadebprovider.R;
import com.my.sadebprovider.act.activity.ServiceDetailActivity;
import com.my.sadebprovider.act.model.service.ResultItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServicesListBillingAdapter extends RecyclerView.Adapter<ServicesListBillingAdapter.ServicesListViewAdapter_View> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private final SelectedItem selecteditem;
    private final Context mContext;
    public List<ResultItem> myservicelist;

    public ServicesListBillingAdapter(Context context/*, List<ResultItem> myservicelist*/, SelectedItem selecteditem) {
        this.mContext = context;
//        this.myservicelist = myservicelist;
        this.selecteditem = selecteditem;
    }

    public void setMyservicelist(List<ResultItem> myservicelist) {
        this.myservicelist = myservicelist;
        notifyDataSetChanged();
    }

    public ServicesListViewAdapter_View onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ServicesListViewAdapter_View(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_billing_service, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesListViewAdapter_View holder, int position) {

        Picasso.get().load(myservicelist.get(position).getImage1()).placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.iv_service_img);
        holder.tv_service_name.setText(myservicelist.get(position).getServiceName());
        holder.tv_service_price.setText("$ "+myservicelist.get(position).getServicePrice());

        holder.ll_main.setOnClickListener(v -> {

            selecteditem.deleteItem(position);

//            Intent intent=new Intent(holder.ll_main.getContext(), ServiceDetailActivity.class);
//            intent.putExtra("service_id",myservicelist.get(position).getId());
//            holder.ll_main.getContext().startActivity(intent);

        });
    }

    public int getItemCount() {
        return this.myservicelist.size();
    }

    public class ServicesListViewAdapter_View extends RecyclerView.ViewHolder {

        private ImageView iv_service_img;
        private TextView tv_service_name, tv_service_price;

        private LinearLayout ll_main;

        public ServicesListViewAdapter_View(final View itemView) {
            super(itemView);

            iv_service_img = itemView.findViewById(R.id.iv_provider_image);

            tv_service_name = itemView.findViewById(R.id.serviceName);

            tv_service_price = itemView.findViewById(R.id.srvicePrice);

            ll_main = itemView.findViewById(R.id.ll_main);

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
