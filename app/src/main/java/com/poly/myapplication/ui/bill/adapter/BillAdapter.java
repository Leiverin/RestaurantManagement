package com.poly.myapplication.ui.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ItemBillBinding;
import com.poly.myapplication.utils.Constants;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {
    private Context context;
    private List<Bill> mListBill;
    private OnListener onListener;

    public BillAdapter(Context context, List<Bill> mListBill, OnListener onListener) {
        this.context = context;
        this.mListBill = mListBill;
        this.onListener = onListener;
    }

    public void setList(List<Bill> mListBill) {
        this.mListBill = mListBill;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillAdapter.BillHolder(ItemBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {
        Bill bill = mListBill.get(position);
        if (bill != null) {
            Constants.setNameTable(bill, holder.binding.txtNameTable);
            holder.binding.txtMoney.setText(" " + bill.getTotalPrice());
            holder.binding.txtTime.setText(bill.getTime());
            holder.binding.txtDate.setText(bill.getDate() + "");
            holder.binding.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener.onClickBill(bill);
                }
            });
            holder.binding.btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener.onStatus(bill);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mListBill != null ? mListBill.size() : 0;
    }

    public class BillHolder extends RecyclerView.ViewHolder {
        public ItemBillBinding binding;

        public BillHolder(ItemBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
