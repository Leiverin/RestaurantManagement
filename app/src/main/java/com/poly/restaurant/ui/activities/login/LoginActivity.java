package com.poly.restaurant.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.databinding.ActivityLoginBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.manage.TableManageActivity;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.contact.ContactActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private AppSharePreference sharePreference;
    private FirebaseMessaging fcm;
    private String mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.prgLoadTable.setVisibility(View.GONE);
        sharePreference = new AppSharePreference(this);
        fcm = FirebaseMessaging.getInstance();
        fcm.getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                mToken = task.getResult();
            }
        });
        if (sharePreference.getRemember()) {
            binding.edUsername.setText(sharePreference.getUsername());
            binding.edPass.setText(sharePreference.getPassword());
            binding.cbxRemember.setChecked(true);
        } else {
            binding.edUsername.setText("");
            binding.edPass.setText("");
            binding.cbxRemember.setChecked(false);
        }
        viewModel.mStaffLiveData.observe(this, new Observer<Staff>() {
            @Override
            public void onChanged(Staff staff) {
                binding.prgLoadTable.setVisibility(View.GONE);
                if (staff != null) {
                    String username = binding.edUsername.getText().toString().trim();
                    String password = binding.edPass.getText().toString().trim();
                    sharePreference.setUsername(username);
                    sharePreference.setPassword(password);
                    if (staff.getRole() == 0){
                        Constants.staff = new Staff(
                                staff.getId(),
                                staff.getName(),
                                staff.getPhoneNumber(),
                                staff.getGender(),
                                staff.getAccount(),
                                staff.getPassword(),
                                staff.getRole(),
                                staff.getFloor(),
                                mToken
                        );
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, TableManageActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Vui lòng đăng nhập bằng tài khoản nhân viên", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect account or password", Toast.LENGTH_SHORT).show();
                }
                binding.btnLogin.setEnabled(true);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.edUsername.getText().toString().trim();
                String password = binding.edPass.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    binding.prgLoadTable.setVisibility(View.VISIBLE);
                    viewModel.callToLogin(username, password, mToken);
                    binding.btnLogin.setEnabled(false);
                    sharePreference.setRemember(binding.cbxRemember.isChecked());
                }
            }
        });
        binding.txtContactManager.setOnClickListener(view -> {
            startActivity(new Intent(this, ContactActivity.class));
        });
    }
}