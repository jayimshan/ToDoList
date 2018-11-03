package com.affogatostudios.todolist.adapters;

import android.animation.ObjectAnimator;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.affogatostudios.todolist.MainActivity;
import com.affogatostudios.todolist.R;
import com.affogatostudios.todolist.data.ToDo;
import com.affogatostudios.todolist.ui.ToDoFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<ToDo> toDoList;

    private static final String TAG = ToDoAdapter.class.getSimpleName();

    public ToDoAdapter(List<ToDo> toDoList) {
        this.toDoList = toDoList;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_add_item, viewGroup, false);

        /*
        if (i == R.layout.todo_add_item) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_add_item, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todo_button_item, viewGroup, false);
        }
        */

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        /*
        if (i == toDoList.size()) {
            /*
            viewHolder.addTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(viewHolder.itemView.getContext(), "Adding a task...", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            ToDo toDo = toDoList.get(i);
            viewHolder.taskEditView.setText(toDo.getEditTextValue());
        }
        */

        final ToDo toDo = toDoList.get(i);
        viewHolder.taskEditView.setText(toDo.getTask());
        viewHolder.checkBox.setChecked(toDo.isChecked());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkBox.isChecked()) {
                    toDo.setChecked(true);
                    viewHolder.taskEditView.setPaintFlags(viewHolder.taskEditView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    MainActivity.db.toDoDao().updateIsCheckedWithId(toDo.getId(), true);
                    //checkedState[i] = true;
                } else {
                    toDo.setChecked(false);
                    viewHolder.taskEditView.setPaintFlags(0);
                    MainActivity.db.toDoDao().updateIsCheckedWithId(toDo.getId(), false);
                    //checkedState[i] = false;
                }
            }
        });
        if (viewHolder.checkBox.isChecked()) {
            viewHolder.taskEditView.setPaintFlags(viewHolder.taskEditView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.taskEditView.setPaintFlags(0);
        }

        animateEditTextView(viewHolder.taskEditView);
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(toDoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--){
                Collections.swap(toDoList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        sort();
        //MainActivity.db.toDoDao().deleteAll();
        //MainActivity.db.toDoDao().insertAll(newList);
        return true;
    }

    private void sort() {
        for (int i = 0; i < toDoList.size(); i++) {
            ToDo toDo = toDoList.get(i);
            toDo.setOrder(i + 1);
            MainActivity.db.toDoDao().update(toDo);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        ToDo toDo = toDoList.get(position);
        toDoList.remove(position);
        MainActivity.db.toDoDao().delete(toDo);
        notifyItemRemoved(position);
    }

    /*
    @Override
    public int getItemViewType(int position) {
        return (position == toDoList.size()) ? R.layout.todo_button_item : R.layout.todo_add_item;
    }
    */

    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText taskEditView;
        public CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskEditView = itemView.findViewById(R.id.taskEditView);
            checkBox = itemView.findViewById(R.id.checkBox);

            taskEditView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ToDo toDo = toDoList.get(getAdapterPosition());
                    toDo.setTask(taskEditView.getText().toString());
                    //toDoList.get(getAdapterPosition()).setTask(taskEditView.getText().toString());
                    MainActivity.db.toDoDao().updateTaskWithId(toDo.getId(), toDo.getTask());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void animateEditTextView(EditText editText) {
        editText.setScaleX(0.5f);
        editText.setScaleY(0.5f);
        editText.animate().scaleX(1).scaleY(1).start();
    }
}
