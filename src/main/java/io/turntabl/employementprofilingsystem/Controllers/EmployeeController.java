package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.turntabl.employementprofilingsystem.DAO.EmployeeDAO;
import io.turntabl.employementprofilingsystem.Models.AddEmployee;
import io.turntabl.employementprofilingsystem.Models.EditEmployee;
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
class EmployeeController implements EmployeeDAO{
    @Autowired
    JdbcTemplate jdbcTemplate;

    Parsor parsor = new Parsor();
    Date date = new Date();


    @ApiOperation("Add New Employee")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/employee")
    @Override
    public Map<String, Object> addEmployee(@RequestBody AddEmployee requestData) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("employee_firstname",requestData.getEmployee_firstname());
        request.put("employee_lastname",requestData.getEmployee_lastname());
        request.put("employee_phonenumber",requestData.getEmployee_phonenumber());
        request.put("employee_email",requestData.getEmployee_email());
        request.put("employee_role",requestData.getEmployee_role());
        request.put("employee_address",requestData.getEmployee_address());
        request.put("employee_dev_level",requestData.getEmployee_dev_level());
        request.put("employee_gender",requestData.getEmployee_gender());
        request.put("employee_status",requestData.getEmployee_status());
        request.put("employee_tech_stack",requestData.getEmployee_tech_stack());

        try{

            List<String> requiredParams = Arrays.asList(
                    "employee_email",
                    "employee_role"
            );
            Map<String, Object> result = parsor.validate_params(request,requiredParams);
            if (result.get("code").equals("00")){

                if (this.checkExistingEmployee(requestData.getEmployee_email())){
                    response.put("code","01");
                    response.put("msg","Employee already exist with the same email");
                }else {
                    java.sql.Date employee_hire_date = date.getCurrentDate();
                    Boolean employee_onleave = false;
                    String employee_dev_level = requestData.getEmployee_dev_level();
                    String employee_gender = requestData.getEmployee_gender();
                    String employee_role = requestData.getEmployee_role();
                    String employee_status = requestData.getEmployee_status();

                    SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate).withTableName("employee").usingGeneratedKeyColumns("employee_id");

                    Map<String, Object> parameters = new HashMap<String, Object>();
                    parameters.put("employee_firstname",requestData.getEmployee_firstname());
                    parameters.put("employee_lastname",requestData.getEmployee_lastname());
                    parameters.put("employee_phonenumber",requestData.getEmployee_phonenumber());
                    parameters.put("employee_email",requestData.getEmployee_email());
                    parameters.put("employee_address",requestData.getEmployee_address());
                    parameters.put("employee_role",employee_role.toUpperCase());
                    parameters.put("employee_status",employee_status.toUpperCase());
                    parameters.put("employee_dev_level",employee_dev_level.toUpperCase());
                    parameters.put("employee_hire_date",employee_hire_date);
                    parameters.put("employee_onleave",employee_onleave);
                    parameters.put("employee_gender",employee_gender.toUpperCase());

                    Number key = insertActor.executeAndReturnKey(parameters);
                    Long employee_key = key.longValue();
                    if (key != null){

                        List<Integer> employee_tech_stack = requestData.getEmployee_tech_stack();
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
                        this.deleteEmployeeRow(employee_key);
                        response.put("code","01");
                        response.put("msg","Failed to add new employee, try again later");
                    }
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
                List<EmployeeProject> projectTOS =  jdbcTemplate.query(
                        "select * from project inner join assignedproject on project.project_id = assignedproject.project_id inner join employee on assignedproject.employee_id = employee.employee_id where employee.employee_id = ? ",
                        new Object[]{employee.getEmployee_id()},
                        BeanPropertyRowMapper.newInstance(EmployeeProject.class)
                );
                List<Tech> techStack =  jdbcTemplate.query(

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

    @ApiOperation("Get Employee Profile By Id")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/employee/{id}")
    @Override
    public Map<String, Object> getEmployeeProfileById(@PathVariable("id") Integer id){
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
                    List<Tech> techStack = jdbcTemplate.query(

                            "select * from tech inner join employeetech on tech.tech_id = employeetech.tech_id inner join employee on employeetech.employee_id = employee.employee_id where employee.employee_id = ? ",
                            new Object[]{id},
                            BeanPropertyRowMapper.newInstance(Tech.class)
                    );
                    response.put("code","00");
                    response.put("msg","Data retrieved successfully");
                    response.put("data",this.SingleProfileTOrowMappper(employee.get(0),projectTOS, techStack));
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

    @ApiOperation("Edit Employee Profile")
    @CrossOrigin(origins = "*")
    @PutMapping("/v1/api/employee")
    @Override
    public Map<String, Object> updateEmployeeProfile(@RequestBody EditEmployee editEmployee){
        List<SingleProfileTO> result = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("employee_id",editEmployee.getEmployee_id());
        request.put("employee_firstname",editEmployee.getEmployee_firstname());
        request.put("employee_lastname",editEmployee.getEmployee_lastname());
        request.put("employee_role",editEmployee.getEmployee_role());
        request.put("employee_address",editEmployee.getEmployee_address());
        request.put("employee_dev_level",editEmployee.getEmployee_dev_level());
        request.put("employee_status",editEmployee.getEmployee_status());
        request.put("employee_tech_stack",editEmployee.getEmployee_tech_stack());

        try{
            List<String> requiredParams = Arrays.asList(
                    "employee_id"
            );
            Map<String, Object> valid = parsor.validate_params(request,requiredParams);
            if (valid.get("code").equals("00")){
                Map<String, Object> updated_params = this.check_updated_params(editEmployee);
                if (updated_params.get("code").equals("00")){
                    UpdateEmployee updateEmployee = (UpdateEmployee) updated_params.get("data");
                    jdbcTemplate.update(
                            "update employee set employee_firstname = ?, employee_lastname = ?, employee_address = ?,employee_dev_level = ?,employee_status = ?,employee_role = ?  where employee_id = ?",
                            new Object[]{
                                    updateEmployee.getEmployee_firstname(),
                                    updateEmployee.getEmployee_lastname(),
                                    updateEmployee.getEmployee_address(),
                                    updateEmployee.getEmployee_dev_level().toUpperCase(),
                                    updateEmployee.getEmployee_status().toUpperCase(),
                                    updateEmployee.getEmployee_role().toUpperCase(),
                                    editEmployee.getEmployee_id()
                            }
                    );
                    List<Integer> user_current_tech = updateEmployee.getEmployee_tech_stack();

                    jdbcTemplate.update(
                            "delete from employeetech where employee_id = ?",
                            new Object[]{
                                    editEmployee.getEmployee_id()
                            }
                    );

                    for(Integer tech: user_current_tech){
                        jdbcTemplate.update(
                                "insert into employeetech(tech_id,employee_id) values(?,?)",
                                new Object[]{
                                        tech,
                                        editEmployee.getEmployee_id()
                                }
                        );
                    }

                    response.put("code","00");
                    response.put("msg","Employee updated successfully");

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

    private Map<String, Object> check_updated_params(EditEmployee editEmployee){
        Map<String, Object> response = new HashMap<>();
        UpdateEmployee updateEmployee = new UpdateEmployee();
        try{
            Integer employee_id = editEmployee.getEmployee_id();
            List<Employee> employee = jdbcTemplate.query(
                    "select * from employee where employee_id = ?",
                    new Object[]{employee_id},
                    BeanPropertyRowMapper.newInstance(Employee.class)
            );
            List<Tech> techStack = jdbcTemplate.query(

                    "select * from tech inner join employeetech on tech.tech_id = employeetech.tech_id inner join employee on employeetech.employee_id = employee.employee_id where employee.employee_id = ? ",
                    new Object[]{employee_id},
                    BeanPropertyRowMapper.newInstance(Tech.class)
            );
            List<Integer> techs = techStack.stream()
                    .map(tech -> tech.getTech_id())
                    .collect(Collectors.toList());

            if (!employee.isEmpty()){
                Employee employeeData = employee.get(0);
                if(editEmployee.getEmployee_firstname().isEmpty()){
                    updateEmployee.setEmployee_firstname(employeeData.getEmployee_firstname());
                }else {
                    updateEmployee.setEmployee_firstname(editEmployee.getEmployee_firstname());
                }
                if(editEmployee.getEmployee_lastname().isEmpty()){
                    updateEmployee.setEmployee_lastname(employeeData.getEmployee_lastname());
                }else {
                    updateEmployee.setEmployee_lastname(editEmployee.getEmployee_lastname());
                }
                if(editEmployee.getEmployee_address().isEmpty()){
                    updateEmployee.setEmployee_address(employeeData.getEmployee_address());
                }else {
                    updateEmployee.setEmployee_address(editEmployee.getEmployee_address());
                }
                if(editEmployee.getEmployee_dev_level().isEmpty()){
                    updateEmployee.setEmployee_dev_level(employeeData.getEmployee_dev_level());
                }else {
                    updateEmployee.setEmployee_dev_level(editEmployee.getEmployee_dev_level());
                }
                if(editEmployee.getEmployee_status().isEmpty()){
                    updateEmployee.setEmployee_status(employeeData.getEmployee_status());
                }else {
                    updateEmployee.setEmployee_status(editEmployee.getEmployee_status());
                }
                if(editEmployee.getEmployee_role().isEmpty()){
                    updateEmployee.setEmployee_role(employeeData.getEmployee_role());
                }else {
                    updateEmployee.setEmployee_role(editEmployee.getEmployee_role());
                }
                if(editEmployee.getEmployee_tech_stack().isEmpty()){
                    updateEmployee.setEmployee_tech_stack(techs);
                }else {
                    updateEmployee.setEmployee_tech_stack(editEmployee.getEmployee_tech_stack());
                }
                response.put("code","00");
                response.put("msg","Data retrieved successfully");
                response.put("data",updateEmployee);

            }else {
                response.put("code","01");
                response.put("msg","Employee with this ID ["+employee_id+"] doesn't exist");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }

        return response;
    }

    private Map<String, Object> getTechStacksByIDs(List<Integer> techIDs){
        Map<String, Object> response = new HashMap<>();
        List<Tech> techList = new ArrayList<>();
        try {
            for (Integer tech_id: techIDs){
                List<Tech> techRow =  jdbcTemplate.query(

                        "select * from tech where tech_id = ?",
                        new Object[]{tech_id},
                        BeanPropertyRowMapper.newInstance(Tech.class)
                );
                techList.add(techRow.get(0));
            }
            response.put("code","00");
            response.put("msg","Data retrieved successfully");
            response.put("data",techList);
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    private SingleProfileTO SingleProfileTOrowMappper(Employee employee, List<EmployeeProject> projectTOS, List<Tech> techStack ) throws SQLException {
        SingleProfileTO singleProfileTO = new SingleProfileTO();
        singleProfileTO.setEmployee(employee);
        singleProfileTO.setProjects(projectTOS);
        singleProfileTO.setTech_stack(techStack);
        return singleProfileTO;
    }
    private Boolean checkExistingEmployee(String email){
        Boolean response = null;

        try{
            List<Employee> employee = jdbcTemplate.query(
                    "select * from employee where employee_email = ?",
                    new Object[]{email},
                    BeanPropertyRowMapper.newInstance(Employee.class)
            );
            if (employee.isEmpty()){
                response = false;
            }else {
                response = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private void deleteEmployeeRow(Long id){
        String sql = "delete from employee where employee_id = ? ";
        jdbcTemplate.update(
                sql,
                new Object[]{id}
        );
    }
}
