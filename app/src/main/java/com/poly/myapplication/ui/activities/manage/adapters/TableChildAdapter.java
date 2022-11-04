package com.poly.myapplication.ui.activities.manage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.databinding.ItemTableChildBinding;

import java.util.List;

public class TableChildAdapter extends RecyclerView.Adapter<TableChildAdapter.TableChildViewModel> {
    private Context context;
    private List<Table> mListTable;

    public TableChildAdapter(Context context, List<Table> mListTable) {
        this.context = context;
        this.mListTable = mListTable;
    }

    @NonNull
    @Override
    public TableChildViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableChildViewModel(ItemTableChildBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableChildViewModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mListTable.size();
    }

    public class TableChildViewModel extends RecyclerView.ViewHolder{
        public ItemTableChildBinding binding;
        public TableChildViewModel(ItemTableChildBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
