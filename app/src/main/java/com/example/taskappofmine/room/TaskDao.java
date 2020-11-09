package com.example.taskappofmine.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskappofmine.models.Task;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task ")
    List<Task> getAll();
    @Query("SELECT *FROM task ORDER BY created_time DESC")
    List<Task> reverseOrderByDate();
    @Query("SELECT * FROM task ORDER BY created_time ASC")
    List<Task> orderByDate();
    @Query("SELECT *FROM task ORDER BY task_title DESC")
    List<Task> reverseOrderByAlphabet();
    @Query("SELECT * FROM task ORDER BY task_title ASC")
    List<Task> orderByAlphabet();
    @Insert
    void insert(Task task);
    @Delete
    void delete(Task task);

}
