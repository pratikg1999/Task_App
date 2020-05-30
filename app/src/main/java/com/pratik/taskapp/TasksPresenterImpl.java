package com.pratik.taskapp;

import java.util.List;

class TasksPresenterImpl implements TasksPresenter, TasksInteractor.OnTransactionFinishedListener {
    TasksView tasksView;
    TasksInteractor interactor;

    public TasksPresenterImpl(TasksView view, TasksInteractor interactor){
        this.tasksView = view;
        this.interactor = interactor;
        interactor.setOnFinishedTransactionListener(this);
    }

    @Override
    public void onFinished(List<Task> tasksList) {
        tasksView.setTasksToDisplay(tasksList);
    }

    @Override
    public void initialize() {
        tasksView.setTasksToDisplay(interactor.getTasksList());
    }

    @Override
    public void onCardClick(int index) {
        interactor.editTaskAt(index, "new title "+ index, "new body "+ index);
//        interactor.deleteTaskAt(index);
    }
}
