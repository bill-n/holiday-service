//package io.turntabl.employementprofilingsystem.Controllers;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.turntabl.employementprofilingsystem.Transfers.Employee;
//import io.turntabl.employementprofilingsystem.Transfers.EmployeeProject;
//import io.turntabl.employementprofilingsystem.Utilities.Parsor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Api
//@RestController
//public class AWSDatabaseOperation {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    Parsor parsor = new Parsor();
//
//    @ApiOperation("Database Operation")
//    @CrossOrigin(origins = "*")
//    @GetMapping("/v1/api/operation")
//    public Map<String,Object> createTable() {
//
//        Map<String, Object> request = new HashMap<>();
//        Map<String, Object> response = new HashMap<>();
//
//        try{
//            jdbcTemplate.update(
//                    "delete from assignedproject where employee_id = ? and project_id = ?",
//                    new Object[]{1,2}
//            );
//            response.put("code","00");
//            response.put("msg","Database Operation was successful");
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("code","02");
//            response.put("msg","Something went wrong, try again later");
//        }
//        return response;
//    }
//}
