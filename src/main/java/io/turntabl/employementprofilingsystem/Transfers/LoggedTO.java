package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class LoggedTO {
    private Integer logged_hours;
    private Date project_date;
    private String project_name;

    public LoggedTO() {

    }

    public Integer getLogged_hours() {
        return logged_hours;
    }

    public void setLogged_hours(Integer logged_hours) {
        this.logged_hours = logged_hours;
    }

    public Date getProject_date() {
        return project_date;
    }

    public void setProject_date(Date project_date) {
        this.project_date = project_date;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
