package com.example.study_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<RecyclerViewTasksAdapter.ViewHolder>{


    List<TasksList> tasksListList;

    private Context mContext;

    public RecyclerViewTasksAdapter(List<TasksList> tasksListList) {
        this.tasksListList = tasksListList;
    }

    @NonNull
    @NotNull
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


        boolean isExpanded = tasksListList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return tasksListList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout expandableLayout;
        TextView TasktitleTextView, taskStatusTextView, specificationTextTextView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            TasktitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            taskStatusTextView = itemView.findViewById(R.id.status);
            specificationTextTextView = itemView.findViewById(R.id.specificationTextTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            TasktitleTextView.setOnClickListener(view -> {

                TasksList movie = tasksListList.get(getAdapterPosition());
                movie.setExpanded(!movie.isExpanded());
                notifyItemChanged(getAdapterPosition());

            });
        }
    }
}
