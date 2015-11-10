package com.dizarale.deliverone.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.dizarale.deliverone.object.FoodItem;
import com.dizarale.deliverone.object.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s84021369 on 8/28/2015.
 */
public class AppSharedPreferences {
    // Setter method for the Navigation Drawer state
    public static void setUserLearned(Context context, String prefName, String prefValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.DIZARALE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }
    public static void setPhone(Context context, String prefName, String prefValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREF_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(prefName, prefValue);
        editor.apply();
    }

    // Getter method for the Navigation Drawer state
    public static String hasUserLearned(Context context, String prefName, String defaultValue) {
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.DIZARALE_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(prefName, defaultValue);
    }

    public static void setLatLong(Context context, double lat, double lon){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.DIZARALE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("lat",(float)lat);
        editor.putFloat("lon",(float)lon);
        editor.apply();
    }

    public static double getLat(Context context, String prefLat, double defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.DIZARALE_PREFERENCE, Context.MODE_PRIVATE);
        return (double)sharedPreferences.getFloat(prefLat, (float) defaultValue);
    }
    public static double getLon(Context context, String prefLon, double defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.DIZARALE_PREFERENCE, Context.MODE_PRIVATE);
        return (double) sharedPreferences.getFloat(prefLon, (float) defaultValue);
    }

    public static boolean hasPhoneNumber(Context context,String prefName,boolean defaultValue){
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREF_PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(prefName, defaultValue);
    }
    public static void setHasPhoneNumber(Context context, String prefName,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREF_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(prefName, value);
        editor.apply();

    }

    public static void setNamePhone(Context context,String name, String phone){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREF_PHONE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstant.PREF_PHONE_VALUE,phone);
        editor.putString(AppConstant.PREF_NAME_VALUE, name);
        editor.apply();
    }
    public static String getName(Context context){
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREF_PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.PREF_NAME_VALUE, null);
    }
    public static String getPhone(Context context){
        android.content.SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREF_PHONE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AppConstant.PREF_PHONE_VALUE, null);
    }

}
