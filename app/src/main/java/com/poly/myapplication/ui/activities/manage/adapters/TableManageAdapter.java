package com.poly.myapplication.ui.activities.manage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.models.TableParent;
import com.poly.myapplication.databinding.ItemTableParentBinding;

import java.util.List;

public class TableManageAdapter extends RecyclerView.Adapter<TableManageAdapter.TableManageViewHolder> {
    private List<TableParent> mListTable;
    private Context context;

    public TableManageAdapter(List<TableParent> mListTable, Context context) {
        this.mListTable = mListTable;
        this.context = context;
    }

    @NonNull
    @Override
    public TableManageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableManageViewHolder(ItemTableParentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableManageViewHolder holder, int position) {
        TableParent table = mListTable.get(position);

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
