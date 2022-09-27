--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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
-- TOC entry 246 (class 1255 OID 58262)
-- Name: check_adiacenza_stesso_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_adiacenza_stesso_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
IF NEW."ID_Tavolo" = NEW."ID_Tavolo_Adiacente" THEN
RAISE EXCEPTION 'Un tavolo non può essere adiacente a sè stesso.';
END IF;

RETURN NEW;
END$$;


ALTER FUNCTION public.check_adiacenza_stesso_tavolo() OWNER TO postgres;

--
-- TOC entry 243 (class 1255 OID 58285)
-- Name: check_cameriere_ristorante(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_cameriere_ristorante() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

BEGIN

IF 
(SELECT "Cameriere"."ID_Ristorante" FROM "Cameriere" 
WHERE "Cameriere"."ID_Cameriere" = NEW."ID_Cameriere") <> (SELECT "Ristorante"."ID_Ristorante" FROM "Ristorante" 
INNER JOIN "Sala" ON "Ristorante"."ID_Ristorante" = "Sala"."ID_Ristorante"
INNER JOIN "Tavolo" ON "Sala"."ID_Sala" = "Tavolo"."ID_Sala"
INNER JOIN "Tavolata" ON "Tavolo"."Codice_Tavolo" = "Tavolata"."Codice_Tavolo" 
WHERE "Tavolata"."Codice_Prenotazione" = NEW."Codice_Prenotazione") 
THEN
RAISE EXCEPTION 'Il Cameriere non può gestire la tavolata perché registrato a un altro Ristorante.';
END IF;
RETURN NEW;
END
$$;


ALTER FUNCTION public.check_cameriere_ristorante() OWNER TO postgres;

--
-- TOC entry 224 (class 1255 OID 41388)
-- Name: check_date(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_date() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE tavolo_trovato integer;
BEGIN
SELECT "Tavolata"."Codice_Tavolo" INTO tavolo_trovato FROM "Tavolata" 
WHERE NEW."Data_Arrivo" = "Data_Arrivo" 
AND
NEW."Codice_Tavolo" = "Codice_Tavolo";

IF FOUND THEN
RAISE EXCEPTION 'Tavolo occupato per la data inserita.';
END IF;
RETURN NEW;
END$$;


ALTER FUNCTION public.check_date() OWNER TO postgres;

--
-- TOC entry 228 (class 1255 OID 49595)
-- Name: check_max_avventori(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_max_avventori() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE max_avventori integer;
BEGIN
SELECT "Tavolo"."Max_Avventori" INTO max_avventori FROM "Tavolo"
INNER JOIN "Tavolata" ON "Tavolo"."Codice_Tavolo" = "Tavolata"."Codice_Tavolo"
INNER JOIN "Prenotazione" ON "Tavolata"."Codice_Prenotazione" = "Prenotazione"."Codice_Prenotazione";

IF 
(SELECT COUNT(*) FROM "Prenotazione"
INNER JOIN "Tavolata" ON "Prenotazione"."Codice_Prenotazione" = "Tavolata"."Codice_Prenotazione"
INNER JOIN "Tavolo" ON "Tavolata"."Codice_Tavolo" = "Tavolo"."Codice_Tavolo" 
WHERE "Prenotazione"."Codice_Prenotazione" = NEW."Codice_Prenotazione") = max_avventori
THEN 
RAISE EXCEPTION 'Numero massimo di avventori raggiunto.';
END IF;
RETURN NEW;
END$$;


ALTER FUNCTION public.check_max_avventori() OWNER TO postgres;

--
-- TOC entry 240 (class 1255 OID 49599)
-- Name: check_tavoli_sale(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.check_tavoli_sale() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE sala1 text;
DECLARE sala2 text;
BEGIN

SELECT "Tavolo"."ID_Sala" INTO sala1 FROM "Tavolo" 
WHERE "Tavolo"."Codice_Tavolo" = NEW."ID_Tavolo";

SELECT "Tavolo"."ID_Sala" INTO sala2 FROM "Tavolo" 
WHERE "Tavolo"."Codice_Tavolo" = NEW."ID_Tavolo_Adiacente";

IF sala1<>sala2 THEN
RAISE EXCEPTION 'I tavoli non possono essere adiacenti: non sono nella stessa sala';
END IF;
RETURN NEW;
END$$;


ALTER FUNCTION public.check_tavoli_sale() OWNER TO postgres;

--
-- TOC entry 241 (class 1255 OID 58137)
-- Name: commutazione_adiacenza(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.commutazione_adiacenza() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
	
INSERT INTO "TavoliAdiacenti" ("ID_Tavolo_Adiacente", "ID_Tavolo") 
VALUES (NEW."ID_Tavolo", NEW."ID_Tavolo_Adiacente") ON CONFLICT DO NOTHING;

RETURN NEW;
END$$;


ALTER FUNCTION public.commutazione_adiacenza() OWNER TO postgres;

--
-- TOC entry 247 (class 1255 OID 58297)
-- Name: conta_posti_1sala_delete_update_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.conta_posti_1sala_delete_update_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT SUM("Max_Avventori") INTO x FROM "Tavolo"
WHERE "Tavolo"."ID_Sala" = OLD."ID_Sala";

UPDATE "Sala" SET "Capienza" = x
WHERE "Sala"."ID_Sala" = OLD."ID_Sala";
RETURN NEW;
END$$;


ALTER FUNCTION public.conta_posti_1sala_delete_update_tavolo() OWNER TO postgres;

--
-- TOC entry 248 (class 1255 OID 58296)
-- Name: conta_posti_1sala_insert_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.conta_posti_1sala_insert_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT SUM("Max_Avventori") INTO x FROM "Tavolo"
WHERE "Tavolo"."ID_Sala" = NEW."ID_Sala";

UPDATE "Sala" SET "Capienza" = x
WHERE "Sala"."ID_Sala" = NEW."ID_Sala";
RETURN NEW;
END$$;


ALTER FUNCTION public.conta_posti_1sala_insert_tavolo() OWNER TO postgres;

--
-- TOC entry 244 (class 1255 OID 58301)
-- Name: conta_posti_2ristorante_delete_update_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.conta_posti_2ristorante_delete_update_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
DECLARE idRist integer;
BEGIN

SELECT "Ristorante"."ID_Ristorante" INTO idRist FROM "Ristorante"
INNER JOIN "Sala" ON "Ristorante"."ID_Ristorante" = "Sala"."ID_Ristorante"
INNER JOIN "Tavolo" ON "Sala"."ID_Sala" = "Tavolo"."ID_Sala"
WHERE "Tavolo"."Codice_Tavolo" = OLD."Codice_Tavolo";

SELECT SUM("Capienza") INTO x FROM "Sala"
WHERE "Sala"."ID_Ristorante" = idRist;

UPDATE "Ristorante" SET "Capienza" = x
WHERE "Ristorante"."ID_Ristorante" = idRist;

RETURN NEW;
END$$;


ALTER FUNCTION public.conta_posti_2ristorante_delete_update_tavolo() OWNER TO postgres;

--
-- TOC entry 245 (class 1255 OID 58300)
-- Name: conta_posti_2ristorante_insert_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.conta_posti_2ristorante_insert_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
DECLARE idRist integer;
BEGIN

SELECT "Ristorante"."ID_Ristorante" INTO idRist FROM "Ristorante"
INNER JOIN "Sala" ON "Ristorante"."ID_Ristorante" = "Sala"."ID_Ristorante"
INNER JOIN "Tavolo" ON "Sala"."ID_Sala" = "Tavolo"."ID_Sala"
WHERE "Tavolo"."Codice_Tavolo" = NEW."Codice_Tavolo";

SELECT SUM("Capienza") INTO x FROM "Sala"
WHERE "Sala"."ID_Ristorante" = idRist;

UPDATE "Ristorante" SET "Capienza" = x
WHERE "Ristorante"."ID_Ristorante" = idRist;

RETURN NEW;
END$$;


ALTER FUNCTION public.conta_posti_2ristorante_insert_tavolo() OWNER TO postgres;

--
-- TOC entry 237 (class 1255 OID 58288)
-- Name: update_ristorante_delete_cameriere(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_ristorante_delete_cameriere() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Cameriere"
WHERE "Cameriere"."ID_Ristorante" = OLD."ID_Ristorante";

UPDATE "Ristorante" SET "Numero_Camerieri" = x
WHERE "Ristorante"."ID_Ristorante" = OLD."ID_Ristorante";
RETURN NEW;
END$$;


ALTER FUNCTION public.update_ristorante_delete_cameriere() OWNER TO postgres;

--
-- TOC entry 238 (class 1255 OID 58287)
-- Name: update_ristorante_insert_cameriere(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_ristorante_insert_cameriere() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Cameriere"
WHERE "Cameriere"."ID_Ristorante" = NEW."ID_Ristorante";

UPDATE "Ristorante" SET "Numero_Camerieri" = x
WHERE "Ristorante"."ID_Ristorante" = NEW."ID_Ristorante";
RETURN NEW;
END$$;


ALTER FUNCTION public.update_ristorante_insert_cameriere() OWNER TO postgres;

--
-- TOC entry 242 (class 1255 OID 58271)
-- Name: update_sala_delete_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_sala_delete_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN
SELECT COUNT(*) INTO x FROM "Tavolo" 
WHERE "Tavolo"."ID_Sala" = OLD."ID_Sala";

UPDATE "Sala" SET "Numero_Tavoli" = x
WHERE "Sala"."ID_Sala" = OLD."ID_Sala";
RETURN NEW;
END$$;


ALTER FUNCTION public.update_sala_delete_tavolo() OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 58270)
-- Name: update_sala_insert_tavolo(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_sala_insert_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN
SELECT COUNT(*) INTO x FROM "Tavolo" 
WHERE "Tavolo"."ID_Sala" = NEW."ID_Sala";

UPDATE "Sala" SET "Numero_Tavoli" = x
WHERE "Sala"."ID_Sala" = NEW."ID_Sala";
RETURN NEW;
END$$;


ALTER FUNCTION public.update_sala_insert_tavolo() OWNER TO postgres;

--
-- TOC entry 249 (class 1255 OID 58305)
-- Name: update_tavolata_delete_clienteprenotazione(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_tavolata_delete_clienteprenotazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Prenotazione"
WHERE "Prenotazione"."Codice_Prenotazione" = OLD."Codice_Prenotazione";

UPDATE "Tavolata" SET "Numero_Clienti" = x
WHERE "Tavolata"."Codice_Prenotazione" = OLD."Codice_Prenotazione";
RETURN NEW;
END$$;


ALTER FUNCTION public.update_tavolata_delete_clienteprenotazione() OWNER TO postgres;

--
-- TOC entry 250 (class 1255 OID 58304)
-- Name: update_tavolata_insert_clienteprenotazione(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_tavolata_insert_clienteprenotazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Prenotazione"
WHERE "Prenotazione"."Codice_Prenotazione" = NEW."Codice_Prenotazione";

UPDATE "Tavolata" SET "Numero_Clienti" = x
WHERE "Tavolata"."Codice_Prenotazione" = NEW."Codice_Prenotazione";
RETURN NEW;
END$$;


ALTER FUNCTION public.update_tavolata_insert_clienteprenotazione() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 33195)
-- Name: Cameriere; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cameriere" (
    "ID_Cameriere" integer NOT NULL,
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "ID_Ristorante" integer NOT NULL,
    "Stipendio_Orario" integer
);


ALTER TABLE public."Cameriere" OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 33194)
-- Name: Cameriere_ID_Cameriere_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Cameriere_ID_Cameriere_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Cameriere_ID_Cameriere_seq" OWNER TO postgres;

--
-- TOC entry 3433 (class 0 OID 0)
-- Dependencies: 217
-- Name: Cameriere_ID_Cameriere_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Cameriere_ID_Cameriere_seq" OWNED BY public."Cameriere"."ID_Cameriere";


--
-- TOC entry 216 (class 1259 OID 16441)
-- Name: Cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cliente" (
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "Numero_ID_Card" character(9) NOT NULL,
    "Numero_Tel" text,
    CONSTRAINT ck_only_numbers CHECK (("Numero_Tel" !~~ '%[^0-9]%'::text))
);


ALTER TABLE public."Cliente" OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 49573)
-- Name: Prenotazione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Prenotazione" (
    "Codice_Prenotazione" integer NOT NULL,
    "Numero_ID" character(9) NOT NULL
);


ALTER TABLE public."Prenotazione" OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16397)
-- Name: Ristorante; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Ristorante" (
    "Nome_Ristorante" name NOT NULL,
    "ID_Ristorante" integer NOT NULL,
    "Numero_Camerieri" integer,
    "Capienza" integer
);


ALTER TABLE public."Ristorante" OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16396)
-- Name: Ristorante_ID_Ristorante_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Ristorante_ID_Ristorante_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Ristorante_ID_Ristorante_seq" OWNER TO postgres;

--
-- TOC entry 3434 (class 0 OID 0)
-- Dependencies: 209
-- Name: Ristorante_ID_Ristorante_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Ristorante_ID_Ristorante_seq" OWNED BY public."Ristorante"."ID_Ristorante";


--
-- TOC entry 211 (class 1259 OID 16403)
-- Name: Sala; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Sala" (
    "ID_Ristorante" integer NOT NULL,
    "Numero_Tavoli" integer,
    "Capienza" integer,
    "Nome_Sala" name NOT NULL,
    "ID_Sala" integer NOT NULL
);


ALTER TABLE public."Sala" OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 58330)
-- Name: Sala_ID_Sala_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Sala_ID_Sala_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Sala_ID_Sala_seq" OWNER TO postgres;

--
-- TOC entry 3435 (class 0 OID 0)
-- Dependencies: 222
-- Name: Sala_ID_Sala_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Sala_ID_Sala_seq" OWNED BY public."Sala"."ID_Sala";


--
-- TOC entry 219 (class 1259 OID 33202)
-- Name: Servizio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Servizio" (
    "ID_Cameriere" integer NOT NULL,
    "Codice_Prenotazione" integer NOT NULL
);


ALTER TABLE public."Servizio" OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16428)
-- Name: Tavolata; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Tavolata" (
    "Data_Arrivo" date NOT NULL,
    "Codice_Prenotazione" integer NOT NULL,
    "Codice_Tavolo" integer NOT NULL,
    "Numero_Clienti" integer
);


ALTER TABLE public."Tavolata" OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16427)
-- Name: Tavolata_Codice_Prenotazione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Tavolata_Codice_Prenotazione_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Tavolata_Codice_Prenotazione_seq" OWNER TO postgres;

--
-- TOC entry 3436 (class 0 OID 0)
-- Dependencies: 214
-- Name: Tavolata_Codice_Prenotazione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Tavolata_Codice_Prenotazione_seq" OWNED BY public."Tavolata"."Codice_Prenotazione";


--
-- TOC entry 220 (class 1259 OID 33218)
-- Name: TavoliAdiacenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."TavoliAdiacenti" (
    "ID_Tavolo" integer NOT NULL,
    "ID_Tavolo_Adiacente" integer NOT NULL
);


ALTER TABLE public."TavoliAdiacenti" OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16414)
-- Name: Tavolo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Tavolo" (
    "Codice_Tavolo" integer NOT NULL,
    "Max_Avventori" integer NOT NULL,
    "ID_Sala" integer NOT NULL,
    CONSTRAINT avventori_positivo CHECK (("Max_Avventori" > 0))
);


ALTER TABLE public."Tavolo" OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16413)
-- Name: Tavolo_Codice_Tavolo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Tavolo_Codice_Tavolo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Tavolo_Codice_Tavolo_seq" OWNER TO postgres;

--
-- TOC entry 3437 (class 0 OID 0)
-- Dependencies: 212
-- Name: Tavolo_Codice_Tavolo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Tavolo_Codice_Tavolo_seq" OWNED BY public."Tavolo"."Codice_Tavolo";


--
-- TOC entry 223 (class 1259 OID 58347)
-- Name: prenotazione_ristorante; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.prenotazione_ristorante AS
 SELECT "Ristorante"."ID_Ristorante",
    "Tavolata"."Codice_Prenotazione"
   FROM (((public."Ristorante"
     JOIN public."Sala" ON (("Ristorante"."ID_Ristorante" = "Sala"."ID_Ristorante")))
     JOIN public."Tavolo" ON (("Sala"."ID_Sala" = "Tavolo"."ID_Sala")))
     JOIN public."Tavolata" ON (("Tavolo"."Codice_Tavolo" = "Tavolata"."Codice_Tavolo")));


ALTER TABLE public.prenotazione_ristorante OWNER TO postgres;

--
-- TOC entry 3227 (class 2604 OID 58141)
-- Name: Cameriere ID_Cameriere; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cameriere" ALTER COLUMN "ID_Cameriere" SET DEFAULT nextval('public."Cameriere_ID_Cameriere_seq"'::regclass);


--
-- TOC entry 3220 (class 2604 OID 58142)
-- Name: Ristorante ID_Ristorante; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ristorante" ALTER COLUMN "ID_Ristorante" SET DEFAULT nextval('public."Ristorante_ID_Ristorante_seq"'::regclass);


--
-- TOC entry 3221 (class 2604 OID 58331)
-- Name: Sala ID_Sala; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Sala" ALTER COLUMN "ID_Sala" SET DEFAULT nextval('public."Sala_ID_Sala_seq"'::regclass);


--
-- TOC entry 3224 (class 2604 OID 58143)
-- Name: Tavolata Codice_Prenotazione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tavolata" ALTER COLUMN "Codice_Prenotazione" SET DEFAULT nextval('public."Tavolata_Codice_Prenotazione_seq"'::regclass);


--
-- TOC entry 3222 (class 2604 OID 58144)
-- Name: Tavolo Codice_Tavolo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tavolo" ALTER COLUMN "Codice_Tavolo" SET DEFAULT nextval('public."Tavolo_Codice_Tavolo_seq"'::regclass);


--
-- TOC entry 3423 (class 0 OID 33195)
-- Dependencies: 218
-- Data for Name: Cameriere; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Cameriere" ("ID_Cameriere", "Nome", "Cognome", "ID_Ristorante", "Stipendio_Orario") FROM stdin;
\.


--
-- TOC entry 3421 (class 0 OID 16441)
-- Dependencies: 216
-- Data for Name: Cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Cliente" ("Nome", "Cognome", "Numero_ID_Card", "Numero_Tel") FROM stdin;
\.


--
-- TOC entry 3426 (class 0 OID 49573)
-- Dependencies: 221
-- Data for Name: Prenotazione; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Prenotazione" ("Codice_Prenotazione", "Numero_ID") FROM stdin;
\.


--
-- TOC entry 3415 (class 0 OID 16397)
-- Dependencies: 210
-- Data for Name: Ristorante; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Ristorante" ("Nome_Ristorante", "ID_Ristorante", "Numero_Camerieri", "Capienza") FROM stdin;
\.


--
-- TOC entry 3416 (class 0 OID 16403)
-- Dependencies: 211
-- Data for Name: Sala; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Sala" ("ID_Ristorante", "Numero_Tavoli", "Capienza", "Nome_Sala", "ID_Sala") FROM stdin;
\.


--
-- TOC entry 3424 (class 0 OID 33202)
-- Dependencies: 219
-- Data for Name: Servizio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Servizio" ("ID_Cameriere", "Codice_Prenotazione") FROM stdin;
\.


--
-- TOC entry 3420 (class 0 OID 16428)
-- Dependencies: 215
-- Data for Name: Tavolata; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Tavolata" ("Data_Arrivo", "Codice_Prenotazione", "Codice_Tavolo", "Numero_Clienti") FROM stdin;
\.


--
-- TOC entry 3425 (class 0 OID 33218)
-- Dependencies: 220
-- Data for Name: TavoliAdiacenti; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."TavoliAdiacenti" ("ID_Tavolo", "ID_Tavolo_Adiacente") FROM stdin;
\.


--
-- TOC entry 3418 (class 0 OID 16414)
-- Dependencies: 213
-- Data for Name: Tavolo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Tavolo" ("Codice_Tavolo", "Max_Avventori", "ID_Sala") FROM stdin;
\.


--
-- TOC entry 3438 (class 0 OID 0)
-- Dependencies: 217
-- Name: Cameriere_ID_Cameriere_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Cameriere_ID_Cameriere_seq"', 1, false);


--
-- TOC entry 3439 (class 0 OID 0)
-- Dependencies: 209
-- Name: Ristorante_ID_Ristorante_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Ristorante_ID_Ristorante_seq"', 1, false);


--
-- TOC entry 3440 (class 0 OID 0)
-- Dependencies: 222
-- Name: Sala_ID_Sala_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Sala_ID_Sala_seq"', 1, false);


--
-- TOC entry 3441 (class 0 OID 0)
-- Dependencies: 214
-- Name: Tavolata_Codice_Prenotazione_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Tavolata_Codice_Prenotazione_seq"', 1, false);


--
-- TOC entry 3442 (class 0 OID 0)
-- Dependencies: 212
-- Name: Tavolo_Codice_Tavolo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Tavolo_Codice_Tavolo_seq"', 1, false);


--
-- TOC entry 3243 (class 2606 OID 33200)
-- Name: Cameriere Cameriere_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_pkey" PRIMARY KEY ("ID_Cameriere");


--
-- TOC entry 3239 (class 2606 OID 16447)
-- Name: Cliente Cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT "Cliente_pkey" PRIMARY KEY ("Numero_ID_Card");


--
-- TOC entry 3229 (class 2606 OID 16402)
-- Name: Ristorante Ristorante_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT "Ristorante_pkey" PRIMARY KEY ("ID_Ristorante");


--
-- TOC entry 3233 (class 2606 OID 58336)
-- Name: Sala Sala_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "Sala_pkey" PRIMARY KEY ("ID_Sala");


--
-- TOC entry 3245 (class 2606 OID 49602)
-- Name: Servizio Servizio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Servizio_pkey" PRIMARY KEY ("ID_Cameriere", "Codice_Prenotazione");


--
-- TOC entry 3237 (class 2606 OID 16435)
-- Name: Tavolata Tavolata_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Tavolata_pkey" PRIMARY KEY ("Codice_Prenotazione");


--
-- TOC entry 3247 (class 2606 OID 49604)
-- Name: TavoliAdiacenti TavoliAdiacenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "TavoliAdiacenti_pkey" PRIMARY KEY ("ID_Tavolo", "ID_Tavolo_Adiacente");


--
-- TOC entry 3235 (class 2606 OID 16421)
-- Name: Tavolo Tavolo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Tavolo_pkey" PRIMARY KEY ("Codice_Tavolo");


--
-- TOC entry 3225 (class 2606 OID 58351)
-- Name: Cliente ck_lunghezza_id; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public."Cliente"
    ADD CONSTRAINT ck_lunghezza_id CHECK ((length("Numero_ID_Card") = 9)) NOT VALID;


--
-- TOC entry 3231 (class 2606 OID 16476)
-- Name: Ristorante unique_nome_ristorante; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT unique_nome_ristorante UNIQUE ("Nome_Ristorante");


--
-- TOC entry 3241 (class 2606 OID 16474)
-- Name: Cliente unique_numero_tel; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT unique_numero_tel UNIQUE ("Numero_Tel");


--
-- TOC entry 3270 (class 2620 OID 49600)
-- Name: TavoliAdiacenti check_adiacenti; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_adiacenti BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_tavoli_sale();


--
-- TOC entry 3273 (class 2620 OID 49596)
-- Name: Prenotazione check_avventori; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_avventori BEFORE INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.check_max_avventori();


--
-- TOC entry 3267 (class 2620 OID 58286)
-- Name: Servizio check_servizio; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_servizio BEFORE INSERT ON public."Servizio" FOR EACH ROW EXECUTE FUNCTION public.check_cameriere_ristorante();


--
-- TOC entry 3268 (class 2620 OID 58263)
-- Name: TavoliAdiacenti check_stesso_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_stesso_tavolo BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_adiacenza_stesso_tavolo();


--
-- TOC entry 3264 (class 2620 OID 41389)
-- Name: Tavolata check_tavolo_libero; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER check_tavolo_libero BEFORE INSERT ON public."Tavolata" FOR EACH ROW EXECUTE FUNCTION public.check_date();


--
-- TOC entry 3269 (class 2620 OID 58138)
-- Name: TavoliAdiacenti commutazione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER commutazione AFTER INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.commutazione_adiacenza();


--
-- TOC entry 3260 (class 2620 OID 58299)
-- Name: Tavolo conta_posti_1sala_delete_update_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER conta_posti_1sala_delete_update_tavolo AFTER DELETE OR UPDATE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_delete_update_tavolo();


--
-- TOC entry 3261 (class 2620 OID 58298)
-- Name: Tavolo conta_posti_1sala_insert_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER conta_posti_1sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_insert_tavolo();


--
-- TOC entry 3258 (class 2620 OID 58303)
-- Name: Tavolo conta_posti_2ristorante_delete_update_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER conta_posti_2ristorante_delete_update_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_delete_update_tavolo();


--
-- TOC entry 3259 (class 2620 OID 58302)
-- Name: Tavolo conta_posti_2ristorante_insert_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER conta_posti_2ristorante_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_insert_tavolo();


--
-- TOC entry 3265 (class 2620 OID 58290)
-- Name: Cameriere update_ristorante_delete_cameriere; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_ristorante_delete_cameriere AFTER DELETE ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_delete_cameriere();


--
-- TOC entry 3266 (class 2620 OID 58289)
-- Name: Cameriere update_ristorante_insert_cameriere; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_ristorante_insert_cameriere AFTER INSERT ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_insert_cameriere();


--
-- TOC entry 3262 (class 2620 OID 58274)
-- Name: Tavolo update_sala_delete_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_sala_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_delete_tavolo();


--
-- TOC entry 3263 (class 2620 OID 58272)
-- Name: Tavolo update_sala_insert_tavolo; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_insert_tavolo();


--
-- TOC entry 3271 (class 2620 OID 58307)
-- Name: Prenotazione update_tavolata_delete_clienteprenotazione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_tavolata_delete_clienteprenotazione AFTER DELETE ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_delete_clienteprenotazione();


--
-- TOC entry 3272 (class 2620 OID 58306)
-- Name: Prenotazione update_tavolata_insert_clienteprenotazione; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER update_tavolata_insert_clienteprenotazione AFTER INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_insert_clienteprenotazione();


--
-- TOC entry 3252 (class 2606 OID 33208)
-- Name: Servizio Cameriere_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Cameriere_FK" FOREIGN KEY ("ID_Cameriere") REFERENCES public."Cameriere"("ID_Cameriere") ON DELETE CASCADE;


--
-- TOC entry 3251 (class 2606 OID 58280)
-- Name: Cameriere Cameriere_ID_Ristorante_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_ID_Ristorante_FK" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante") ON DELETE CASCADE;


--
-- TOC entry 3256 (class 2606 OID 49576)
-- Name: Prenotazione Codice_Prenotazione_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Codice_Prenotazione_FK" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione") ON DELETE CASCADE;


--
-- TOC entry 3250 (class 2606 OID 16436)
-- Name: Tavolata Codice_Tavolo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Codice_Tavolo" FOREIGN KEY ("Codice_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo") ON DELETE CASCADE;


--
-- TOC entry 3248 (class 2606 OID 16408)
-- Name: Sala ID_Ristorante; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "ID_Ristorante" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante") ON DELETE CASCADE;


--
-- TOC entry 3249 (class 2606 OID 58337)
-- Name: Tavolo ID_Sala_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "ID_Sala_FK" FOREIGN KEY ("ID_Sala") REFERENCES public."Sala"("ID_Sala") ON DELETE CASCADE;


--
-- TOC entry 3257 (class 2606 OID 49586)
-- Name: Prenotazione Numero_ID_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Numero_ID_FK" FOREIGN KEY ("Numero_ID") REFERENCES public."Cliente"("Numero_ID_Card") ON DELETE CASCADE;


--
-- TOC entry 3253 (class 2606 OID 33213)
-- Name: Servizio Prenotazione_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Prenotazione_FK" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione") ON DELETE CASCADE;


--
-- TOC entry 3255 (class 2606 OID 33226)
-- Name: TavoliAdiacenti TavoloAD_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "TavoloAD_FK" FOREIGN KEY ("ID_Tavolo_Adiacente") REFERENCES public."Tavolo"("Codice_Tavolo") ON DELETE CASCADE;


--
-- TOC entry 3254 (class 2606 OID 33221)
-- Name: TavoliAdiacenti Tavolo_FK; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "Tavolo_FK" FOREIGN KEY ("ID_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo") ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

