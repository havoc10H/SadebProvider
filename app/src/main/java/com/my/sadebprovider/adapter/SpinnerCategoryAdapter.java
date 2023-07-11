package com.my.sadebprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.sadebprovider.R;

import com.my.sadebprovider.act.model.category.ResultItem;

import java.util.List;

public class SpinnerCategoryAdapter extends BaseAdapter {

    private  Context context;
    private List<ResultItem> list;

    public SpinnerCategoryAdapter(Context context, List<ResultItem> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.items, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text);
        text.setText(list.get(position).getCategoryName());

        return convertView;
    }
}
