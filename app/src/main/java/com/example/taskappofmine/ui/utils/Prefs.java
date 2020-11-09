package com.example.taskappofmine.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static SharedPreferences preferences;
    private String name;
    public Prefs(Context context){
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void setShown(){
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown(){
        return preferences.getBoolean("isShown", false);
    }
    public void clean(){
        preferences.edit().clear().apply();
    }

    public void putString(String key,String name){
        preferences.edit().putString("name", name).apply();}
    public String getString(String key){
        if (preferences.contains("name")){
             name = preferences.getString("name", "");}
        else {
            name = "KAIRAT";
        }
        return name;
    }
}
