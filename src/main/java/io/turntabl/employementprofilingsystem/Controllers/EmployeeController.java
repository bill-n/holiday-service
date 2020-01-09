package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.DAO.EmployeeDAO;

import io.turntabl.employementprofilingsystem.Transfers.Employee;
import io.turntabl.employementprofilingsystem.Transfers.Project;
import io.turntabl.employementprofilingsystem.Transfers.SingleProfileTO;
import io.turntabl.employementprofilingsystem.Transfers.Tech;
import io.turntabl.employementprofilingsystem.Utilities.Date;
import io.turntabl.employementprofilingsystem.Utilities.Parsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@Api
@RestController
class EmployeeController implements EmployeeDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Parsor parsor = new Parsor();
    Date date = new Date();


    @ApiOperation("Add New Employee")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/employee")
    @Override
    public Map<String, Object> addEmployee(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();

        try{

            List<String> requiredParams = Arrays.asList(
                    "employee_firstname",
                    "employee_lastname",
                    "employee_phonenumber",
                    "employee_email",
                    "employee_role",
                    "employee_address",
                    "employee_dev_level",
                    "employee_gender",
                    "employee_tech_stack"
            );

            Map<String, Object> result = parsor.validate_params(requestData,requiredParams);
            if (result.get("code").equals("00")){

                java.sql.Date employee_hire_date = date.getCurrentDate();
                Boolean employee_onleave = false;
                String employee_dev_level = (String) requestData.get("employee_dev_level");
                String employee_gender = (String) requestData.get("employee_gender");
                String employee_role = (String) requestData.get("employee_role");

                SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName("employee").usingGeneratedKeyColumns("employee_id");

                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("employee_firstname",requestData.get("employee_firstname"));
                parameters.put("employee_lastname",requestData.get("employee_lastname"));
                parameters.put("employee_phonenumber",requestData.get("employee_phonenumber"));
                parameters.put("employee_email",requestData.get("employee_email"));
                parameters.put("employee_address",requestData.get("employee_address"));
                parameters.put("employee_role",employee_role.toUpperCase());
                parameters.put("employee_dev_level",employee_dev_level.toUpperCase());
                parameters.put("employee_hire_date",employee_hire_date);
                parameters.put("employee_onleave",employee_onleave);
                parameters.put("employee_gender",employee_gender.toUpperCase());
                Number key = insertActor.executeAndReturnKey(parameters);
                if (key != null){
                    Long employee_key = key.longValue();
                    List<Integer> employee_tech_stack = (List<Integer>)requestData.get("employee_tech_stack");
                    for(Integer tech: employee_tech_stack){
                        jdbcTemplate.update(
                                "insert into employeetech(tech_id,employee_id) values(?,?)",
                                new Object[]{
                                        tech,
                                        employee_key
                                }
                        );
                    }
                    response.put("code","00");
                    response.put("msg","New employee added successfully");
                }else {
                    response.put("code","01");
                    response.put("msg","Failed to add new employee, try again later");
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
    @ApiOperation("List of Employee Profile")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/employees")
    @Override
    public Map<String, Object> getAllEmployeeProfile(){
        List<SingleProfileTO> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try{
            List<Employee> employeeTOList =  jdbcTemplate.query(
                    "select * from employee",
                    BeanPropertyRowMapper.newInstance(Employee.class)
            );

            for (Employee employee: employeeTOList){
                List<Project> projectTOS =  jdbcTemplate.query(
                        "select * from project inner join assignedproject on project.project_id = assignedproject.project_id inner join employee on assignedproject.employee_id = employee.employee_id where employee.employee_id = ? ",
                        new Object[]{employee.getEmployee_id()},
                        BeanPropertyRowMapper.newInstance(Project.class)
                );
                List<Tech>techStack =  jdbcTemplate.query(

                        "select * from tech inner join employeetech on tech.tech_id = employeetech.tech_id inner join employee on employeetech.employee_id = employee.employee_id where employee.employee_id = ? ",
                        new Object[]{employee.getEmployee_id()},
                        BeanPropertyRowMapper.newInstance(Tech.class)
                );
                result.add(this.SingleProfileTOrowMappper(employee,projectTOS, techStack));
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

    private SingleProfileTO SingleProfileTOrowMappper(Employee employee, List<Project> projectTOS, List<Tech> techStack ) throws SQLException {
        SingleProfileTO singleProfileTO = new SingleProfileTO();
        singleProfileTO.setEmployee(employee);
        singleProfileTO.setProjects(projectTOS);
        singleProfileTO.setTech_stack(techStack);
        return singleProfileTO;
    }


}
