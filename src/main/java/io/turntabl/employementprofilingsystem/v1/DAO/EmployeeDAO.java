package io.turntabl.employementprofilingsystem.v1.DAO;

import org.springframework.stereotype.Component;

import java.util.Map;


public interface EmployeeDAO {
    public Map<String,Object> addEmployee(Map<String,Object> requestData);
}
