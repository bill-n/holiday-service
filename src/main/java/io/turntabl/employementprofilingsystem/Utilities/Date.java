package io.turntabl.employementprofilingsystem.Utilities;

import org.springframework.stereotype.Component;

@Component
public class Date {
    public java.sql.Date getCurrentDate (){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        return date;
    }
}
