package com.example.study_application;

import org.jetbrains.annotations.NotNull;

public class TasksList {

    private final String title;
    private final String IDs;
    private final String status;
    private final String Specifications;
    private boolean selected;
    private boolean expanded;

    public TasksList(String IDs, String title, String status, String Specifications) {
        this.IDs = IDs;
        this.title = title;
        this.status = status;
        this.Specifications = Specifications;
        this.expanded = false;
        this.selected = false;
    }

    //used private variables so to use them i have to call get but because
    // I don't set anything other than expanded i deleted the set methods
    public String getIDs() {
        return IDs;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecifications() {
        return Specifications;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public  boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // the layout to put data inside listArray
    @Override
    public @NotNull String toString() {
        return "TasksList{" +
                "IDs='" + IDs + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", Specifications='" + Specifications + '\'' +
                ", expanded=" + expanded + '}' +
                ", selected='" + selected + '}';
    }
}
