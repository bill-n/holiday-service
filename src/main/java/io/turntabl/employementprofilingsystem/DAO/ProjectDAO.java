package io.turntabl.employementprofilingsystem.DAO;

import com.google.gson.JsonObject;
import io.turntabl.employementprofilingsystem.Models.AddProject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
public interface ProjectDAO {
    public Map<String, Object> addProject(@RequestBody AddProject requestData);
}
