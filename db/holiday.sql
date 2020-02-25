--- DATABASE FOR HOLIDAY ME ---

DROP TABLE IF EXISTS requests cascade;
DROP TABLE IF EXISTS request_status cascade;

--- REQUEST STATUS ---

CREATE TABLE request_status(
    request_status_id primary key NOT NULL,
    req_status varchar(10)
);

INSERT INTO request_status(request_status_id, req_status) VALUES (1, 'PENDING');
INSERT INTO request_status(request_status_id, req_status) VALUES (2, 'DECLINED');
INSERT INTO request_status(request_status_id, req_status) VALUES (3, 'APPROVED');

--- REQUESTS TABLE ---

CREATE TABLE requests(
    request_id serial primary key NOT NULL,
    requester_id int references employee(employee_id) NOT NULL,
    request_start_date date NOT NULL,
    request_report_date date NOT NULL,
    request_status_id int references request_status(request_status_id) NOT NULL default 1
);


