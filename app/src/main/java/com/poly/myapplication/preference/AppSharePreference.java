package com.poly.myapplication.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.poly.myapplication.utils.Constants;

public class AppSharePreference {
    private Context context;

    public AppSharePreference(Context context) {
        this.context = context;
    }

    public SharedPreferences getRef(Context context){
        SharedPreferences preferences = context.getSharedPreferences(
                context.getPackageName(),
                Context.MODE_PRIVATE
        );
        return preferences;
    }

    public void setTableId(String tableId){
        getRef(context).edit().putString(Constants.TABLE_ID_SELECTED, tableId).apply();
    }

    public String getTableId(){
        return getRef(context).getString(Constants.TABLE_ID_SELECTED, "");
    }
}
