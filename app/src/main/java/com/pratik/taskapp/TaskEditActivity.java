package com.pratik.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class TaskEditActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etTitle;
    EditText etBody;
    ImageButton btSave;
    ImageButton btDelete;
    private TaskEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        etTitle = findViewById(R.id.et_title);
        etBody = findViewById(R.id.et_body);
        btSave = findViewById(R.id.bt_save_btn);
        btDelete = findViewById(R.id.bt_delete_btn);

        presenter = new TaskEditPresenterImpl(this, Constants.global_interactor);
        presenter.initialize();

        btSave.setOnClickListener(this);
        btDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_save_btn:
                presenter.onSaveButtonClick();
                break;
            case R.id.bt_delete_btn:
                presenter.onDeleteButtonClick();
                break;
        }
    }

    public void setTaskTitle(String title){
        etTitle.setText(title);
    }

    public void setTaskBody(String body){
        etBody.setText(body);
    }

    public String getTaskTitle(){
        return etTitle.getText().toString();
    }

    public String getTaskBody(){
        return etBody.getText().toString();
    }

    public Context getContext() {
        return this;
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
