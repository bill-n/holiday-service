package io.turntabl.employementprofilingsystem.Models;

public class LogProjectVolunteering {
    private Integer employee_id;
    private Integer project_id;
    private Integer project_hours;
    private Integer volunteering_hours;
    private String logged_date;

    public LogProjectVolunteering() {
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getProject_hours() {
        return project_hours;
    }

    public void setProject_hours(Integer project_hours) {
        this.project_hours = project_hours;
    }

    public Integer getVolunteering_hours() {
        return volunteering_hours;
    }

    public void setVolunteering_hours(Integer volunteering_hours) {
        this.volunteering_hours = volunteering_hours;
    }

    public String getLogged_date() {
        return logged_date;
    }

    public void setLogged_date(String logged_date) {
        this.logged_date = logged_date;
    }
}