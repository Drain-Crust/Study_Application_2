package com.example.study_application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<RecyclerViewTasksAdapter.ViewHolder> {
    public static final String EXTRA_NUMBER = "package com.example.study_application";
    private static boolean deletingTask = false;


    List<TasksList> tasksListList;

    private final Context mContext;
    ArrayList<TasksList> selectedItems = new ArrayList<>();

    //already explain in other java file
    public RecyclerViewTasksAdapter(List<TasksList> tasksListList, Context mContext) {
        this.tasksListList = tasksListList;
        this.mContext = mContext;
    }

    public static void deletingTasks(boolean b) {
        deletingTask = b;
    }

    //already explain in other java file
    @Override
    @NotNull
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_items, parent, false);
        return new ViewHolder(view);
    }

    //already explain in other java file
    @Override
    public void onBindViewHolder(RecyclerViewTasksAdapter.@NotNull ViewHolder holder, int position) {
        TasksList TasksList = tasksListList.get(position);

        if (deletingTask){
            holder.arrowButton.setVisibility(View.INVISIBLE);
            holder.checkBox.setVisibility(View.VISIBLE);

        } else{
            holder.arrowButton.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }


        holder.TaskTitleTextView.setOnClickListener(view -> {
            TasksList.setExpanded(!TasksList.isExpanded());
            notifyItemChanged(position);
        });

        holder.checkBox.setOnClickListener(v -> {
            TasksList.setSelected(!TasksList.isSelected());
            notifyItemChanged(position);
        });

        holder.TaskTitleTextView.setText(TasksList.getTitle());
        holder.taskStatusTextView.setText(TasksList.getStatus());
        holder.specificationTextTextView.setText(TasksList.getSpecifications());

        holder.checkBox.setChecked(TasksList.isSelected());
        if (holder.checkBox.isChecked()){
            if (!selectedItems.contains(tasksListList.get(position))){
                selectedItems.add(tasksListList.get(position));
            }
        } else {
            selectedItems.remove(tasksListList.get(position));
            System.out.println("the check box unTicked" + selectedItems);
        }
        boolean isExpanded = TasksList.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        ArrowAnimation.toggleArrow(holder.arrowButton, TasksList.isExpanded());

        holder.StartTask.setOnClickListener(v -> {
            Intent intent1 = new Intent(mContext, TaskScreen.class);
            intent1.putExtra(EXTRA_NUMBER, TasksList.getIDs());
            mContext.startActivity(intent1);
        });

    }

    //already explain in other java file
    @Override
    public int getItemCount() {
        return tasksListList.size();
    }

    // this is used to configure the data to the filtered Data
    public void filterList(ArrayList<TasksList> filteredList) {
        tasksListList = filteredList;
        notifyDataSetChanged();
    }

    //already explain in other java file
    static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout expandableLayout;
        TextView TaskTitleTextView, taskStatusTextView, specificationTextTextView;
        ImageView arrowButton;
        Button StartTask;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            TaskTitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            taskStatusTextView = itemView.findViewById(R.id.status);
            specificationTextTextView = itemView.findViewById(R.id.specificationTextTextView);
            arrowButton = itemView.findViewById(R.id.viewMoreBtn);
            StartTask = itemView.findViewById(R.id.StartTask);
            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.bringToFront();
        }
    }
}
