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
import com.poly.restaurant.databinding.DialogAnnounceBinding;
import com.poly.restaurant.databinding.DialogRequestBinding;
import com.poly.restaurant.ui.dialog.interfaces.IOnEventDialogListener;
import com.poly.restaurant.utils.Constants;

public class DialogRequest extends DialogFragment {
    private DialogRequestBinding binding;
    private IOnEventDialogListener onEventDialogListener;

    public DialogRequest(IOnEventDialogListener onEventDialogListener) {
        this.onEventDialogListener = onEventDialogListener;
    }

    //    public static DialogRequest getInstance(String content){
//        DialogRequest instance = new DialogRequest();
//        Bundle args = new Bundle();
//        args.putString(Constants.STRING_CONTENT_TO_ANNOUNCE, content);
//        instance.setArguments(args);
//        return instance;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogRequestBinding.inflate(getLayoutInflater());
//        String content = getArguments().getString(Constants.STRING_CONTENT_TO_ANNOUNCE);
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
                onEventDialogListener.onClickPositive();
                dismiss();
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
