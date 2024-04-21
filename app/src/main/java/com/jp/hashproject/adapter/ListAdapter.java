package com.jp.hashproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jp.hashproject.R;
import com.jp.hashproject.model.Hash;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Hash> {
    private List<Hash> hashList;
    private Context context;
    private int resourceLayout;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Hash> objects) {
        super(context, resource, objects);
        this.hashList = objects;
        this.context = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(resourceLayout, null);
        }
        Hash hash = hashList.get(position);

        TextView tvItemFileName = view.findViewById(R.id.tvItemFileName);
        TextView tvItemDate = view.findViewById(R.id.tvItemDate);

        tvItemFileName.setText(hash.fileName);
        tvItemDate.setText(hash.date);

        return view;
    }
}
