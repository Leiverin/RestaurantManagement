package com.poly.myapplication.ui.bill.adapter;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;

public interface OnListener {
    void onClickBill(Bill bill);
    void onStatus(Bill bill);
}
