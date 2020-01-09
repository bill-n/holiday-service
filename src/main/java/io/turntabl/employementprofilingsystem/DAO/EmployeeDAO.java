package io.turntabl.employementprofilingsystem.DAO;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface EmployeeDAO {
    public Map<String,Object> addEmployee(Map<String,Object> requestData);
    public Map<String, Object> getAllEmployeeProfile();
}
