package io.turntabl.employementprofilingsystem.v1.Utilities;

public class Date {
    public java.sql.Date getCurrentDate (){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        return date;
    }
}
