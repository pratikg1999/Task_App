package com.pratik.taskapp;

import java.util.List;

interface TasksView {
    void setTasksToDisplay(List<Task> tasks);

    void showEditScreen(Task task);
}
