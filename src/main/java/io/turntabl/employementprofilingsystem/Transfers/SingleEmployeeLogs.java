package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class SingleEmployeeLogs {

    private String employee_firstname;
    private String employee_lastname;
    private String project_name;
    private Integer project_hours;
    private Boolean issick;
    private Boolean isonvacation;
    private Integer volunteering_hours;
    private java.sql.Date project_logged_date;

    public SingleEmployeeLogs() {
    }

    public String getEmployee_firstname() {
        return employee_firstname;
    }

    public void setEmployee_firstname(String employee_firstname) {
        this.employee_firstname = employee_firstname;
    }

    public String getEmployee_lastname() {
        return employee_lastname;
    }

    public void setEmployee_lastname(String employee_lastname) {
        this.employee_lastname = employee_lastname;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Integer getProject_hours() {
        return project_hours;
    }

    public void setProject_hours(Integer project_hours) {
        this.project_hours = project_hours;
    }

    public Boolean getIssick() {
        return issick;
    }

    public void setIssick(Boolean issick) {
        this.issick = issick;
    }

    public Boolean getIsonvacation() {
        return isonvacation;
    }

    public void setIsonvacation(Boolean isonvacation) {
        this.isonvacation = isonvacation;
    }

    public Integer getVolunteering_hours() {
        return volunteering_hours;
    }

    public void setVolunteering_hours(Integer volunteering_hours) {
        this.volunteering_hours = volunteering_hours;
    }

    public Date getProject_logged_date() {
        return project_logged_date;
    }

    public void setProject_logged_date(Date project_logged_date) {
        this.project_logged_date = project_logged_date;
    }
}
