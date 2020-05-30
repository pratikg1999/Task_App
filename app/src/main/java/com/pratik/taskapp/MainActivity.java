package com.pratik.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksView{

    TasksPresenter tasksPresenter;
    TasksRViewAdapter rViewAdapter;
    FloatingActionButton fab;
    RecyclerView rView;
    public static final String EDIT_TASK_KEY = "EDIT_TASK_KEY";
//    private Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.global_interactor = new TasksInteractorImpl(getApplicationContext());
        Constants.global_tasks_presenter = new TasksPresenterImpl(this, Constants.global_interactor);
        rView = findViewById(R.id.rview);
        fab = findViewById(R.id.fab);
        tasksPresenter = Constants.global_tasks_presenter;
        rView.setLayoutManager(new LinearLayoutManager(this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasksPresenter.onFabClick();
            }
        });
        tasksPresenter.initialize();
    }

    @Override
    public void setTasksToDisplay(List<Task> tasks) {
        if(rViewAdapter == null){
            rViewAdapter = new TasksRViewAdapter(MainActivity.this, tasks);
            rView.setAdapter(rViewAdapter);
        }
        rViewAdapter.setTasks(tasks);
//        rViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEditScreen(Task task) {
        if(task==null){
            startActivity(new Intent(this, TaskEditActivity.class));
        }
        else {
            startActivity(new Intent(this, TaskEditActivity.class).putExtra(EDIT_TASK_KEY, task.getId()));
        }
    }


    class TasksRViewAdapter extends RecyclerView.Adapter<TasksRViewAdapter.TasksViewHolder> {
        private  List<Task> tasks;
        private Context ctx;

        public void setTasks(List<Task> tasks){
            if(this.tasks!=tasks) {
                this.tasks = tasks;
            }
            this.notifyDataSetChanged();
        }

        public TasksRViewAdapter(Context ctx, List<Task> tasks) {
            this.ctx = ctx;
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(ctx);
            View view = inflater.inflate(R.layout.task_card_view, parent, false);
            return new TasksViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TasksViewHolder holder, final int position) {
            Task curTask = tasks.get(position);
            holder.tvTitle.setText(curTask.getTitle());

            String strTimeFormat = "hh:mm:ss a";
            DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
            String time= timeFormat.format(curTask.getDate());
            holder.tvTime.setText(time);

//            String strDateFormat = "dd:mm:ss a";
            DateFormat dateFormat = DateFormat.getDateInstance();
            String date= dateFormat.format(curTask.getDate());
            holder.tvDate.setText(date);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasksPresenter.onCardClick(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        class TasksViewHolder extends RecyclerView.ViewHolder{
            TextView tvTitle;
            TextView tvDate;
            TextView tvTime;
            public TasksViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_card_title);
                tvDate= itemView.findViewById(R.id.tv_card_date);
                tvTime = itemView.findViewById(R.id.tv_card_time);
            }
        }
    }
}
