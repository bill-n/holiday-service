package io.turntabl.employementprofilingsystem.Transfers;

import java.util.List;

public class SingleProfileTO {
    private Employee employee;
    private List<Tech> tech_stack;
    private List<EmployeeProject> projects;

    public SingleProfileTO() {
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

    public List<Tech> getTech_stack() {
        return tech_stack;
    }

    public void setTech_stack(List<Tech> tech_stack) {
        this.tech_stack = tech_stack;
    }
}
