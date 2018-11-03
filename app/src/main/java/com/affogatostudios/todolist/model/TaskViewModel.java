package com.affogatostudios.todolist.model;

import android.arch.lifecycle.ViewModel;

import com.affogatostudios.todolist.data.ToDo;

import java.util.Collections;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private List<ToDo> listOfTasks;

    public List<ToDo> getListOfTasks() {
        Collections.sort(listOfTasks);
        return listOfTasks;
    }

    public void setListOfTasks(List<ToDo> listOfTasks) {
        this.listOfTasks = listOfTasks;
    }
}
