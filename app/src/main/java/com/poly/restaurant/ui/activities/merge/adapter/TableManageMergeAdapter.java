package com.poly.restaurant.ui.activities.merge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.models.TableParent;
import com.poly.restaurant.databinding.ItemMergeBinding;

import java.util.List;

public class TableManageMergeAdapter extends RecyclerView.Adapter<TableManageMergeAdapter.TableManageMergeViewHolder> {
    private List<TableParent> mListTable;
    private Context context;
    private TableChildMergeAdapter adapter;
    private OnListenerMerge onListenerMerge;

    public TableManageMergeAdapter(List<TableParent> mListTable, Context context, OnListenerMerge onListenerMerge) {
        this.mListTable = mListTable;
        this.context = context;
        this.onListenerMerge = onListenerMerge;
    }

    public void setList(List<TableParent> mListTable) {
        this.mListTable = mListTable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TableManageMergeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableManageMergeViewHolder(ItemMergeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableManageMergeViewHolder holder, int position) {
        TableParent tableParent = mListTable.get(position);
        adapter = new TableChildMergeAdapter(context, tableParent.getTables(), new OnListener() {
            @Override
            public void onClickAddTable(Table table) {
                onListenerMerge.onAddTable(table);
            }

            @Override
            public void onClickDeleteTable(Table table) {
                onListenerMerge.onDeleteTable(table);
            }


        });
        holder.binding.rvTable.setAdapter(adapter);
        holder.binding.tvTitle.setText(tableParent.getTitle());
    }

    @Override
    public int getItemCount() {
        return mListTable.size();
    }

    public class TableManageMergeViewHolder extends RecyclerView.ViewHolder {
        private ItemMergeBinding binding;

        public TableManageMergeViewHolder(ItemMergeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

