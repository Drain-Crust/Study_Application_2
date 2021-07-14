package com.example.study_application;

import org.jetbrains.annotations.NotNull;

public class TasksList {

    private final String TITLE;
    private final String IDs;
    private final String STATUS;
    private final String SPECIFICATIONS;
    private boolean selected;
    private boolean expanded;

    public TasksList(String IDs, String title, String status, String Specifications) {
        this.IDs = IDs;
        this.TITLE = title;
        this.STATUS = status;
        this.SPECIFICATIONS = Specifications;
        this.expanded = false;
        this.selected = false;
    }

    //used private variables so to use them i have to call the variables through methods set and get
    public String getIDs() {
        return IDs;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getSPECIFICATIONS() {
        return SPECIFICATIONS;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isSelected() {
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
                ", title='" + TITLE + '\'' +
                ", status='" + STATUS + '\'' +
                ", Specifications='" + SPECIFICATIONS + '\'' +
                ", expanded=" + expanded + '\'' +
                ", selected='" + selected + '}';
    }
}
