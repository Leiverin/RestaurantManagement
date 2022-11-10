package com.poly.myapplication.ui.history;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ActivityHistoryBinding;
import com.poly.myapplication.ui.history.adapter.HistoryAdapter;

import java.util.List;


public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<Bill> list;
    private HistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initRec() {

    }

    private void initVieModel() {

    }
}