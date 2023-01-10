package com.poly.restaurant.ui.activities.merge.adapter;

import com.poly.restaurant.data.models.Table;

public interface OnListenerMerge {
    void onAddTable(Table table);
    void onDeleteTable(String id);
}
