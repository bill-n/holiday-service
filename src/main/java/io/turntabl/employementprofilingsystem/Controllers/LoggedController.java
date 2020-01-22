package io.turntabl.employementprofilingsystem.Controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.DAO.LoggedDAO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedProjectTO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedSickTO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedTO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedVacationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Api
@RestController
public class LoggedController implements LoggedDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ApiOperation("Log Project Hour")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/v1/api/addloggedproject")
    @Override
    public void addLoggedProject(@RequestBody LoggedProjectTO loggedProjectTO) {
         this.jdbcTemplate.update("insert into LoggedProject (project_id, employee_id, project_hours, project_date) values (?, ?, ?, ?)",
                loggedProjectTO.getProject_id(), loggedProjectTO.getEmployee_id(), loggedProjectTO.getProject_hours(), loggedProjectTO.getProject_date());

    }


    @ApiOperation(" get Logged Projects ")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/v1/api/getloggedproject")
    @Override
    public List<LoggedProjectTO> getAllLoggedProject() {
       return this.jdbcTemplate.query("select * from LoggedProject", BeanPropertyRowMapper.newInstance(LoggedProjectTO.class));
    }

    @ApiOperation(" get Logged Sick ")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/v1/api/getloggedsick")
    @Override
    public List<LoggedSickTO> getAllLoggedSick() {
        return this.jdbcTemplate.query("select * from LoggedSick", BeanPropertyRowMapper.newInstance(LoggedSickTO.class));
    }


    @ApiOperation("Log Sick")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/v1/api/addloggedsick")
    @Override
    public void addLoggedSick(@RequestBody LoggedSickTO loggedSickTO) {
        this.jdbcTemplate.update("insert into LoggedSick (employee_id, sick_date) values (?, ?)",
               loggedSickTO.getEmployee_id(), loggedSickTO.getSick_date());

    }


    @ApiOperation(" get Logged Vacation ")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/v1/api/getloggedvacation")
    @Override
    public List<LoggedVacationTO> getAllLoggedVacation() {
        return this.jdbcTemplate.query("select * from LoggedVacation", BeanPropertyRowMapper.newInstance(LoggedVacationTO.class));
    }


    @ApiOperation("Log Vacation")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/v1/api/addloggedvaction")
    @Override
    public void addLoggedVacation(@RequestBody LoggedVacationTO loggedVacationTO) {
        this.jdbcTemplate.update("insert into LoggedVacation (employee_id, vacation_date) values (?, ?)",
                loggedVacationTO.getEmployee_id(), loggedVacationTO.getVacation_date());

    }

    @ApiOperation(" get ALL Logged ")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/v1/api/getlogged")
    @Override
    public List<LoggedTO> getAllLogged(@RequestParam("start_date") Date startDate, @RequestParam("end_date") Date endDate) {
        return this.jdbcTemplate.query("select * from LoggedChart(?::date,?::date)",
                new Object[]{startDate, endDate},
                BeanPropertyRowMapper.newInstance(LoggedTO.class));
    }
}
// List<LoggedTO> getAllLogged(Date startDate, Date endDate);
//@DateTimeFormat(pattern = "yyyy-MM-dd")
