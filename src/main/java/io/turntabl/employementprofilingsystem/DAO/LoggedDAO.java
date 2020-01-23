package io.turntabl.employementprofilingsystem.DAO;

import io.swagger.annotations.ApiOperation;
import io.turntabl.employementprofilingsystem.Transfers.LoggedProjectTO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedSickTO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedTO;
import io.turntabl.employementprofilingsystem.Transfers.LoggedVacationTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LoggedDAO {

    Map<String, Object> addLoggedProject(LoggedProjectTO loggedProjectTO);

   // List<LoggedProjectTO>
    Map<String, Object> getAllLoggedProject();


    Map<String, Object> addLoggedSick(LoggedSickTO loggedSickTO);

   // List<LoggedSickTO>
    Map<String, Object> getAllLoggedSick();

    Map<String, Object> addLoggedVacation(LoggedVacationTO loggedVacationTO);

   // List<LoggedVacationTO>
    Map<String, Object> getAllLoggedVacation();



  // List<LoggedTO>
    Map<String, Object> getAllLogged(java.sql.Date endDate);
}

