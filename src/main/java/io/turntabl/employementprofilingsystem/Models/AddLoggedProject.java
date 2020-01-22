package io.turntabl.employementprofilingsystem.Models;

public class AddLoggedProject {

        private Integer project_id;
        private Integer emp_id ;
        private String project_date;
        private Integer project_hours;


        public AddLoggedProject() {
        }


    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }

    public String getProject_date() {
        return project_date;
    }

    public void setProject_date(String project_date) {
        this.project_date = project_date;
    }

    public Integer getProject_hours() {
        return project_hours;
    }

    public void setProject_hours(Integer project_hours) {
        this.project_hours = project_hours;
    }
}
