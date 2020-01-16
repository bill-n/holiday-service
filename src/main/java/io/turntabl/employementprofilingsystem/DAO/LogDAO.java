package io.turntabl.employementprofilingsystem.DAO;

import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Models.LogProjectVolunteering;
import io.turntabl.employementprofilingsystem.Models.LogSick;
import io.turntabl.employementprofilingsystem.Models.LogVacation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface LogDAO {
    @ApiOperation("List of All Employee Logs")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/employees")
    Map<String, Object> getAllEmployeeLogs();

    @ApiOperation("Log Hours on Project or Volunteering")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/log/project_volunteering")
    Map<String, Object> logOnProjectVolunteering(@RequestBody LogProjectVolunteering logProjectVolunteering);

    @ApiOperation("Log Hours on Sick")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/log/sick")
    Map<String, Object> logOnSick(@RequestBody LogSick logSick);

    @ApiOperation("Log Hours on Vacation")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/log/vacation")
    Map<String, Object> logOnVacation(@RequestBody LogVacation logVacation);
}
