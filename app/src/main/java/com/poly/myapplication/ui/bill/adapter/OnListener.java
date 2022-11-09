package com.poly.myapplication.ui.bill.adapter;

import com.poly.myapplication.data.models.Bill;

public interface OnListener {
    void onClickBill(Bill bill);
    void onSetStatus(int status);
}
