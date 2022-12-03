package com.poly.restaurant.ui.activities.manage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.models.TableParent;
import com.poly.restaurant.databinding.ItemTableParentBinding;

import java.util.List;

public class TableManageAdapter extends RecyclerView.Adapter<TableManageAdapter.TableManageViewHolder> {
    private List<TableParent> mListTable;
    private Context context;
    private TableChildAdapter adapter;
    private IOnClickItemParent onClickItem;

    public TableManageAdapter(List<TableParent> mListTable, Context context, IOnClickItemParent onClickItem) {
        this.mListTable = mListTable;
        this.context = context;
        this.onClickItem = onClickItem;
        notifyDataSetChanged();
    }

    public void setList(List<TableParent> mListTable){
        this.mListTable = mListTable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TableManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableManageViewHolder(ItemTableParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableManageViewHolder holder, int position) {
        TableParent tableParent = mListTable.get(position);
        adapter = new TableChildAdapter(context, tableParent.getTables(), new IOnClickItem() {
            @Override
            public void onClickChild(Table table) {
                onClickItem.onClick(table);
            }
        });
        holder.binding.rvTableParent.setAdapter(adapter);
        holder.binding.rvTableParent.setLayoutManager(new GridLayoutManager(context, 3));
        holder.binding.tvTitle.setText(tableParent.getTitle());
    }

    @Override
    public int getItemCount() {
        return mListTable.size();
    }

    public class TableManageViewHolder extends RecyclerView.ViewHolder{
        private ItemTableParentBinding binding;
        public TableManageViewHolder(ItemTableParentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

