package io.turntabl.employementprofilingsystem.Models;

import java.util.List;

public class EditEmployee {
    private Integer employee_id;
    private String employee_firstname;
    private String employee_lastname;
    private String employee_address;
    private String employee_dev_level;
    private String employee_status;
    private String employee_role;

    public EditEmployee() {
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
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


    public String getEmployee_address() {
        return employee_address;
    }

    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }

    public String getEmployee_dev_level() {
        return employee_dev_level;
    }

    public void setEmployee_dev_level(String employee_dev_level) {
        this.employee_dev_level = employee_dev_level;
    }

    public String getEmployee_status() {
        return employee_status;
    }

    public void setEmployee_status(String employee_status) {
        this.employee_status = employee_status;
    }

    public String getEmployee_role() {
        return employee_role;
    }

    public void setEmployee_role(String employee_role) {
        this.employee_role = employee_role;
    }

}
