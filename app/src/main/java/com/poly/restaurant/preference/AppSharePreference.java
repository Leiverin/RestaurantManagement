package com.poly.restaurant.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.poly.restaurant.utils.Constants;

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

    public void setIdStaff(String idStaff){
        getRef(context).edit().putString(Constants.KEY_ID_STAFF, idStaff).apply();
    }

    public String getIdStaff(){
        return getRef(context).getString(Constants.KEY_ID_STAFF, "");
    }

    public void setUsername(String idStaff){
        getRef(context).edit().putString(Constants.KEY_USERNAME, idStaff).apply();
    }

    public String getUsername(){
        return getRef(context).getString(Constants.KEY_USERNAME, "");
    }

    public void setPassword(String idStaff){
        getRef(context).edit().putString(Constants.KEY_PASSWORD, idStaff).apply();
    }

    public String getPassword(){
        return getRef(context).getString(Constants.KEY_PASSWORD, "");
    }

    public void setRemember(boolean isChecked){
        getRef(context).edit().putBoolean(Constants.KEY_REMEMBER, isChecked).apply();
    }

    public Boolean getRemember(){
        return getRef(context).getBoolean(Constants.KEY_REMEMBER, false);
    }

    public void setBeforeTableId(String tableId){
        getRef(context).edit().putString(Constants.KEY_BEFORE_TABLE_ID, tableId).apply();
    }

    public String getBeforeTableId(){
        return getRef(context).getString(Constants.KEY_BEFORE_TABLE_ID, "");
    }
}
