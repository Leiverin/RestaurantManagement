package com.poly.restaurant.ui.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.poly.restaurant.R;
import com.poly.restaurant.databinding.ActivityLoginBinding;
import com.poly.restaurant.utils.helps.NetworkChangeListener;

public class LoginActivity extends AppCompatActivity {
    NetworkChangeListener net = new NetworkChangeListener();
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(net, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(net);
        super.onStop();
    }
}