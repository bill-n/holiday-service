
CREATE TABLE employee (

	employee_id serial primary key,
	employee_firstName text not null,
	employee_lastName text not null,
	employee_phoneNumber text not null,
	employee_email text not null,
	employee_address text not null,
	employee_role text not null,
	employee_dev_level text not null,
	employee_hire_date DATE not null,
	employee_onLeave BOOLEAN not null,
	employee_gender text not null,
	employee_status text not null
);




