package io.turntabl.employementprofilingsystem.Transfers;

import java.sql.Date;

public class LoggedVacationTO {
    private Integer emp_id;
    private java.sql.Date vacation_date;


    public LoggedVacationTO() {

    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public Date getVacation_date() {
        return vacation_date;
    }

    public void setVacation_date(Date vacation_date) {
        this.vacation_date = vacation_date;
    }
}
