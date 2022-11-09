package com.poly.myapplication.ui.bill.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;
import com.poly.myapplication.databinding.ItemBillBinding;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewModel> {
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
    public BillViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillAdapter.BillViewModel(ItemBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewModel holder, int position) {
        Bill bill = mListBill.get(position);
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Table>> call = serviceAPI.getTableByFloor(1);
        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                assert response.body() != null;
                for (int i = 0; i < response.body().size(); i++) {
                    if (Objects.equals(bill.getIdTable(), response.body().get(i).getId())) {
                        holder.binding.txtNameTable.setText(response.body().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
            }
        });
        if (bill != null) {
            holder.binding.txtMoney.setText(" " + bill.getTotalPrice());
            holder.binding.txtTime.setText(bill.getTime());
            holder.binding.txtDate.setText(bill.getDate() + "");
            holder.binding.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener.onClickBill(bill);
                }
            });
            holder.binding.btnDone.setOnClickListener(view -> {
                onListener.onSetStatus(bill.getStatus());
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
