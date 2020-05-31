package com.pratik.taskapp;

import androidx.annotation.Nullable;

import java.util.Date;

class Task {
    private int id;
    private String title;
    private String body;
    private boolean done;
    private Date date;

    public Task(int id, String title, String body){
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = new Date();
        this.done = false;
    }


    public Task(int id, String title){
        this.id = id;
        this.title = title;
        this.date = new Date();
        this.body = "";
        this.done = false;
    }

    public Task(int id, String title, String body, boolean doneStatus) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = new Date();
        this.done = doneStatus;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Task && this.id == ((Task) obj).id);
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
