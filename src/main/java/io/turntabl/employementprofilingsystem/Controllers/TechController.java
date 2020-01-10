package io.turntabl.employementprofilingsystem.Controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Models.AddTech;
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
    @PostMapping("/v1/api/addtechnology")
    public Map<String, Object> addTechnology(@RequestBody AddTech requestData) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> request = new HashMap<>();
        request.put("tech_name",requestData.getTech_name());
        request.put("tech_status",requestData.getTech_status());
        try {

            List<String> requiredParams = Arrays.asList("tech_name", "tech_status");
            Map<String, Object> result = parsor.validate_params(request, requiredParams);
            if (result.get("code").equals("00")) {
                jdbcTemplate.update(
                        "insert into tech(tech_name, tech_status) values(?,?)",
                        new Object[]{
                                requestData.getTech_name(),
                                requestData.getTech_status()
                        }
                );
                response.put("code", "00");
                response.put("msg", "New technology added successfully");
            } else {
                response.put("code", "01");
                response.put("msg", "Failed to add new technology, try again");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.put("code","02");
            response.put("msg","Something went wrong, try again later");
        }
        return response;

    }

    public Optional<List<Tech>> searchTechByID(Integer techID) {
        Optional<List<Tech>> technology = Optional.ofNullable(jdbcTemplate.query(
                "select * from tech where tech_id = ?",
                new Object[]{techID},
                BeanPropertyRowMapper.newInstance(Tech.class)
        ));
        return technology;
    }

    @ApiOperation("Edit Technology")
    @CrossOrigin(origins = "*")
    @PutMapping("/v1/api/editTechnology")
    public void  editTechnology(@RequestBody AddTech requestData) {

        Integer id = Integer.parseInt(requestData.getTech_id());
        Map<String, Object> response = new HashMap<>();
        Optional<List<Tech>> technology = searchTechByID(Integer.parseInt(requestData.getTech_id()));
//        String tech_name;
//        String tech_status;

        if (technology.isPresent()) {
            String tech_name;
            String tech_status;

            if (requestData.getTech_name().isEmpty()) {
                tech_name = technology.get().get(0).getTech_name();
            } else {
                tech_name = requestData.getTech_name();
            }
            if (requestData.getTech_status().isEmpty()) {
                tech_status = technology.get().get(0).getTech_status();
            } else {
                tech_status = requestData.getTech_status();
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
    }

}

