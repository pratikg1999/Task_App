package com.pratik.taskapp;

interface TaskEditPresenter {
    void onSaveButtonClick();

    void initialize();

    void onDeleteButtonClick();

    void onShareButtonClick();

    void onBackPressed();
}
