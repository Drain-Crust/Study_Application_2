package com.example.study_application;

import org.jetbrains.annotations.NotNull;

public class TasksList {

    private final String title;
    private final String IDs;
    private final String status;
    private final String Specifications;
    private boolean expanded;

    public TasksList(String IDs, String title, String status, String Specifications) {
        this.IDs = IDs;
        this.title = title;
        this.status = status;
        this.Specifications = Specifications;
        this.expanded = false;
    }

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

    @Override
    public @NotNull String toString() {
        return "TasksList{" +
                "" + IDs + '\'' +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", Specifications='" + Specifications + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}
