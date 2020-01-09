package io.turntabl.employementprofilingsystem.Transfers;

import java.util.List;

public class SingleProfileTO {
    private Employee employee;
//    private List<TechTO> techToList;
    private List<Project> projects;

    public SingleProfileTO() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
