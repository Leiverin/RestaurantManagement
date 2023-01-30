package com.poly.restaurant.ui.activities.manage.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ItemTableChildBinding;

import java.util.List;
import java.util.Objects;

public class TableChildAdapter extends RecyclerView.Adapter<TableChildAdapter.TableChildViewModel> {
    private Context context;
    private List<Table> mListTable;
    private IOnClickItem onClickItem;

    public TableChildAdapter(Context context, List<Table> mListTable, IOnClickItem onClickItem) {
        this.context = context;
        this.mListTable = mListTable;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public TableChildViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableChildViewModel(ItemTableChildBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TableChildViewModel holder, int position) {
        Table table = mListTable.get(position);
        if (table != null) {
            if (table.getTableParent()==null) {
                holder.binding.viewItem.setEnabled(true);
                holder.binding.tvTableMerge.setVisibility(View.GONE);
                holder.binding.tvTableMerge.setText("");
            } else {
                holder.binding.viewItem.setEnabled(false);
                holder.binding.tvTableMerge.setText("( " +table.getTableParent() +" )");
            }
            holder.binding.tvNameTable.setText(table.getName());
            holder.binding.tvContentTable.setText("Bàn " + table.getCapacity() + " người");
            holder.binding.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItem.onClickChild(table);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListTable != null ? mListTable.size() : 0;
    }

    public class TableChildViewModel extends RecyclerView.ViewHolder {
        public ItemTableChildBinding binding;

        public TableChildViewModel(ItemTableChildBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

interface IOnClickItem {
    void onClickChild(Table table);
}
