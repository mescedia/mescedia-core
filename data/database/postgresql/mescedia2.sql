--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5 (Debian 14.5-1.pgdg100+1)
-- Dumped by pg_dump version 14.5 (Debian 14.5-1.pgdg100+1)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: contentanalyserrule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contentanalyserrule (
    id integer NOT NULL,
    formatid integer NOT NULL,
    ritem character varying(20) NOT NULL,
    rtype character varying(20) NOT NULL,
    rvalue character varying(255) NOT NULL,
    active integer DEFAULT 0
);


ALTER TABLE public.contentanalyserrule OWNER TO postgres;

--
-- Name: contentanalyserrule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contentanalyserrule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contentanalyserrule_id_seq OWNER TO postgres;

--
-- Name: contentanalyserrule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.contentanalyserrule_id_seq OWNED BY public.contentanalyserrule.id;


--
-- Name: dbconnections; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dbconnections (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    url character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    passwd character varying(255) NOT NULL
);


ALTER TABLE public.dbconnections OWNER TO postgres;

--
-- Name: dbconnections_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dbconnections_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dbconnections_id_seq OWNER TO postgres;

--
-- Name: dbconnections_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dbconnections_id_seq OWNED BY public.dbconnections.id;


--
-- Name: formatanalyserrule; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.formatanalyserrule (
    id integer NOT NULL,
    formatid integer NOT NULL,
    rtype character varying(20) NOT NULL,
    rvalue character varying(255) NOT NULL,
    active integer DEFAULT 0
);


ALTER TABLE public.formatanalyserrule OWNER TO postgres;

--
-- Name: formatanalyserrule_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.formatanalyserrule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.formatanalyserrule_id_seq OWNER TO postgres;

--
-- Name: formatanalyserrule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.formatanalyserrule_id_seq OWNED BY public.formatanalyserrule.id;


--
-- Name: messageformat; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messageformat (
    id integer NOT NULL,
    formatname character varying(50) NOT NULL,
    extractstartindex integer NOT NULL,
    extractendindex integer,
    extractendindexstring character varying(50) DEFAULT NULL::character varying,
    active integer DEFAULT 0
);


ALTER TABLE public.messageformat OWNER TO postgres;

--
-- Name: messageformat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messageformat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.messageformat_id_seq OWNER TO postgres;

--
-- Name: messageformat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messageformat_id_seq OWNED BY public.messageformat.id;


--
-- Name: contentanalyserrule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contentanalyserrule ALTER COLUMN id SET DEFAULT nextval('public.contentanalyserrule_id_seq'::regclass);


--
-- Name: dbconnections id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dbconnections ALTER COLUMN id SET DEFAULT nextval('public.dbconnections_id_seq'::regclass);


--
-- Name: formatanalyserrule id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formatanalyserrule ALTER COLUMN id SET DEFAULT nextval('public.formatanalyserrule_id_seq'::regclass);


--
-- Name: messageformat id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messageformat ALTER COLUMN id SET DEFAULT nextval('public.messageformat_id_seq'::regclass);


--
-- Data for Name: contentanalyserrule; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contentanalyserrule (id, formatid, ritem, rtype, rvalue, active) FROM stdin;
1	1	senderId	regex	UNB\\\\+.+?\\\\+(.*?)[\\\\:|\\\\+]	1
2	1	receiverId	regex	UNB\\\\+.+?[\\\\+].+?[\\\\+](.*?)[\\\\:|\\\\+]	1
3	1	messageType	regex	UNH.+?\\\\+(.+?)\\\\:	1
4	1	messageVersion	regex	UNH.+?\\\\+[A-Z]{6}\\\\:([D])\\\\:([0-9]{2}[A|B|C]{1})\\\\:	1
5	2	senderId	regex	<E0004>(.+?)</E0004>	1
6	2	receiverId	regex	<E0010>(.+?)</E0010>	1
7	2	messageType	regex	<UNH>.+?<E0065>(.+?)</E0065>	1
8	2	messageVersion	regex	<UNH>.+?<E0052>(.+?)</E0052>.*?<E0054>(.+?)</E0054>	1
9	3	senderId	regex	<SNDPRN>(.+?)</SNDPRN>	1
10	3	receiverId	regex	<RCVPRN>(.+?)</RCVPRN>	1
11	3	messageType	regex	<MESTYP>(.+?)</MESTYP>	1
12	3	messageVersion	regex	<IDOCTYP>(.+?)</IDOCTYP>	1
\.


--
-- Data for Name: dbconnections; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dbconnections (id, name, url, username, passwd) FROM stdin;
1	dbMariaDbDemoERP	jdbc:mysql://localhost:3306/demoerp	erp	erp
2	dbPostgresDemoERP	jdbc:postgresql://localhost:5433/demoerp	mescedia	mescedia
\.


--
-- Data for Name: formatanalyserrule; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.formatanalyserrule (id, formatid, rtype, rvalue, active) FROM stdin;
1	1	indexof	UNB+	1
2	1	indexof	UNH+	1
3	1	indexof	BGM+	1
4	2	indexof	<UNB>	1
5	2	indexof	<UNH>	1
6	2	indexof	<BGM>	1
7	3	indexof	<IDOC BEGIN=	1
8	3	indexof	<EDI_DC40	1
\.


--
-- Data for Name: messageformat; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messageformat (id, formatname, extractstartindex, extractendindex, extractendindexstring, active) FROM stdin;
1	Edifact	0	400	\N	1
2	DfdlXmlEdifact	0	\N	</BGM>	1
3	SapIdocXml	0	\N	</EDI_DC40	1
\.


--
-- Name: contentanalyserrule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contentanalyserrule_id_seq', 1, false);


--
-- Name: dbconnections_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dbconnections_id_seq', 1, false);


--
-- Name: formatanalyserrule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.formatanalyserrule_id_seq', 1, false);


--
-- Name: messageformat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messageformat_id_seq', 1, false);


--
-- Name: contentanalyserrule contentanalyserrule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contentanalyserrule
    ADD CONSTRAINT contentanalyserrule_pkey PRIMARY KEY (id);


--
-- Name: dbconnections dbconnections_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dbconnections
    ADD CONSTRAINT dbconnections_pkey PRIMARY KEY (id);


--
-- Name: formatanalyserrule formatanalyserrule_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formatanalyserrule
    ADD CONSTRAINT formatanalyserrule_pkey PRIMARY KEY (id);


--
-- Name: messageformat messageformat_formatname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messageformat
    ADD CONSTRAINT messageformat_formatname_key UNIQUE (formatname);


--
-- Name: messageformat messageformat_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messageformat
    ADD CONSTRAINT messageformat_pkey PRIMARY KEY (id);


--
-- Name: contentanalyserrule contentanalyserrule_formatid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contentanalyserrule
    ADD CONSTRAINT contentanalyserrule_formatid_fkey FOREIGN KEY (formatid) REFERENCES public.messageformat(id);


--
-- Name: formatanalyserrule formatanalyserrule_formatid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.formatanalyserrule
    ADD CONSTRAINT formatanalyserrule_formatid_fkey FOREIGN KEY (formatid) REFERENCES public.messageformat(id);


--
-- PostgreSQL database dump complete
--

