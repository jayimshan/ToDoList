package com.affogatostudios.todolist.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ToDoDao {

    @Query("SELECT * FROM to_do")
    List<ToDo> getAll();

    @Insert
    void insertAll(List<ToDo> toDo);

    @Query("DELETE FROM to_do")
    void deleteAll();

    @Insert
    void addTask(ToDo task);

    @Query("UPDATE to_do SET task = :task WHERE id = :id")
    void updateTaskWithId(int id, String task);

    @Query("UPDATE to_do SET checked = :isChecked WHERE id = :id")
    void updateIsCheckedWithId(int id, boolean isChecked);

    @Query("UPDATE to_do SET id = :newId WHERE id = :currentId")
    void updateIdWithId(int currentId, int newId);

    @Update
    void update(ToDo toDo);

    @Query("DELETE FROM to_do WHERE id = :id")
    void deleteTaskWithId(int id);

    @Delete
    void delete(ToDo toDo);
}
