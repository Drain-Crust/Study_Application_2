package com.example.study_application;

public class TasksList {

    private String title;
    private String IDs;
    private String status;
    private String Specifications;
    private boolean expanded;

    public TasksList(String IDs, String title, String status, String Specifications) {
        this.IDs = IDs;
        this.title = title;
        this.status = status;
        this.Specifications = Specifications;
        this.expanded = false;
    }

    public String getIDs(){
        return IDs;
    }

    public void setIDs(String IDs){
        this.IDs =IDs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecifications() {
        return Specifications;
    }

    public void setSpecifications(String Specifications) {
        this.Specifications = Specifications;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "TasksList{" +
                "" + IDs + '\'' +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", Specifications='" + Specifications + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}
