package com.example.taskappofmine.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskappofmine.models.Task;

@Database(entities = {Task.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
