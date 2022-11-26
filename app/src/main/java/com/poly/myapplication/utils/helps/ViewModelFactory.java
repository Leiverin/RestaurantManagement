package com.poly.myapplication.utils.helps;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.poly.myapplication.ui.activities.product.FoodViewModel;
import com.poly.myapplication.ui.activities.product.drinks.DrinksViewModel;
import com.poly.myapplication.ui.bottomsheet.BottomSheetProductViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FoodViewModel.class){
            return (T) new FoodViewModel(context);
        }else if (modelClass == DrinksViewModel.class){
            return (T) new DrinksViewModel(context);
        }else if (modelClass == BottomSheetProductViewModel.class){
            return (T) new BottomSheetProductViewModel(context);
        }else{
            return ViewModelProvider.Factory.super.create(modelClass);
        }
    }
}
