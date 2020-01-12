package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Models.AddTech;
import io.turntabl.employementprofilingsystem.Models.EditTech;
import io.turntabl.employementprofilingsystem.Transfers.Employee;
import io.turntabl.employementprofilingsystem.Transfers.Tech;
import io.turntabl.employementprofilingsystem.Utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api
@RestController
public class TechController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    Parsor parsor = new Parsor();

    @ApiOperation("List of Technologies")
    @CrossOrigin(origins = "*")
    @GetMapping("/v1/api/technologies")
    public Map<String, Object> getAllTechnologies(){

        Map<String, Object> response = new HashMap<>();
        try{
            List<Tech> Technologies =  jdbcTemplate.query(
                    "select * from tech",
                    BeanPropertyRowMapper.newInstance(Tech.class)
            );
            response.put("code","00");
            response.put("msg","Data retrieved successfully");
            response.put("data",Technologies);

        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;

    }
    @ApiOperation("Add New Technology")
    @CrossOrigin(origins = "*")
    @PostMapping("/v1/api/technology")
    public Map<String, Object> addTechnology(@RequestBody AddTech requestData) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("tech_name",requestData.getTech_name());
        request.put("tech_status",requestData.getTech_status());
        try {

            List<String> requiredParams = Arrays.asList("tech_name", "tech_status");
            Map<String, Object> result = parsor.validate_params(request, requiredParams);
            if (result.get("code").equals("00")) {
                List<Tech> tech = jdbcTemplate.query(
                        "select * from tech where tech_name = ?",
                        new Object[]{requestData.getTech_name().toUpperCase()},
                        BeanPropertyRowMapper.newInstance(Tech.class)
                );
                if (tech.isEmpty()) {
                    jdbcTemplate.update(
                            "insert into tech(tech_name, tech_status) values(?,?)",
                            new Object[]{
                                    requestData.getTech_name().toUpperCase(),
                                    requestData.getTech_status().toUpperCase()
                            }
                    );
                    response.put("code", "00");
                    response.put("msg", "New technology added successfully");
                }
                else {
                    response.put("code", "01");
                    response.put("msg", "Technology with such name already exists!");
                }
            }else {
                    response.put("code", result.get("code"));
                    response.put("msg", result.get("msg"));
                }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;
    }

    public List<Tech> searchTechByID(Integer techID) {
       List<Tech> technology = jdbcTemplate.query(
                "select * from tech where tech_id = ?",
                new Object[]{techID},
                BeanPropertyRowMapper.newInstance(Tech.class)
        );
        return technology;
    }

    @ApiOperation("Edit Technology")
    @CrossOrigin(origins = "*")
    @PutMapping("/v1/api/technology")
    public Map<String, Object> editTechnology(@RequestBody EditTech requestData) {

        Integer id = requestData.getTech_id();
        Map<String, Object> response = new HashMap<>();
        try{
            List<Tech> technology = searchTechByID(id);

            if (!technology.isEmpty()) {
                String tech_name;
                String tech_status;

                if (requestData.getTech_name().isEmpty()) {
                    tech_name = technology.get(0).getTech_name();
                }else {
                    tech_name = requestData.getTech_name().toUpperCase();
                }
                if (requestData.getTech_status().isEmpty()) {
                    tech_status = technology.get(0).getTech_status();
                }else {
                    tech_status = requestData.getTech_status().toUpperCase();
                }
                jdbcTemplate.update(
                        "update tech set tech_name = ?, tech_status = ? where tech_id = ?",
                        new Object[]{tech_name, tech_status, id}
                );
                response.put("code","00");
                response.put("msg","Technology updated successfully");

            } else {
                response.put("code","00");
                response.put("msg","Technology does not exist!");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }

        return response;
    }

    private Boolean checkExistingTechnology(String email, String phoneNumber){
        Boolean response = null;

        try{
            List<Employee> employee = jdbcTemplate.query(
                    "select * from employee where employee_email = ? or employee_phonenumber = ? ",
                    new Object[]{email, phoneNumber},
                    BeanPropertyRowMapper.newInstance(Employee.class)
            );
            if (employee.isEmpty()){
                response = false;
            }else {
                response = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}

