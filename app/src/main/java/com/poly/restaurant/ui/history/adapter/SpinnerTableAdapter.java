package com.poly.restaurant.ui.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Table;

import java.util.List;

public class SpinnerTableAdapter extends BaseAdapter {
    private Context context;
    private List<Table> list;

    public SpinnerTableAdapter(Context context, List<Table> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Table> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_spinner_table, viewGroup, false);
        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(list.get(i).getName());
        image.setImageResource(R.drawable.ic_icon_table);

        return rootView;
    }
}
