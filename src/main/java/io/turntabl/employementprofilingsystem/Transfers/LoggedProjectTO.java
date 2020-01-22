package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class LoggedProjectTO {

    private Integer project_id;
    private Integer project_hours;
    private java.sql.Date project_date;
    private Integer emp_id;

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

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }
}

