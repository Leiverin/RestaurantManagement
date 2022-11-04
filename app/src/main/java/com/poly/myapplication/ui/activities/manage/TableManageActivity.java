package com.poly.myapplication.ui.activities.manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.databinding.ActivityTableManageBinding;

import java.util.List;

public class TableManageActivity extends AppCompatActivity {
    private ActivityTableManageBinding binding;
    private TableManageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel =  new ViewModelProvider(this).get(TableManageViewModel.class);
        binding = ActivityTableManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel.mListTableLiveData.observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                Log.d("TAG", "onChanged: "+ new Gson().toJson(tables));
            }
        });
        viewModel.callToGetTable();
    }
}