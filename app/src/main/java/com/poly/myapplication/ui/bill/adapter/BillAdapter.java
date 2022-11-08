package com.poly.myapplication.ui.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ItemBillBinding;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewModel> {
    private Context context;
    private List<Bill> mListBill;
    private OnListener onListener;

    public BillAdapter(Context context, List<Bill> mListBill, OnListener onListener) {
        this.context = context;
        this.mListBill = mListBill;
        this.onListener = onListener;
    }

    public void setList(List<Bill> mListBill){
        this.mListBill = mListBill;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BillViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillAdapter.BillViewModel(ItemBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewModel holder, int position) {
        Bill bill = mListBill.get(position);
        if (bill != null) {
            holder.binding.txtNameTable.setText(bill.getIdTable());
            holder.binding.txtMoney.setText(bill.getTotalPrice() + "");
            holder.binding.txtTime.setText(bill.getTime());
            holder.binding.txtDate.setText("date" + bill.getTime());
            holder.binding.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener.onClickBill(bill);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListBill != null ? mListBill.size() : 0;
    }

    public class BillViewModel extends RecyclerView.ViewHolder {
        public ItemBillBinding binding;

        public BillViewModel(ItemBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
