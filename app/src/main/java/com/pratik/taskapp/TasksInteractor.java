package com.pratik.taskapp;

import java.util.List;

interface TasksInteractor {
    interface OnTransactionFinishedListener {
        void onFinished(List<Task> tasks);
    }

    Task createNewTask(String title, String body);

    boolean deleteTaskAt(int index); // TODO create task not found exception and add throws here
    boolean deleteTask(Task taskToDelete);

    Task editTaskAt(int index, String newTitle, String newBody); // TODO create task not found exception and add throws here
    Task editTask(Task taskToEdit, String newTitle, String newBody);

    void setCompletionStatus(Task task, boolean status);
    void setCompletionStatusAt(int index, boolean status); // TODO create task not found exception and add throws here

    Task findTaskWithId(int id, List<Task> tasks);

    void setOnFinishedTransactionListener(OnTransactionFinishedListener listener);

    List<Task> getTasksList();
}
