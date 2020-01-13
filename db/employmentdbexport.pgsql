--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: assignedproject; Type: TABLE; Schema: public; Owner: sammy
--

CREATE TABLE public.assignedproject (
    assignedproject_id integer NOT NULL,
    employee_id integer,
    project_id integer,
    isworkingon boolean
);


ALTER TABLE public.assignedproject OWNER TO sammy;

--
-- Name: assignedproject_assignedproject_id_seq; Type: SEQUENCE; Schema: public; Owner: sammy
--

CREATE SEQUENCE public.assignedproject_assignedproject_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.assignedproject_assignedproject_id_seq OWNER TO sammy;

--
-- Name: assignedproject_assignedproject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sammy
--

ALTER SEQUENCE public.assignedproject_assignedproject_id_seq OWNED BY public.assignedproject.assignedproject_id;


--
-- Name: employee; Type: TABLE; Schema: public; Owner: sammy
--

CREATE TABLE public.employee (
    employee_id integer NOT NULL,
    employee_firstname text NOT NULL,
    employee_lastname text NOT NULL,
    employee_phonenumber text NOT NULL,
    employee_email text NOT NULL,
    employee_address text NOT NULL,
    employee_dev_level text NOT NULL,
    employee_hire_date date NOT NULL,
    employee_onleave boolean NOT NULL,
    employee_gender text NOT NULL,
    employee_status text NOT NULL,
    employee_role text
);


ALTER TABLE public.employee OWNER TO sammy;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE; Schema: public; Owner: sammy
--

CREATE SEQUENCE public.employee_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employee_employee_id_seq OWNER TO sammy;

--
-- Name: employee_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sammy
--

ALTER SEQUENCE public.employee_employee_id_seq OWNED BY public.employee.employee_id;


--
-- Name: employeetech; Type: TABLE; Schema: public; Owner: sammy
--

CREATE TABLE public.employeetech (
    techproject_id integer NOT NULL,
    tech_id integer,
    employee_id integer
);


ALTER TABLE public.employeetech OWNER TO sammy;

--
-- Name: employeetech_techproject_id_seq; Type: SEQUENCE; Schema: public; Owner: sammy
--

CREATE SEQUENCE public.employeetech_techproject_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employeetech_techproject_id_seq OWNER TO sammy;

--
-- Name: employeetech_techproject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sammy
--

ALTER SEQUENCE public.employeetech_techproject_id_seq OWNED BY public.employeetech.techproject_id;


--
-- Name: project; Type: TABLE; Schema: public; Owner: sammy
--

CREATE TABLE public.project (
    project_id integer NOT NULL,
    project_name text NOT NULL,
    project_description text NOT NULL,
    project_start_date date NOT NULL,
    project_end_date date NOT NULL,
    project_status text NOT NULL
);


ALTER TABLE public.project OWNER TO sammy;

--
-- Name: project_project_id_seq; Type: SEQUENCE; Schema: public; Owner: sammy
--

CREATE SEQUENCE public.project_project_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.project_project_id_seq OWNER TO sammy;

--
-- Name: project_project_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sammy
--

ALTER SEQUENCE public.project_project_id_seq OWNED BY public.project.project_id;


--
-- Name: tech; Type: TABLE; Schema: public; Owner: sammy
--

CREATE TABLE public.tech (
    tech_id integer NOT NULL,
    tech_name text NOT NULL,
    tech_status text NOT NULL
);


ALTER TABLE public.tech OWNER TO sammy;

--
-- Name: tech_tech_id_seq; Type: SEQUENCE; Schema: public; Owner: sammy
--

CREATE SEQUENCE public.tech_tech_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tech_tech_id_seq OWNER TO sammy;

--
-- Name: tech_tech_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sammy
--

ALTER SEQUENCE public.tech_tech_id_seq OWNED BY public.tech.tech_id;


--
-- Name: techproject; Type: TABLE; Schema: public; Owner: sammy
--

CREATE TABLE public.techproject (
    techproject_id integer NOT NULL,
    tech_id integer,
    project_id integer
);


ALTER TABLE public.techproject OWNER TO sammy;

--
-- Name: techproject_techproject_id_seq; Type: SEQUENCE; Schema: public; Owner: sammy
--

CREATE SEQUENCE public.techproject_techproject_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.techproject_techproject_id_seq OWNER TO sammy;

--
-- Name: techproject_techproject_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sammy
--

ALTER SEQUENCE public.techproject_techproject_id_seq OWNED BY public.techproject.techproject_id;


--
-- Name: assignedproject assignedproject_id; Type: DEFAULT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.assignedproject ALTER COLUMN assignedproject_id SET DEFAULT nextval('public.assignedproject_assignedproject_id_seq'::regclass);


--
-- Name: employee employee_id; Type: DEFAULT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.employee ALTER COLUMN employee_id SET DEFAULT nextval('public.employee_employee_id_seq'::regclass);


--
-- Name: employeetech techproject_id; Type: DEFAULT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.employeetech ALTER COLUMN techproject_id SET DEFAULT nextval('public.employeetech_techproject_id_seq'::regclass);


--
-- Name: project project_id; Type: DEFAULT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.project ALTER COLUMN project_id SET DEFAULT nextval('public.project_project_id_seq'::regclass);


--
-- Name: tech tech_id; Type: DEFAULT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.tech ALTER COLUMN tech_id SET DEFAULT nextval('public.tech_tech_id_seq'::regclass);


--
-- Name: techproject techproject_id; Type: DEFAULT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.techproject ALTER COLUMN techproject_id SET DEFAULT nextval('public.techproject_techproject_id_seq'::regclass);


--
-- Data for Name: assignedproject; Type: TABLE DATA; Schema: public; Owner: sammy
--

COPY public.assignedproject (assignedproject_id, employee_id, project_id, isworkingon) FROM stdin;
4	3	3	t
3	3	2	f
\.


--
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: sammy
--

COPY public.employee (employee_id, employee_firstname, employee_lastname, employee_phonenumber, employee_email, employee_address, employee_dev_level, employee_hire_date, employee_onleave, employee_gender, employee_status, employee_role) FROM stdin;
4	Akos	Offei	0244795544	akos.offei@turntabl.io	Dansoman	ADVANCED	2020-01-10	f	FEMALE	ACTIVE	DEVELOPER
3	Doreen	Dodoo	0244665544	doreen.dodoo@turntabl.io	Cape Coast	ADVANCED	2020-01-10	f	FEMALE	ACTIVE	DEVELOPER
5	string	string	string	string	string	STRING	2020-01-13	f	STRING	STRING	STRING
\.


--
-- Data for Name: employeetech; Type: TABLE DATA; Schema: public; Owner: sammy
--

COPY public.employeetech (techproject_id, tech_id, employee_id) FROM stdin;
4	5	4
17	3	3
18	0	5
\.


--
-- Data for Name: project; Type: TABLE DATA; Schema: public; Owner: sammy
--

COPY public.project (project_id, project_name, project_description, project_start_date, project_end_date, project_status) FROM stdin;
3	Employment WEB APP	Employement Management System	2020-05-20	2020-06-01	ACTIVE
2	TCMPS MOBILE APP	TCMPS	2020-02-02	2020-03-03	ACTIVE
\.


--
-- Data for Name: tech; Type: TABLE DATA; Schema: public; Owner: sammy
--

COPY public.tech (tech_id, tech_name, tech_status) FROM stdin;
1	Java	ACTIVE
2	Python	ACTIVE
3	AWS	ACTIVE
4	Hadoop	ACTIVE
6	Node JS	INACTIVE
5	Java	ACTIVE
\.


--
-- Data for Name: techproject; Type: TABLE DATA; Schema: public; Owner: sammy
--

COPY public.techproject (techproject_id, tech_id, project_id) FROM stdin;
2	1	3
3	5	3
6	2	2
\.


--
-- Name: assignedproject_assignedproject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sammy
--

SELECT pg_catalog.setval('public.assignedproject_assignedproject_id_seq', 5, true);


--
-- Name: employee_employee_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sammy
--

SELECT pg_catalog.setval('public.employee_employee_id_seq', 5, true);


--
-- Name: employeetech_techproject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sammy
--

SELECT pg_catalog.setval('public.employeetech_techproject_id_seq', 18, true);


--
-- Name: project_project_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sammy
--

SELECT pg_catalog.setval('public.project_project_id_seq', 3, true);


--
-- Name: tech_tech_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sammy
--

SELECT pg_catalog.setval('public.tech_tech_id_seq', 6, true);


--
-- Name: techproject_techproject_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sammy
--

SELECT pg_catalog.setval('public.techproject_techproject_id_seq', 6, true);


--
-- Name: assignedproject assignedproject_pkey; Type: CONSTRAINT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.assignedproject
    ADD CONSTRAINT assignedproject_pkey PRIMARY KEY (assignedproject_id);


--
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (employee_id);


--
-- Name: employeetech employeetech_pkey; Type: CONSTRAINT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.employeetech
    ADD CONSTRAINT employeetech_pkey PRIMARY KEY (techproject_id);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (project_id);


--
-- Name: tech tech_pkey; Type: CONSTRAINT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.tech
    ADD CONSTRAINT tech_pkey PRIMARY KEY (tech_id);


--
-- Name: techproject techproject_pkey; Type: CONSTRAINT; Schema: public; Owner: sammy
--

ALTER TABLE ONLY public.techproject
    ADD CONSTRAINT techproject_pkey PRIMARY KEY (techproject_id);


--
-- PostgreSQL database dump complete
--

