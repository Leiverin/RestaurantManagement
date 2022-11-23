package com.poly.myapplication.ui.history.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ItemHistoryBinding;
import com.poly.myapplication.ui.FeedBackActivity;
import com.poly.myapplication.utils.Constants;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<Bill> list;

    public HistoryAdapter(Context context, List<Bill> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Bill> mListBill) {
        this.list = mListBill;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.HistoryViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Bill bill = list.get(position);
        if (bill != null) {
            Constants.setNameTable(bill, holder.binding.txtNameTable);
            holder.binding.txtTime.setText(bill.getTime() + " " + bill.getDate());
            holder.binding.txtMount.setText("Số lượng :" + bill.getCheckoutType());
            holder.binding.txtMoney.setText(bill.getTotalPrice() + "");
            holder.binding.imgFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, FeedBackActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        ItemHistoryBinding binding;

        public HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
