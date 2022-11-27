package com.poly.myapplication.ui.activities.verify;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.poly.myapplication.databinding.ActivityVerifyBinding;
import com.poly.myapplication.utils.helps.ViewModelFactory;

public class VerifyActivity extends AppCompatActivity {
    private ActivityVerifyBinding binding;
    private VerifyViewModel mViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        ViewModelFactory factory = new ViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, factory).get(VerifyViewModel.class);
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
