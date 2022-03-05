package com.wallet.speedpe.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
    private static String SHARED_PREFERENCE_FILE = "speedpe";
    static String sharedNameValue;
    public static void setStringPreference(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
        editor.commit();
    }

    public static String getStringPreference(Context context, String key){
        if (context!=null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_MULTI_PROCESS);
            sharedNameValue = sharedPreferences.getString(key,"");
        }
        return sharedNameValue;
    }
}
