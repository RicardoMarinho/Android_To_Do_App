package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.todoapp.Adapter.ToDoAdapter;
import com.example.todoapp.Helper.DataBaseHelper;
import com.example.todoapp.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DataBaseHelper helper;
    private List<Task> taskList;
    private ToDoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        helper = new DataBaseHelper(MainActivity.this);
        taskList = new ArrayList<>();
        adapter = new ToDoAdapter(helper, MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        taskList = helper.List();
        Collections.reverse(taskList);
        adapter.setTaskList(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        taskList = helper.List();
        Collections.reverse(taskList);
        adapter.setTaskList(taskList);
        adapter.notifyDataSetChanged();
    }
}