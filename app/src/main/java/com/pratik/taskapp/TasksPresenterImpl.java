package com.pratik.taskapp;


import java.util.List;

class TasksPresenterImpl implements TasksPresenter, TasksInteractor.OnTransactionFinishedListener {
    private MainActivity mainActivity;
    private TasksInteractor interactor;

    public TasksPresenterImpl(MainActivity activity, TasksInteractor interactor){
        this.mainActivity = activity;
        this.interactor = interactor;
        interactor.setOnFinishedTransactionListener(this);
    }

    @Override
    public void onFinished(List<Task> tasksList) {
        if(tasksList!=null && tasksList.size()>0){
            mainActivity.enableRV();
            mainActivity.setTasksToDisplay(tasksList);
        }else{
            mainActivity.disableRV();

        }
    }

    @Override
    public void initialize() {
        List<Task> tasks = interactor.getTasksList();
        if(tasks!=null && tasks.size()>0){
            mainActivity.enableRV();
        mainActivity.setTasksToDisplay(tasks);
        }
        else {
            mainActivity.disableRV();
        }
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
