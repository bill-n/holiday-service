package io.turntabl.employementprofilingsystem.Models;

public class Email {
    private String employee_email;


 public String getEmployee_email() {
        return employee_email;
    }

    public void setEmployee_email(String employee_email) {
        this.employee_email = employee_email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "employee_email='" + employee_email + '\'' +
                '}';
    }
}