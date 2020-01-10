package io.turntabl.employementprofilingsystem.DAO;

import com.google.gson.JsonObject;
import io.turntabl.employementprofilingsystem.Models.AddProject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


public interface ProjectDAO {
    public Map<String, Object> addProject(@RequestBody AddProject requestData);
    public Map<String, Object> getAllProjects();
    public Map<String, Object> assignedProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id);
    public Map<String, Object> getProjectById(@PathVariable("id") Integer id);
}
