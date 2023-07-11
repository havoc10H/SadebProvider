package com.my.sadebprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.sadebprovider.R;


public class SpinnerGender extends BaseAdapter {
    private Context context;
    String s[];

    public SpinnerGender(Context context, String[] s) {
        this.context = context;
        this.s=s;

    }

    @Override
    public int getCount() {
        return s.length;
    }

    @Override
    public Object getItem(int position) {
        return s[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.gender, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text);
        text.setText(s[position]);

        return convertView;
    }
}
