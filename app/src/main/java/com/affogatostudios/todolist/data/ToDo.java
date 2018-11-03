package com.affogatostudios.todolist.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "to_do")
public class ToDo implements Comparable<ToDo> {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "task")
    private String task;
    @ColumnInfo(name = "checked")
    private boolean isChecked;
    @ColumnInfo(name = "order")
    private int order;

    public ToDo(String task, boolean isChecked, int order) {
        this.task = task;
        this.isChecked = isChecked;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int compareTo(ToDo o) {
        return getOrder() - o.getOrder();
    }
}
