package com.pratik.taskapp;

import androidx.annotation.Nullable;

import java.util.Date;

class Task {
    int id;
    private String title;
    private String body;
    private boolean completionStatus;
    private Date date;

    public Task(int id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = new Date();
    }


    public Task(int id, String title){
        this.id = id;
        this.title = title;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if((obj instanceof Task && this.id == ((Task)obj).id)){
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
