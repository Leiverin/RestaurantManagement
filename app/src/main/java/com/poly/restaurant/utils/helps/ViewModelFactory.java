package com.poly.restaurant.utils.helps;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.poly.restaurant.ui.activities.login.LoginViewModel;
import com.poly.restaurant.ui.activities.product.FoodViewModel;
import com.poly.restaurant.ui.activities.product.appetizer.AppetizerViewModel;
import com.poly.restaurant.ui.activities.product.desserts.DessertViewModel;
import com.poly.restaurant.ui.activities.product.drinks.DrinksViewModel;
import com.poly.restaurant.ui.activities.product.main.MainDishesViewModel;
import com.poly.restaurant.ui.activities.table.TableDetailViewModel;
import com.poly.restaurant.ui.activities.verify.VerifyViewModel;
import com.poly.restaurant.ui.bottomsheet.BottomSheetProductViewModel;
import com.poly.restaurant.ui.notification.receiptnotice.ReceiptNoticeViewModel;
import com.poly.restaurant.ui.notification.sendnotification.SendNotificationViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FoodViewModel.class) {
            return (T) new FoodViewModel(context);
        } else if (modelClass == DrinksViewModel.class) {
            return (T) new DrinksViewModel(context);
        } else if (modelClass == BottomSheetProductViewModel.class) {
            return (T) new BottomSheetProductViewModel(context);
        } else if (modelClass == AppetizerViewModel.class) {
            return (T) new AppetizerViewModel(context);
        } else if (modelClass == DessertViewModel.class) {
            return (T) new DessertViewModel(context);
        } else if (modelClass == VerifyViewModel.class) {
            return (T) new VerifyViewModel(context);
        } else if (modelClass == MainDishesViewModel.class) {
            return (T) new MainDishesViewModel(context);
        } else if (modelClass == TableDetailViewModel.class) {
            return (T) new TableDetailViewModel(context);
        } else if (modelClass == LoginViewModel.class) {
            return (T) new LoginViewModel(context);
        } else if (modelClass == ReceiptNoticeViewModel.class) {
            return (T) new ReceiptNoticeViewModel(context);
        } else if (modelClass == SendNotificationViewModel.class) {
            return (T) new SendNotificationViewModel(context);
        } else {
            return ViewModelProvider.Factory.super.create(modelClass);
        }
    }
}
