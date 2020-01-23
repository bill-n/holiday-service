package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class LoggedProjectTO {

    private Integer project_id;
    private Integer project_hours;
    private java.sql.Date project_date;
    private Integer employee_id;

    public LoggedProjectTO(){

    }

    public Integer getProject_hours() {
        return project_hours;
    }

    public void setProject_hours(Integer project_hours) {
        this.project_hours = project_hours;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Date getProject_date() {
        return project_date;
    }

    public void setProject_date(Date project_date) {
        this.project_date = project_date;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }
}

