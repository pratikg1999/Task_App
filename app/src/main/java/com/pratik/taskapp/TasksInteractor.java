package com.pratik.taskapp;

import java.util.List;

interface TasksInteractor {
    interface OnTransactionFinishedListener {
        void onFinished(List<Task> tasks);
    }

    Task createNewTask(String title, String body, boolean doneStatus);

    boolean deleteTaskAt(int index); // TODO create task not found exception and add throws here
    boolean deleteTask(Task taskToDelete);

    Task editTaskAt(int index, String newTitle, String newBody, boolean doneStatus); // TODO create task not found exception and add throws here
    Task editTask(Task taskToEdit, String newTitle, String newBody, boolean doneStatus);


    void setCompletionStatus(Task task, boolean status);
    void setCompletionStatusAt(int index, boolean status); // TODO create task not found exception and add throws here

    Task findTaskWithId(int id);

    void setOnFinishedTransactionListener(OnTransactionFinishedListener listener);

    List<Task> getTasksList();
}
