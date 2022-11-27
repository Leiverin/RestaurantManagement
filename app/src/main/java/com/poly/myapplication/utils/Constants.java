package com.poly.myapplication.utils;

import android.annotation.SuppressLint;
import android.widget.TextView;

@SuppressLint("SetTextI18n")
public class Constants {
    public static String EXTRA_TABLE_TO_DETAIL = "extra_table_to_detail";
    public static String TABLE_ID_SELECTED = "TABLE_ID_SELECTED";
    public static int TYPE_IN_TABLE = 0;
    public static int TYPE_IN_PRODUCT = 1;

    public static void handleIncrease(TextView tvQuantity, int type){
        if (type == TYPE_IN_TABLE){
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            quantity++;
            tvQuantity.setText(quantity+"");
        }else{
            int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
            quantity++;
            tvQuantity.setText("x"+quantity);
        }

    }

    public static void handleDecrease(TextView tvQuantity, int type){
        if (type == TYPE_IN_TABLE){
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            if (quantity > 0){
                quantity--;
                tvQuantity.setText(quantity+"");
            }
        }else{
            int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
            if (quantity > 0){
                quantity--;
                tvQuantity.setText("x"+quantity);
            }
        }
    }
}
