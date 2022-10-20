package com.poly.myapplication.ui.add_product.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.poly.myapplication.ui.add_product.AddProductActivity;
import com.poly.myapplication.ui.add_product.desserts.DessertsFragment;
import com.poly.myapplication.ui.add_product.drinks.DrinksFragment;
import com.poly.myapplication.ui.add_product.food.appetizer.AppetizerFragment;
import com.poly.myapplication.ui.add_product.main_dishes.MainDishesFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AppetizerFragment(); // calls fragment
            case 1:
                return new MainDishesFragment(); // chats fragment
            case 2:
                return new DessertsFragment();
            case 3:
                return new DrinksFragment();
        }
        return new AppetizerFragment();
    }

    @Override
    public int getItemCount() {
        return AddProductActivity.categories.length;
    }
}
