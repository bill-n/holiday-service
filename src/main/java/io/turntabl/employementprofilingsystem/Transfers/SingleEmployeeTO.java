package io.turntabl.employementprofilingsystem.Transfers;

import java.util.List;

public class SingleEmployeeTO {
    private Employee employee;
    private List<EmployeeProject> projects;

    public SingleEmployeeTO() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<EmployeeProject> getProjects() {
        return projects;
    }

    public void setProjects(List<EmployeeProject> projects) {
        this.projects = projects;
    }
}
