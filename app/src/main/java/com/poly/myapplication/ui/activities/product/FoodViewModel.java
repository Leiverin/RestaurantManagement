package com.poly.myapplication.ui.activities.product;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Product;

import java.util.List;

public class FoodViewModel extends ViewModel {

    public MutableLiveData<List<Product>> mListProductLiveData;


}
