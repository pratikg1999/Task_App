package com.pratik.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksView{

    private TasksPresenter tasksPresenter;
    private TasksRViewAdapter rViewAdapter;
    private FloatingActionButton fab;
    private RecyclerView rView;
    private TextView tvNoTaskCards;
    public static final String EDIT_TASK_KEY = "EDIT_TASK_KEY";
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.global_interactor = new TasksInteractorImpl(getApplicationContext());
        Constants.global_tasks_presenter = new TasksPresenterImpl(this, Constants.global_interactor);
        rView = findViewById(R.id.rview);
        fab = findViewById(R.id.fab);
        tvNoTaskCards = findViewById(R.id.tv_no_task_cards);
        tasksPresenter = Constants.global_tasks_presenter;
        typeface = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
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
            //, ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        }
        else {
            startActivity(new Intent(this, TaskEditActivity.class).putExtra(EDIT_TASK_KEY, task.getId()));
        }
    }

    public void enableRV() {
        tvNoTaskCards.setVisibility(View.GONE);
        rView.setVisibility(View.VISIBLE);
    }

    public void disableRV() {
        rView.setVisibility(View.GONE);
        tvNoTaskCards.setVisibility(View.VISIBLE);
    }


    class TasksRViewAdapter extends RecyclerView.Adapter<TasksRViewAdapter.TasksViewHolder> {
        private  List<Task> tasks;
        private Context ctx;

        void setTasks(List<Task> tasks){
            if(this.tasks!=tasks) {
                this.tasks = tasks;
            }
            this.notifyDataSetChanged();
        }

        TasksRViewAdapter(Context ctx, List<Task> tasks) {
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
            holder.tvTitle.setTypeface(typeface);
            holder.tvBody.setText(curTask.getBody());
            holder.cbStatus.setChecked(curTask.isDone());
            if(curTask.isDone()) {
                holder.llTitleHeader.setBackgroundColor(Color.parseColor("#90be6d"));
            }else{
                holder.llTitleHeader.setBackgroundColor(Color.parseColor("#ef233c"));
            }
            String strTimeFormat = "hh:mm a";
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
            CheckBox cbStatus;
            TextView tvBody;
            LinearLayout llTitleHeader;
            public TasksViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_card_title);
                tvDate= itemView.findViewById(R.id.tv_card_date);
                tvTime = itemView.findViewById(R.id.tv_card_time);
                cbStatus = itemView.findViewById(R.id.cb_done_status);
                tvBody = itemView.findViewById(R.id.tv_card_body);
                llTitleHeader = itemView.findViewById(R.id.ll_title_header);
            }
        }
    }
}
