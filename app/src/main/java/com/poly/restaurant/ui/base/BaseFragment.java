package com.poly.restaurant.ui.base;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.fragment.app.Fragment;

import com.poly.restaurant.utils.helps.NetworkChangeListener;


public abstract class BaseFragment extends Fragment {
    NetworkChangeListener net = new NetworkChangeListener();

    @Override
    public void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(net, intentFilter);
        super.onStart();
    }

    @Override
    public void onStop() {
        requireActivity().unregisterReceiver(net);
        super.onStop();
    }
}
