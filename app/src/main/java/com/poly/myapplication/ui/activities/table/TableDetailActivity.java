package com.poly.myapplication.ui.activities.table;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.databinding.ActivityTableDetailBinding;
import com.poly.myapplication.utils.Constants;

public class TableDetailActivity extends AppCompatActivity {
    private ActivityTableDetailBinding binding;
    private TableDetailViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TableDetailViewModel.class);
        binding = ActivityTableDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));

        Table table = getIntent().getParcelableExtra(Constants.EXTRA_TABLE_TO_DETAIL);
        binding.tvNameTable.setText(table.getName());
        binding.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}