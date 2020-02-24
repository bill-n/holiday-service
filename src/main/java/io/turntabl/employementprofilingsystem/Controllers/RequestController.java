package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Models.RequestTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class RequestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @CrossOrigin
    @ApiOperation("Make a request")
    @PostMapping("/api/v1/request")
    public void makeARequest(@RequestBody RequestTO request) {
        jdbcTemplate.update("insert into requests(requester_id, request_start_date, request_report_date) values(?,?,?)",
                request.getRequester_id(), request.getRequest_start_date(), request.getRequest_report_date());
    }

    @CrossOrigin
    @ApiOperation("Get all requests for requester")
    @GetMapping("/api/v1/request/requester/{id}")
    public List<RequestTO> getRequestByRequesterId(@PathVariable("id") Integer id) {
        return this.jdbcTemplate.query(
                "select select request_start_date, request_report_date, request_status.req_status from requests inner join request_status on " +
                        "requests.request_status_id = request_status.request_status_id where requester_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(RequestTO.class)
        );
    }

    @CrossOrigin
    @ApiOperation("Get all requests")
    @GetMapping("/api/v1/requests")
    public List<RequestTO> getAllRequests() {
        return this.jdbcTemplate.query("select * from requests",
                new BeanPropertyRowMapper<RequestTO>(RequestTO.class)
        );
    }

    @CrossOrigin
    @ApiOperation("Accept Request")
    @PutMapping("/api/v1/requests/approve/{id}")
    public void approveRequest(@PathVariable("id") Integer request_id) {
        this.jdbcTemplate.update("update requests set request_status_id = 3 where request_status_id = 1 and request_id = ?", request_id);
    }

    @CrossOrigin
    @ApiOperation("Decline Request")
    @PutMapping("/api/v1/requests/decline/{id}")
    public void declineRequest(@PathVariable("id") Integer request_id) {
        this.jdbcTemplate.update("update requests set request_status_id = 2 where request_status_id = 1 and request_id = ?", request_id);
    }
}

