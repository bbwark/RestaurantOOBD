PGDMP          3        	        z           RestaurantBD    14.1    14.1 `    f           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            g           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            h           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            i           1262    16395    RestaurantBD    DATABASE     j   CREATE DATABASE "RestaurantBD" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "RestaurantBD";
                postgres    false            �            1255    58262    check_adiacenza_stesso_tavolo()    FUNCTION        CREATE FUNCTION public.check_adiacenza_stesso_tavolo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
IF NEW."ID_Tavolo" = NEW."ID_Tavolo_Adiacente" THEN
RAISE EXCEPTION 'Un tavolo non può essere adiacente a sè stesso.';
END IF;

RETURN NEW;
END$$;
 6   DROP FUNCTION public.check_adiacenza_stesso_tavolo();
       public          postgres    false            �            1255    58285    check_cameriere_ristorante()    FUNCTION     �  CREATE FUNCTION public.check_cameriere_ristorante() RETURNS trigger
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
       public          postgres    false            �            1255    41388    check_date()    FUNCTION     v  CREATE FUNCTION public.check_date() RETURNS trigger
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
       public          postgres    false            �            1255    49595    check_max_avventori()    FUNCTION       CREATE FUNCTION public.check_max_avventori() RETURNS trigger
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
       public          postgres    false            �            1255    49599    check_tavoli_sale()    FUNCTION     �  CREATE FUNCTION public.check_tavoli_sale() RETURNS trigger
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
       public          postgres    false            �            1255    58137    commutazione_adiacenza()    FUNCTION     
  CREATE FUNCTION public.commutazione_adiacenza() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
	
INSERT INTO "TavoliAdiacenti" ("ID_Tavolo_Adiacente", "ID_Tavolo") 
VALUES (NEW."ID_Tavolo", NEW."ID_Tavolo_Adiacente") ON CONFLICT DO NOTHING;

RETURN NEW;
END$$;
 /   DROP FUNCTION public.commutazione_adiacenza();
       public          postgres    false            �            1255    58297 !   conta_posti_1sala_delete_tavolo()    FUNCTION     9  CREATE FUNCTION public.conta_posti_1sala_delete_tavolo() RETURNS trigger
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
       public          postgres    false            �            1255    58296 !   conta_posti_1sala_insert_tavolo()    FUNCTION     9  CREATE FUNCTION public.conta_posti_1sala_insert_tavolo() RETURNS trigger
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
       public          postgres    false            �            1255    58301 '   conta_posti_2ristorante_delete_tavolo()    FUNCTION     Z  CREATE FUNCTION public.conta_posti_2ristorante_delete_tavolo() RETURNS trigger
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
       public          postgres    false            �            1255    58300 '   conta_posti_2ristorante_insert_tavolo()    FUNCTION     Z  CREATE FUNCTION public.conta_posti_2ristorante_insert_tavolo() RETURNS trigger
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
       public          postgres    false            �            1255    58288 $   update_ristorante_delete_cameriere()    FUNCTION     b  CREATE FUNCTION public.update_ristorante_delete_cameriere() RETURNS trigger
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
       public          postgres    false            �            1255    58287 $   update_ristorante_insert_cameriere()    FUNCTION     b  CREATE FUNCTION public.update_ristorante_insert_cameriere() RETURNS trigger
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
       public          postgres    false            �            1255    58271    update_sala_delete_tavolo()    FUNCTION     ,  CREATE FUNCTION public.update_sala_delete_tavolo() RETURNS trigger
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
       public          postgres    false            �            1255    58270    update_sala_insert_tavolo()    FUNCTION     ,  CREATE FUNCTION public.update_sala_insert_tavolo() RETURNS trigger
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
       public          postgres    false            �            1255    58305 ,   update_tavolata_delete_clienteprenotazione()    FUNCTION     �  CREATE FUNCTION public.update_tavolata_delete_clienteprenotazione() RETURNS trigger
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
       public          postgres    false            �            1255    58304 ,   update_tavolata_insert_clienteprenotazione()    FUNCTION     �  CREATE FUNCTION public.update_tavolata_insert_clienteprenotazione() RETURNS trigger
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
       public          postgres    false            �            1259    33195 	   Cameriere    TABLE     �   CREATE TABLE public."Cameriere" (
    "ID_Cameriere" integer NOT NULL,
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "ID_Ristorante" integer NOT NULL,
    "Stipendio_Orario" integer
);
    DROP TABLE public."Cameriere";
       public         heap    postgres    false            �            1259    33194    Cameriere_ID_Cameriere_seq    SEQUENCE     �   CREATE SEQUENCE public."Cameriere_ID_Cameriere_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public."Cameriere_ID_Cameriere_seq";
       public          postgres    false    218            j           0    0    Cameriere_ID_Cameriere_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public."Cameriere_ID_Cameriere_seq" OWNED BY public."Cameriere"."ID_Cameriere";
          public          postgres    false    217            �            1259    16441    Cliente    TABLE     �   CREATE TABLE public."Cliente" (
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "Numero_ID_Card" character(9) NOT NULL,
    "Numero_Tel" text,
    CONSTRAINT ck_only_numbers CHECK (("Numero_Tel" !~~ '%[^0-9]%'::text))
);
    DROP TABLE public."Cliente";
       public         heap    postgres    false            �            1259    49573    Prenotazione    TABLE     z   CREATE TABLE public."Prenotazione" (
    "Codice_Prenotazione" integer NOT NULL,
    "Numero_ID" character(9) NOT NULL
);
 "   DROP TABLE public."Prenotazione";
       public         heap    postgres    false            �            1259    16397 
   Ristorante    TABLE     �   CREATE TABLE public."Ristorante" (
    "Nome_Ristorante" name NOT NULL,
    "ID_Ristorante" integer NOT NULL,
    "Numero_Camerieri" integer,
    "Capienza" integer
);
     DROP TABLE public."Ristorante";
       public         heap    postgres    false            �            1259    16396    Ristorante_ID_Ristorante_seq    SEQUENCE     �   CREATE SEQUENCE public."Ristorante_ID_Ristorante_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public."Ristorante_ID_Ristorante_seq";
       public          postgres    false    210            k           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public."Ristorante_ID_Ristorante_seq" OWNED BY public."Ristorante"."ID_Ristorante";
          public          postgres    false    209            �            1259    16403    Sala    TABLE     �   CREATE TABLE public."Sala" (
    "ID_Ristorante" integer NOT NULL,
    "Numero_Tavoli" integer,
    "Capienza" integer,
    "Nome_Sala" name NOT NULL,
    "ID_Sala" integer NOT NULL
);
    DROP TABLE public."Sala";
       public         heap    postgres    false            �            1259    58330    Sala_ID_Sala_seq    SEQUENCE     �   CREATE SEQUENCE public."Sala_ID_Sala_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."Sala_ID_Sala_seq";
       public          postgres    false    211            l           0    0    Sala_ID_Sala_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public."Sala_ID_Sala_seq" OWNED BY public."Sala"."ID_Sala";
          public          postgres    false    222            �            1259    33202    Servizio    TABLE     t   CREATE TABLE public."Servizio" (
    "ID_Cameriere" integer NOT NULL,
    "Codice_Prenotazione" integer NOT NULL
);
    DROP TABLE public."Servizio";
       public         heap    postgres    false            �            1259    16428    Tavolata    TABLE     �   CREATE TABLE public."Tavolata" (
    "Data_Arrivo" date NOT NULL,
    "Codice_Prenotazione" integer NOT NULL,
    "Codice_Tavolo" integer NOT NULL,
    "Numero_Clienti" integer
);
    DROP TABLE public."Tavolata";
       public         heap    postgres    false            �            1259    16427     Tavolata_Codice_Prenotazione_seq    SEQUENCE     �   CREATE SEQUENCE public."Tavolata_Codice_Prenotazione_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE public."Tavolata_Codice_Prenotazione_seq";
       public          postgres    false    215            m           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public."Tavolata_Codice_Prenotazione_seq" OWNED BY public."Tavolata"."Codice_Prenotazione";
          public          postgres    false    214            �            1259    33218    TavoliAdiacenti    TABLE     x   CREATE TABLE public."TavoliAdiacenti" (
    "ID_Tavolo" integer NOT NULL,
    "ID_Tavolo_Adiacente" integer NOT NULL
);
 %   DROP TABLE public."TavoliAdiacenti";
       public         heap    postgres    false            �            1259    16414    Tavolo    TABLE     �   CREATE TABLE public."Tavolo" (
    "Codice_Tavolo" integer NOT NULL,
    "Max_Avventori" integer NOT NULL,
    "ID_Sala" integer NOT NULL,
    CONSTRAINT avventori_positivo CHECK (("Max_Avventori" > 0))
);
    DROP TABLE public."Tavolo";
       public         heap    postgres    false            �            1259    16413    Tavolo_Codice_Tavolo_seq    SEQUENCE     �   CREATE SEQUENCE public."Tavolo_Codice_Tavolo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public."Tavolo_Codice_Tavolo_seq";
       public          postgres    false    213            n           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public."Tavolo_Codice_Tavolo_seq" OWNED BY public."Tavolo"."Codice_Tavolo";
          public          postgres    false    212            �            1259    58347    prenotazione_ristorante    VIEW     �  CREATE VIEW public.prenotazione_ristorante AS
 SELECT "Ristorante"."ID_Ristorante",
    "Tavolata"."Codice_Prenotazione"
   FROM (((public."Ristorante"
     JOIN public."Sala" ON (("Ristorante"."ID_Ristorante" = "Sala"."ID_Ristorante")))
     JOIN public."Tavolo" ON (("Sala"."ID_Sala" = "Tavolo"."ID_Sala")))
     JOIN public."Tavolata" ON (("Tavolo"."Codice_Tavolo" = "Tavolata"."Codice_Tavolo")));
 *   DROP VIEW public.prenotazione_ristorante;
       public          postgres    false    213    215    211    211    210    215    213            �           2604    58141    Cameriere ID_Cameriere    DEFAULT     �   ALTER TABLE ONLY public."Cameriere" ALTER COLUMN "ID_Cameriere" SET DEFAULT nextval('public."Cameriere_ID_Cameriere_seq"'::regclass);
 I   ALTER TABLE public."Cameriere" ALTER COLUMN "ID_Cameriere" DROP DEFAULT;
       public          postgres    false    217    218    218            �           2604    58142    Ristorante ID_Ristorante    DEFAULT     �   ALTER TABLE ONLY public."Ristorante" ALTER COLUMN "ID_Ristorante" SET DEFAULT nextval('public."Ristorante_ID_Ristorante_seq"'::regclass);
 K   ALTER TABLE public."Ristorante" ALTER COLUMN "ID_Ristorante" DROP DEFAULT;
       public          postgres    false    209    210    210            �           2604    58331    Sala ID_Sala    DEFAULT     r   ALTER TABLE ONLY public."Sala" ALTER COLUMN "ID_Sala" SET DEFAULT nextval('public."Sala_ID_Sala_seq"'::regclass);
 ?   ALTER TABLE public."Sala" ALTER COLUMN "ID_Sala" DROP DEFAULT;
       public          postgres    false    222    211            �           2604    58143    Tavolata Codice_Prenotazione    DEFAULT     �   ALTER TABLE ONLY public."Tavolata" ALTER COLUMN "Codice_Prenotazione" SET DEFAULT nextval('public."Tavolata_Codice_Prenotazione_seq"'::regclass);
 O   ALTER TABLE public."Tavolata" ALTER COLUMN "Codice_Prenotazione" DROP DEFAULT;
       public          postgres    false    214    215    215            �           2604    58144    Tavolo Codice_Tavolo    DEFAULT     �   ALTER TABLE ONLY public."Tavolo" ALTER COLUMN "Codice_Tavolo" SET DEFAULT nextval('public."Tavolo_Codice_Tavolo_seq"'::regclass);
 G   ALTER TABLE public."Tavolo" ALTER COLUMN "Codice_Tavolo" DROP DEFAULT;
       public          postgres    false    213    212    213            _          0    33195 	   Cameriere 
   TABLE DATA           m   COPY public."Cameriere" ("ID_Cameriere", "Nome", "Cognome", "ID_Ristorante", "Stipendio_Orario") FROM stdin;
    public          postgres    false    218   ��       ]          0    16441    Cliente 
   TABLE DATA           V   COPY public."Cliente" ("Nome", "Cognome", "Numero_ID_Card", "Numero_Tel") FROM stdin;
    public          postgres    false    216   ��       b          0    49573    Prenotazione 
   TABLE DATA           L   COPY public."Prenotazione" ("Codice_Prenotazione", "Numero_ID") FROM stdin;
    public          postgres    false    221   ɏ       W          0    16397 
   Ristorante 
   TABLE DATA           j   COPY public."Ristorante" ("Nome_Ristorante", "ID_Ristorante", "Numero_Camerieri", "Capienza") FROM stdin;
    public          postgres    false    210   �       X          0    16403    Sala 
   TABLE DATA           f   COPY public."Sala" ("ID_Ristorante", "Numero_Tavoli", "Capienza", "Nome_Sala", "ID_Sala") FROM stdin;
    public          postgres    false    211   �       `          0    33202    Servizio 
   TABLE DATA           K   COPY public."Servizio" ("ID_Cameriere", "Codice_Prenotazione") FROM stdin;
    public          postgres    false    219    �       \          0    16428    Tavolata 
   TABLE DATA           m   COPY public."Tavolata" ("Data_Arrivo", "Codice_Prenotazione", "Codice_Tavolo", "Numero_Clienti") FROM stdin;
    public          postgres    false    215   =�       a          0    33218    TavoliAdiacenti 
   TABLE DATA           O   COPY public."TavoliAdiacenti" ("ID_Tavolo", "ID_Tavolo_Adiacente") FROM stdin;
    public          postgres    false    220   Z�       Z          0    16414    Tavolo 
   TABLE DATA           O   COPY public."Tavolo" ("Codice_Tavolo", "Max_Avventori", "ID_Sala") FROM stdin;
    public          postgres    false    213   w�       o           0    0    Cameriere_ID_Cameriere_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public."Cameriere_ID_Cameriere_seq"', 1, false);
          public          postgres    false    217            p           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public."Ristorante_ID_Ristorante_seq"', 1, false);
          public          postgres    false    209            q           0    0    Sala_ID_Sala_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public."Sala_ID_Sala_seq"', 1, false);
          public          postgres    false    222            r           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public."Tavolata_Codice_Prenotazione_seq"', 1, false);
          public          postgres    false    214            s           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public."Tavolo_Codice_Tavolo_seq"', 1, false);
          public          postgres    false    212            �           2606    33200    Cameriere Cameriere_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_pkey" PRIMARY KEY ("ID_Cameriere");
 F   ALTER TABLE ONLY public."Cameriere" DROP CONSTRAINT "Cameriere_pkey";
       public            postgres    false    218            �           2606    16447    Cliente Cliente_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT "Cliente_pkey" PRIMARY KEY ("Numero_ID_Card");
 B   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT "Cliente_pkey";
       public            postgres    false    216            �           2606    16402    Ristorante Ristorante_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT "Ristorante_pkey" PRIMARY KEY ("ID_Ristorante");
 H   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT "Ristorante_pkey";
       public            postgres    false    210            �           2606    58336    Sala Sala_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "Sala_pkey" PRIMARY KEY ("ID_Sala");
 <   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "Sala_pkey";
       public            postgres    false    211            �           2606    49602    Servizio Servizio_pkey 
   CONSTRAINT     {   ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Servizio_pkey" PRIMARY KEY ("ID_Cameriere", "Codice_Prenotazione");
 D   ALTER TABLE ONLY public."Servizio" DROP CONSTRAINT "Servizio_pkey";
       public            postgres    false    219    219            �           2606    16435    Tavolata Tavolata_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Tavolata_pkey" PRIMARY KEY ("Codice_Prenotazione");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Tavolata_pkey";
       public            postgres    false    215            �           2606    49604 $   TavoliAdiacenti TavoliAdiacenti_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "TavoliAdiacenti_pkey" PRIMARY KEY ("ID_Tavolo", "ID_Tavolo_Adiacente");
 R   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "TavoliAdiacenti_pkey";
       public            postgres    false    220    220            �           2606    16421    Tavolo Tavolo_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Tavolo_pkey" PRIMARY KEY ("Codice_Tavolo");
 @   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "Tavolo_pkey";
       public            postgres    false    213            �           2606    58351    Cliente ck_lunghezza_id    CHECK CONSTRAINT     r   ALTER TABLE public."Cliente"
    ADD CONSTRAINT ck_lunghezza_id CHECK ((length("Numero_ID_Card") = 9)) NOT VALID;
 >   ALTER TABLE public."Cliente" DROP CONSTRAINT ck_lunghezza_id;
       public          postgres    false    216    216            �           2606    16476 !   Ristorante unique_nome_ristorante 
   CONSTRAINT     k   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT unique_nome_ristorante UNIQUE ("Nome_Ristorante");
 M   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT unique_nome_ristorante;
       public            postgres    false    210            �           2606    16474    Cliente unique_numero_tel 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT unique_numero_tel UNIQUE ("Numero_Tel");
 E   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT unique_numero_tel;
       public            postgres    false    216            �           2620    49600    TavoliAdiacenti check_adiacenti    TRIGGER     �   CREATE TRIGGER check_adiacenti BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_tavoli_sale();
 :   DROP TRIGGER check_adiacenti ON public."TavoliAdiacenti";
       public          postgres    false    240    220            �           2620    49596    Prenotazione check_avventori    TRIGGER     �   CREATE TRIGGER check_avventori BEFORE INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.check_max_avventori();
 7   DROP TRIGGER check_avventori ON public."Prenotazione";
       public          postgres    false    228    221            �           2620    58286    Servizio check_servizio    TRIGGER     �   CREATE TRIGGER check_servizio BEFORE INSERT ON public."Servizio" FOR EACH ROW EXECUTE FUNCTION public.check_cameriere_ristorante();
 2   DROP TRIGGER check_servizio ON public."Servizio";
       public          postgres    false    243    219            �           2620    58263 #   TavoliAdiacenti check_stesso_tavolo    TRIGGER     �   CREATE TRIGGER check_stesso_tavolo BEFORE INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.check_adiacenza_stesso_tavolo();
 >   DROP TRIGGER check_stesso_tavolo ON public."TavoliAdiacenti";
       public          postgres    false    246    220            �           2620    41389    Tavolata check_tavolo_libero    TRIGGER     y   CREATE TRIGGER check_tavolo_libero BEFORE INSERT ON public."Tavolata" FOR EACH ROW EXECUTE FUNCTION public.check_date();
 7   DROP TRIGGER check_tavolo_libero ON public."Tavolata";
       public          postgres    false    224    215            �           2620    58138    TavoliAdiacenti commutazione    TRIGGER     �   CREATE TRIGGER commutazione AFTER INSERT ON public."TavoliAdiacenti" FOR EACH ROW EXECUTE FUNCTION public.commutazione_adiacenza();
 7   DROP TRIGGER commutazione ON public."TavoliAdiacenti";
       public          postgres    false    241    220            �           2620    58299 &   Tavolo conta_posti_1sala_delete_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_1sala_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_delete_tavolo();
 A   DROP TRIGGER conta_posti_1sala_delete_tavolo ON public."Tavolo";
       public          postgres    false    247    213            �           2620    58298 &   Tavolo conta_posti_1sala_insert_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_1sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_1sala_insert_tavolo();
 A   DROP TRIGGER conta_posti_1sala_insert_tavolo ON public."Tavolo";
       public          postgres    false    248    213            �           2620    58303 ,   Tavolo conta_posti_2ristorante_delete_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_2ristorante_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_delete_tavolo();
 G   DROP TRIGGER conta_posti_2ristorante_delete_tavolo ON public."Tavolo";
       public          postgres    false    213    244            �           2620    58302 ,   Tavolo conta_posti_2ristorante_insert_tavolo    TRIGGER     �   CREATE TRIGGER conta_posti_2ristorante_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.conta_posti_2ristorante_insert_tavolo();
 G   DROP TRIGGER conta_posti_2ristorante_insert_tavolo ON public."Tavolo";
       public          postgres    false    245    213            �           2620    58290 ,   Cameriere update_ristorante_delete_cameriere    TRIGGER     �   CREATE TRIGGER update_ristorante_delete_cameriere AFTER DELETE ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_delete_cameriere();
 G   DROP TRIGGER update_ristorante_delete_cameriere ON public."Cameriere";
       public          postgres    false    218    237            �           2620    58289 ,   Cameriere update_ristorante_insert_cameriere    TRIGGER     �   CREATE TRIGGER update_ristorante_insert_cameriere AFTER INSERT ON public."Cameriere" FOR EACH ROW EXECUTE FUNCTION public.update_ristorante_insert_cameriere();
 G   DROP TRIGGER update_ristorante_insert_cameriere ON public."Cameriere";
       public          postgres    false    218    238            �           2620    58274     Tavolo update_sala_delete_tavolo    TRIGGER     �   CREATE TRIGGER update_sala_delete_tavolo AFTER DELETE ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_delete_tavolo();
 ;   DROP TRIGGER update_sala_delete_tavolo ON public."Tavolo";
       public          postgres    false    213    242            �           2620    58272     Tavolo update_sala_insert_tavolo    TRIGGER     �   CREATE TRIGGER update_sala_insert_tavolo AFTER INSERT ON public."Tavolo" FOR EACH ROW EXECUTE FUNCTION public.update_sala_insert_tavolo();
 ;   DROP TRIGGER update_sala_insert_tavolo ON public."Tavolo";
       public          postgres    false    239    213            �           2620    58307 7   Prenotazione update_tavolata_delete_clienteprenotazione    TRIGGER     �   CREATE TRIGGER update_tavolata_delete_clienteprenotazione AFTER DELETE ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_delete_clienteprenotazione();
 R   DROP TRIGGER update_tavolata_delete_clienteprenotazione ON public."Prenotazione";
       public          postgres    false    249    221            �           2620    58306 7   Prenotazione update_tavolata_insert_clienteprenotazione    TRIGGER     �   CREATE TRIGGER update_tavolata_insert_clienteprenotazione AFTER INSERT ON public."Prenotazione" FOR EACH ROW EXECUTE FUNCTION public.update_tavolata_insert_clienteprenotazione();
 R   DROP TRIGGER update_tavolata_insert_clienteprenotazione ON public."Prenotazione";
       public          postgres    false    250    221            �           2606    33208    Servizio Cameriere_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Cameriere_FK" FOREIGN KEY ("ID_Cameriere") REFERENCES public."Cameriere"("ID_Cameriere") NOT VALID;
 C   ALTER TABLE ONLY public."Servizio" DROP CONSTRAINT "Cameriere_FK";
       public          postgres    false    3243    219    218            �           2606    58280 $   Cameriere Cameriere_ID_Ristorante_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_ID_Ristorante_FK" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante") NOT VALID;
 R   ALTER TABLE ONLY public."Cameriere" DROP CONSTRAINT "Cameriere_ID_Ristorante_FK";
       public          postgres    false    3229    218    210            �           2606    49576 #   Prenotazione Codice_Prenotazione_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Codice_Prenotazione_FK" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione");
 Q   ALTER TABLE ONLY public."Prenotazione" DROP CONSTRAINT "Codice_Prenotazione_FK";
       public          postgres    false    221    215    3237            �           2606    16436    Tavolata Codice_Tavolo    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Codice_Tavolo" FOREIGN KEY ("Codice_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Codice_Tavolo";
       public          postgres    false    3235    215    213            �           2606    16408    Sala ID_Ristorante    FK CONSTRAINT     �   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "ID_Ristorante" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante");
 @   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "ID_Ristorante";
       public          postgres    false    211    3229    210            �           2606    58337    Tavolo ID_Sala_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "ID_Sala_FK" FOREIGN KEY ("ID_Sala") REFERENCES public."Sala"("ID_Sala") NOT VALID;
 ?   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "ID_Sala_FK";
       public          postgres    false    211    3233    213            �           2606    49586    Prenotazione Numero_ID_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Prenotazione"
    ADD CONSTRAINT "Numero_ID_FK" FOREIGN KEY ("Numero_ID") REFERENCES public."Cliente"("Numero_ID_Card");
 G   ALTER TABLE ONLY public."Prenotazione" DROP CONSTRAINT "Numero_ID_FK";
       public          postgres    false    216    221    3239            �           2606    33213    Servizio Prenotazione_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."Servizio"
    ADD CONSTRAINT "Prenotazione_FK" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione") NOT VALID;
 F   ALTER TABLE ONLY public."Servizio" DROP CONSTRAINT "Prenotazione_FK";
       public          postgres    false    3237    219    215            �           2606    33226    TavoliAdiacenti TavoloAD_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "TavoloAD_FK" FOREIGN KEY ("ID_Tavolo_Adiacente") REFERENCES public."Tavolo"("Codice_Tavolo");
 I   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "TavoloAD_FK";
       public          postgres    false    213    220    3235            �           2606    33221    TavoliAdiacenti Tavolo_FK    FK CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "Tavolo_FK" FOREIGN KEY ("ID_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo");
 G   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "Tavolo_FK";
       public          postgres    false    220    3235    213            _      x������ � �      ]      x������ � �      b      x������ � �      W      x������ � �      X      x������ � �      `      x������ � �      \      x������ � �      a      x������ � �      Z      x������ � �     