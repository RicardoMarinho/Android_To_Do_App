package com.example.todoapp.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AddNewTask;
import com.example.todoapp.Helper.DataBaseHelper;
import com.example.todoapp.MainActivity;
import com.example.todoapp.Model.Task;
import com.example.todoapp.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private List<Task> taskList;
    private MainActivity activity;
    private DataBaseHelper helper;

    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayerDelete;

    public ToDoAdapter( DataBaseHelper helper, MainActivity activity){
        this.activity = activity;
        this.helper = helper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.check_mark);
        mediaPlayerDelete = MediaPlayer.create(getContext(), R.raw.delete);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Task task = taskList.get(position);
        holder.checkBox.setText(task.get_name());
        holder.checkBox.setChecked(parseBoolean(task.get_status()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    helper.updateStatus(task, 1);
                } else {
                    helper.updateStatus(task, 0);
                }
                mediaPlayer.start();
            }
        });
    }

    public boolean parseBoolean (int i){
        return i!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTaskList(List<Task> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        Task task = taskList.get(position);
        helper.Delete(task);
        taskList.remove(position);
        notifyItemRemoved(position);
        mediaPlayerDelete.start();
    }

    public void editTask(int position){
        Task task = taskList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("UID", task.get_uid());
        bundle.putString("NAME", task.get_name());

        AddNewTask addNewTask = new AddNewTask();
        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.mcheckbox);
        }
    }
}
