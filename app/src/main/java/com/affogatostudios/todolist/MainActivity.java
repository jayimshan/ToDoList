package com.affogatostudios.todolist;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.affogatostudios.todolist.data.ToDoDatabase;
import com.affogatostudios.todolist.ui.ToDoFragment;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static ToDoDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        db = Room.databaseBuilder(getApplicationContext(), ToDoDatabase.class, "to_do").allowMainThreadQueries().build();
        //db.clearAllTables();

        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragmentManager.beginTransaction().add(R.id.container, new ToDoFragment()).commit();
        }
    }
}
