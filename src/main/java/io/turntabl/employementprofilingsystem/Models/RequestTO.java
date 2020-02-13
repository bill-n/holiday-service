package io.turntabl.employementprofilingsystem.Models;

import java.util.Date;

public class RequestTO {
    private int request_id;
    private int requester_id;
    private Date requester_start_date;
    private Date requester_end_date;
    private String requester_reason;
    private int request_status_id;

    public RequestTO() {
    }

    public RequestTO(int request_id, int requester_id, Date requester_start_date, Date requester_end_date, String requester_reason, int request_status_id) {
        this.request_id = request_id;
        this.requester_id = requester_id;
        this.requester_start_date = requester_start_date;
        this.requester_end_date = requester_end_date;
        this.requester_reason = requester_reason;
        this.request_status_id = request_status_id;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getRequester_id() {
        return requester_id;
    }

    public void setRequester_id(int requester_id) {
        this.requester_id = requester_id;
    }

    public Date getRequester_start_date() {
        return requester_start_date;
    }

    public void setRequester_start_date(Date requester_start_date) {
        this.requester_start_date = requester_start_date;
    }

    public Date getRequester_end_date() {
        return requester_end_date;
    }

    public void setRequester_end_date(Date requester_end_date) {
        this.requester_end_date = requester_end_date;
    }

    public String getRequester_reason() {
        return requester_reason;
    }

    public void setRequester_reason(String requester_reason) {
        this.requester_reason = requester_reason;
    }

    public int getRequest_status_id() {
        return request_status_id;
    }

    public void setRequest_status_id(int request_status_id) {
        this.request_status_id = request_status_id;
    }

    @Override
    public String toString() {
        return "RequestTO{" +
                "request_id=" + request_id +
                ", requester_id=" + requester_id +
                ", requester_start_date=" + requester_start_date +
                ", requester_end_date=" + requester_end_date +
                ", requester_reason='" + requester_reason + '\'' +
                ", request_status_id=" + request_status_id +
                '}';
    }
}
