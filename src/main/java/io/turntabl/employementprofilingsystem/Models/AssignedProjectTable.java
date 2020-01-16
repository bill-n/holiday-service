package io.turntabl.employementprofilingsystem.Models;

public class AssignedProjectTable {
    private Integer employee_id;
    private Integer project_id;
    private Boolean isworkingon;

    public AssignedProjectTable() {
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Boolean getIsworkingon() {
        return isworkingon;
    }

    public void setIsworkingon(Boolean isworkingon) {
        this.isworkingon = isworkingon;
    }
}
