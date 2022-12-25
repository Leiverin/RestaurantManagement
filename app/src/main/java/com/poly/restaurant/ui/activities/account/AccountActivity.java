package com.poly.restaurant.ui.activities.account;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;

import com.google.android.material.snackbar.Snackbar;
import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.databinding.ActivityAccountBinding;
import com.poly.restaurant.databinding.DialogChangePassBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.login.LoginActivity;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.utils.Constants;

public class AccountActivity extends BaseActivity {
    private ActivityAccountBinding binding;
    private AppSharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharePreference = new AppSharePreference(AccountActivity.this);
        initData(Constants.staff);
        binding.passAccount.setOnClickListener(view -> {
            showDialogChangePass();
            binding.passAccount.setEnabled(false);
        });
        binding.nameAccount.setOnClickListener(view -> {
            Snackbar.make(binding.getRoot(), "Thông tin tên đầy đủ không được sửa !", Snackbar.LENGTH_LONG).show();
        });
        binding.genderAccount.setOnClickListener(view -> {
            Snackbar.make(binding.getRoot(), "Thông tin giới tính không được sửa !", Snackbar.LENGTH_LONG).show();
        });
        binding.phoneAccount.setOnClickListener(view -> {
            Snackbar.make(binding.getRoot(), "Thông tin số điện thoại không được sửa !", Snackbar.LENGTH_LONG).show();
        });
        binding.btnLogout.setOnClickListener(view -> {
            sharePreference.setRemember(false);
            Constants.staff = null;
            Intent i = new Intent(AccountActivity.this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });
    }

    private void showDialogChangePass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogChangePassBinding changePassBinding = DialogChangePassBinding.inflate(LayoutInflater.from(this));
        builder.setView(changePassBinding.getRoot());
        AlertDialog dialog = builder.create();
        changePassBinding.btnNo.setOnClickListener(view -> {
            dialog.dismiss();
            binding.passAccount.setEnabled(true);
        });
        changePassBinding.btnYes.setOnClickListener(view -> {
            String passOld = changePassBinding.passOld.getText().toString().trim();
            String passNew = changePassBinding.passNew.getText().toString().trim();
            String passNewAgain = changePassBinding.passNewAgain.getText().toString().trim();
            if (passOld.equals("") || passNew.equals("") || passNewAgain.equals("")) {
                Snackbar.make(changePassBinding.getRoot(), "Vui lòng điền đầy đủ thông tin !", Snackbar.LENGTH_LONG).show();
            } else if (!passOld.equals(Constants.staff.getPassword())) {
                Snackbar.make(changePassBinding.getRoot(), "Mật khẩu cũ không đúng !", Snackbar.LENGTH_LONG).show();
            } else if (!passNew.equals(passNewAgain)) {
                Snackbar.make(changePassBinding.getRoot(), "Mật khẩu mới không khớp !", Snackbar.LENGTH_LONG).show();
            } else {
                Constants.changePasswordStaff(passNewAgain);
                sharePreference.setPassword(passNewAgain);
                binding.password.setText(passNewAgain);
                binding.passAccount.setEnabled(true);
                Snackbar.make(changePassBinding.getRoot(), "Đổi mật khẩu thành công !", Snackbar.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }

    private void initData(Staff staff) {
        binding.tvName.setText(staff.getName());
        binding.tvNameAccount.setText(staff.getAccount());
        if (staff.getRole() == 0 || staff.getGender() == 0) {
            binding.tvNameRole.setText("Nhân viên");
            binding.tvGender.setText("Nam");
        } else if (staff.getRole() == 1 || staff.getGender() == 1) {
            binding.tvNameRole.setText("Admin");
            binding.tvGender.setText("Nữ");
        }
        binding.password.setText(staff.getPassword());
        binding.nameStaff.setText(staff.getName());
        binding.tvPhone.setText(staff.getPhoneNumber());
    }
}