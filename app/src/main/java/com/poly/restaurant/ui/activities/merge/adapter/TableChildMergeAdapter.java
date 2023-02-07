package com.poly.restaurant.ui.activities.merge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ItemTableMergeBinding;
import com.poly.restaurant.preference.AppSharePreference;

import java.util.List;
import java.util.Objects;

public class TableChildMergeAdapter extends RecyclerView.Adapter<TableChildMergeAdapter.TableChildMergeViewModel> {
    private final Context context;
    private final List<Table> mListTable;
    private final OnListener onListener;
    private AppSharePreference sharePreference;

    public TableChildMergeAdapter(Context context, List<Table> mListTable, OnListener onListener) {
        this.context = context;
        this.mListTable = mListTable;
        this.onListener = onListener;
    }

    @NonNull
    @Override
    public TableChildMergeViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableChildMergeViewModel(ItemTableMergeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TableChildMergeViewModel holder, int position) {
        Table table = mListTable.get(position);
        sharePreference = new AppSharePreference(context);
        if (table != null) {
            if (Objects.equals(table.getId(), sharePreference.getTableId())) {
                holder.itemView.setVisibility(View.GONE);
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
            holder.binding.tvNameTable.setText(table.getName());
            holder.binding.tvCapacityTable.setText("Bàn " + table.getCapacity() + " người");
            holder.binding.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.binding.clickMerge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        holder.binding.viewItem.setCardBackgroundColor(Color.parseColor("#FF7F50"));
                        holder.binding.tvNameTable.setTextColor(Color.WHITE);
                        holder.binding.tvCapacityTable.setTextColor(Color.WHITE);
                        holder.binding.imgTable.setColorFilter(Color.WHITE);
                        onListener.onClickAddTable(table);
                    } else {
                        holder.binding.viewItem.setCardBackgroundColor(Color.WHITE);
                        holder.binding.tvNameTable.setTextColor(Color.BLACK);
                        holder.binding.tvCapacityTable.setTextColor(Color.BLACK);
                        holder.binding.imgTable.setColorFilter(Color.BLACK);
                        onListener.onClickDeleteTable(table);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mListTable != null ? mListTable.size() : 0;
    }

    public class TableChildMergeViewModel extends RecyclerView.ViewHolder {
        public ItemTableMergeBinding binding;

        public TableChildMergeViewModel(ItemTableMergeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

interface OnListener {
    void onClickAddTable(Table table);
    void onClickDeleteTable(Table table);
}

