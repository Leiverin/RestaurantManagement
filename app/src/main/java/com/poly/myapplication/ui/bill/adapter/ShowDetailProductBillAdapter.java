package com.poly.myapplication.ui.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.databinding.ItemShowDetailBillBinding;

import java.util.List;

public class ShowDetailProductBillAdapter extends RecyclerView.Adapter<ShowDetailProductBillAdapter.DetailBillHolder> {
    private Context context;
    private List<Product> list;

    public ShowDetailProductBillAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Product> products) {
        this.list = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowDetailProductBillAdapter.DetailBillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowDetailProductBillAdapter.DetailBillHolder(ItemShowDetailBillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDetailProductBillAdapter.DetailBillHolder holder, int position) {
        Product product = list.get(position);
        if (product != null) {
            holder.binding.tvName.setText(product.getName());
            holder.binding.tvPrice.setText(product.getPrice() + "$");
            holder.binding.tvQuantity.setText("Số lượng : " + product.getAmount());
            Glide.with(context).load(product.getUrlImage()).into(holder.binding.imgProduct);
            holder.binding.cardView.setOnClickListener(view -> {

            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class DetailBillHolder extends RecyclerView.ViewHolder {
        private ItemShowDetailBillBinding binding;

        public DetailBillHolder(ItemShowDetailBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
