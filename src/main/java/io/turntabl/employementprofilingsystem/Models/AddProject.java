package io.turntabl.employementprofilingsystem.Models;

import java.util.List;

public class AddProject {

    private String project_name;
    private String project_description;
    private String project_start_date;
    private String project_end_date;
    private String project_status;
    private List<Integer> project_tech_stack;

    public AddProject() {
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public String getProject_start_date() {
        return project_start_date;
    }

    public void setProject_start_date(String project_start_date) {
        this.project_start_date = project_start_date;
    }

    public String getProject_end_date() {
        return project_end_date;
    }

    public void setProject_end_date(String project_end_date) {
        this.project_end_date = project_end_date;
    }

    public List<Integer> getProject_tech_stack() {
        return project_tech_stack;
    }

    public void setProject_tech_stack(List<Integer> project_tech_stack) {
        this.project_tech_stack = project_tech_stack;
    }
}
