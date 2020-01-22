package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class LoggedSickTO {
    private Integer emp_id;
    private java.sql.Date sick_date;

    public LoggedSickTO() {

    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public Date getSick_date() {
        return sick_date;
    }

    public void setSick_date(Date sick_date) {
        this.sick_date = sick_date;
    }
}
