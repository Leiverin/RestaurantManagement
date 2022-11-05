package com.poly.myapplication.ui.activities.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.models.TableParent;
import com.poly.myapplication.databinding.ActivityTableManageBinding;
import com.poly.myapplication.ui.activities.manage.adapters.IOnClickItemParent;
import com.poly.myapplication.ui.activities.manage.adapters.TableManageAdapter;
import com.poly.myapplication.ui.activities.table.TableDetailActivity;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class TableManageActivity extends AppCompatActivity {
    private ActivityTableManageBinding binding;
    private TableManageViewModel viewModel;
    private TableManageAdapter adapter;
    private List<Table> mListTables;
    private List<TableParent> mListTableMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel =  new ViewModelProvider(this).get(TableManageViewModel.class);
        binding = ActivityTableManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        binding.prgLoadTable.setVisibility(View.VISIBLE);

        mListTables = new ArrayList<>();
        mListTableMain = new ArrayList<>();

        adapter = new TableManageAdapter(mListTableMain, this, new IOnClickItemParent() {
            @Override
            public void onClick(Table table) {
                Intent intent = new Intent(TableManageActivity.this, TableDetailActivity.class);
                intent.putExtra(Constants.EXTRA_TABLE_TO_DETAIL, table);
                startActivity(intent);
            }
        });
        binding.rvTable.setAdapter(adapter);
        binding.rvTable.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewModel.mListTableLiveData.observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                mListTables = tables;
                mListTableMain = getListTableMain();
                binding.prgLoadTable.setVisibility(View.GONE);
                adapter.setList(mListTableMain);
            }
        });
        viewModel.callToGetTable();
    }

    private List<TableParent> getListTableMain(){
        List<TableParent> mTableMain = new ArrayList<>();
        mTableMain.add(new TableParent("Empty table", mListTables));
        return mTableMain;
    }
}