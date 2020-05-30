package com.pratik.taskapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

class TaskEditPresenterImpl implements TaskEditPresenter{
    TasksInteractor interactor;
    TaskEditActivity taskEditActivity;
    private boolean forNewTask = false;
//    private Gson gson = new Gson();
    Task task;


    TaskEditPresenterImpl(TaskEditActivity activity, TasksInteractor interactor){
        this.taskEditActivity = activity;
        this.interactor = interactor;
    }


    @Override
    public void initialize() {
        Intent intent = taskEditActivity.getIntent();
//        String taskJson = intent.getStringExtra(MainActivity.EDIT_TASK_KEY);
        int taskId = intent.getIntExtra(MainActivity.EDIT_TASK_KEY, -1);
        if(taskId==-1){
            forNewTask = true;
        }
        else{
            forNewTask = false;
//            Type type = new TypeToken<Task>(){}.getType();
//            task = gson.fromJson(taskJson, type);
            task = interactor.findTaskWithId(taskId);
            taskEditActivity.setTaskTitle(task.getTitle());
            taskEditActivity.setTaskBody(task.getBody());
        }
    }

    @Override
    public void onDeleteButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(taskEditActivity.getContext());
        AlertDialog dialog = builder.setTitle("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!forNewTask){
                            interactor.deleteTask(task);
                        }
                        taskEditActivity.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true).create();
        dialog.show();
    }

    @Override
    public void onSaveButtonClick() {
        if(forNewTask){
            task = interactor.createNewTask(taskEditActivity.getTaskTitle(), taskEditActivity.getTaskBody());
            forNewTask = false;
        }else{
            task = interactor.editTask(task, taskEditActivity.getTaskTitle(), taskEditActivity.getTaskBody());
        }
        taskEditActivity.showToast("Task saved successfully");
    }

}
