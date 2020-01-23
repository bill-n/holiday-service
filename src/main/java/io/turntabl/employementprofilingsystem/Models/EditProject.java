package io.turntabl.employementprofilingsystem.Models;

import java.util.List;

public class EditProject {

    private Integer project_id;
    private String project_name;
    private String project_description;
    private String project_start_date;
    private String project_end_date;
    private List<Integer> project_tech_stack;

    public EditProject() {
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
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
