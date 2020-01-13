package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Transfers.*;
import io.turntabl.employementprofilingsystem.Utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@Api
@RestController
public class LoginController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Parsor parsor = new Parsor();

    @ApiOperation("Get Employee By Email")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/login/{employee_email}")
    public List<Employee> getEmployeeProfileById(@PathVariable("employee_email") String email) {
        List<Employee> employee = new ArrayList<>();
        Map<String, Object> request = new HashMap<>();
        request.put("employee_email", email);

            List<String> requiredParams = Arrays.asList(
                    "employee_email"
            );
            Map<String, Object> valid = parsor.validate_params(request, requiredParams);
            if (valid.get("code").equals("00")) {
                employee = jdbcTemplate.query(
                        "select * from employee where employee_email = ?",
                        new Object[]{email},
                        BeanPropertyRowMapper.newInstance(Employee.class)
                );

            }
                return employee;

    }
}