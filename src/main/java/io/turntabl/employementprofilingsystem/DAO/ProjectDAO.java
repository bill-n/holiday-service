package io.turntabl.employementprofilingsystem.DAO;

import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Models.AddProject;
import io.turntabl.employementprofilingsystem.Models.EditProject;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface ProjectDAO {
    @ApiOperation("Add New Project")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/project")
    Map<String, Object> addProject(@RequestBody AddProject requestData);

    @ApiOperation("List of Projects")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/projects")
    Map<String, Object> getAllProjects();

    @ApiOperation("Get Project By Id")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{id}")
    Map<String, Object> getProjectById(@PathVariable("id") Integer id);

    @ApiOperation("Assign Project to Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/assign/employee/{employee_id}")
    Map<String, Object> assignedProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id);

    @ApiOperation("Activate Project on Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/active/employee/{employee_id}")
    Map<String, Object> activateEmployeeProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id);

    @ApiOperation("Deactivate Project on Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/deactive/employee/{employee_id}")
    Map<String, Object> deActivateEmployeeProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id);

    @ApiOperation("Edit Project Details")
    @CrossOrigin(origins = "*")
    @PutMapping("/v1/api/project")
    Map<String, Object> updateProjectDetails(@RequestBody EditProject editProject);

    void deleteProjectRow(Long id);
}
