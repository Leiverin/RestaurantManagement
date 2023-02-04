package com.poly.restaurant.ui.dialog;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.poly.restaurant.R;
import com.poly.restaurant.databinding.DialogQuestionBackBinding;

public class DialogQuestionBack extends DialogFragment {
    private DialogQuestionBackBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogQuestionBackBinding.inflate(getLayoutInflater());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().setCancelable(true);
        }

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = screenWidth - screenWidth/8;
        params.windowAnimations = R.style.DialogAnimation;
        getDialog().getWindow().setAttributes(params);

        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                requireActivity().onBackPressed();
            }
        });
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }



}
