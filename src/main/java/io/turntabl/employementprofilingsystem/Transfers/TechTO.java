package io.turntabl.employementprofilingsystem.Transfers;

import org.springframework.stereotype.Component;

@Component
public class TechTO {
    private Integer tech_id;
    private String tech_name;

    public TechTO() {
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
}
