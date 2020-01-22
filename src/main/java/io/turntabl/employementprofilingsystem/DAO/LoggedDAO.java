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

public interface LoggedDAO {

    void addLoggedProject(LoggedProjectTO loggedProjectTO);

    List<LoggedProjectTO> getAllLoggedProject();


    void addLoggedSick(LoggedSickTO loggedSickTO);

    List<LoggedSickTO> getAllLoggedSick();

    void addLoggedVacation(LoggedVacationTO loggedVacationTO);

    List<LoggedVacationTO> getAllLoggedVacation();

  //  List<LoggedTO> getAllLogged(Date startDate, Date endDate);

   List<LoggedTO> getAllLogged( java.sql.Date startDate, java.sql.Date endDate);
}

