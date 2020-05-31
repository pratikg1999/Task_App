package com.pratik.taskapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

class TaskEditPresenterImpl implements TaskEditPresenter {
    private TasksInteractor interactor;
    private TaskEditActivity taskEditActivity;
    private boolean forNewTask = false;
    private Task task;


    TaskEditPresenterImpl(TaskEditActivity activity, TasksInteractor interactor) {
        this.taskEditActivity = activity;
        this.interactor = interactor;
    }


    @Override
    public void initialize() {
        Intent intent = taskEditActivity.getIntent();
//        String taskJson = intent.getStringExtra(MainActivity.EDIT_TASK_KEY);
        int taskId = intent.getIntExtra(MainActivity.EDIT_TASK_KEY, -1);
        if (taskId == -1) {
            forNewTask = true;
        } else {
            forNewTask = false;
//            Type type = new TypeToken<Task>(){}.getType();
//            task = gson.fromJson(taskJson, type);
            task = interactor.findTaskWithId(taskId);
            taskEditActivity.setTaskTitle(task.getTitle());
            taskEditActivity.setTaskBody(task.getBody());
            taskEditActivity.setDoneStatus(task.isDone());
        }
    }

    @Override
    public void onDeleteButtonClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(taskEditActivity.getContext());
        AlertDialog dialog = builder.setTitle("Do you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!forNewTask) {
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

    private boolean validate() {
        String curTitle = taskEditActivity.getTaskTitle();
        if (curTitle == null || curTitle.trim().equals("")) {
            taskEditActivity.setErrorOnTitle("Can't be empty");
            return false;
        }
        return true;
    }

    private void save() {
        if (forNewTask) {
            task = interactor.createNewTask(taskEditActivity.getTaskTitle(), taskEditActivity.getTaskBody(), taskEditActivity.getDoneStatus());
            forNewTask = false;
        } else {
            task = interactor.editTask(task, taskEditActivity.getTaskTitle(), taskEditActivity.getTaskBody(), taskEditActivity.getDoneStatus());
        }
        taskEditActivity.showToast("Task saved successfully", Toast.LENGTH_SHORT);
    }

    @Override
    public void onShareButtonClick() {
        String toShare = "Title:\n" + taskEditActivity.getTaskTitle() + "\n\nBody:\n" + taskEditActivity.getTaskBody() + "\n\nCompletion status:\n" + (taskEditActivity.getDoneStatus() ? "Done" : "Not done");
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, toShare);
        sendIntent.setType("text/plain");
        taskEditActivity.startActivity(sendIntent);
    }

    @Override
    public void onBackPressed() {
        if (forNewTask && taskEditActivity.getTaskTitle().trim().equals("") && taskEditActivity.getTaskBody().trim().equals("")) {
            taskEditActivity.finish();
            taskEditActivity.showToast("Empty task not created!", Toast.LENGTH_LONG);
            return;
        }
        if (forNewTask || !task.getBody().equals(taskEditActivity.getTaskBody()) || !task.getTitle().equals(taskEditActivity.getTaskTitle()) || (task.isDone() != taskEditActivity.getDoneStatus())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(taskEditActivity.getContext());
            AlertDialog dialog = builder.setTitle("Do you want to save?")
                    .setMessage("Task not saved! ")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (validate()) {
                                save();
                                taskEditActivity.finish();
                            }
                        }
                    })
                    .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            taskEditActivity.finish();
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true).create();
            dialog.show();
        } else {
            taskEditActivity.finish();
        }
    }

    @Override
    public void onSaveButtonClick() {
        if (validate()) {
            save();
        }
    }

}
