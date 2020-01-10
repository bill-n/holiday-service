package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.DAO.ProjectDAO;
import io.turntabl.employementprofilingsystem.Models.AddProject;
import io.turntabl.employementprofilingsystem.Transfers.*;
import io.turntabl.employementprofilingsystem.Utilities.Date;
import io.turntabl.employementprofilingsystem.Utilities.Parsor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

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
        request.put("project_tech_stack",requestData.getProject_tech_stack());

        try{

            List<String> requiredParams = Arrays.asList(
                    "project_name",
                    "project_description",
                    "project_start_date",
                    "project_end_date",
                    "project_tech_stack"
            );

            Map<String, Object> result = parsor.validate_params(request,requiredParams);
            if (result.get("code").equals("00")){

                String project_startDate =  requestData.getProject_start_date();
                String project_endDate = requestData.getProject_end_date();

                java.sql.Date project_start_date = date.getDateObject(project_startDate);
                java.sql.Date project_end_date = date.getDateObject(project_endDate);

                SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName("project").usingGeneratedKeyColumns("project_id");

                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("project_name",requestData.getProject_name());
                parameters.put("project_description",requestData.getProject_description());
                parameters.put("project_start_date",project_start_date);
                parameters.put("project_end_date",project_end_date);

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

    @ApiOperation("Assign Projects to Employee")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/project/{project_id}/assign/employee/{employee_id}")
    @Override
    public Map<String, Object> getAllProjects(@PathVariable("project_id") Integer project_id,@PathVariable("employee_id") Integer employee_id){

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
                        "insert into assignedproject(employee_id,project_id) values(?,?)",
                        new Object[]{
                                employee_id,
                                project_id
                        }
                );
                if(resp > 0){
                    response.put("code","00");
                    response.put("msg","Project assigned successfully");
                }else {
                    response.put("code","01");
                    response.put("msg","Failed to assign project to an employee");
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

    private SingleProjectTO SingleProjectTOrowMappper(Project project, List<Tech> techStack ) throws SQLException {
        SingleProjectTO singleProjectTO = new SingleProjectTO();
        singleProjectTO.setProject(project);
        singleProjectTO.setTech_stack(techStack);
        return singleProjectTO;
    }

    public void deleteProjectRow(Long id){
        String sql = "delete from project where project_id = ? ";
        jdbcTemplate.update(
                sql,
                new Object[]{id}
        );
    }
}
