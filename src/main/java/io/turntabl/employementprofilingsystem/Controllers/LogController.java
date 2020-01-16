package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.DAO.LogDAO;
import io.turntabl.employementprofilingsystem.Models.LogProjectVolunteering;
import io.turntabl.employementprofilingsystem.Models.LogSick;
import io.turntabl.employementprofilingsystem.Models.LogVacation;
import io.turntabl.employementprofilingsystem.Transfers.*;
import io.turntabl.employementprofilingsystem.Utilities.Date;
import io.turntabl.employementprofilingsystem.Utilities.Parsor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

@Api
@RestController
public class LogController implements LogDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Parsor parsor = new Parsor();
    Date date = new Date();


//    @ApiOperation("List of All Employee Logs")
//    @CrossOrigin(origins = "*")
//    @GetMapping("/v1/api/logs")
//    @Override
//    public Map<String, Object> getAllEmployeeLogs(){
//        List<SingleEmployeeLogs> result = new ArrayList<>();
//        Map<String, Object> response = new HashMap<>();
//        try{
//            List<SingleEmployeeLogs> singleEmployeeLogs =  jdbcTemplate.query(
//                    "select employee.employee_firstname,employee.employee_lastname, project.project_name, log_project.project_hours,log_volunteering.volunteering_hours,log_sick.issick,log_vacation.isonvacation,log_project.project_logged_date from employee inner join log_vacation on employee.employee_id = log_vacation.employee_id inner join log_sick on log_vacation.employee_id = log_sick.employee_id inner join log_volunteering on log_sick.employee_id = log_volunteering.employee_id inner join log_project on log_volunteering.employee_id = log_project.employee_id inner join project on log_project.project_id = project.project_id ",
//                    BeanPropertyRowMapper.newInstance(SingleEmployeeLogs.class)
//            );
//            response.put("code","00");
//            response.put("msg","Data retrieved successfully");
//            response.put("data",singleEmployeeLogs);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("code","02");
//            response.put("msg","Something went wrong, try again later");
//        }
//        return response;
//    }
//
//    @Override
//    @ApiOperation("Log Hours on Project or Volunteering")
//    @CrossOrigin(origins = "*")
//    @PostMapping("/v1/api/log/project_volunteering")
//    public Map<String, Object> logOnProjectVolunteering(@RequestBody LogProjectVolunteering logProjectVolunteering){
//        List<SingleProfileTO> result = new ArrayList<>();
//        Map<String, Object> response = new HashMap<>();
//        Map<String, Object> request = new HashMap<>();
//        request.put("employee_id",logProjectVolunteering.getEmployee_id());
//        request.put("logged_date",logProjectVolunteering.getLogged_date());
//
//        try{
//            List<String> requiredParams = Arrays.asList(
//                    "employee_id",
//                    "logged_date"
//            );
//            Map<String, Object> valid = parsor.validate_params(request,requiredParams);
//            if (valid.get("code").equals("00")){
//                Map<String, Object> log_params = this.check_log_params(logProjectVolunteering);
//                if (log_params.get("code").equals("00")){
//                    LogProjectVolunteering updatedLogProjectVolunteering = (LogProjectVolunteering) log_params.get("data");
//                    java.sql.Date logged_date = date.getDateObject(updatedLogProjectVolunteering.getLogged_date());
//                    jdbcTemplate.update(
//                            "insert into log_project(employee_id,project_id,project_logged_date,project_hours) values(?,?,?,?)",
//                            new Object[]{
//                                    updatedLogProjectVolunteering.getEmployee_id(),
//                                    updatedLogProjectVolunteering.getProject_id(),
//                                    logged_date,
//                                    updatedLogProjectVolunteering.getProject_hours()
//                            }
//                    );
//                    jdbcTemplate.update(
//                            "insert into log_volunteering(employee_id,volunteering_hours,volunteering_logged_date) values(?,?,?)",
//                            new Object[]{
//                                    updatedLogProjectVolunteering.getEmployee_id(),
//                                    updatedLogProjectVolunteering.getVolunteering_hours(),
//                                    logged_date
//                            }
//                    );
//                    jdbcTemplate.update(
//                            "insert into log_vacation(employee_id,isonvacation,vacation_logged_date,resumed_logged_date) values(?,?,?,?)",
//                            new Object[]{
//                                    updatedLogProjectVolunteering.getEmployee_id(),
//                                    null,
//                                    null,
//                                    null
//                            }
//                    );
//                    jdbcTemplate.update(
//                            "insert into log_sick(employee_id,issick,sick_logged_date,recovered_logged_date) values(?,?,?,?)",
//                            new Object[]{
//                                    updatedLogProjectVolunteering.getEmployee_id(),
//                                    null,
//                                    null,
//                                    null
//                            }
//                    );
//                    response.put("code","00");
//                    response.put("msg","Data recorded successfully");
//                }else {
//                    response.put("code",log_params.get("code"));
//                    response.put("msg",log_params.get("msg"));
//                }
//            }else {
//                response.put("code",valid.get("code"));
//                response.put("msg",valid.get("msg"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("code","02");
//            response.put("msg","Something went wrong, try again later");
//        }
//        return response;
//    }
//
//    @Override
//    @ApiOperation("Log Hours on Sick")
//    @CrossOrigin(origins = "*")
//    @PostMapping("/v1/api/log/sick")
//    public Map<String, Object> logOnSick(@RequestBody LogSick logSick){
//        List<SingleProfileTO> result = new ArrayList<>();
//        Map<String, Object> response = new HashMap<>();
//        Map<String, Object> request = new HashMap<>();
//        request.put("employee_id",logSick.getEmployee_id());
//        request.put("logged_date",logSick.getLogged_date());
//
//        try{
//            List<String> requiredParams = Arrays.asList(
//                    "employee_id",
//                    "logged_date"
//            );
//            Map<String, Object> valid = parsor.validate_params(request,requiredParams);
//            if (valid.get("code").equals("00")){
//
//                java.sql.Date logged_date = date.getDateObject(logSick.getLogged_date());
//                jdbcTemplate.update(
//                        "insert into log_sick(employee_id,issick,sick_logged_date,recovered_logged_date) values(?,?,?,?)",
//                        new Object[]{
//                                logSick.getEmployee_id(),
//                                true,
//                                logged_date,
//                                null
//                        }
//                );
//                jdbcTemplate.update(
//                        "insert into log_project(employee_id,project_id,project_logged_date,project_hours) values(?,?,?,?)",
//                        new Object[]{
//                                logSick.getEmployee_id(),
//                                null,
//                                logged_date,
//                                null
//                        }
//                );
//                jdbcTemplate.update(
//                        "insert into log_volunteering(employee_id,volunteering_hours,volunteering_logged_date) values(?,?,?)",
//                        new Object[]{
//                                logSick.getEmployee_id(),
//                                null,
//                                null
//                        }
//                );
//                jdbcTemplate.update(
//                        "insert into log_vacation(employee_id,isonvacation,vacation_logged_date,resumed_logged_date) values(?,?,?,?)",
//                        new Object[]{
//                                logSick.getEmployee_id(),
//                                null,
//                                null,
//                                null
//                        }
//                );
//                response.put("code","00");
//                response.put("msg","Data recorded successfully");
//            }else {
//                response.put("code",valid.get("code"));
//                response.put("msg",valid.get("msg"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("code","02");
//            response.put("msg","Something went wrong, try again later");
//        }
//        return response;
//    }
//
//    @Override
//    @ApiOperation("Log Hours on Vacation")
//    @CrossOrigin(origins = "*")
//    @PostMapping("/v1/api/log/vacation")
//    public Map<String, Object> logOnVacation(@RequestBody LogVacation logVacation){
//        List<SingleProfileTO> result = new ArrayList<>();
//        Map<String, Object> response = new HashMap<>();
//        Map<String, Object> request = new HashMap<>();
//        request.put("employee_id",logVacation.getEmployee_id());
//        request.put("logged_date",logVacation.getLogged_date());
//
//        try{
//            List<String> requiredParams = Arrays.asList(
//                    "employee_id",
//                    "logged_date"
//            );
//            Map<String, Object> valid = parsor.validate_params(request,requiredParams);
//            if (valid.get("code").equals("00")){
//
//                java.sql.Date logged_date = date.getDateObject(logVacation.getLogged_date());
//                jdbcTemplate.update(
//                        "insert into log_vacation(employee_id,isonvacation,vacation_logged_date,resumed_logged_date) values(?,?,?,?)",
//                        new Object[]{
//                                logVacation.getEmployee_id(),
//                                true,
//                                logged_date,
//                                null
//                        }
//                );
//                jdbcTemplate.update(
//                        "insert into log_project(employee_id,project_id,project_logged_date,project_hours) values(?,?,?,?)",
//                        new Object[]{
//                                logVacation.getEmployee_id(),
//                                null,
//                                logged_date,
//                                null
//                        }
//                );
//                jdbcTemplate.update(
//                        "insert into log_volunteering(employee_id,volunteering_hours,volunteering_logged_date) values(?,?,?)",
//                        new Object[]{
//                                logVacation.getEmployee_id(),
//                                null,
//                                null
//                        }
//                );
//                jdbcTemplate.update(
//                        "insert into log_sick(employee_id,issick,sick_logged_date,recovered_logged_date) values(?,?,?,?)",
//                        new Object[]{
//                                logVacation.getEmployee_id(),
//                                null,
//                                null,
//                                null
//                        }
//                );
//                response.put("code","00");
//                response.put("msg","Data recorded successfully");
//            }else {
//                response.put("code",valid.get("code"));
//                response.put("msg",valid.get("msg"));
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("code","02");
//            response.put("msg","Something went wrong, try again later");
//        }
//        return response;
//    }
//
//    private Map<String, Object> check_log_params(LogProjectVolunteering logProjectVolunteering){
//        Map<String, Object> response = new HashMap<>();
//        LogProjectVolunteering newLogProjectVolunteering = new LogProjectVolunteering();
//        try{
//            newLogProjectVolunteering.setEmployee_id(logProjectVolunteering.getEmployee_id());
//            newLogProjectVolunteering.setLogged_date(logProjectVolunteering.getLogged_date());
//            newLogProjectVolunteering.setProject_id(logProjectVolunteering.getProject_id());
//
//            if (logProjectVolunteering.getProject_hours() == null){
//                newLogProjectVolunteering.setProject_hours(0);
//            }else{
//                newLogProjectVolunteering.setProject_hours(logProjectVolunteering.getProject_hours());
//            }
//            if (logProjectVolunteering.getVolunteering_hours() == null){
//                newLogProjectVolunteering.setVolunteering_hours(0);
//            }else {
//                newLogProjectVolunteering.setVolunteering_hours(logProjectVolunteering.getVolunteering_hours());
//            }
//            response.put("code","00");
//            response.put("msg","Data retrieved successfully");
//            response.put("data",newLogProjectVolunteering);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("code","02");
//            response.put("msg","Something went wrong, try again later");
//        }
//        return response;
//    }

}
