package com.poly.restaurant.ui.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.databinding.ItemHistoryBinding;
import com.poly.restaurant.ui.bill.adapter.OnListener;

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
        return new HistoryAdapter.HistoryViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Bill bill = list.get(position);
        if (bill != null) {
            holder.binding.txtNameTable.setText(bill.getTable().getName());
            holder.binding.txtTime.setText(bill.getTime() + " " + bill.getDate());
            holder.binding.txtMount.setText("Số lượng :" + bill.getProducts().size());
            holder.binding.txtNameTable.setText(bill.getTable().getName());
            int totalPrice = (int) (bill.getTotalPrice() * 23000);
            holder.binding.txtMoney.setText(totalPrice + " vnđ");
            holder.binding.imgFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener.onClickFeedback(bill);
                }
            });
            holder.binding.itemView.setOnClickListener(view -> {
                onListener.onClickBill(bill, holder.binding.itemView);
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
