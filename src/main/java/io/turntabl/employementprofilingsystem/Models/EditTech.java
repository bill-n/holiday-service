package io.turntabl.employementprofilingsystem.Models;

public class EditTech {
    private Integer tech_id;
    private String tech_name;
    private String tech_status;

    public EditTech() {
    }

    public Integer getTech_id() {
        return tech_id;
    }

    public void setTech_id(Integer tech_id) {
        this.tech_id = tech_id;
    }

    public String getTech_name() {
        return tech_name;
    }

    public void setTech_name(String tech_name) {
        this.tech_name = tech_name;
    }

    public String getTech_status() {
        return tech_status;
    }

    public void setTech_status(String tech_status) {
        this.tech_status = tech_status;
    }
}
