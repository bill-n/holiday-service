DROP TABLE IF EXISTS employee cascade;

CREATE TABLE employee (
	employee_id serial primary key,
	employee_firstName text not null,
	employee_lastName text not null,
	employee_phoneNumber text,
	employee_email text not null,
	employee_address text,
	employee_role text,
	employee_dev_level text,
	employee_hire_date DATE,
	employee_onLeave BOOLEAN,
	employee_gender text,
	employee_status text
);

--- DATABASE FOR HOLIDAY ME ---
