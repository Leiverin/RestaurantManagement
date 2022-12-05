package com.poly.restaurant.ui.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.databinding.ActivityLoginBinding;
import com.poly.restaurant.ui.activities.manage.TableManageActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.prgLoadTable.setVisibility(View.GONE);
        viewModel.mStaffLiveData.observe(this, new Observer<Staff>() {
            @Override
            public void onChanged(Staff staff) {
                binding.prgLoadTable.setVisibility(View.GONE);
                if (staff != null) {
                    Constants.staff = staff;
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, TableManageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect account or password", Toast.LENGTH_SHORT).show();
                }
                binding.btnLogin.setEnabled(true);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.prgLoadTable.setVisibility(View.VISIBLE);
                String username = binding.edUsername.getText().toString().trim();
                String password = binding.edPass.getText().toString().trim();
                if (username.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.callToLogin(username, password);
                    binding.btnLogin.setEnabled(false);
                }
            }
        });
    }
}