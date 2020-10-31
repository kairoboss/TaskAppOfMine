package com.example.taskappofmine;

import android.app.Application;

import androidx.room.Room;

import com.example.taskappofmine.room.AppDataBase;

public class App extends Application {
    private static AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(this, AppDataBase.class,"database").allowMainThreadQueries().build();
    }
    public static AppDataBase getDataBase(){
        return dataBase;
    }
}
