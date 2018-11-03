package com.affogatostudios.todolist.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.affogatostudios.todolist.MainActivity;
import com.affogatostudios.todolist.R;
import com.affogatostudios.todolist.VerticalSpaceItemDecoration;
import com.affogatostudios.todolist.adapters.SimpleItemTouchHelperCallback;
import com.affogatostudios.todolist.adapters.ToDoAdapter;
import com.affogatostudios.todolist.data.ToDo;
import com.affogatostudios.todolist.model.TaskViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoFragment extends Fragment {
    private static final String TAG = ToDoFragment.class.getSimpleName();

    private FloatingActionButton fab;
    private ToDoAdapter adapter;

    public ToDoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);
        /*
        TaskViewModel viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        viewModel.setListOfTasks(MainActivity.db.toDoDao().getAll());
        items = viewModel.getListOfTasks();
        */

        final List<ToDo> items = MainActivity.db.toDoDao().getAll();
        Collections.sort(items);

        adapter = new ToDoAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setVerticalFadingEdgeEnabled(true);
        VerticalSpaceItemDecoration decoration = new VerticalSpaceItemDecoration(10);
        recyclerView.addItemDecoration(decoration);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab(fab);
                Toast.makeText(getActivity(), "New ToDo added", Toast.LENGTH_SHORT).show();
                ToDo toDo = new ToDo(null, false, items.size() + 1);
                items.add(toDo);
                adapter.notifyItemInserted(items.size());
                MainActivity.db.toDoDao().addTask(toDo);
            }
        });
        return view;
    }

    private void animateFab(FloatingActionButton fab) {
        fab.setScaleX(0);
        fab.setScaleY(0);
        fab.animate().scaleX(1).scaleY(1).start();
    }
}
