package com.poly.myapplication.ui.history.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.ui.bill.adapter.OnListener;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<Bill> list;
    private OnListener onListener;


    public HistoryAdapter(Context context, List<Bill> list, OnListener onListener) {
        this.context = context;
        this.list = list;
        this.onListener = onListener;
    }
    public void setList(List<Bill> mListBill) {
        this.list = mListBill;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
