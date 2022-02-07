PGDMP     2    3                 z            RestaurantBD    14.1    14.1 &               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16395    RestaurantBD    DATABASE     j   CREATE DATABASE "RestaurantBD" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "RestaurantBD";
                postgres    false            �            1255    16566    check_max_avventori()    FUNCTION     �  CREATE FUNCTION public.check_max_avventori() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE max_avventori integer;
begin
SELECT "Tavolo"."Max_Avventori" INTO max_avventori FROM "Tavolo"
INNER JOIN "Tavolata" ON "Tavolo"."Codice_Tavolo" = "Tavolata"."Codice_Tavolo"
INNER JOIN "Cliente" ON "Tavolata"."Codice_Prenotazione" = "Cliente"."Codice_Prenotazione";

IF 
(SELECT COUNT(*) FROM "Cliente"
INNER JOIN "Tavolata" ON "Cliente"."Codice_Prenotazione" = "Tavolata"."Codice_Prenotazione"
INNER JOIN "Tavolo" ON "Tavolata"."Codice_Tavolo" = "Tavolo"."Codice_Tavolo") = max_avventori
THEN 
RAISE EXCEPTION 'Numero massimo di avventori raggiunto.';
END IF;
RETURN NEW;
END
$$;
 ,   DROP FUNCTION public.check_max_avventori();
       public          postgres    false            �            1259    16441    Cliente    TABLE     �   CREATE TABLE public."Cliente" (
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "Numero_ID_Card" character(9) NOT NULL,
    "Numero_Tel" text,
    "Codice_Prenotazione" integer
);
    DROP TABLE public."Cliente";
       public         heap    postgres    false            �            1259    16397 
   Ristorante    TABLE     p   CREATE TABLE public."Ristorante" (
    "Nome_Ristorante" name NOT NULL,
    "ID_Ristorante" integer NOT NULL
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
       public          postgres    false    210                       0    0    Ristorante_ID_Ristorante_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public."Ristorante_ID_Ristorante_seq" OWNED BY public."Ristorante"."ID_Ristorante";
          public          postgres    false    209            �            1259    16403    Sala    TABLE     �   CREATE TABLE public."Sala" (
    "Nome_Sala" name NOT NULL,
    "Num_Tavoli" integer NOT NULL,
    "ID_Ristorante" integer NOT NULL
);
    DROP TABLE public."Sala";
       public         heap    postgres    false            �            1259    16428    Tavolata    TABLE     �   CREATE TABLE public."Tavolata" (
    "Data_Arrivo" date NOT NULL,
    "Codice_Prenotazione" integer NOT NULL,
    "Camerieri" text NOT NULL,
    "Codice_Tavolo" integer
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
       public          postgres    false    215                       0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public."Tavolata_Codice_Prenotazione_seq" OWNED BY public."Tavolata"."Codice_Prenotazione";
          public          postgres    false    214            �            1259    16414    Tavolo    TABLE     �   CREATE TABLE public."Tavolo" (
    "Codice_Tavolo" integer NOT NULL,
    "Max_Avventori" integer NOT NULL,
    "Nome_Sala" name NOT NULL,
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
       public          postgres    false    213                        0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public."Tavolo_Codice_Tavolo_seq" OWNED BY public."Tavolo"."Codice_Tavolo";
          public          postgres    false    212            o           2604    16400    Ristorante ID_Ristorante    DEFAULT     �   ALTER TABLE ONLY public."Ristorante" ALTER COLUMN "ID_Ristorante" SET DEFAULT nextval('public."Ristorante_ID_Ristorante_seq"'::regclass);
 K   ALTER TABLE public."Ristorante" ALTER COLUMN "ID_Ristorante" DROP DEFAULT;
       public          postgres    false    210    209    210            r           2604    16431    Tavolata Codice_Prenotazione    DEFAULT     �   ALTER TABLE ONLY public."Tavolata" ALTER COLUMN "Codice_Prenotazione" SET DEFAULT nextval('public."Tavolata_Codice_Prenotazione_seq"'::regclass);
 O   ALTER TABLE public."Tavolata" ALTER COLUMN "Codice_Prenotazione" DROP DEFAULT;
       public          postgres    false    215    214    215            p           2604    16417    Tavolo Codice_Tavolo    DEFAULT     �   ALTER TABLE ONLY public."Tavolo" ALTER COLUMN "Codice_Tavolo" SET DEFAULT nextval('public."Tavolo_Codice_Tavolo_seq"'::regclass);
 G   ALTER TABLE public."Tavolo" ALTER COLUMN "Codice_Tavolo" DROP DEFAULT;
       public          postgres    false    213    212    213                      0    16441    Cliente 
   TABLE DATA           m   COPY public."Cliente" ("Nome", "Cognome", "Numero_ID_Card", "Numero_Tel", "Codice_Prenotazione") FROM stdin;
    public          postgres    false    216   �.                 0    16397 
   Ristorante 
   TABLE DATA           J   COPY public."Ristorante" ("Nome_Ristorante", "ID_Ristorante") FROM stdin;
    public          postgres    false    210   /                 0    16403    Sala 
   TABLE DATA           L   COPY public."Sala" ("Nome_Sala", "Num_Tavoli", "ID_Ristorante") FROM stdin;
    public          postgres    false    211   B/                 0    16428    Tavolata 
   TABLE DATA           h   COPY public."Tavolata" ("Data_Arrivo", "Codice_Prenotazione", "Camerieri", "Codice_Tavolo") FROM stdin;
    public          postgres    false    215   �/                 0    16414    Tavolo 
   TABLE DATA           Q   COPY public."Tavolo" ("Codice_Tavolo", "Max_Avventori", "Nome_Sala") FROM stdin;
    public          postgres    false    213   �/       !           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public."Ristorante_ID_Ristorante_seq"', 2, true);
          public          postgres    false    209            "           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public."Tavolata_Codice_Prenotazione_seq"', 1, false);
          public          postgres    false    214            #           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public."Tavolo_Codice_Tavolo_seq"', 1, false);
          public          postgres    false    212            ~           2606    16447    Cliente Cliente_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT "Cliente_pkey" PRIMARY KEY ("Numero_ID_Card");
 B   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT "Cliente_pkey";
       public            postgres    false    216            t           2606    16402    Ristorante Ristorante_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT "Ristorante_pkey" PRIMARY KEY ("ID_Ristorante");
 H   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT "Ristorante_pkey";
       public            postgres    false    210            x           2606    16407    Sala Sala_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "Sala_pkey" PRIMARY KEY ("Nome_Sala");
 <   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "Sala_pkey";
       public            postgres    false    211            |           2606    16435    Tavolata Tavolata_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Tavolata_pkey" PRIMARY KEY ("Codice_Prenotazione");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Tavolata_pkey";
       public            postgres    false    215            z           2606    16421    Tavolo Tavolo_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Tavolo_pkey" PRIMARY KEY ("Codice_Tavolo");
 @   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "Tavolo_pkey";
       public            postgres    false    213            v           2606    16476 !   Ristorante unique_nome_ristorante 
   CONSTRAINT     k   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT unique_nome_ristorante UNIQUE ("Nome_Ristorante");
 M   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT unique_nome_ristorante;
       public            postgres    false    210            �           2606    16474    Cliente unique_numero_tel 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT unique_numero_tel UNIQUE ("Numero_Tel");
 E   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT unique_numero_tel;
       public            postgres    false    216            �           2606    16448    Cliente Codice_Prenotazione    FK CONSTRAINT     �   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT "Codice_Prenotazione" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione");
 I   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT "Codice_Prenotazione";
       public          postgres    false    215    216    3196            �           2606    16436    Tavolata Codice_Tavolo    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Codice_Tavolo" FOREIGN KEY ("Codice_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Codice_Tavolo";
       public          postgres    false    213    3194    215            �           2606    16408    Sala ID_Ristorante    FK CONSTRAINT     �   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "ID_Ristorante" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante");
 @   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "ID_Ristorante";
       public          postgres    false    3188    210    211            �           2606    16422    Tavolo Nome_Sala    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Nome_Sala" FOREIGN KEY ("Nome_Sala") REFERENCES public."Sala"("Nome_Sala");
 >   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "Nome_Sala";
       public          postgres    false    213    3192    211                  x������ � �            x�sITp�,��4����� �@         0   x�(��K�,H�I�44�4�
NM��KI�44 rBR��9A�1z\\\ ';Y            x������ � �            x������ � �     