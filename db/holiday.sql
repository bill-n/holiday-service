--- DATABASE FOR HOLIDAY ME ---

DROP TABLE IF EXISTS requests cascade;
DROP TABLE IF EXISTS request_status cascade;

--- REQUEST STATUS ---

CREATE TABLE request_status(
    request_status_id serial primary key NOT NULL,
    req_status varchar(10)
);

--- REQUESTS TABLE ---

CREATE TABLE requests(
    request_id serial primary key NOT NULL,
    requester_id int references employee(employee_id) NOT NULL,
    request_start_date date NOT NULL,
    request_report_date date NOT NULL,
    request_status_id int references request_status(request_status_id) NOT NULL default 1
);

--- -------------------------------------------------------------------------------------------- ---
                        --- Dumping table for employees ---
--- -------------------------------------------------------------------------------------------- ---

INSERT INTO request_status(req_status) VALUES ('PENDING');
INSERT INTO request_status(req_status) VALUES ('DECLINED');
INSERT INTO request_status(req_status) VALUES ('APPROVED');

--- -------------------------------------------------------------------------------------------- ---
                        --- Dumping table for requests ---
--- -------------------------------------------------------------------------------------------- ---

INSERT INTO requests(requester_id, request_start_date, request_report_date, request_status_id) values(1,'2020-02-20', '2020-02-25', 2);
INSERT INTO requests(requester_id, request_start_date, request_report_date, request_status_id) values(1,'2020-02-20', '2020-02-25', 3);
INSERT INTO requests(requester_id, request_start_date, request_report_date, request_status_id) values(1,'2020-02-20', '2020-02-25', 1);
