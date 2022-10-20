package com.poly.myapplication.ui.add_product.main_dishes;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.myapplication.R;
import com.poly.myapplication.databinding.FragmentMainDishesBinding;

public class MainDishesFragment extends Fragment {

    private MainDishesViewModel mViewModel;
    private FragmentMainDishesBinding binding;

    public static MainDishesFragment newInstance() {
        return new MainDishesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View view = binding.getRoot();

      return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainDishesViewModel.class);
        // TODO: Use the ViewModel
    }

}