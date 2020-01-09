package io.turntabl.employementprofilingsystem.v1.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.v1.DAO.EmployeeDAO;
import io.turntabl.employementprofilingsystem.v1.Utilities.Date;
import io.turntabl.employementprofilingsystem.v1.Utilities.Parsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.*;

@Api
@RestController
public class Employee implements EmployeeDAO {
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
                    "employee_address",
                    "employee_dev_level",
                    "employee_gender"

            );

            Map<String, Object> result = parsor.validate_params(requestData,requiredParams);
            if (result.get("code").equals("00")){

                java.sql.Date employee_hire_date = date.getCurrentDate();
                Boolean employee_onleave = false;

                Integer resp = jdbcTemplate.update(
                        "insert into employee(employee_firstname,employee_lastname,employee_phonenumber,employee_email,employee_address,employee_dev_level,employee_hire_date,employee_onleave,employee_gender) values(?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                requestData.get("employee_firstname"),
                                requestData.get("employee_lastname"),
                                requestData.get("employee_phonenumber"),
                                requestData.get("employee_email"),
                                requestData.get("employee_address"),
                                requestData.get("employee_dev_level"),
                                employee_hire_date,
                                employee_onleave,
                                requestData.get("employee_gender")
                        }
                );
                if (resp > 0){
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


}
