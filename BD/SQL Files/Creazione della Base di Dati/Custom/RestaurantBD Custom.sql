PGDMP         3                z        
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
       public          postgres    false            �            1255    16401 !   conta_posti_1sala_delete_tavolo()    FUNCTION     9  CREATE FUNCTION public.conta_posti_1sala_delete_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE x integer;
BEGIN

SELECT SUM("Max_Avventori") INTO x FROM "Tavolo"
WHERE "Tavolo"."ID_Sala" = OLD."ID_Sala";

UPDATE "Sala" SET "Capienza" = x
WHERE "Sala"."ID_Sala" = OLD."ID_Sala";
RETURN NEW;
END$$;
 8   DROP FUNCTION public.conta_posti_1sala_delete_tavolo();
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
       public          postgres    false            �            1255    16403 '   conta_posti_2ristorante_delete_tavolo()    FUNCTION     Z  CREATE FUNCTION public.conta_posti_2ristorante_delete_tavolo() RETURNS trigger
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
 >   DROP FUNCTION public.conta_posti_2ristorante_delete_tavolo();
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
    public          postgres    false    209   �       b          0    16415    Cliente 
   TABLE DATA           V   COPY public."Cliente" ("Nome", "Cognome", "Numero_ID_Card", "Numero_Tel") FROM stdin;
    public          postgres    false    211   ,�       c          0    16421    Prenotazione 
   TABLE DATA           L   COPY public."Prenotazione" ("Codice_Prenotazione", "Numero_ID") FROM stdin;
    public          postgres    false    212   ��       d          0    16424 
   Ristorante 
   TABLE DATA           j   COPY public."Ristorante" ("Nome_Ristorante", "ID_Ristorante", "Numero_Camerieri", "Capienza") FROM stdin;
    public          postgres    false    213   }�       f          0    16428    Sala 
   TABLE DATA           f   COPY public."Sala" ("ID_Ristorante", "Numero_Tavoli", "Capienza", "Nome_Sala", "ID_Sala") FROM stdin;
    public          postgres    false    215   ��       h          0    16432    Servizio 
   TABLE DATA           K   COPY public."Servizio" ("ID_Cameriere", "Codice_Prenotazione") FROM stdin;
    public          postgres    false    217   �       i          0    16435    Tavolata 
   TABLE DATA           m   COPY public."Tavolata" ("Data_Arrivo", "Codice_Prenotazione", "Codice_Tavolo", "Numero_Clienti") FROM stdin;
    public          postgres    false    218   (�       k          0    16439    TavoliAdiacenti 
   TABLE DATA           O   COPY public."TavoliAdiacenti" ("ID_Tavolo", "ID_Tavolo_Adiacente") FROM stdin;
    public          postgres    false    220   ��       l          0    16442    Tavolo 
   TABLE DATA           O   COPY public."Tavolo" ("Codice_Tavolo", "Max_Avventori", "ID_Sala") FROM stdin;
    public          postgres    false    221   A�       y           0    0    Cameriere_ID_Cameriere_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public."Cameriere_ID_Cameriere_seq"', 60, true);
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
       public          postgres    false    231    220            �           2620    16478    Prenotazione check_avventori    TRIGGER     �   CREATE TRIGGER check_avventori BEFORE INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.check_max_avventori();
 7   DROP TRIGGER check_avventori ON public."Prenotazione";
       public          postgres    false    212    227            �           2620    16479    Servizio check_servizio    TRIGGER     �   CREATE TRIGGER check_servizio BEFORE INSERT ON public."Servizio" FOR EACH ROW EXECUTE FUNCTION public.check_cameriere_ristorante();
 2   DROP TRIGGER check_servizio ON public."Servizio";
       public          postgres    false    217    225            �           2620    16480 #   TavoliAdiacenti check_stesso_tavolo    TRIGGER     �   CREATE TRIGGER check_stesso_tavolo BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_adiacenza_stesso_tavolo();
 >   DROP TRIGGER check_stesso_tavolo ON public."TavoliAdiacenti";
       public          postgres    false    220    224            �           2620    16481    Tavolata check_tavolo_libero    TRIGGER     y   CREATE TRIGGER check_tavolo_libero BEFORE INSERT ON public."Tavolata" FOR EACH ROW EXECUTE FUNCTION public.check_date();
 7   DROP TRIGGER check_tavolo_libero ON public."Tavolata";
       public          postgres    false    226    218            �           2620    16482    TavoliAdiacenti commutazione    TRIGGER     �   CREATE TRIGGER commutazione AFTER INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.commutazione_adiacenza();
 7   DROP TRIGGER commutazione ON public."TavoliAdiacenti";
       public          postgres    false    220    232            �           2620    16483 &   Tavolo conta_posti_1sala_delete_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_1sala_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_delete_tavolo();
 A   DROP TRIGGER conta_posti_1sala_delete_tavolo ON public."Tavolo";
       public          postgres    false    233    221            �           2620    16484 &   Tavolo conta_posti_1sala_insert_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_1sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_insert_tavolo();
 A   DROP TRIGGER conta_posti_1sala_insert_tavolo ON public."Tavolo";
       public          postgres    false    221    234            �           2620    16485 ,   Tavolo conta_posti_2ristorante_delete_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_2ristorante_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_delete_tavolo();
 G   DROP TRIGGER conta_posti_2ristorante_delete_tavolo ON public."Tavolo";
       public          postgres    false    221    243            �           2620    16486 ,   Tavolo conta_posti_2ristorante_insert_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_2ristorante_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_insert_tavolo();
 G   DROP TRIGGER conta_posti_2ristorante_insert_tavolo ON public."Tavolo";
       public          postgres    false    221    244            �           2620    16487 ,   Cameriere update_ristorante_delete_cameriere    TRIGGER     �   CREATE TRIGGER update_ristorante_delete_cameriere AFTER DELETE ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_delete_cameriere();
 G   DROP TRIGGER update_ristorante_delete_cameriere ON public."Cameriere";
       public          postgres    false    209    245            �           2620    16488 ,   Cameriere update_ristorante_insert_cameriere    TRIGGER     �   CREATE TRIGGER update_ristorante_insert_cameriere AFTER INSERT ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_insert_cameriere();
 G   DROP TRIGGER update_ristorante_insert_cameriere ON public."Cameriere";
       public          postgres    false    209    246            �           2620    16489     Tavolo update_sala_delete_tavolo    TRIGGER     �   CREATE TRIGGER update_sala_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_delete_tavolo();
 ;   DROP TRIGGER update_sala_delete_tavolo ON public."Tavolo";
       public          postgres    false    221    247            �           2620    16490     Tavolo update_sala_insert_tavolo    TRIGGER     �   CREATE TRIGGER update_sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_insert_tavolo();
 ;   DROP TRIGGER update_sala_insert_tavolo ON public."Tavolo";
       public          postgres    false    221    248            �           2620    16491 7   Prenotazione update_tavolata_delete_clienteprenotazione    TRIGGER     �   CREATE TRIGGER update_tavolata_delete_clienteprenotazione AFTER DELETE ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_delete_clienteprenotazione();
 R   DROP TRIGGER update_tavolata_delete_clienteprenotazione ON public."Prenotazione";
       public          postgres    false    212    249            �           2620    16492 7   Prenotazione update_tavolata_insert_clienteprenotazione    TRIGGER     �   CREATE TRIGGER update_tavolata_insert_clienteprenotazione AFTER INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_insert_clienteprenotazione();
 R   DROP TRIGGER update_tavolata_insert_clienteprenotazione ON public."Prenotazione";
       public          postgres    false    212    250            �           2606    16563    Servizio Cameriere_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Servizio"
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
       public          postgres    false    221    220    3513            `   7  x�=TM��:<�~�+l�|�l�W-\r��LY�R��������i�3�==�hk�������2Z��Bm��Z9{���Li�����*�y+�I���L��p���J�J:�����b��竚��H���L*���x�1�xW���%���Г?Dn���+R-�ˎo#���hM-�z���S�,�Å[kh=�{7�9��v�:��}G5=���Rm�@t?�
(���}KڛF1�p�_��Q��9=���-��0�u������M�,'�@��,I�����܊���1��9��<��pMrmm�Lr�T�ћ�O�cj�i�rlΚ�6pcs���������k�*=�'��!�F��5�d-z�(s^"���&K�Ϳ\=��|����Z#� M��{4�%�9't�����X̎^�����S�N�ǱPg�hB@ �1�bB�*s����#Q�J�PZ#�3����0����8��l��w����@���]@�:�1�-�}��ń�W����,	ޛk�LN+��tL���p>˕^A�5	\c)`po[����oo��'������Jsz�d�G�{?�v���{_e.�Q`~��sG{X�N2�+����I���[JI��顕�>�G�$�X�� ���vr�XⷩF�2%�Xѷz@O\���G6���r�u!O'�a��o�ܕR�G|�.���M��~�����w�\������TeA����h�ٚ�ˇ4n�c��2�;�q�_R�J(��8<�.>mR�B���	�I:@dǓ咞��N&r�c
��ɑF����3ӝ�g�`��P�];���J���b��      b      x�E|�v�Ʋ�8���ѽM�DG�DC�zHDI��]�,Y��oo�>��>�Zb��̈�DD�/��Ad�o�� �MT�6�B�����a�+���k��x���MH���^O����n8������8�b=M��G����9�� l��T]q,s�~������>�]{�uTCG8�bj��Uc���z�7�g��=���&GW��ch6�3������q�Ei�+Ӊw��i8��Hs����x���"z`���,����:�aێ�����*�W�������������	�PG�M�Xeõ��"��JTإW�p4�:�4u{Uc���o���rf����aH�64E��*�*��d��&��=��ic
Ñ�iڦ-W�|n��M����>]Qk�.�
og:�cڪ����ڿ������*�Tϴ�^Kai��;�ᨫ�;��>�	�4�?�Xzzjvn�)��Ӱ4��w���u�?}�{�}�%�4j��jZ�e�W�	�1NUة�f� ܁U�8��w�+�D��:�:��ā H4g�M���t����Z_�qQ'�FY��tT|��Hg������Wn���ڤ�N������
>�c�e|{��m�&Ub���,S�TŖ�߰͆��GXA9�:���+��jڪ���G���e��X���8��kĲL[�a��x�����]�U��mh8:��U,�Z=�c�_�*��.�X*�n�kR�,G��U4>x"����p��f��u4�%;����P<̟#�;�_��i�#֙t�z���fHi��r�o|�[�R��qlKs�?�G���:w�� tC�l����a��x����1��1��]a��r[�T;�~GbW\�>���s7�Zd�b }4EuV�4\~�2��Uq�R��TRU��e9�|D0oz�b�%�*U$+����#����dUQƇ���T�0\H?��E�ߗ'���v��ݲ��8&&)6{^��s���&�m��6�0mYh( �������%��f�f�BX�mI���]p�w������(��R��#ͲEA0�׫8"�y>m���:������6޺Ƕ ��	�{}^���,w],[Z�<��"o�i9���%�^�5��h�&}b ��O��lowm%kO8��5��U�>]����z�MHS�BJl�SWV�넠���Ǖ��{e�l)p�:�a�ח�/r��*�|��J�5�a�R���Ml2��qz��je%�X�J�i+�$@"]��w�L����Ƚ�l���Jl^8�y|% ��)��0�H���A��+΀/ə�]�T�]b!��1���E:~������]���k�Ah�ʛ�!W1vn��ӥgĻ���,I�4S�/��x��p�GFa+�8�jC��'�Y��%]���.�u���a� /<��ϟ����-h>F�E�_waR�\��Iik������2	{��A�Y�o���⣫h�z�(`� dYn�;a��ed �~���"�����o�ܗaJ��c3V��9̀Ęhxa�=�@�x���o��"�.���bz�4�<�;���\HH���1�#�n�l��s���\WW�t�/�|l�ޟqr��T)U�m��h��#���희3\�ޅ�G��� ]�����"|�׷�~����RV1��!-��V	X�~�Ňow��Ri�}�B�f*�Z�XGz��/l3W���K@�f�:9~�1�r���-
���VsH�A�&�	���=y�u�c^f��i�y�����ؐ"x��L֊@��A�$Ų��K��o7�@�����0=3���G��X�� #.�g��{�)��6Nn#ñ[F��Ֆ�B!e�^{�]�B 0-U[5��x���@���̇�E�׆nl�`(��n�.����G$��g2���c;�_~#��'�$$�p����(�#t������#~�˝�i U��0�����Ե�bl��i�aS �{�Oc��@$��&��Ɗ��� +T�t�U
iՋ�G��}�ˤ(��8�Z��q$?��RCCQ;3�.H#0�j���D��Z*��'�(AӔ
�*D�B�g����p��RM���<����E��'V�@�tW5sRJ�%D���_��e�V��x<D[ �תi���h����m���x%�$�t�U��HB����2}�6�O��sQ-Ui!��G ��~c���c�OQ(�UB !y�x�3��-B7�(��@%?��B\|���[�u��Aq�F��ӅRk��3˦l��"#�i�KS�pc��jmBZ��,/�@�>Rj��(�쇁�d���$ѷ�{��Q4ѩ��?nKf8[}��C�� ��Ҷ���ٟ^���.N��k�b�4�@\ d���.���sv��h�NC��\m�`���]t����ޙ ��+)���\����o�&�Y������4�3����g�,7�m^ǠM�7@
$�B�#���q�>D����غH(��JA�:ÛУ}�N�_d(S��1dgr�B�<?^_G�[zǚ[R�kx)EBT����I9�ui���"\M��zrV��/��I�%ݨ�B������#Rs���#�C��+�)�3�T�
����0�8=��H�mx��&�}�I��T��zz��a�M�Z��F6���6��!,/E�ص-��@�6yj���ÎZl�'��	�MK���&�qI���!ۯk] �-
�7N{�1���S:�F\!���0:�	9O�I�X���R��㺂H�t�	#� Nf��ur�jp�Y��5����[��
5v�C�7; ����\a�j��wظ�nq�@�ذ�Tux_�5f�r�9^�Ils+6�cW	A���+�*���=C��)ZX|p������חع��&�Q,�M��"�o��u���O�8z�i���p�ݲm�Bf�Å�zd�9�w'��਺ƶ5u�=^)��ܴ�AUũ�3����ͥ�RB�!K�+a��3�ܶ�=%:�p�ޗ����&�2�~
t�X��C=ݡ����<R\�2����@�[`Iu��N!e�_�$�)���B��+��_�.��_��, a׽X�my�/�x*AU��o�}/�>���Өm	���������L��ȭ��yrc�~���!�d��oH��h�����Dt�ɱ���L�����{|�#d'��v���AdH"7��ode��^+a�Nւ� !��Ӑ�XO!.�ͦ	���X N.�5�t_[O�cïM�m��}�U�H�|۞��]��
*���T����DT��(�̃$F���B��.H��q&�xp�H����Hh�y\\�`)���G_ɑ�8N�C�Tė/�yjv��T��z;�������ʥS��K!>!�  `V�D�L��씃���M[��08���.���흏ɠov�"� �x���D򦿽�Fz�F;l�j��0�`p�� ѯWҲ3$��9Z�'62B�&���(��)�����Z�w�!��%�{�.���?0;9p"�ܱ����O�R\Y�R�S~�l���۬b$w~��w�v�keŚ�� �-	��c�cQ� �6<vցp��l>膱��wm���6�͂��Oo�{<ě�&8�������P�f��#W-�4�5D�6~Xmr�N���#�ޝ��� �0���΃�.%0���`�Qf?es�X������!�L�r>/�L�a����:]ql�q���P����/�2���c�%�j;kZ�����,	� t!���I}=\�0F&k)K	���x���ۄe�s�Qa����К�aÂ��FinjBEtC�P�.�d<���,�z�ڻ���\�-}�ܙ� ꤆�ՙ�*�/��������no�V����l�0��?�޹XV�N���mY��0�y�Δ�		N03�8����R_�b �de�Zy�Re�|ُpN�)N�
��I��h��j�����X����/��MU��1ΰ_�,�S��7FT�85�e�ťb1,��>��d�F=�u��C���!A
7_3��N��&���)j&��)��r��;��u    �&f�]7��)8BH�����?����5��M���� �HV2��y�o�n���Md��R Ý	�A>���T�r�lI� ��%%>��H�P���� �n���;�`-�!�\�}����rV׮��P�Hl�
�>Y��3*�CY��p�!��b�,'����Gy�zJ7y�[��D��n��ᯈ	��r/�3��l8;�x��t�a���Z'e�����hG��V//ุ�U}ST���B�k��D	�O��>= Pv�N͜�FXP�g�(h����(ˎ����G:D��Q�)4I�Զt��$	<�B:H�ټ�B�`�	բ��&n��y(��}b ��z�#�S�h4�.�k��U�R:�K��:�//b���Uu�� P�~���쩧~���f�V���p�r_'ߟ��O�)��	H]S����Ƿ7l�0� JS�-�n ���)���Y�I3Su��RX����9PmAO�-��Ee����ۘIq	oY�:�1�줏�wJ�Ӻ��}�8���Uڳh�ù.��Fn�L��7��ܥ�u^��(wN]g�	z�q���{�� w��6�@�k��la՘�
o��/ ��üȋX��SL�?�ݶ�����U��2�����f
�"�p;��s����=l����,��KHOb1������'��X�B*:��Ki��?�"32�xH����H ^̡1=OF�t~!���Hֱ�	���X�ߪ}�f�2�Ns��;(7҄m��L�_'���J�A[��X�v��!�a:�`ٛ�,��%F��D�;,�X�֔�"��������MYs��o���X������Q��^;�ɂ��������$� ��M��Y@��j��/��me7'��A��.a��= f-��7���n#ݏ L5*W �����=��Fj�ƠQ<�tp��E!0Wʹ�b��y��>�����8,��8��N�p���CJt^S��� ��^~.��ؼ�0��amiUw�X�3u�����)u�/�>��z�ҭ����P�?�A� ��Ga��awt��ƞ���d�bW~b�&xD��)ҡ�\Rpp+��~Ytᷨ�J;�ev���ٺC\��b��W�)(��5d�V&d�vRg��.�,��,��8<Z)�Á^�H��k���nb/��t0K����wl�y�<�M�w���l_�����(��6���n[ձ�Mm*x����`5@���x���1(M�3zSp>)iy��D�,�Z
����!�`b@�x��d�j  <n�_�~� �I8U�wEG�b{l1��`M~���1kd��:�lC��,Q �@��|t�N�����Z	�B�ٝY����Vu�T��
Sf�6�Q-��~	�d�"�E!+3U�I&h�m�7m�y�l~Cr[QTƇm��P%�A�����I��'�YqR|U���&(�㕅���U{��T3+ �L���y��y6�)m3R�6JR����@-:����|�`&=�#���R��HMIT���P��U�@�Jm�)��t�g�/�<�?�gK}��D�R�ӑ����V�������u Ui8&֟o��)mj����+�R��Uy{�3��x>�X,0���w���ǀ~�� ������A�Ch�׍��=3t3��e^��=��D��z�GPc҆[ �S����u�/ʍG@@���r����|"�4r�۬�)I_�H��#c �^�ɩ^;^�مKKS�(��	�ݳ5#�f�j�Z	�` |r��b��.vRn58�X���������i��W�m^v:�B�Sb���?q��ؖu�_I�a �z�E��, h����M�8�iԱ:{�����D��9�/wН�XWk�_�:���h�@c�.�d��Oj)��K�;a"�� �O� ��̳5�ڗ�茢���?=]�J}�`����D	�|gZk"$��_u����� l���$Љͨ���ORncʶ4�f�b���.҈h �(�����:ĨQbi�� 7Ȣ���q�<���ŧ��Ƿ,8�/(�G�U��C�um��z�®��!������>�s�s��;'��SE �l ��B%�|N�'q�bs�*d�ҁU�~��b����tUy�d�A�6
�b�"~�9>�*r���S+a�J��@��g���FDQ�*_�q"l�Y ]�{-�'�t-�\���Y�C��#����_6z�q4%�&"*C[��7@��sB�5��ڰ��XI0W����j����~zQYV���Ĭa@?�IB=GM6=!�X[�V�Ξ-'*m"x|]�~YM�����%irh �M0������7�u�����B��P��4��f��ck��F5��¡!�B�m6Jc�����E.ȇ�/J���,���Z5�q�R8����$��L����B��Kom8�M,��@v2�1��R��E� �J��zl}AԘ�-��;�W�l����ܪ-�a��q4 ֱ狹f�����E�f�P�����)p!p�W��Y]�:�t�����ʍBě��hh��fǻ�r�ԎY&���o ��	J�% ��$���~De�)e��� k8��X��f�-��z`V��6:B�B�0Ηf�/��$M�M�Q�n�
�l��m�+,G�'G�Q{I��3�X@���_�����?�8�Ma�5P�`;��-H�#�����Y'��o7��&�@���W���H�wը�h<P���8	�p<<��.�lT��1T����Y�a;�t�g�,�*�
�&��=,���.w��H���"�:�@�	��
f��?}��]��J=SH�i:*�w����g@�$dWj�^7aY<�0.X��lwu����X�m�Ǘ��b��Zf�;�r�I���W�I
]�B��S�6p.�Bf0�xA���N�z�����*���4>�hT$�*��aq���ᴵ��	��8p9����W��'~�4�t�5��Yz�P����l������UT�lgs�G�p��?��0\�.O��I$A��Zg�4��^vzb�J�֎cH�K�Yr���󳊢Յl4m�k�C�/aZh2q���s`yBVe�%zC3�wKK�|�_pâ6��.T�#dA4��Q����p �"�(9"��t��p��[$��䟍SV�e��82P)0�;�pHF���62s,O�n���8v�W�Yi��"�4�v�5Kc���[�~�H?����U�����,�i�<%l��O��'�~�Yuk;�@&W��\fR8wz�n�4^CJ���
�b�4(Y�����0Sh��C�h�l���ѹ��Ư/�R%��/cO`A:�����=��I���T�W$��/��6�3���x[��ɬ(����7\~s���x�#��1� u�7�<ٲ��ĝCz�����Jlb�q�O��LyV�ܮH6Y+� I$R	@�=��F���Di�����۬��6�
�ꚶ�󇣽�t�[��Ȉ��CD�]X`������v���.I5ڢ�^?���i-���E�-6e�	vU8M��`��:$	V���Ȏ�*T-��,ZǑ��]���~�!mt�G��\����ԋÁ�I����YȠ�!V�e��{��i�@���
��2
����3N�)����w���*���3� ;̊�"�g�i_�^���4-l��\����.���k���g5�V����!n�ERԦ^�(,3A&p8J��q{��(�40WU9I�$Th��a1�=�����ȫ�R7T�C���G�p����V�Y�t=Y~�Tvŗ�7d�[ԍ�n;,iZp�@���2��y^�� ��:<�:S*CǕ�է!ꗁ�מB:��P���Ȫ��qs�9��n�^&Й���w^a�&;
\����9�v�E^��Ҝ�M�@��CφY8����4� A�B��_��!�l��Ȏ�h��p�� �@Щ�GDCv���/dAyܥ��4��'%@�bbg��i��AI��c4x�Ѫ|VtӁ<4@�ý�u n  �5��΂��S�3�*�荊C]g��Xz%`;���l�D��}\΢�OZ��E*hT�����2��P���S[�F��,�a�C��p�ar�鿐��_��"����b�*�*vg�g�)۟�ns�;Уm�H3�Z?���~�x���#����Ħ4�g��7{�UvTS�ݥ�3+��?CZ�p}{�G:[O6�Ɗq�P�@j�f��m�3�����i���t��ܞZ}Xf� �6�m����gk��tmߡ09Y��+ʒb����u��*_��Ke�e�ytM�֩�yeӉr���1�As�lN���q�g�%����Ϝ��=`��î+��@�;��j���P�m7(��.0r;Ql��Z���(f��s�B�Iq�<K����X^��L?Xj�ڹe�c�������2F���K�uzUz�h�E;��>����X��Ɋ��8�zjUB$�2cK�ʺ����q����m�	��EY`x&|�䫓EJ��{��y �������Wi�ͳhbg����jk�ǭ�q~}'�M"n��H�,��^����7��E��C��sWe��v�p�z�H�AM?�tz�0�>��Rg�:ʡ[Ý[�K�߻,�A{���7�Tz&�������b#2�]Ȃ��+�=>����2��
i%��Dְ����|����MӔv��GTHF9@!�eN,��K8��$k-*�]ˀ{`��� !�� �g�x{Ŕ�j�����p�}|R���{�rHk�=���/x%C�D�8�A\p����p�؋�:��:�>����,ItC�!�۹�A��������$�׺&��0�c^� ��.?���M�.aC����t�Ho5���>�Ap
@�"��l	A8�p|~a�:k�)��5�H�[�V���1��7�#��d�⥄��˳��w����:��©�r�1
KNh!�DRō��&(q%$*4}��ێ8a��<�-��b�'����H�{{c�H�v����+v8I����p�]���u�i�fi˰#���2W1RY��u���㠉�(19��*H���eZ�l��0�x/tM�����E�o2��V3�ą��&0�	��,5�wf����Z�)�	˕}�\��]|$}f�hI�]/�R z�����Q#Ì]qF�gDݶ@�.���.�~|�3��	&�E�lOz�QHKS�h���(3g���hS�&�]mSf���1GUT�L���A}l�R2朅X�������b�qc��ؚ��w�	��r�n]�7ϩ�Ĵv�#����ņ�+~��<��ť�UD��8��q\^�����hN�Qe@�fk��t�ǥ'���&6۴���Z��I��-]�y��zya).mY嚃̖�a�0�|^v��鼌�m�_�>�;k96�

�T~�]8�`���������/R���;,lg�Mծ;�k�,C��gӍs��eq)�k�����D�J��4�8��ȱ������;'^�[U�q)ee]].n��$,����hu�����o�n��[�����k��H����2��Y��`�yP�W2X��'�|.��f�2�©��@6���y��WX���)sKs��W�=5T���v�����<��`��d#�ݼ"尅 خr�cLF���z�C�J싪u�M�%������?Ї7�A[e5��v��ac���r����kW�Z�����NvV!!��/�t~ƨg������-Ͱ8��~{��L�F��X�Fi�9�a���ѥ��Ł%�uZ�Q�Q�)�,5UE��Dc�U�W<r�����t��B����� z.��!�v�o� �p �N�~�@��~���D�m�{z}�� �x+W�h�_�	�cS�6uc �e�RmbAa;O/����j�Ba��{���)�b�͓�$z��`��D�W��1!�XR!��[�ݪ�~�X&����R?���I曐�:�5H���.��9,��1��^�Ufw���,�J�
�Z��,L-(��.�n�m�؃a�ݶUe��8|}]�l�Ja�i4N�"�<��ީV�	g��M�o��bT	�����α|�*���,�@p��b;D�ϭ��?�\���m��^[�^�Rn���/P�޾8�䔬����{G#6;y��̺�B�(�c�p�#���U���@�Y���8�����G>SY���P'/��"� ����E�6)G]�T0���@�]i�y �q�j�\��ۖOwi��&�#�`���(�i����s:��Zk�rf��r<ɽ�)>���mp��X��؋ܷ����	 �]���Dq*�����@yA�/�t��/�8E���l������rKZ����v��P2,���b�E��h|�JYW�E�T	�\����� �� �\&Ncǡ�}ދt�?�o@j���4O'o/��
9����c����]�T-�	�N,
���8�y���N���2Q�푒�-p�`
=g@X�����b��4�$���7�l�U�&H7Ev���Q9�#9(��?.w���O\7Ap�*����	��Ҟ�?.ӷț�Ï]�R����Ts�:Ǳ��&���"8u$m��Yҿr\hM���]�f�ʊ���!���<:���r�@3%j��f��u��a���	nV�޽y�٤�_�q'am�N��Ol"tg�rI��4	L������a��ސ7�w�?Q��A]/�+�5���[K�d p�a���Pڪ ��������oS�ך`�̫V��=.�̧�h��--=o�ab����������E|��X�yg�5��,ց���[�0k��8�V9��G�;p�)��,��8���A�
�P�d�q���]���(D�7�)/����#�9��A;��¶�Y�bx^�Mv�k.p�^^zhyTe�D�]@i�6V�	�_����U��%�C�b��͌�?��c/,��"���#Md3�0a୤kÿt�K#��V�5/���r����[6��ީ�%�/X���?�j��<�w 
[��� ��:��/}�d�>����)
���Z.���1c!Te^�kv�ɗ�!d�������v���d��-����l.�|��[� K�jMps�+!,G^,>AA����]��3����ރw���/��F�X�b�=k��K�����m>�]w�)���9§q@�dԽ���;�G���)�S�gj��)-zU�K�?��/��M��ښp>��mLd���S��1xB��,P�֚���R�N��\�>�p��I��y��b!�����_1ވǞ�6����Yjx^ؾ/�=�Q�'�U᭭}��6oh�W�$/����Tes2|c[�`XQ�(�`aV�߻��[5ola#|6vlޕy�TjzY~U��Vw������	Y�2{�`CqNʡ֝��@�:o�&T��s�M�m�(ךk1 ����xX�u�$���p����� �HSx�j�ɫ��+�Sc9���@�ѿ%�31ŴDryv��D��L��PY\_�O~��T�U������P[��������˱\��}'Q�N��j���kN�< �����Ҹ�y����w���MԳ(���n�����oP9�ڌ7N�{���r�n��zӹ�O�S�lL�����Q�M��$�)/Z��,�~^^D�Yz����^U�����j�� �5'%      c   �  x�MVI��(\�a:�����}�st��������JB��8��� ��w�k_�M#ϔn]A8E!��mi)B)Y���_P�q�r�&o�"(ۡ��\
�P���D�ڂ�n���2�M���Х����m{�rO��}��H��AQ�{��_��v�`p�^�b�
"%J��dqWp�nW/���
^�h���#�B4���}��HN��v��Bp�K�yVU!	*���a�WA�ACn{�κ,�A~qYc+)\��l�IS��j�.Sn��҂���U�qq j�!I��j�c�mq`�,�Bs���i�>*h��ܒq��<�"+�$���Ṯ��Hk
AQ�GV92
����1����2����6��:�2�=��X�Kyz�
Ɛ"�%�_�{w�c�q�� -�v'�S����ې"�v�,'���ih�J4q��y
佣�um�`>�!>���� �Q��Ұ��SPѶ���ڦ��XC+�1MG��0);v&�-̑!]
�E�� ��6��(c����}�9P.�/ZړTT��\��15�aH�5�z�r�t�ƨ�3����Ԇ�0����2Gh 3Ȏ�m�g!b}�ϕ��(Z����������L؜������%ԏ�@��=x
x�)?�ɰ*3�`��qVp\c���c?_���j��g����_��˾��M���*d���<��[q���:<��g�ύ1����,Y⭾�~vG��H�ڱ�{��M%Y�2������m�=��g�R!��S�Q�p�+Q���!)J��tng/�#s����ȕo�i��m�p��4
����{� ��(��+�G�F��:vM|qp�F1���ƕ�ulq���-����<�Z(

����q���'�peu��[A�@�f���r啕W��:�=��C$@g`�m�^�$�JbօB�	nR��������:���ii�;��� �_����5�N� F��89�[}'F��L7�K}�U������&H�d�����e�K�O�G���F�$�z�V�3"0꺤B?f�#U7�7J緓�P&��+R]M��cZd�V�4 �4 j�Jq6���p�4� I�
�&���ć �ԗ��0>�A8�����6��ݦ( c��R����#�v�B,B�F��8�s���ظ(�
>z�}��tCUB����N|lڹź\a̠u_��o�BPbe��+��.��>G:
���<�/����v�� ��s=���� F�Nݩ�.��%7;��0��h7>�%c��E2 HN��t�5��L�Mۃ����l��_>��\S�kP�P�,�)��q�_# }6�t�l��a�x��љD�Mb?��f�S�AG{����ȏENy��c�����j�t
�,�˟'��n�}<]	�vd)ݞ`��P���Q�)PD:'�����R��f7��p�w<�OQ�Ӧ�$�>ddow�慭$d�S��R� �\����j{�<-��#|3&`�����	Ds��t�������b ���\�2���н]�a���L�0�����,g���!���W�;�%�9��O���M>a�rnS��a�v���>�gK�� J�
�9ɴ���8>iA|,
�!�+��wXEO��?}������+�Dk@$�E��,o	i�w��O����GH)0+��qm8�����������'(�ӋnzHǼ�M^W��{#��Y�����=���k ��;.R��d�5h�V�j �l5�b�Fxu����X�a���ܿ㈮ܮ�|6j���V}�����!�:	o��i�������)е
�����U�p��# p��	)���!������Ar~|�$9����r��4��D��N��� �_.�����dR<��I󠭑�~��<�pﳮDw��#�_Lv�G�n"�a�t��v6d�f�-7;�f0IL�~���/�K
��q~p�t�����S��/�      d   R   x��1
�0�z�y�x���-U,,mX�����Yi7�iH�	�G��:��:��	���p�+�1\�V����� g'"?RCh      f   �   x�5��
�@E�;#�N,�	*X���E�l���D,s��㖛���	w�@N!8\�5�G���p�3O�^���!�*p�9m��~u�S�:-[o�q�mV��h��+�7�Z���"��UH�¥��{�s� ��@D_f(�      h   �  x�5�K��8C��`j��˛�8jK��Ib$!p�m���_�+�9[f����z��sp>��c|�|>�9v;$��OE��(_����r}a�����ݸ���"K'�ﶣb�#4�B��6hoF]��4:����W��%"�G���l�N��%zB ���Z8zL�ɩ=�QmO���Ț�m�KI�?����ö�x����d�ev��g-@!�Z!ɊB�Ϯ!B�ܕDCK�
{�[.0�t�����̉��!�=V������K��׷�y<#��v�J��
:Xv8߂k����%�z!c��B���]m��w����Y�dJ�1��a���=4��cIS�,YXh�%P�޲��
N��i	���X[�I��NG��آ�%'3�z)+��h�(Y�D�ٲmn����x�����*��5Վ`��H�$��C�u�-́�c��@w7������4ڶ��
�U���l�J8���,	��^�yه�ǫ���q�ߘ	�~�$�;ݖ�[V��y�Ն�fj:T騑9��L�^�������]��x����>�d���ah�V�|�味��n2�ͮz$ �W�G�;��	�F�z�P�������b�A��N�W;.\� ��AUQ\a:�UF���\�u���Ӎ�e�5˭���嫞zpU$�Y��0���GP���t�t�G�ŗ�j�@dĩ+�t�/�<.Ns��{��ѡ�H�:�K.6��3����C+�[0�V��^ftD:^ٔA�F�����㯢�?E�J%�b-�ҊU�k@���G�Pb��QV��ݿp��K�A�&� �z��]�r(�*x.��
^�W��0U�D���']`�Q���yz-Oy�
�v�8�*�^a�B�2�Ns�;c�f��d�7�y��>��x�֫�,1�T]D��0��g$U��v���}��?��Hg      i   f  x�eXk&7����W��=A���d�0��Zi�y�m�iU����]�����U�W�WGE�%�ª�U�bI�Rvirwm�Zl�m�'Xk��Ͼ]d��YS� V4�œ��J[w_��,�E�0��7����s^؃ӑ�2Û�>q2�b�Q�g����9d����f��3X,=*����'�I!��sȎ��)�V���NȳL�Go��\��*�֋c.sҸk�,�l�U$��[� 8�퓭�"��L�˂�u�E�JD>�4�JD	l�� ��4�ʇcpwp�C��Y#H06���!�,�'s�$����C��+g{F@#�t���nHx}��eѲ�&i���J	�<�⹀ݖ3bH��ӓb�OS����Y*��s�;�$`�6�
l-���d&<ԑ��$��x@r�>����E¹Ra{��"@JxT�sx��$��q:����~I�fm�Fԅl+�M�Qޙ^��B��|y�B���q�T�Ы���+�`q3Ȯ2ґ�{?��	����7�T�u�!�2Z�(�w!�U���U�+#yڂ�.�W�lO���%Y�Q��
������I�+�W/�{�c�����/g��#w��t�G.���h�����gQ���V��Y;EJ/R>-7Cp��W>�{��m��k{o�]"�d~i�KB&������Ax7�V1"��Ȇ���K��Pno���T"Q�h[�O�	-N10x�~�`��eB�T� b[Ϡa ��=U�/@���H�E�j8V8�zsgZ���W��*	)�Aj�5�פO����E
G^l�+��ვjY3J�aDc�Ok�>4z;�cSx��8���'�3�Tn�H>��Z�p��d, �)���7i,�c4t������`OwB�9��S�2����ޤ6%9f0������f�ө��%Q���|��%�g}��
a�� ����QI,�=��D��bC�(pyD(gh}��W� ���*Mhf��Ԛ�Z�D��Q��*y�%�d�;p �)T��k.��%��5�`�5&��eC#Vw1R��S�	�|�YʩO�0�O�H2sG.��f�;�X�"��m7~$||i;�T��g������Ӯ��+h�d��|���!���ID ��0y2�m�r���hM8��8H�����rՁ7���g������]@�|��<�֭�:8X���t��.cF���[{cJ���D4S�)�̫v&Cy&'�uh��5@tW��U�@�0fP��E@�X;9�K^ن��"r�����Z:�Y50�`�2|k�?(�n�s�r���|�Y5U��3@��&N:�B��Pv�N%Oc���^ɛ��	�5�X�u�#?${�M�?:�L�'��N�b��;{�^�m˰p�v\���3+?廍E��HJB�H�������ϒtI�p�M�o��YŔ;;+��7R�)1�i�������*��=Z�<��,�bE�9�[IRa��7�"I�Qf�=��\k��J\-s)�c��ǻG�>}���ʣCgoS ��6R�ז�#���=�,���k����_�3Z)��\/��G�1�{#��3F��|E��"��;MVf����7�`��9�Qo���w%�9�����3�`ʰ��&?J���|��xV8���vM��~������ϟ@=�      k   �   x�-��@B�PLfQ��K��#����' V�k����K]�fB�ZH�P,�����
:ԁ�qX��N��f�S9����iٗj�F_Mo����:9+667b?�hz�qq��X'��9ꅬ�-���;��'���^����%�      l   �   x�%P�0��a��c��?G�~Q�v-�mi�l��	,�Pwl��c�	�i��s$�p'Т]p�� GK���f��l���A,�������F�����[>���G.��tdъNDֿ1�����#��� ���٣f-���9����R�P˥U�5u�&���F��7�[��I�3�     