package com.poly.restaurant.ui.activities.merge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ItemTableMergeBinding;

import java.util.List;

public class TableChildMergeAdapter extends RecyclerView.Adapter<TableChildMergeAdapter.TableChildMergeViewModel> {
    private final Context context;
    private final List<Table> mListTable;
    private final OnListener onListener;

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
        if (table != null) {
            holder.binding.tvNameTable.setText(table.getName());
            holder.binding.tvCapacityTable.setText("Bàn " + table.getCapacity() + " người");
            holder.binding.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListener.onClickChild(table);
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
    void onClickChild(Table table);
}

