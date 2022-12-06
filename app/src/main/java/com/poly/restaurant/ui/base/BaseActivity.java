package com.poly.restaurant.ui.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.restaurant.utils.helps.NetworkChangeListener;

public abstract class BaseActivity extends AppCompatActivity {
    NetworkChangeListener net = new NetworkChangeListener();
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
