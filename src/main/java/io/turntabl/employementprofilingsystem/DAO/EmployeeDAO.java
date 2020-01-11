package io.turntabl.employementprofilingsystem.DAO;

import io.turntabl.employementprofilingsystem.Models.AddEmployee;
import io.turntabl.employementprofilingsystem.Models.EditEmployee;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


public interface EmployeeDAO {
    public Map<String,Object> addEmployee( AddEmployee requestData);
    public Map<String, Object> getAllEmployeeProfile();
    public Map<String, Object> getEmployeeProfileById(@PathVariable("id") Integer id);
    public Map<String, Object> updateEmployeeProfile(@RequestBody EditEmployee editEmployee);
}
