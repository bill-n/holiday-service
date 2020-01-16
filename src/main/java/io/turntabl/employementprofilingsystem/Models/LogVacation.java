package io.turntabl.employementprofilingsystem.Models;

public class LogVacation {
    private Integer employee_id;
    private String logged_date;

    public LogVacation() {
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getLogged_date() {
        return logged_date;
    }

    public void setLogged_date(String logged_date) {
        this.logged_date = logged_date;
    }
}
