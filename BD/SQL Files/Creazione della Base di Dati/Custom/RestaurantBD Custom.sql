PGDMP         	                z        
   Restaurant    14.5    14.4 `    p           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            q           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            r           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            s           1262    16394 
   Restaurant    DATABASE     W   CREATE DATABASE "Restaurant" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C';
    DROP DATABASE "Restaurant";
                postgres    false            �            1255    16395    check_adiacenza_stesso_tavolo()    FUNCTION        CREATE FUNCTION public.check_adiacenza_stesso_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
IF NEW."ID_Tavolo" = NEW."ID_Tavolo_Adiacente" THEN
RAISE EXCEPTION 'Un tavolo non può essere adiacente a sè stesso.';
END IF;

RETURN NEW;
END$$;
 6   DROP FUNCTION public.check_adiacenza_stesso_tavolo();
       public          postgres    false            �            1255    16396    check_cameriere_ristorante()    FUNCTION     �  CREATE FUNCTION public.check_cameriere_ristorante() RETURNS trigger
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
 3   DROP FUNCTION public.check_cameriere_ristorante();
       public          postgres    false            �            1255    16397    check_date()    FUNCTION     v  CREATE FUNCTION public.check_date() RETURNS trigger
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
 #   DROP FUNCTION public.check_date();
       public          postgres    false            �            1255    16398    check_max_avventori()    FUNCTION       CREATE FUNCTION public.check_max_avventori() RETURNS trigger
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
 ,   DROP FUNCTION public.check_max_avventori();
       public          postgres    false            �            1255    16399    check_tavoli_sale()    FUNCTION     �  CREATE FUNCTION public.check_tavoli_sale() RETURNS trigger
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
 *   DROP FUNCTION public.check_tavoli_sale();
       public          postgres    false            �            1255    16400    commutazione_adiacenza()    FUNCTION     
  CREATE FUNCTION public.commutazione_adiacenza() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
	
INSERT INTO "TavoliAdiacenti" ("ID_Tavolo_Adiacente", "ID_Tavolo") 
VALUES (NEW."ID_Tavolo", NEW."ID_Tavolo_Adiacente") ON CONFLICT DO NOTHING;

RETURN NEW;
END$$;
 /   DROP FUNCTION public.commutazione_adiacenza();
       public          postgres    false            �            1255    16401 (   conta_posti_1sala_delete_update_tavolo()    FUNCTION     @  CREATE FUNCTION public.conta_posti_1sala_delete_update_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT SUM("Max_Avventori") INTO x FROM "Tavolo"
WHERE "Tavolo"."ID_Sala" = OLD."ID_Sala";

UPDATE "Sala" SET "Capienza" = x
WHERE "Sala"."ID_Sala" = OLD."ID_Sala";
RETURN NEW;
END$$;
 ?   DROP FUNCTION public.conta_posti_1sala_delete_update_tavolo();
       public          postgres    false            �            1255    16402 !   conta_posti_1sala_insert_tavolo()    FUNCTION     9  CREATE FUNCTION public.conta_posti_1sala_insert_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT SUM("Max_Avventori") INTO x FROM "Tavolo"
WHERE "Tavolo"."ID_Sala" = NEW."ID_Sala";

UPDATE "Sala" SET "Capienza" = x
WHERE "Sala"."ID_Sala" = NEW."ID_Sala";
RETURN NEW;
END$$;
 8   DROP FUNCTION public.conta_posti_1sala_insert_tavolo();
       public          postgres    false            �            1255    16403 .   conta_posti_2ristorante_delete_update_tavolo()    FUNCTION     a  CREATE FUNCTION public.conta_posti_2ristorante_delete_update_tavolo() RETURNS trigger
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
 E   DROP FUNCTION public.conta_posti_2ristorante_delete_update_tavolo();
       public          postgres    false            �            1255    16404 '   conta_posti_2ristorante_insert_tavolo()    FUNCTION     Z  CREATE FUNCTION public.conta_posti_2ristorante_insert_tavolo() RETURNS trigger
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
 >   DROP FUNCTION public.conta_posti_2ristorante_insert_tavolo();
       public          postgres    false            �            1255    16405 $   update_ristorante_delete_cameriere()    FUNCTION     b  CREATE FUNCTION public.update_ristorante_delete_cameriere() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Cameriere"
WHERE "Cameriere"."ID_Ristorante" = OLD."ID_Ristorante";

UPDATE "Ristorante" SET "Numero_Camerieri" = x
WHERE "Ristorante"."ID_Ristorante" = OLD."ID_Ristorante";
RETURN NEW;
END$$;
 ;   DROP FUNCTION public.update_ristorante_delete_cameriere();
       public          postgres    false            �            1255    16406 $   update_ristorante_insert_cameriere()    FUNCTION     b  CREATE FUNCTION public.update_ristorante_insert_cameriere() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Cameriere"
WHERE "Cameriere"."ID_Ristorante" = NEW."ID_Ristorante";

UPDATE "Ristorante" SET "Numero_Camerieri" = x
WHERE "Ristorante"."ID_Ristorante" = NEW."ID_Ristorante";
RETURN NEW;
END$$;
 ;   DROP FUNCTION public.update_ristorante_insert_cameriere();
       public          postgres    false            �            1255    16407    update_sala_delete_tavolo()    FUNCTION     ,  CREATE FUNCTION public.update_sala_delete_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN
SELECT COUNT(*) INTO x FROM "Tavolo" 
WHERE "Tavolo"."ID_Sala" = OLD."ID_Sala";

UPDATE "Sala" SET "Numero_Tavoli" = x
WHERE "Sala"."ID_Sala" = OLD."ID_Sala";
RETURN NEW;
END$$;
 2   DROP FUNCTION public.update_sala_delete_tavolo();
       public          postgres    false            �            1255    16408    update_sala_insert_tavolo()    FUNCTION     ,  CREATE FUNCTION public.update_sala_insert_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN
SELECT COUNT(*) INTO x FROM "Tavolo" 
WHERE "Tavolo"."ID_Sala" = NEW."ID_Sala";

UPDATE "Sala" SET "Numero_Tavoli" = x
WHERE "Sala"."ID_Sala" = NEW."ID_Sala";
RETURN NEW;
END$$;
 2   DROP FUNCTION public.update_sala_insert_tavolo();
       public          postgres    false            �            1255    16409 ,   update_tavolata_delete_clienteprenotazione()    FUNCTION     �  CREATE FUNCTION public.update_tavolata_delete_clienteprenotazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Prenotazione"
WHERE "Prenotazione"."Codice_Prenotazione" = OLD."Codice_Prenotazione";

UPDATE "Tavolata" SET "Numero_Clienti" = x
WHERE "Tavolata"."Codice_Prenotazione" = OLD."Codice_Prenotazione";
RETURN NEW;
END$$;
 C   DROP FUNCTION public.update_tavolata_delete_clienteprenotazione();
       public          postgres    false            �            1255    16410 ,   update_tavolata_insert_clienteprenotazione()    FUNCTION     �  CREATE FUNCTION public.update_tavolata_insert_clienteprenotazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT COUNT(*) INTO x FROM "Prenotazione"
WHERE "Prenotazione"."Codice_Prenotazione" = NEW."Codice_Prenotazione";

UPDATE "Tavolata" SET "Numero_Clienti" = x
WHERE "Tavolata"."Codice_Prenotazione" = NEW."Codice_Prenotazione";
RETURN NEW;
END$$;
 C   DROP FUNCTION public.update_tavolata_insert_clienteprenotazione();
       public          postgres    false            �            1259    16411 	   Cameriere    TABLE     �   CREATE TABLE public."Cameriere" (
    "ID_Cameriere" integer NOT NULL,
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "ID_Ristorante" integer NOT NULL,
    "Stipendio_Orario" integer
);
    DROP TABLE public."Cameriere";
       public         heap    postgres    false            �            1259    16414    Cameriere_ID_Cameriere_seq    SEQUENCE     �   CREATE SEQUENCE public."Cameriere_ID_Cameriere_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public."Cameriere_ID_Cameriere_seq";
       public          postgres    false    209            t           0    0    Cameriere_ID_Cameriere_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public."Cameriere_ID_Cameriere_seq" OWNED BY public."Cameriere"."ID_Cameriere";
          public          postgres    false    210            �            1259    16415    Cliente    TABLE     �   CREATE TABLE public."Cliente" (
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "Numero_ID_Card" character(9) NOT NULL,
    "Numero_Tel" text,
    CONSTRAINT ck_only_numbers CHECK (("Numero_Tel" !~~ '%[^0-9]%'::text))
);
    DROP TABLE public."Cliente";
       public         heap    postgres    false            �            1259    16421    Prenotazione    TABLE     z   CREATE TABLE public."Prenotazione" (
    "Codice_Prenotazione" integer NOT NULL,
    "Numero_ID" character(9) NOT NULL
);
 "   DROP TABLE public."Prenotazione";
       public         heap    postgres    false            �            1259    16424 
   Ristorante    TABLE     �   CREATE TABLE public."Ristorante" (
    "Nome_Ristorante" name NOT NULL,
    "ID_Ristorante" integer NOT NULL,
    "Numero_Camerieri" integer,
    "Capienza" integer
);
     DROP TABLE public."Ristorante";
       public         heap    postgres    false            �            1259    16427    Ristorante_ID_Ristorante_seq    SEQUENCE     �   CREATE SEQUENCE public."Ristorante_ID_Ristorante_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public."Ristorante_ID_Ristorante_seq";
       public          postgres    false    213            u           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public."Ristorante_ID_Ristorante_seq" OWNED BY public."Ristorante"."ID_Ristorante";
          public          postgres    false    214            �            1259    16428    Sala    TABLE     �   CREATE TABLE public."Sala" (
    "ID_Ristorante" integer NOT NULL,
    "Numero_Tavoli" integer,
    "Capienza" integer,
    "Nome_Sala" name NOT NULL,
    "ID_Sala" integer NOT NULL
);
    DROP TABLE public."Sala";
       public         heap    postgres    false            �            1259    16431    Sala_ID_Sala_seq    SEQUENCE     �   CREATE SEQUENCE public."Sala_ID_Sala_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."Sala_ID_Sala_seq";
       public          postgres    false    215            v           0    0    Sala_ID_Sala_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public."Sala_ID_Sala_seq" OWNED BY public."Sala"."ID_Sala";
          public          postgres    false    216            �            1259    16432    Servizio    TABLE     t   CREATE TABLE public."Servizio" (
    "ID_Cameriere" integer NOT NULL,
    "Codice_Prenotazione" integer NOT NULL
);
    DROP TABLE public."Servizio";
       public         heap    postgres    false            �            1259    16435    Tavolata    TABLE     �   CREATE TABLE public."Tavolata" (
    "Data_Arrivo" date NOT NULL,
    "Codice_Prenotazione" integer NOT NULL,
    "Codice_Tavolo" integer NOT NULL,
    "Numero_Clienti" integer
);
    DROP TABLE public."Tavolata";
       public         heap    postgres    false            �            1259    16438     Tavolata_Codice_Prenotazione_seq    SEQUENCE     �   CREATE SEQUENCE public."Tavolata_Codice_Prenotazione_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE public."Tavolata_Codice_Prenotazione_seq";
       public          postgres    false    218            w           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public."Tavolata_Codice_Prenotazione_seq" OWNED BY public."Tavolata"."Codice_Prenotazione";
          public          postgres    false    219            �            1259    16439    TavoliAdiacenti    TABLE     x   CREATE TABLE public."TavoliAdiacenti" (
    "ID_Tavolo" integer NOT NULL,
    "ID_Tavolo_Adiacente" integer NOT NULL
);
 %   DROP TABLE public."TavoliAdiacenti";
       public         heap    postgres    false            �            1259    16442    Tavolo    TABLE     �   CREATE TABLE public."Tavolo" (
    "Codice_Tavolo" integer NOT NULL,
    "Max_Avventori" integer NOT NULL,
    "ID_Sala" integer NOT NULL,
    CONSTRAINT avventori_positivo CHECK (("Max_Avventori" > 0))
);
    DROP TABLE public."Tavolo";
       public         heap    postgres    false            �            1259    16446    Tavolo_Codice_Tavolo_seq    SEQUENCE     �   CREATE SEQUENCE public."Tavolo_Codice_Tavolo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public."Tavolo_Codice_Tavolo_seq";
       public          postgres    false    221            x           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public."Tavolo_Codice_Tavolo_seq" OWNED BY public."Tavolo"."Codice_Tavolo";
          public          postgres    false    222            �            1259    16447    prenotazione_ristorante    VIEW     �  CREATE VIEW public.prenotazione_ristorante AS
 SELECT "Ristorante"."ID_Ristorante",
    "Tavolata"."Codice_Prenotazione"
   FROM (((public."Ristorante"
     JOIN public."Sala" ON (("Ristorante"."ID_Ristorante" = "Sala"."ID_Ristorante")))
     JOIN public."Tavolo" ON (("Sala"."ID_Sala" = "Tavolo"."ID_Sala")))
     JOIN public."Tavolata" ON (("Tavolo"."Codice_Tavolo" = "Tavolata"."Codice_Tavolo")));
 *   DROP VIEW public.prenotazione_ristorante;
       public          postgres    false    221    221    218    218    215    215    213            �           2604    16451    Cameriere ID_Cameriere    DEFAULT     �   ALTER TABLE ONLY public."Cameriere" ALTER COLUMN "ID_Cameriere" SET DEFAULT nextval('public."Cameriere_ID_Cameriere_seq"'::regclass);
 I   ALTER TABLE public."Cameriere" ALTER COLUMN "ID_Cameriere" DROP DEFAULT;
       public          postgres    false    210    209            �           2604    16452    Ristorante ID_Ristorante    DEFAULT     �   ALTER TABLE ONLY public."Ristorante" ALTER COLUMN "ID_Ristorante" SET DEFAULT nextval('public."Ristorante_ID_Ristorante_seq"'::regclass);
 K   ALTER TABLE public."Ristorante" ALTER COLUMN "ID_Ristorante" DROP DEFAULT;
       public          postgres    false    214    213            �           2604    16453    Sala ID_Sala    DEFAULT     r   ALTER TABLE ONLY public."Sala" ALTER COLUMN "ID_Sala" SET DEFAULT nextval('public."Sala_ID_Sala_seq"'::regclass);
 ?   ALTER TABLE public."Sala" ALTER COLUMN "ID_Sala" DROP DEFAULT;
       public          postgres    false    216    215            �           2604    16454    Tavolata Codice_Prenotazione    DEFAULT     �   ALTER TABLE ONLY public."Tavolata" ALTER COLUMN "Codice_Prenotazione" SET DEFAULT nextval('public."Tavolata_Codice_Prenotazione_seq"'::regclass);
 O   ALTER TABLE public."Tavolata" ALTER COLUMN "Codice_Prenotazione" DROP DEFAULT;
       public          postgres    false    219    218            �           2604    16455    Tavolo Codice_Tavolo    DEFAULT     �   ALTER TABLE ONLY public."Tavolo" ALTER COLUMN "Codice_Tavolo" SET DEFAULT nextval('public."Tavolo_Codice_Tavolo_seq"'::regclass);
 G   ALTER TABLE public."Tavolo" ALTER COLUMN "Codice_Tavolo" DROP DEFAULT;
       public          postgres    false    222    221            `          0    16411 	   Cameriere 
   TABLE DATA           m   COPY public."Cameriere" ("ID_Cameriere", "Nome", "Cognome", "ID_Ristorante", "Stipendio_Orario") FROM stdin;
    public          postgres    false    209   [�       b          0    16415    Cliente 
   TABLE DATA           V   COPY public."Cliente" ("Nome", "Cognome", "Numero_ID_Card", "Numero_Tel") FROM stdin;
    public          postgres    false    211   ��       c          0    16421    Prenotazione 
   TABLE DATA           L   COPY public."Prenotazione" ("Codice_Prenotazione", "Numero_ID") FROM stdin;
    public          postgres    false    212   E�       d          0    16424 
   Ristorante 
   TABLE DATA           j   COPY public."Ristorante" ("Nome_Ristorante", "ID_Ristorante", "Numero_Camerieri", "Capienza") FROM stdin;
    public          postgres    false    213   ��       f          0    16428    Sala 
   TABLE DATA           f   COPY public."Sala" ("ID_Ristorante", "Numero_Tavoli", "Capienza", "Nome_Sala", "ID_Sala") FROM stdin;
    public          postgres    false    215   ]�       h          0    16432    Servizio 
   TABLE DATA           K   COPY public."Servizio" ("ID_Cameriere", "Codice_Prenotazione") FROM stdin;
    public          postgres    false    217   ��       i          0    16435    Tavolata 
   TABLE DATA           m   COPY public."Tavolata" ("Data_Arrivo", "Codice_Prenotazione", "Codice_Tavolo", "Numero_Clienti") FROM stdin;
    public          postgres    false    218   ��       k          0    16439    TavoliAdiacenti 
   TABLE DATA           O   COPY public."TavoliAdiacenti" ("ID_Tavolo", "ID_Tavolo_Adiacente") FROM stdin;
    public          postgres    false    220   !�       l          0    16442    Tavolo 
   TABLE DATA           O   COPY public."Tavolo" ("Codice_Tavolo", "Max_Avventori", "ID_Sala") FROM stdin;
    public          postgres    false    221   ��       y           0    0    Cameriere_ID_Cameriere_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public."Cameriere_ID_Cameriere_seq"', 66, true);
          public          postgres    false    210            z           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public."Ristorante_ID_Ristorante_seq"', 4, false);
          public          postgres    false    214            {           0    0    Sala_ID_Sala_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public."Sala_ID_Sala_seq"', 13, false);
          public          postgres    false    216            |           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public."Tavolata_Codice_Prenotazione_seq"', 250, true);
          public          postgres    false    219            }           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public."Tavolo_Codice_Tavolo_seq"', 51, false);
          public          postgres    false    222            �           2606    16457    Cameriere Cameriere_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_pkey" PRIMARY KEY ("ID_Cameriere");
 F   ALTER TABLE ONLY public."Cameriere" DROP CONSTRAINT "Cameriere_pkey";
       public            postgres    false    209            �           2606    16459    Cliente Cliente_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT "Cliente_pkey" PRIMARY KEY ("Numero_ID_Card");
 B   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT "Cliente_pkey";
       public            postgres    false    211            �           2606    16461    Ristorante Ristorante_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT "Ristorante_pkey" PRIMARY KEY ("ID_Ristorante");
 H   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT "Ristorante_pkey";
       public            postgres    false    213            �           2606    16463    Sala Sala_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "Sala_pkey" PRIMARY KEY ("ID_Sala");
 <   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "Sala_pkey";
       public            postgres    false    215            �           2606    16465    Servizio Servizio_pkey 
   CONSTRAINT     {   ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Servizio_pkey" PRIMARY KEY ("ID_Cameriere", "Codice_Prenotazione");
 D   ALTER TABLE ONLY public."Servizio" DROP CONSTRAINT "Servizio_pkey";
       public            postgres    false    217    217            �           2606    16467    Tavolata Tavolata_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Tavolata_pkey" PRIMARY KEY ("Codice_Prenotazione");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Tavolata_pkey";
       public            postgres    false    218            �           2606    16469 $   TavoliAdiacenti TavoliAdiacenti_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "TavoliAdiacenti_pkey" PRIMARY KEY ("ID_Tavolo", "ID_Tavolo_Adiacente");
 R   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "TavoliAdiacenti_pkey";
       public            postgres    false    220    220            �           2606    16471    Tavolo Tavolo_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Tavolo_pkey" PRIMARY KEY ("Codice_Tavolo");
 @   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "Tavolo_pkey";
       public            postgres    false    221            �           2606    16472    Cliente ck_lunghezza_id    CHECK CONSTRAINT     r   ALTER TABLE public."Cliente"
    ADD CONSTRAINT ck_lunghezza_id CHECK ((length("Numero_ID_Card") = 9)) NOT VALID;
 >   ALTER TABLE public."Cliente" DROP CONSTRAINT ck_lunghezza_id;
       public          postgres    false    211    211            �           2606    16474 !   Ristorante unique_nome_ristorante 
   CONSTRAINT     k   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT unique_nome_ristorante UNIQUE ("Nome_Ristorante");
 M   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT unique_nome_ristorante;
       public            postgres    false    213            �           2606    16476    Cliente unique_numero_tel 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT unique_numero_tel UNIQUE ("Numero_Tel");
 E   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT unique_numero_tel;
       public            postgres    false    211            �           2620    16477    TavoliAdiacenti check_adiacenti    TRIGGER     �   CREATE TRIGGER check_adiacenti BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_tavoli_sale();
 :   DROP TRIGGER check_adiacenti ON public."TavoliAdiacenti";
       public          postgres    false    232    220            �           2620    16478    Prenotazione check_avventori    TRIGGER     �   CREATE TRIGGER check_avventori BEFORE INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.check_max_avventori();
 7   DROP TRIGGER check_avventori ON public."Prenotazione";
       public          postgres    false    228    212            �           2620    16479    Servizio check_servizio    TRIGGER     �   CREATE TRIGGER check_servizio BEFORE INSERT ON public."Servizio" FOR EACH ROW EXECUTE FUNCTION public.check_cameriere_ristorante();
 2   DROP TRIGGER check_servizio ON public."Servizio";
       public          postgres    false    225    217            �           2620    16480 #   TavoliAdiacenti check_stesso_tavolo    TRIGGER     �   CREATE TRIGGER check_stesso_tavolo BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_adiacenza_stesso_tavolo();
 >   DROP TRIGGER check_stesso_tavolo ON public."TavoliAdiacenti";
       public          postgres    false    224    220            �           2620    16481    Tavolata check_tavolo_libero    TRIGGER     y   CREATE TRIGGER check_tavolo_libero BEFORE INSERT ON public."Tavolata" FOR EACH ROW EXECUTE FUNCTION public.check_date();
 7   DROP TRIGGER check_tavolo_libero ON public."Tavolata";
       public          postgres    false    227    218            �           2620    16482    TavoliAdiacenti commutazione    TRIGGER     �   CREATE TRIGGER commutazione AFTER INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.commutazione_adiacenza();
 7   DROP TRIGGER commutazione ON public."TavoliAdiacenti";
       public          postgres    false    233    220            �           2620    16605 -   Tavolo conta_posti_1sala_delete_update_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_1sala_delete_update_tavolo AFTER DELETE OR UPDATE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_delete_update_tavolo();
 H   DROP TRIGGER conta_posti_1sala_delete_update_tavolo ON public."Tavolo";
       public          postgres    false    221    226            �           2620    16484 &   Tavolo conta_posti_1sala_insert_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_1sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_insert_tavolo();
 A   DROP TRIGGER conta_posti_1sala_insert_tavolo ON public."Tavolo";
       public          postgres    false    234    221            �           2620    16606 3   Tavolo conta_posti_2ristorante_delete_update_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_2ristorante_delete_update_tavolo AFTER DELETE OR UPDATE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_delete_update_tavolo();
 N   DROP TRIGGER conta_posti_2ristorante_delete_update_tavolo ON public."Tavolo";
       public          postgres    false    221    250            �           2620    16486 ,   Tavolo conta_posti_2ristorante_insert_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_2ristorante_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_insert_tavolo();
 G   DROP TRIGGER conta_posti_2ristorante_insert_tavolo ON public."Tavolo";
       public          postgres    false    221    243            �           2620    16487 ,   Cameriere update_ristorante_delete_cameriere    TRIGGER     �   CREATE TRIGGER update_ristorante_delete_cameriere AFTER DELETE ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_delete_cameriere();
 G   DROP TRIGGER update_ristorante_delete_cameriere ON public."Cameriere";
       public          postgres    false    209    244            �           2620    16488 ,   Cameriere update_ristorante_insert_cameriere    TRIGGER     �   CREATE TRIGGER update_ristorante_insert_cameriere AFTER INSERT ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_insert_cameriere();
 G   DROP TRIGGER update_ristorante_insert_cameriere ON public."Cameriere";
       public          postgres    false    245    209            �           2620    16489     Tavolo update_sala_delete_tavolo    TRIGGER     �   CREATE TRIGGER update_sala_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_delete_tavolo();
 ;   DROP TRIGGER update_sala_delete_tavolo ON public."Tavolo";
       public          postgres    false    221    246            �           2620    16490     Tavolo update_sala_insert_tavolo    TRIGGER     �   CREATE TRIGGER update_sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_insert_tavolo();
 ;   DROP TRIGGER update_sala_insert_tavolo ON public."Tavolo";
       public          postgres    false    221    247            �           2620    16491 7   Prenotazione update_tavolata_delete_clienteprenotazione    TRIGGER     �   CREATE TRIGGER update_tavolata_delete_clienteprenotazione AFTER DELETE ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_delete_clienteprenotazione();
 R   DROP TRIGGER update_tavolata_delete_clienteprenotazione ON public."Prenotazione";
       public          postgres    false    212    248            �           2620    16492 7   Prenotazione update_tavolata_insert_clienteprenotazione    TRIGGER     �   CREATE TRIGGER update_tavolata_insert_clienteprenotazione AFTER INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_insert_clienteprenotazione();
 R   DROP TRIGGER update_tavolata_insert_clienteprenotazione ON public."Prenotazione";
       public          postgres    false    212    249            �           2606    16563    Servizio Cameriere_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Cameriere_FK" FOREIGN KEY ("ID_Cameriere") REFERENCES public."Cameriere"("ID_Cameriere") ON DELETE CASCADE;
 C   ALTER TABLE ONLY public."Servizio" DROP CONSTRAINT "Cameriere_FK";
       public          postgres    false    217    3495    209            �           2606    16595 $   Cameriere Cameriere_ID_Ristorante_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_ID_Ristorante_FK" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante") ON DELETE CASCADE;
 R   ALTER TABLE ONLY public."Cameriere" DROP CONSTRAINT "Cameriere_ID_Ristorante_FK";
       public          postgres    false    3501    209    213            �           2606    16545 #   Prenotazione Codice_Prenotazione_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Codice_Prenotazione_FK" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione") ON DELETE CASCADE;
 Q   ALTER TABLE ONLY public."Prenotazione" DROP CONSTRAINT "Codice_Prenotazione_FK";
       public          postgres    false    212    3509    218            �           2606    16588    Tavolata Codice_Tavolo    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Codice_Tavolo" FOREIGN KEY ("Codice_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo") ON DELETE CASCADE;
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Codice_Tavolo";
       public          postgres    false    221    218    3513            �           2606    16568    Sala ID_Ristorante    FK CONSTRAINT     �   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "ID_Ristorante" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante") ON DELETE CASCADE;
 @   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "ID_Ristorante";
       public          postgres    false    215    213    3501            �           2606    16573    Tavolo ID_Sala_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "ID_Sala_FK" FOREIGN KEY ("ID_Sala") REFERENCES public."Sala"("ID_Sala") ON DELETE CASCADE;
 ?   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "ID_Sala_FK";
       public          postgres    false    221    3505    215            �           2606    16553    Prenotazione Numero_ID_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Numero_ID_FK" FOREIGN KEY ("Numero_ID") REFERENCES public."Cliente"("Numero_ID_Card") ON DELETE CASCADE;
 G   ALTER TABLE ONLY public."Prenotazione" DROP CONSTRAINT "Numero_ID_FK";
       public          postgres    false    212    3497    211            �           2606    16558    Servizio Prenotazione_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Prenotazione_FK" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione") ON DELETE CASCADE;
 F   ALTER TABLE ONLY public."Servizio" DROP CONSTRAINT "Prenotazione_FK";
       public          postgres    false    3509    218    217            �           2606    16583    TavoliAdiacenti TavoloAD_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "TavoloAD_FK" FOREIGN KEY ("ID_Tavolo_Adiacente") REFERENCES public."Tavolo"("Codice_Tavolo") ON DELETE CASCADE;
 I   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "TavoloAD_FK";
       public          postgres    false    221    3513    220            �           2606    16578    TavoliAdiacenti Tavolo_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "Tavolo_FK" FOREIGN KEY ("ID_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo") ON DELETE CASCADE;
 G   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "Tavolo_FK";
       public          postgres    false    221    220    3513            `   T  x�=T˒�0<�?&�-��q���־j�����'fʲ���%����f��QwO�����jh��f`C)-��|���99˨�i�h3xmӫX댐"����}M��4{t�����ə�N*��~�&%��/���N�F�tg8Mkg��غ���jz���غ�]�}�m��u��3:]K'�^]8�4:Mi��hZu��N匶b:t�]���U=�u�R���t?Is0������t����?�oaM�HҒ��`�0:O�9����wG��.�Y"i���ܒ�NC���Z��8���MTm��H2J�,�7#�L/.�8XQ�d؜�}�����U5��'��
֧��؇���n|9�i�W��/�Gi1�@�uh�.&J�ZyW�(�rU{���igԂj܀�ܬ�-0�Z�{/��p,�f�����W�f��V;r�F�ң�x�Cg��ԙяh��
F�vy�l�;]-��u#5������w��U��ȼ�0�}r��؀� ���活�$�w�=��2Q:DrX�y8�P��%�cq�� �ml>��|��2�SN�A볃:<�f��Q�5B�{7�m�tw��{.���}��v��ǝ�������s���,E'��Jzh���1?R���A�x��83��/�[W#D���/�{=`&�����u=���a]��Qzp���wE
�߿��}�m"m��ޟb���F"�eßp*�����x�6�V����7qh��3���2�=�8ܒ�%'x�Wg7�h��[p�� ��G��'w����M8�qr����Ab�vz�i9�.�E�l�iO%��u�'F�N�/d2�i���)�xY`>���_ߒ$�۷��      b      x�E|�v�Ȓ�����U����� 1O$� Oo !�DQ ����{��י�^,� ��fw03�?��1��8]�o�x��7�Q��L��膣[�ƽ~N� ���q�Dv�Ϲ�%Ga[���c���e��xԼ\���ӭ�:�QS����b�N�� �e�\G�w�ڦ'W��ch6��L���\�i\���V�
�)�pE��y��~~��7�o���q]
�v��lG77�x����_�M(QiＸJ��I�1��ۛ�Ǻ����U�����n�!��]������LwD�Ή)G�i����m�?�w?���c�����C���tǴUSݤ�mx���?��Mę�k�z+�����(��n����Ǆ'��|}�ճssp�\ф������ӽ��� ���e��w}I��-a��e[6����x�/�dGQ���iV����ؤ���x���x�v+�^G}�y�l��l�����<_D��￶�aZD���.Q��0_e)������@��ˍ��6�|������h����n�����C�s7�Rc��2UKUl)7�wH����%����f���+��jڦ^���G����exq5�q��
�e���-��]�,o����]�e���,[W����;8����n^������q$U�r4��Dӓ'2��y}�7�Znn{GxeGUu���s⺋�4��m.ݢ���M�7�Q�ӝ0uíC��F�8��9�Ɵ/����Q]8}x�����P7����W,j|�}��|�m�Pڙ���4��m���i̓�p3�EV+RCSTgS����Y>�������t!U�hY��)&����"�A���C���D��M�e�}|���kAxyU���Y�j*@�Ȱ,�Ul��'������
ݲ��8&I�M�^��q��no��}[-���m#�47ɯ������!��|[
K�-��:�+F=����g�}��D*��S��p��Y��(��v'D4ϧ:���lI9ac��(��H����ڃ]��ⵥ�͓���&��s.�Z�wj�	<D�5��� �	U�ݷu�n=��eo���v�N���'U M�3���>���l��A��o��#V;e�m)p�:�a����/r��&���
���a��g�&6���@�꽦����b+@�mRf�(���􆌈N����	�PG���-��x��w|v����)���l�[U%68\��2��@��*��4�H��&K߀pF�H�44����S���3��,���?�8��h�U]s�B�%P�4�����������T���i&����mؠb����Y��n�ªy x��f6_�J��Qo�@���[y�)&#c�.�nA�1!w�nۇi�LMJ[3���x{��Y؝�aot�n�edzb��룐��p�0;Kv#{ ��s�>�&���&�.��]�;
S�,T��i�pl,!�@Â��)�9!�Ob�����}���l�30<�H�>r%i���ɗ����m���x�n��>\�9���}�����R��O��h�������4^�[�K�y9�6�L10!~���+P�X?�d#�q�RD$X�~�Ǉ��Ri]׻ǣP����km2�Gv�_�f����v�CͰu� X�vg( ���(�wj�{� J����&@f=��D�c�N�.O�F�EvF�&�o�G�����V� Jb$)��m\J��~�������!�3����q4 ����y�B�{>E��y�6P �a:��+�Ҫ��<�Ua+	�R�M3�M�O�T~{,|�ZEm� +�@�lw�xuM_"��"�A�<�À �$ ����n!���;-����C|`�
� ��h��w�s�Q4@��xq�: �C~�S��'c�T���¦�9���&�QB�]T�Ċ���+T�t�M�5�v@����eZ�u�m JN�.�ɏd���P�ތ����To�����
!� g�.�_�S�)��4�B��	��J�����.�{�L@w n%ϫy ��b�Ċh���gNJII���m��z~�'j�;�����=ĵj�J�"�����W�x�%� q�cm�aA��./��S�Y}6�]x�j�J��}એ�6Yف�,���)��ޤ3���gxY[�n`)P?K�J�^��~u�5z��C�;
d��@����+��tg
�]�W	M��
8���}$}���8�E�����P� lE��_�f�"�DU�����ď���N��I���u_3�����/�5" �J�R6�y~���9?�uZ6a�Y�-����XQ�����!z��]U�MP�J֕a�x ��oᦺY������4��~;���g]^;�c�&���q!�������"wzX�]�
2�� yˑ�z�M�Q׫��i�/2d��X2���|�L�,���I���Ʊ��(�5,J�P�#�j;}~RLy}u��B�K��)q��7�W��,�ڒnTV!�O�}#�n1<���C�t���9T*4��ZW��9���;RB�n���	aq�?�vr���@�zj�~Ӷ����� (�<!�6�L?<��eH�V�DU��&cB+�x�I����X�i)��$�!B!�5]9�ݶ�ԂT 0���"F�~��J�Ԉ�P6G1������{u���i[A"i:i�N � ���uvNjp
S�Y,�LJ��]M��ձ���W�in�@a:�w��oE⸁��0��tX�g���<|N�i���T�SB�a] 7:%���e�_�8q��-� x_�����S�ZSӝI,�M��*����u��z̋8y�i���p�ٲm�B�e㕶�Jc�5��3BSJT�ۚ���H[�������@��X�TT��7d�t#�������}G���r���uo��q�����m���$�Yr����j���r�(���b��
}`RqEd��D��m��A��[*$������_��ے�b ���X�@/0l7@$�L����U�g��v����94�:0˛>��3��4O&�	����)&�B��������G�4NC�lw�c�\d��H�p��7�s���6�
�$�`�6B�R}�g%I��R���U���D�Bf�X�=���6Ym1@ӊBcE�0 7�}{vb3t6��"�.]W�ڏ�pQ���sJ��"�	�u�"_������"�ѠMl�"A�˴�=�P4ʩ/N�R �pV*��[�^2�3�w�窐�̐q����]Dv�χ8뇾�����KI�8��BX!n�Ω���eP�Py��+KL��w�[l���w E[��0�9ЂU,��������!^]U�Q<��S�'�9�￐��ю���1�1�Y�)(�u������
�dA|�H
M� 9�h��Sm-#��'�ǵ���!����{��?@�_p2ip
"�۱!��0O���otfj|.N�m�9 i�e�t��̠��`i�N�U,�8p	��0�>W�5�-�aH��T9N1���k^~���� ��~$�^��}�C��o�cX��V]h��q�%e��f �h�����hEI�<"��e�������=���ZS	\@
�%5��&.�TS�i�X�E���t�����U���S���Մ�����	fFx?����Zm�M�B�A7[@S�5�;T,t�Q:������d1e�a������)��p��qT�:.���v����Ei�QV��P��]�gd�uZ��G]}i}��C�`I:���qD�Bsu2�N�v&�
�5m�x����B���∷���X���o�|Y�����Ώ�vW��0�y����)96`�,8��*ԝ�=�� �I̊��*P���/ᜋs�i���V@�.�+�u��1��ㅵ�S4U����x��j�R�G���	��ZP�+z�q<�Q�;����!AKᐠs��|�ξ�����5���㈉���;��u�&�G��6G��)8BH�")��W?a�@��5��͊�Hc�a$K������7    �7��&r�?G��΄�� %��JR��k�i� �a%>����  �׭9 ��Z9�@y��B�Cb�R���_���n]#�! �,�0|�N�fT쎻�1*�g�-�/^���?�~y�Q^����(��k�Qz	�|>�1��V��E�[B��ۆO��5P�5�4Tk�t�/�?�Q���e��4|�Ԫ��U �����2S���Oh�C~Ps'�[#�����dٯaa���k���O�1wD`F�Y2��-:=M��Ђ��V@9�|��M���wl�pа�&�C���3�}�����@(g��h�C� ѫ��t���://b���*�zهt �C���M:J�_�؅N�w+t	�@��@̯��ߟ�����9L����)�BD����6I�Ye��8P�
��Y�?/��d��:MP),�st6(� �����*����x:$fZ�BAV��.#�6�s������]V�E��&����V���U!爛��.W��́�`5w�l]V"9ɃS׹r���B�,��n��<ɓs�5�c���l�/��@1��B�r��v��X�����/C'�n�"�L��f(6KWY��8��j��5)��LKɩ�T!h��`��?O�-��tL����x��������全|(#�Ʀ' �,��P6��� ?�KZ�h\�<�Hv3yr�rz��7�Ax&�ulm�Vc�ف���I�|�i��v]�K��l}��vX�� ��@���pe-[��]}*, �	�
դw�R$ܚi[{a@&Kh8�I;|~�k�o�P�LW��5���M�|K�S?_@(��n�Zy��cU]Bރ�� LXt����F�A�j�@_g}�W�,���F���M�KWŃh�4�Z<�F8�����1�
��;tְZ���i&�����X}�{M]��4�
{s��~"yya.m��Ҫ�\�xf�6����<�=��H={���
�8��0�{|�$��;NN�|��)Q�,���O,����!�b@�S� 
n��߯���-���[�������`��xY�9w��Q���ʄ����N�,X��zظ[DA�'�#o@O80��i^~�W��$��}@, �t�D	��&��<���ݤE��0�v�EP���d,h��ӅP���W��\��y���n��|&&��o�6wǜAiBr�s���3�j�W�)�Lj�0o-�����n��u
l�UH� @�~����f�T�ޗQ	D�������C�U�UK�����_�|e�>���p&䣻wB�'�;�@ rSS:N��{��WUH�ߪ�c��"���!ְN�q�]��23�KsAsls�i/�$�mE�.>�3��*��*�_�H�����˳⫒�J�6!sX�~�n,(=f��9qPJ5g���o��}����7U�mFꮍ�L � *�D�8���~��t�g0�q">T�;V	)�
<��7��r2��R�zh��f����.���Q�x6UN��oda��t~���#���R�.��n�� B*���ω��̔6��quz��n@)��fw��5�t��Y0�������1�s��0� .`��c'�Zݶ��R�]�f���N8�毻(��Uo�Kڰ $���ڕλr��qI ء3���{ϑ��od�F�{���4{�Z�2����D�ű�5�!\�(Ѝ��@�>O ���4�LԒH��  Q06}�Gq�21��Omk�s�,f�����y���+���:\�B�SV����?s�����=	.I� �z�U��,�R����l�/�S���~��_"f��߇�u'*�Ֆ]UK���mW'֛��������$r�&��",O"B��$�)�Q�{�&X�2�Q���k�}B~�̮� ������i�oq�N�$3�޳3���8־	tf�(gI铔ۘ���G I1�M`�g�4�k�Z�Y�4R��T J,���Y��9~O�8��;���ǧ�ڝ�,�����w`�����<u��s!������>�H�{蜳�י"�Q6	����j>g�8�^���
�g�t�? �q�xW*��m����<�,�(X�o1f?#�
�{�QCʩ�-�J�ow ��3���Bw�(qUl�8��s]���8�D+"W�7�9u��c�D�(�׍>�Fm����	��m���;�m8ܠŚ_�mX���|,�����U�6����.����ΗS�YÀ~`[�z��l�oP�rk%ĪdW�]V�I�D��y��e�0)��E��( ��`��k-?�`�b/#ߏ�49w����^�@'
خ�oc�lC�jX�ʡ![�B9$��X�0�d}���OJ��G,�2�[5�q�R8j���$������,�gB�mw��p�$d} ;��y�1?~E� �w���ج��1aF �W��o��8Q��U[�!>Mc3~p��\3�j�'ŶY&����e��	���Mdv^�]Pg����=�r��u�ֻ���\��c@ցI)F��7���;�% �m$���>Ce�)��T_A�p�±���|_��E&���#%2v���
�ł7�]�I�ʚ0Q�~�
�l��m�+,'v'�Q{i�/rXS@���9�������,��>:[���6�	�[�G�o�pU�MM��'L�&�@����	��A��sը�h<P����p<~��O}@IT��1T���t,�|��ܳΖm��
[�&��k\1q;_ �zG+C`��'�N�{,��1���+�L!��`����7</��Y�~���n4²6x
6'`\�!"3��Tk{�x_��O//_�$ya��[f{8h]�I��O7�$���� ��)`8h!3�[,0?�����`�
�-<������	�7DxXp�z<�cnrfC�z,�H@N������:M.]c��q�.1�t=}0�6m�Į�ʪ�6�ut	Wi�������F4�a�:����2��0 ��U���v."_�p�j��x�YN��.�S��!�ᓥ�J.������㹯K��Tp��p����ן���� JO��S 	 ��ƙ���V���ކ)��2[�X��ŧ�X���r�C�������!,���a��`�� Ű�x�R���-[�����XpnJp2Aڂ�0�����{�÷v�\�̭����o�]ZD8z�)ܝ�xY� ;�+𾊅��Y�$`�J:�q����f�@0rZ��_6��	Ʃd��e�	��Ni v�Ȃ}R�.>�ҳ
a���UB�����B�s��f�Vzu6�c�eaC�a��_|�����#��T�M p:�_��n"?��T]ݝ�q��Q�,��*>����/�$o�	���@� ���0�m~���2����J�nӊP�n��?�8g�&`j�R�74�M=�-VteA�zr+���u��L�dmc�����|�u��ޗ�.�� L�&7{�`	jo���k�@
��#F]�D1ڮ�Xð��5���PP�p��u��q�ռ@�d�J�w�Y�� |�6�u��1�i��@ٺ`$�ڽ�@?��f@dv���)C��)�M�f����5�Yԕ���ۏ��\Ł푟kO�c\~��-v��9�Q[юk�����_7�)jSo�**+HP �'��m���l}n`���s!�<9���>W���۴?��b���M���!7@u�Eݘ��f�����)�$/������@Tlã�s�2��pzء��v2T��y�����N�N,�X������0��u�}�~��^�d}a���8߾��N��+�sVp3i]��zؾ
���f�$]�l#������n�2�,50\y4�Pb*�gπ\�V��'b|w:d��4=�G���b�f�I}���QI˜+X?�d����f#	d����N�{��qg�<F!��	�y�TP��i6���m���t�]�׋h���e�2tK����}��N)��ҩ���*���p��8|�$�!Ln_�O�\�ŗh爣6:@�Vn����a��qλ��'' l  0�ن�$�҆	�5����{;��p��3lj��x��f��Ojf��h�X��d�u�ooo�Ho�i$V�#����"n8 )�/>(�M�T�Mh�:��qO8����ﺵ�fk��dl�!9�9����2A��6'/N��=ε�����<�&�vֹ�y; ��Brԕ%-�IW�&�S���9����ؠ���|O�(��w�����v@N;��2�׼��M0H��!0
;Ul��̂���e��#�B+�iy�<KG�`�X��=8(q�B�2=���drz�؋�e�lz��y�h��vر9��1?���'K�2qZ�ܪ"hqT�nr�"^ް7��ⰴ�D����P�M �f�Gk�:y�d�ݱf��c�"~{]�-�yM�$��C$���p�ٛ��w��,�Fo�T�#��U��
;�:D��6�hU��m:p�Rh�e�E������o:��(�~[mA�BV�-����kuR���/V�T���o;�%��Fd�����ti|��k�_�F��J7���a��s�_�H���n'�3쎨��Tr�B0��矬����n�he�Xd?�.��q8@��9T��$�T�S��_|��������OC�X�h8�?|�`I��D�8*@K��0�'^�ث����:�>��0uHtC�!��e�B�����O��,�׺&ْ�t�cY��֡8;[�J������m��j��}�@�g
@�K<��ˁ,��(��>s���[�� #�l�X�lW�Na�%���d�����OX���{�������
'Ut�]��(92��i7�������@��p�m?�p����Է�/��|�y�k�M��7�}�o�����|c���,����c�B֮��N� 0K[�P�6>�)����o+�ݗ�>TD��Q2�A�=�/�
g��l�N�j�!��8�t�	��f����E`(�:�Z�����Kᙙ��S�3^WZ4�r=j��]��C��TK��v�\�K�z�Nu�ٞ��ɺ��[X���p�KWb[����e�4U��[��9D��F5��j�.�*G�9��jdB��j�Sk8���pլ�J]E&w__��睥�Skz~�GXK$@���tQN�gxR�:�N���;T[;M�����Q�<�Y}A"�����|������|�Um6��kk{Z;���W1"jb�ͪ.v�Kr6lm�,���k(�`���m93l V����{�2_���V�6�a�Y���PP������y�4־k�=C�3_���qqx�ޮ�����7@Y�*7��2v��ՃخH�먲���y��q�/�c�Nk�����x{nS=���s�t����a������hs�׼���ݢ�t�hY�5���H�a����<�C��&��g�o��\QW�ee�S�t��l��b��o�Y�Oh��1�+X�"웒'蘡�O�a�ClG��o��@:�Hv0���)�-����b�0��	�k�eLb_Tm7h�=iM���8	���>��9l효�p��dkI����'L�_���:M̼t���		Q�C���w�zF�(y�H^8�Kс#8yU͔iD�Ī���b�O��>����y���Fq�۝a��*2O'�҇��Ⱦ�]ht���c�q@��`��� z��� �v[�� �q 0F�~�@x�a�	�V2�}�{z}:p"[������O?!���:'uc �u
Rmf�`��/��S���K���L[���	\h�l6�k8X��� Q�M9}�3LHf�^s���mW����xY�'����rR�fv�b��M��?�u�9������1�ӖUX��^��v!+J+�1�wu�#��C�,�mUYo�?]�#���4:��H5�����eƙ$����VYm(A�2��é�Qeq��� Y�c�����3]�ώ^r
��J����`��|�����(�s�]�c���L����|L�:�z���O��YM���F7��:�?�2�gq�ه����4Sٜ��P'/��"�"���ŗ6mR��i�`X�y���� �q,�Es�<�o?=;du!��C�T!`!QN�Z毟8��Z��k�]��Y�Aֹ�$���4��\'����c�c�r�İyO+0}|�S֡a����+�٭������}<�N[���8Ao�����S	��ʳ���BN\8P(�����4^��������h��ߛ���u� J���\�
Sq������Y�"�u�X�2����CZ�; ���N?ݥJ�?Q+�)�̍��+Z%܇���R8+��#��_�f�$��&Ȓ��JТ���]y�����'n� 8i�A���]rpm�,���(����.��Z@�\����F�q"?Is���Iۀ��.��8ts-��ȶ�!�7X�0�z�m���Z؝�?�˃R��s��>��R��ư�B�85�
ﯼ�P0���Ӱ63'mK�"6��#�k$����T	vY�vᩱn��;T��cy� ��f��b�\�Ī����@�0�w�t�mJ2�2�\;�� �}f�Z��y]�v���u^�<�F9K�����9�6�^b��Ṫ*��*���������V9x�.lzc}��+'��ڣ�����3��Y��#�����J�PX����t୔�W�D_4����ś;0jp�D;����?X�bxŶ�����lo��]�"*�]�D,J@B�������7�Vy��o��R����ŏ�����8�3Њ4��9"��܆;�С�0��4�f^���h�F��'pڮ��� |��b�w5�#]��7N��ciK�_�������&���b����9��7ml�76�1|ۅ������[�pT���^x�~��i��A�A�����&X��M���t�������*/8@�� �'o��_��L����8y�:��k�b��}5|~w�UgWռ�ˑ9�!&c�e_�Y�ɦ�O���"W�� �h1��=Z|~���U[�����Xwx	i��hx5����m',�EE����/�n9R��r_������0�+�;�ֳ��ҳ,g6��J��kϏu�f��E�*����x��M'ځz�R�x_����$g�7��Z�enہ0
V�g�����U��6��`��杓W�vf��.�[��{��
C�R�e�#����k݉l|��a��q֟��Y�k[CId\k�ŀP���9`����YM��^�ى����k4��! �I����Q�4��O�P
DmW����;�k$��!P�����&�x����OpxI����ֹ᱂i������y�"֋s��j���?��R9���'�����A�8/6%ֲlx������ٶ�<?:��w�r����E�����bڡ���w��*G����?x������R�i(3^XbWƿ~_^D�[z���LU�T!��V�4���Ɵ����b4�B�D;��I	�尟���J��F������I̸��R��!QX�~eg���_�|O�hUp�s�`Cr�ӂ\�H�l�]�f�3��H|��q��(R��b:���Ð���6-N�4��z�&\���k�Q�j�P V,$�7�:J;,�����f����%�      c   �  x�MVG�*]�aF�t��Ys�s���4�]Ӳ��KU�I��q.D/���w���h�x۶�
�B����R`�y���Kx�D]�����f�ǳ=,8*L���[��D%��p�qaEжG���X�`Р����9�!>oQ���O��PìY�a�*�R��fjH��D��[�c�)Uh(��#&,R�g���	K�H�;��#���;+� 07�dk	f�˔�ulf��%v��B �@tHJ��\��h�J�/�ZlZ�\�K5t(Pn鸔G��e�Œ�ɥ�y�/.�(�(�k[�p5k�J{M�\nUA1��#M�K������ԓ��	pRz;���NԽ��3�a؆Env��yď !�HL�,b;@E��YX^P~*��B�m�rn��M~@A��1MG�[�t3v6Wm#S�F�r�����L�X@�BT��<� 6��ᘘŖ#�W�w��+.�%�N�����kti��1�e�K�x�s�M�{��}�����{j�6��'6
	��}nB|�"(�j�Y$���ª�(n�"[ߓ�k�!���'~�$����	���_�٫��h�8�y\Ƿǲ�c1�ux�������	[��Y;^wOW������V��G�u"�߷��D���-U�B�j(��tng/�c����(���k�	�q?�0(���ݫ�=D��˞���*.l%ژ��$���g�ծx�����7%����`+c�)�?T�kEܫ�����^�/ņ���4��M���������Ҡ�Y�!R��K��4�^�$�Jc6X#Z7�s��n�s���~EV���M��W�1Q�`%��&,�� T�兢��n���z?�T���Et?jU�(%6��n�<���eo
�O¾�G#ъz�C
j�A�J��.�Џ�B�HWu��6�m~-�@��s�@˫~x���dO���Ԃ��a�L8�u[��bh+��_����8���0f>h�?��a��`B�&=�f��4��݃y��S�|��R���0������	'>.Z��B.�{�4�Е4({�#i��!��#�vn�)V��[��M�p���y3�%�0�(C�ċ8-w�]����v���(�N��t�f���\jk��`k�e"����ǎt�$�o��S�/]|��<Ѥ��J�}^6W��vs����Oj�ؔ*�dէy ���v	s�T��~�#�V���ݟ><�E��7��u3)Ģ�c��Zn�}<]� R�
��p[�q��|H=��r��=��'��m����O�vg�_
J�*Y>��3���9��D�ہ��w%$cQ��'MH�_F0�ڛo續}�a�෋��~{7
�y����m�۾�t��O<Q�j���4�y+j%��y�^b�D���B�#��4$Ҧ�Ð&(��BM�t`H'��S�(�m����8>�V~������;��g���Q��a|5o���'�_a���RZ�]� \�t��<6����z[��❓Z��{�M������_}m44d�0�H�c�R���O�	�j�h4o�?�~�֪5L5`|�v4Y�{�_�+�h�E=�$�X龄8���	��:IoWhѢ���V��Uڅ~?"����{xF!xo�-�l�$'�3�) ���3��k$���;P���,sJ�ƒ�<0n8�4�X�pz�l��S=J�l�G]>�&h{��_�6�/��lJٝ����TB���C"���� ��[�����̯�����Bޅs�F(� �>��v���K�Ȍmo供�
��1twI��\��;AXg�+����P�G^::������.�'tZ��Lw��=M��b�Ű_����<8���8ÞC�TM�d��٢�N�myX��cY������Yt�a	d��mV|�)���P�� ��}Hk�D��H`���8��`��V��0���'-�=��c�X���      d   R   x��1
�0�z�y���X��T���Y0`.���;�0���W��ak��",'��v��=��&��~�w:Fh�F�s�s�b      f   �   x�5��
�@E�;#�N,�	*X���E�l���D,s��㖛���	w�@N!8\�5�G���p�3O�^���!�*p�9m��~u�S�:-[o�q�mV��h��+�7�Z���"��UH�¥��{�s� ��@D_f(�      h   �  x�5�Kd!C����?{����sc�DK��j�o����������e�V��9+����y����Xߖ��by�j}�7�L���e��!�6��Tގ�s9p�����i�e��El�Go9&&�x�Á2 w�D�i^�]S����A�ŶdHb����q8oP�Kd<�`"3��ST��/��|��m��^�a,�m�Z�+ew�3[	:������e}�T���J��H��H�o�$F���u<Z)�ʦŖl�jKCr������B���P����?Q�Au�ִєBb9
69���G�s����rlJN:�AH�O��2F-�u*�2MF	�A�a�]�ғ�"�27��Js��:�@���D�T?	F�L�w����Ф��%c·9T��(�aͤ�1���A��޺C��|)�T�e�P�"��8~֮�dR��*�0������ n��x>�_]$WJDݦu�����In��P4�p�V½���c�Nf�9�v�{U~��D!_;v���Ʉ�P7=�+ֵC�60�I��lnw�E7W(�+W�fT,) (*��*�I	�x�K��.�?]���:�r��k���M�?�����6���w�����%㦎o�x�x�
B�w.7��ZW�@�"��hO�"���R�J��}�J}��1Ҋ�B��g?@d��!n�/{�[U|}W�Ֆ �L��|�pU�E�e 8�v��m!j�@��W�Yc�Z0�-4��^^
I�\%h>;Ĉ���)���˩����Bm#[��.'#���'
�a?����O�;Ca�Ӳ��"q��KNǠV�^�&����HLׁ���(�Y��'�X&�䉦�P��剦������J�����aʚ�P�	��6T>�?_zj�A�h�ܩR�%��gK�>e��Q����[������Vy\����Z��5G&      i   a  x�eX[�$�
�v�n$��&f��u\	W���Q>�!N}T>��<�D����������=M��%��̾K���g�Y�|T�i]���.y����|k�����4Y�wהFŚ����?0+}����T������6���6j؃����͕&nd�l�����YC���������
� �(�	aV�f�� d��#��D�|�����uy�e�h�mk6˓b��ObG��:�4n�Zo0[���b� �w���]��83hY���w2HD�a�F,ȮiE�wHӅw��*�++�u��<��2*�������F�-;�?L�b�t	��GY�r�a2���YQ�t��\�n�ѥ�,��E^xV1V���RCw��w\I,�������z���?Yu�x����$`��&�����E¹K{1�"@*x<~��R�Ȕ����q�6��	�Jc܈�cm�p�kdrM�pqGM�aZ>������R}�>�P�6��8 ���ʢɌ+��5�Ga�~6RH'R3�
�ҍ�ͨ��g ���f�n^R��Ը���7�4�&�>�����Q��0j���T��r��r��T.�����UÛ䥔�p;`����!.��V���H5<��t���i���#��TE6���"��F2h���X:�>
A�%�(LX�dnQhz����e�3�Rz�_'A-lK7�3+1��KLI8G�n��A��mFP#v��z�n _2{5�H�������|�>L��G�.+�E4�a��aYX}��;��P��9y��I��4j���D�F/����p�;2��	�XH�]��$K���<��F��al&�6
2j-=s��%Q�����[���,Q��P�*����,�}59�S⤷��߲���{vz?4�M_G|�iT��-�5��qY��3L智���zţR##��S��E�ʗJr�ӷ�hҠ�ψK�.�f�|{*N�ZXR2�|�t��<mK�
�80W�UXO%�Ⓓ�	5��Ჩ�k���w�3�2J��AÍ��<���c.A�n%w>�o����t���B�)��
S�L�]q��V�Su6'܄��0}��i���o)G��@"0lE�����Q\f	�W��E���$� ��V�S��J�Î��Hf;�q�U
�L^�q��z��{�1���F�g5��^G\�V)��T�Q B��	]V�r�ܵ!:Q�=�^�`f�� Ǔ�$�n��J�-�;TX�+>1��`-r�br������2c�@ri,3�k��W�6�$�`��������[��Gc8�I)��؂# o�\�kI������{�e�2x^JiK�s��كOnYU7�4F(�|_҂�D�>৔4Q��܄Q�36zG�^=Yy�b|�U�EN�s�J�!cW�9R�� �_񒪓qv��Rwds`��N���)5�S�zl�G��Iz��;�P��q�
�}P��x�݆#Y/7���b���f=�-M�M�t��;Bn��Pb1�F�-��^��>Qx<N./ܞ��A3�����8˘y�؞�Tv~��O�n�?#Y$�+�龿����_���.d#)�?"��!)����F���I	��;٢���W����ϟ�A=E      k   �   x�-��0C��0�a��?G!�%J�v2�����8PQ�8hSA��]�B�������ޡ�0�A�s6�iL��h�j;כz����<�LZ��IG��e\�j��4m���#�C��I%��5��-%��?��%�      l   �   x�%P�0:�0}A�ݥ��Q�'""A7
\�P����X8��nX�AB��؄8���X�����������/rx�H�^�l���ljR���^�����]�I{	^ר�r����x��I�&Y����uu$Q/�<�Ǜ�K��>���y�^S�����Q�0o(#��՗w�W�alu�� �V�3�     