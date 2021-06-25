package com.example.study_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<RecyclerViewTasksAdapter.ViewHolder> {
    public static final String EXTRA_NUMBER = "package com.example.study_application";

    List<TasksList> tasksListList;

    private final Context mContext;

    public RecyclerViewTasksAdapter(List<TasksList> tasksListList, Context mContext) {
        this.tasksListList = tasksListList;
        this.mContext = mContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewTasksAdapter.ViewHolder holder, int position) {

        TasksList TasksList = tasksListList.get(position);
        holder.TasktitleTextView.setText(TasksList.getTitle());
        holder.taskStatusTextView.setText(TasksList.getStatus());
        holder.specificationTextTextView.setText(TasksList.getSpecifications());

        boolean isExpanded = TasksList.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        arrowAnimation.toggleArrow(holder.arrowButton, tasksListList.get(position).isExpanded());

        holder.StartTask.setOnClickListener(v -> {
            TasksList TasksLists = tasksListList.get(position);
            Intent intent1 = new Intent(mContext, TaskScreen.class);
            intent1.putExtra(EXTRA_NUMBER, TasksLists.getIDs());
            mContext.startActivity(intent1);
        });

    }

    @Override
    public int getItemCount() {
        return tasksListList.size();
    }

    public void filterList(ArrayList<TasksList> filteredList) {
        tasksListList = filteredList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout expandableLayout;
        TextView TasktitleTextView, taskStatusTextView, specificationTextTextView;
        ImageView arrowButton;

        Button StartTask;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            TasktitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            taskStatusTextView = itemView.findViewById(R.id.status);
            specificationTextTextView = itemView.findViewById(R.id.specificationTextTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            arrowButton = itemView.findViewById(R.id.viewMoreBtn);
            StartTask = itemView.findViewById(R.id.StartTask);

            TasktitleTextView.setOnClickListener(view -> {

                TasksList TaskList = tasksListList.get(getAdapterPosition());
                TaskList.setExpanded(!TaskList.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
