package com.poly.myapplication.ui.activities.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Staff;
import com.poly.myapplication.databinding.ActivityLoginBinding;
import com.poly.myapplication.utils.helps.ViewModelFactory;

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

        viewModel.mStaffLiveData.observe(this, new Observer<Staff>() {
            @Override
            public void onChanged(Staff staff) {
                if (staff != null){
                    Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.edUsername.getText().toString().trim();
                String password = binding.edPass.getText().toString().trim();
                viewModel.callToLogin(username, password);
            }
        });
    }
}