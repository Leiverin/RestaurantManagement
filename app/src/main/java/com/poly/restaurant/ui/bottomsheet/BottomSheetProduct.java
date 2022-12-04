package com.poly.restaurant.ui.bottomsheet;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.poly.restaurant.R;
import com.poly.restaurant.databinding.BottomSheetProductBinding;
import com.poly.restaurant.utils.helps.ViewModelFactory;

public class BottomSheetProduct extends BottomSheetDialogFragment{
    public static String tag = "BottomSheetProduct";
    private BottomSheetProductBinding binding;
    private BottomSheetProductViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetProductBinding.inflate(getLayoutInflater());
        ViewModelFactory factory = new ViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this, factory).get(BottomSheetProductViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int screenHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            screenHeight = requireActivity().getWindowManager().getCurrentWindowMetrics().getBounds().height();
        } else {
            screenHeight = Resources.getSystem().getDisplayMetrics().widthPixels;
        }
        ViewGroup.LayoutParams layoutParams = binding.viewBottomSheet.getLayoutParams();
        layoutParams.height = screenHeight;
        super.onViewCreated(view, savedInstanceState);
    }
}
