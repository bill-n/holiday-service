package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.DAO.ProjectDAO;
import io.turntabl.employementprofilingsystem.Models.AddProject;
import io.turntabl.employementprofilingsystem.Models.AssignedProjectTable;
import io.turntabl.employementprofilingsystem.Models.EditProject;
import io.turntabl.employementprofilingsystem.Transfers.*;
import io.turntabl.employementprofilingsystem.Utilities.Date;
import io.turntabl.employementprofilingsystem.Utilities.Parsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Api
@RestController
public class ProjectController implements ProjectDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Parsor parsor = new Parsor();
    Date date = new Date();

    @ApiOperation("Add New Project")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/project")
    @Override
    public Map<String, Object> addProject(@RequestBody AddProject requestData) {

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("project_name",requestData.getProject_name());
        request.put("project_description",requestData.getProject_description());
        request.put("project_start_date",requestData.getProject_start_date());
        request.put("project_end_date",requestData.getProject_end_date());
        request.put("project_status",requestData.getProject_status());
        request.put("project_tech_stack",requestData.getProject_tech_stack());

        try{

            List<String> requiredParams = Arrays.asList(
                    "project_name",
                    "project_description",
                    "project_start_date",
                    "project_end_date",
                    "project_status",
                    "project_tech_stack"
            );

            Map<String, Object> result = parsor.validate_params(request,requiredParams);
            if (result.get("code").equals("00")){

                String project_startDate =  requestData.getProject_start_date();
                String project_endDate = requestData.getProject_end_date();
                String project_status = requestData.getProject_status();

                java.sql.Date project_start_date = date.getDateObject(project_startDate);
                java.sql.Date project_end_date = date.getDateObject(project_endDate);

                SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName("project").usingGeneratedKeyColumns("project_id");

                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("project_name",requestData.getProject_name());
                parameters.put("project_description",requestData.getProject_description());
                parameters.put("project_start_date",project_start_date);
                parameters.put("project_end_date",project_end_date);
                parameters.put("project_status",project_status.toUpperCase());

                Number key = insertActor.executeAndReturnKey(parameters);
                Long project_key = key.longValue();
                if (key != null){

                    List<Integer> project_tech_stack = requestData.getProject_tech_stack();
                    for(Integer tech: project_tech_stack){
                        if (tech instanceof Integer){
                            jdbcTemplate.update(
                                    "insert into techproject(tech_id,project_id) values(?,?)",
                                    new Object[]{
                                            tech,
                                            project_key
                                    }
                            );
                            response.put("code","00");
                            response.put("msg","New project added successfully");
                        }else {
                            this.deleteProjectRow(project_key);
                            response.put("code","01");
                            response.put("msg","Invalid input type [project_tech_stack], try again later");
                        }
                    }
                }else {
                    this.deleteProjectRow(project_key);
                    response.put("code","01");
                    response.put("msg","Failed to add new project, try again later");
                }
            }else {
                response.put("code",result.get("code"));
                response.put("msg",result.get("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    @ApiOperation("List of Projects")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/projects")
    @Override
    public Map<String, Object> getAllProjects(){
        List<SingleProjectTO> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try{
            List<Project> projectList =  jdbcTemplate.query(
                    "select * from project",
                    BeanPropertyRowMapper.newInstance(Project.class)
            );

            for (Project project: projectList){

                List<Tech>techStack =  jdbcTemplate.query(
                        "select * from tech inner join techproject on tech.tech_id = techproject.tech_id inner join project on techproject.project_id = project.project_id where project.project_id = ? ",
                        new Object[]{project.getProject_id()},
                        BeanPropertyRowMapper.newInstance(Tech.class)
                );
                result.add(this.SingleProjectTOrowMappper(project, techStack));
            }
            response.put("code","00");
            response.put("msg","Data retrieved successfully");
            response.put("data",result);
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    @ApiOperation("Get Project By Id")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{id}")
    @Override
    public Map<String, Object> getProjectById(@PathVariable("id") Integer id){
        List<SingleProjectTO> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try{
            List<Project> projectList =  jdbcTemplate.query(
                    "select * from project where project_id = ?",
                    new Object[]{id},
                    BeanPropertyRowMapper.newInstance(Project.class)
            );

            List<Tech>techStack =  jdbcTemplate.query(
                    "select * from tech inner join techproject on tech.tech_id = techproject.tech_id inner join project on techproject.project_id = project.project_id where project.project_id = ? ",
                    new Object[]{id},
                    BeanPropertyRowMapper.newInstance(Tech.class)
            );
            if (!projectList.isEmpty()){
                response.put("code","00");
                response.put("msg","Data retrieved successfully");
                response.put("data",this.SingleProjectTOrowMappper(projectList.get(0), techStack));
            }else {
                response.put("code","00");
                response.put("msg","No Data found");
                response.put("data",new HashMap<>());
            }

        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    @ApiOperation("Assign Project to Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/assign/employee/{employee_id}")
    @Override
    public Map<String, Object> assignedProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id){

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("project_id",project_id);
        request.put("employee_id",employee_id);
        try{
            List<String> requiredParams = Arrays.asList(
                    "project_id",
                    "employee_id"
            );
            Map<String, Object> result = parsor.validate_params(request,requiredParams);
            if (result.get("code").equals("00")){
                List<AssignedProjectTable> assignedProjectTables =  jdbcTemplate.query(
                        "select * from assignedproject where employee_id = ?",
                        new Object[]{employee_id},
                        BeanPropertyRowMapper.newInstance(AssignedProjectTable.class)
                );
                List<Integer> existingAssgnProjects = new ArrayList<>();
                for(AssignedProjectTable assignedProjectTable :assignedProjectTables){
                    if (assignedProjectTable.getProject_id().equals(project_id)){
                        existingAssgnProjects.add(assignedProjectTable.getProject_id());
                    }
                }
                if (existingAssgnProjects.isEmpty()){

                    int resp = jdbcTemplate.update(
                            "insert into assignedproject(employee_id,project_id,isworkingon) values(?,?,?)",
                            new Object[]{
                                    employee_id,
                                    project_id,
                                    true
                            }
                    );
                    if(resp > 0){
                        response.put("code","00");
                        response.put("msg","Project assigned successfully");
                    }else {
                        response.put("code","01");
                        response.put("msg","Failed to assign project to an employee");
                    }
                }else{
                    response.put("code","01");
                    response.put("msg","Employee has already been assigned to this project");
                }
            }else {
                response.put("code",result.get("code"));
                response.put("msg",result.get("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    @ApiOperation("Get Assigned Projects By Employee Id")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/projects/assigned/employee/{id}")
//    @Override
    public Map<String, Object> getEmployeeAssignedProjects(@PathVariable("id") Integer id){
        List<SingleProfileTO> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("id",id);

        try{
            List<String> requiredParams = Arrays.asList(
                    "id"
            );
            Map<String, Object> valid = parsor.validate_params(request,requiredParams);
            if (valid.get("code").equals("00")){

                List<Employee> employee = jdbcTemplate.query(
                        "select * from employee where employee_id = ?",
                        new Object[]{id},
                        BeanPropertyRowMapper.newInstance(Employee.class)
                );

                if (!employee.isEmpty()){
                    List<EmployeeProject> projectTOS = jdbcTemplate.query(
                            "select * from project inner join assignedproject on project.project_id = assignedproject.project_id inner join employee on assignedproject.employee_id = employee.employee_id where employee.employee_id = ? ",
                            new Object[]{id},
                            BeanPropertyRowMapper.newInstance(EmployeeProject.class)
                    );
                    response.put("code","00");
                    response.put("msg","Data retrieved successfully");
                    response.put("data",this.SingleEmployeeAssignedProjectTOrowMappper(employee.get(0),projectTOS));
                }else {
                    response.put("code","00");
                    response.put("msg","No Data found");
                    response.put("data",new HashMap<>());
                }
            }else {
                response.put("code",valid.get("code"));
                response.put("msg",valid.get("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    private SingleEmployeeAssignedProjectTO SingleEmployeeAssignedProjectTOrowMappper(Employee employee, List<EmployeeProject> projectTOS ) throws SQLException {
        SingleEmployeeAssignedProjectTO  singleEmployeeAssignedProjectTO = new SingleEmployeeAssignedProjectTO();
        singleEmployeeAssignedProjectTO.setEmployee(employee);
        singleEmployeeAssignedProjectTO.setProjects(projectTOS);
        return singleEmployeeAssignedProjectTO;
    }

    @ApiOperation("Activate Project on Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/active/employee/{employee_id}")
    @Override
    public Map<String, Object> activateEmployeeProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id){

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("project_id",project_id);
        request.put("employee_id",employee_id);
        try{
            List<String> requiredParams = Arrays.asList(
                    "project_id",
                    "employee_id"
            );
            Map<String, Object> result = parsor.validate_params(request,requiredParams);
            if (result.get("code").equals("00")){
                int resp = jdbcTemplate.update(
                        "update assignedproject set isworkingon = ? where employee_id = ? and project_id = ?",
                        new Object[]{
                                true,
                                employee_id,
                                project_id,
                        }
                );
                if(resp > 0){
                    response.put("code","00");
                    response.put("msg","Project activated successfully on employee");
                }else {
                    response.put("code","01");
                    response.put("msg","Failed to activate project on an employee");
                }
            }else {
                response.put("code",result.get("code"));
                response.put("msg",result.get("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }
    @ApiOperation("Deactivate Project on Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/deactive/employee/{employee_id}")
    @Override
    public Map<String, Object> deActivateEmployeeProjects(@PathVariable("project_id") Integer project_id, @PathVariable("employee_id") Integer employee_id){

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("project_id",project_id);
        request.put("employee_id",employee_id);
        try{
            List<String> requiredParams = Arrays.asList(
                    "project_id",
                    "employee_id"
            );
            Map<String, Object> result = parsor.validate_params(request,requiredParams);
            if (result.get("code").equals("00")){
                int resp = jdbcTemplate.update(
                        "update assignedproject set isworkingon = ? where employee_id = ? and project_id = ?",
                        new Object[]{
                                false,
                                employee_id,
                                project_id,
                        }
                );
                if(resp > 0){
                    response.put("code","00");
                    response.put("msg","Project deactivated successfully on employee");
                }else {
                    response.put("code","01");
                    response.put("msg","Failed to deactivate project on an employee");
                }
            }else {
                response.put("code",result.get("code"));
                response.put("msg",result.get("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    @Override
    @ApiOperation("Edit Project Details")
    @CrossOrigin(origins = "*")
    @PutMapping("/v1/api/project")
    public Map<String, Object> updateProjectDetails(@RequestBody EditProject editProject){
        List<SingleProfileTO> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("project_id",editProject.getProject_id());
        request.put("project_name",editProject.getProject_name());
        request.put("project_description",editProject.getProject_description());
        request.put("project_status",editProject.getProject_status());
        request.put("project_start_date",editProject.getProject_start_date());
        request.put("project_end_date",editProject.getProject_end_date());

        try{
            List<String> requiredParams = Arrays.asList(
                    "project_id"
            );
            Map<String, Object> valid = parsor.validate_params(request,requiredParams);
            if (valid.get("code").equals("00")){
                Map<String, Object> updated_params = this.check_updated_params(editProject);
                if (updated_params.get("code").equals("00")){
                    UpdateProject updateProject = (UpdateProject) updated_params.get("data");
                    jdbcTemplate.update(
                            "update project set project_name = ?, project_description = ?, project_status = ?, project_start_date = ?, project_end_date = ? where project_id = ?",
                            new Object[]{
                                    updateProject.getProject_name(),
                                    updateProject.getProject_description(),
                                    updateProject.getProject_status().toUpperCase(),
                                    updateProject.getProject_start_date(),
                                    updateProject.getProject_end_date(),
                                    editProject.getProject_id()
                            }
                    );
                    List<Integer> project_current_tech = updateProject.getProject_tech_stack();

                    jdbcTemplate.update(
                            "delete from techproject where project_id = ?",
                            new Object[]{
                                    editProject.getProject_id()
                            }
                    );

                    for(Integer tech: project_current_tech){
                        jdbcTemplate.update(
                                "insert into techproject(tech_id,project_id) values(?,?)",
                                new Object[]{
                                        tech,
                                        editProject.getProject_id()
                                }
                        );
                    }

                    response.put("code","00");
                    response.put("msg","Project details updated successfully");

                }else {
                    response.put("code",valid.get("code"));
                    response.put("msg",valid.get("msg"));
                }
            }else {
                response.put("code",valid.get("code"));
                response.put("msg",valid.get("msg"));
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    private Map<String, Object> check_updated_params(EditProject editProject){
        Map<String, Object> response = new HashMap<>();
        UpdateProject updateProject = new UpdateProject();
        try{
            Integer project_id = editProject.getProject_id();
            List<Project> project = jdbcTemplate.query(
                    "select * from project where project_id = ?",
                    new Object[]{project_id},
                    BeanPropertyRowMapper.newInstance(Project.class)
            );
            List<Tech> techStack = jdbcTemplate.query(

                    "select * from tech inner join techproject on tech.tech_id = techproject.tech_id inner join project on techproject.project_id = project.project_id where project.project_id = ? ",
                    new Object[]{project_id},
                    BeanPropertyRowMapper.newInstance(Tech.class)
            );
            List<Integer> techs = techStack.stream()
                    .map(tech -> tech.getTech_id())
                    .collect(Collectors.toList());

            if (!project.isEmpty()){
                Project projectDetails = project.get(0);
                if(editProject.getProject_name().isEmpty()){
                    updateProject.setProject_name(projectDetails.getProject_name());
                }else {
                    updateProject.setProject_name(editProject.getProject_name());
                }
                if(editProject.getProject_description().isEmpty()){
                    updateProject.setProject_description(projectDetails.getProject_description());
                }else {
                    updateProject.setProject_description(editProject.getProject_description());
                }
                if(editProject.getProject_status().isEmpty()){
                    updateProject.setProject_status(projectDetails.getProject_status());
                }else {
                    updateProject.setProject_status(editProject.getProject_status());
                }
                if(editProject.getProject_start_date().isEmpty()){
                    updateProject.setProject_start_date(projectDetails.getProject_start_date());
                }else {
                    java.sql.Date start_date = java.sql.Date.valueOf(editProject.getProject_start_date());
                    updateProject.setProject_start_date(start_date);
                }
                if(editProject.getProject_end_date().isEmpty()){
                    updateProject.setProject_end_date(projectDetails.getProject_end_date());
                }else {
                    java.sql.Date end_date = java.sql.Date.valueOf(editProject.getProject_end_date());
                    updateProject.setProject_end_date(end_date);
                }
                if(editProject.getProject_tech_stack().isEmpty()){
                    updateProject.setProject_tech_stack(techs);
                }else {
                    updateProject.setProject_tech_stack(editProject.getProject_tech_stack());
                }
                response.put("code","00");
                response.put("msg","Data retrieved successfully");
                response.put("data",updateProject);

            }else {
                response.put("code","01");
                response.put("msg","Project data with this ID ["+project_id+"] doesn't exist");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }

        return response;
    }




    private SingleProjectTO SingleProjectTOrowMappper(Project project, List<Tech> techStack ) throws SQLException {
        SingleProjectTO singleProjectTO = new SingleProjectTO();
        singleProjectTO.setProject(project);
        singleProjectTO.setTech_stack(techStack);
        return singleProjectTO;
    }

    @Override
    public void deleteProjectRow(Long id){
        String sql = "delete from project where project_id = ? ";
        jdbcTemplate.update(
                sql,
                new Object[]{id}
        );
    }
}
