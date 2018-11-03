package com.affogatostudios.todolist.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ToDo.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();
}
