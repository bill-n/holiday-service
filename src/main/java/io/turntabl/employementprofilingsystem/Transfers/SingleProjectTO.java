package io.turntabl.employementprofilingsystem.Transfers;

import java.util.List;

public class SingleProjectTO {
    private Project project;
    private List<Tech> tech_stack;

    public SingleProjectTO() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Tech> getTech_stack() {
        return tech_stack;
    }

    public void setTech_stack(List<Tech> tech_stack) {
        this.tech_stack = tech_stack;
    }
}
