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

public class RecyclerViewTasksAdapter extends RecyclerView.Adapter<RecyclerViewTasksAdapter.viewHolder> {
    public static final String EXTRA_NUMBER = "package com.example.study_application";
    private static boolean deletingTask = false;

    // arrays
    private List<TasksList> tasksListList;
    private final ArrayList<TasksList> selectedItems = new ArrayList<>();

    private final Context context;

    //already explain in other java file
    public RecyclerViewTasksAdapter(List<TasksList> tasksListList, Context Context) {
        this.tasksListList = tasksListList;
        this.context = Context;
    }

    public static void deletingTasks(boolean b) {
        deletingTask = b;
    }

    //already explain in other java file
    @Override
    @NotNull
    public RecyclerViewTasksAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_items, parent, false);
        return new viewHolder(view);
    }

    //already explain in other java file
    @Override
    public void onBindViewHolder(@NotNull viewHolder holder, int position) {
        TasksList tasksList = tasksListList.get(position);

        // this iterates through every task once taskAdapter.notifyDataSetChanged(); is called
        // and changes the arrows to a TextBox
        if (deletingTask) {
            holder.arrowButton.setVisibility(View.INVISIBLE);
            holder.checkBox.setVisibility(View.VISIBLE);

        } else {
            holder.arrowButton.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.INVISIBLE);
        }

        // checks if the task has been clicked
        holder.taskTitleTextView.setOnClickListener(view -> {
            tasksList.setExpanded(!tasksList.isExpanded());
            //reloads the task
            notifyItemChanged(position);
        });

        //checks if the checkbox has been clicked
        // this needs to be here as a glitch occurs with the search where it would unTick itself
        holder.checkBox.setOnClickListener(v -> {
            tasksList.setSelected(!tasksList.isSelected());
            notifyItemChanged(position);
        });

        // replaces the underscores with spaces to make it look better
        holder.taskTitleTextView.setText(tasksList.getTITLE().replace("_", " "));
        holder.taskStatusTextView.setText(tasksList.getSTATUS().replace("_", " "));
        holder.specificationTextTextView.setText(tasksList.getSPECIFICATIONS().replace("_", " "));

        //this makes sure that the textBox stays selected even after a filtered search
        holder.checkBox.setChecked(tasksList.isSelected());
        if (holder.checkBox.isChecked()) {
            if (!selectedItems.contains(tasksListList.get(position))) {
                selectedItems.add(tasksListList.get(position));
            }
        } else {
            selectedItems.remove(tasksListList.get(position));
        }

        //makes sure that the task stays expanded after a filtered search
        boolean isExpanded = tasksList.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        ArrowAnimation.toggleArrow(holder.arrowButton, tasksList.isExpanded());

        //listens if the start button has been pressed
        holder.startTask.setOnClickListener(v -> {
            Intent toTaskScreen = new Intent(context, TaskScreen.class);
            toTaskScreen.putExtra(EXTRA_NUMBER, tasksList.getIDS());
            context.startActivity(toTaskScreen);
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
    static class viewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout expandableLayout;
        TextView taskTitleTextView, taskStatusTextView, specificationTextTextView;
        ImageView arrowButton;
        Button startTask;
        CheckBox checkBox;

        public viewHolder(View itemView) {
            super(itemView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            taskTitleTextView = itemView.findViewById(R.id.taskTitleTextView);
            taskStatusTextView = itemView.findViewById(R.id.status);
            specificationTextTextView = itemView.findViewById(R.id.specificationTextTextView);
            arrowButton = itemView.findViewById(R.id.viewMoreBtn);
            startTask = itemView.findViewById(R.id.StartTask);
            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.bringToFront();
        }
    }

    //sends the information from the tasks
    // selected from the the textBoxes to the taskList screen to be deleted
    public List<TasksList> getSelectedItems() {
        ArrayList<TasksList> selectedItemsList;
        selectedItemsList = selectedItems;
        return selectedItemsList;
    }
}
