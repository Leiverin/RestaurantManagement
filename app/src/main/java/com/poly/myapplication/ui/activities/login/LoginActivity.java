package com.poly.myapplication.ui.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.poly.myapplication.R;
import com.poly.myapplication.databinding.ActivityLoginBinding;
import com.poly.myapplication.ui.activities.MainActivity;
import com.poly.myapplication.ui.notification.NotificationActivity;
import com.poly.myapplication.utils.helps.NetworkChangeListener;

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