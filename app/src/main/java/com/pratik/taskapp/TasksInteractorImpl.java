package com.pratik.taskapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class TasksInteractorImpl implements TasksInteractor {
    private OnTransactionFinishedListener onTransactionFinishedListener;
    private SharedPreferences preferences;
    private List<Task> taskList;
    private static final Gson gson = new Gson();
    private static int curIdCounter;
    private static final String SP_NAME = "TASK_SP";
    private static final String CUR_ID_KEY = "CUR_ID_KEY";
    private static final String TASKS_KEY = "TASKS_KEY";

    private List<Task> readTasksFromSP(){
        String jsonTasks = preferences.getString(TASKS_KEY, null);
        if(jsonTasks !=null){
            Type type = new TypeToken<List<Task>>(){}.getType();
            return gson.fromJson(jsonTasks, type);
        }
        return new ArrayList<Task>();

    }

    private void saveTasksToSP(List<Task> tasks){
        String jsonTasks = gson.toJson(tasks);
        preferences.edit().putString(TASKS_KEY, jsonTasks).apply();
    }

    public  TasksInteractorImpl(Context context){
//        this.onTransactionFinishedListener = listener;
        preferences = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
        curIdCounter = preferences.getInt(CUR_ID_KEY, 0);
        taskList = readTasksFromSP();
        for(int i =0; i<3; i++){
            taskList.add(new Task(curIdCounter, "title " +i, "body "+i ));
            incrementCurId();
        }
        saveTasksToSP(taskList);
    }


    @Override
    public Task createNewTask(String title, String body) {
        Task newTask = new Task(curIdCounter, title, body);
        taskList.add(newTask);
        incrementCurId();
        saveTasksToSP(taskList);
        onTransactionFinishedListener.onFinished(taskList);
        return  newTask;
    }

    @Override
    public boolean deleteTaskAt(int index) { // TODO create task not found exception and add throws here
//        Task task = findTaskWithId(index, taskList);
        Task task = taskList.get(index);
        return deleteTask(task);

    }

    @Override
    public boolean deleteTask(Task taskToDelete) {
        boolean result = taskList.remove(taskToDelete);
        if(result){
            decrementCurId();
            saveTasksToSP(taskList);
        }
        onTransactionFinishedListener.onFinished(taskList);
        return result;
    }

    @Override
    public Task editTaskAt(int index, String newTitle, String newBody) {
//        Task task = findTaskWithId(index, taskList);
        Task task = taskList.get(index);
        return editTask(task, newTitle, newBody);

    }

    @Override
    public Task editTask(Task taskToEdit, String newTitle, String newBody) {
        taskToEdit.setTitle(newTitle);
        taskToEdit.setBody(newBody);
        saveTasksToSP(taskList);
        onTransactionFinishedListener.onFinished(taskList);
        return taskToEdit;
    }

    @Override
    public void setCompletionStatus(Task task, boolean status) {
        task.setCompletionStatus(status);
        saveTasksToSP(taskList);
    }

    @Override
    public void setCompletionStatusAt(int index, boolean status) {
//        Task task = findTaskWithId(id, taskList);
        Task task = taskList.get(index);
        setCompletionStatus(task, status);
    }

    @Override
    public Task findTaskWithId(int id, List<Task> tasks) {
        for(Task task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        return null;
    }

    @Override
    public void setOnFinishedTransactionListener(OnTransactionFinishedListener listener) {
        onTransactionFinishedListener = listener;
    }

    @Override
    public List<Task> getTasksList() {
        return taskList;
    }

    private void incrementCurId(){
        curIdCounter++;
        preferences.edit().putInt(CUR_ID_KEY, curIdCounter).apply();
    }

    private void decrementCurId(){
        curIdCounter = curIdCounter>0 ? curIdCounter -1 : 0;
        preferences.edit().putInt(CUR_ID_KEY, curIdCounter).apply();
    }
}
