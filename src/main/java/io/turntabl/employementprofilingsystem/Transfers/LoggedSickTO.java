package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class LoggedSickTO {
    private Integer employee_id;
    private java.sql.Date sick_date;

    public LoggedSickTO() {

    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Date getSick_date() {
        return sick_date;
    }

    public void setSick_date(Date sick_date) {
        this.sick_date = sick_date;
    }
}
