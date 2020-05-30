package com.pratik.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

class TasksPresenterImpl implements TasksPresenter, TasksInteractor.OnTransactionFinishedListener {
    MainActivity mainActivity;
    TasksInteractor interactor;

    public TasksPresenterImpl(MainActivity activity, TasksInteractor interactor){
        this.mainActivity = activity;
        this.interactor = interactor;
        interactor.setOnFinishedTransactionListener(this);
    }

    @Override
    public void onFinished(List<Task> tasksList) {
        mainActivity.setTasksToDisplay(tasksList);
    }

    @Override
    public void initialize() {
        mainActivity.setTasksToDisplay(interactor.getTasksList());
    }

    @Override
    public void onCardClick(int index) {
        mainActivity.showEditScreen(interactor.getTasksList().get(index));
//        interactor.editTaskAt(index, "new title "+ index, "new body "+ index);
//        interactor.deleteTaskAt(index);
    }

    @Override
    public void onFabClick() {
        mainActivity.showEditScreen(null);
    }
}
