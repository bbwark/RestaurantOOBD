PGDMP                         z            RestaurantOO    14.1    14.1 7    7           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            8           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            9           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            :           1262    32951    RestaurantOO    DATABASE     j   CREATE DATABASE "RestaurantOO" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "RestaurantOO";
                postgres    false            �            1259    33014 	   Cameriere    TABLE     �   CREATE TABLE public."Cameriere" (
    "ID_Cameriere" integer NOT NULL,
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL
);
    DROP TABLE public."Cameriere";
       public         heap    postgres    false            �            1259    33073    CameriereTavolata    TABLE     }   CREATE TABLE public."CameriereTavolata" (
    "ID_Cameriere" integer NOT NULL,
    "Codice_Prenotazione" integer NOT NULL
);
 '   DROP TABLE public."CameriereTavolata";
       public         heap    postgres    false            �            1259    33013    Cameriere_ID Cameriere_seq    SEQUENCE     �   CREATE SEQUENCE public."Cameriere_ID Cameriere_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public."Cameriere_ID Cameriere_seq";
       public          postgres    false    218            ;           0    0    Cameriere_ID Cameriere_seq    SEQUENCE OWNED BY     _   ALTER SEQUENCE public."Cameriere_ID Cameriere_seq" OWNED BY public."Cameriere"."ID_Cameriere";
          public          postgres    false    217            �            1259    32953    Cliente    TABLE     �   CREATE TABLE public."Cliente" (
    "Nome" name NOT NULL,
    "Cognome" name NOT NULL,
    "Numero_ID_Card" character(9) NOT NULL,
    "Numero_Tel" text
);
    DROP TABLE public."Cliente";
       public         heap    postgres    false            �            1259    33060    ClienteTavolata    TABLE     �   CREATE TABLE public."ClienteTavolata" (
    "Numero_ID_Card" character(9) NOT NULL,
    "Codice_Prenotazione" integer NOT NULL
);
 %   DROP TABLE public."ClienteTavolata";
       public         heap    postgres    false            �            1259    32958 
   Ristorante    TABLE     p   CREATE TABLE public."Ristorante" (
    "Nome_Ristorante" name NOT NULL,
    "ID_Ristorante" integer NOT NULL
);
     DROP TABLE public."Ristorante";
       public         heap    postgres    false            �            1259    32961    Ristorante_ID_Ristorante_seq    SEQUENCE     �   CREATE SEQUENCE public."Ristorante_ID_Ristorante_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public."Ristorante_ID_Ristorante_seq";
       public          postgres    false    210            <           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public."Ristorante_ID_Ristorante_seq" OWNED BY public."Ristorante"."ID_Ristorante";
          public          postgres    false    211            �            1259    32962    Sala    TABLE     d   CREATE TABLE public."Sala" (
    "Nome_Sala" name NOT NULL,
    "ID_Ristorante" integer NOT NULL
);
    DROP TABLE public."Sala";
       public         heap    postgres    false            �            1259    32965    Tavolata    TABLE     �   CREATE TABLE public."Tavolata" (
    "Data_Arrivo" date NOT NULL,
    "Codice_Prenotazione" integer NOT NULL,
    "Codice_Tavolo" integer NOT NULL
);
    DROP TABLE public."Tavolata";
       public         heap    postgres    false            �            1259    32970     Tavolata_Codice_Prenotazione_seq    SEQUENCE     �   CREATE SEQUENCE public."Tavolata_Codice_Prenotazione_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 9   DROP SEQUENCE public."Tavolata_Codice_Prenotazione_seq";
       public          postgres    false    213            =           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public."Tavolata_Codice_Prenotazione_seq" OWNED BY public."Tavolata"."Codice_Prenotazione";
          public          postgres    false    214            �            1259    33047    TavoliAdiacenti    TABLE     f   CREATE TABLE public."TavoliAdiacenti" (
    "ID_Tavolo" integer,
    "ID_Tavolo_Adiacente" integer
);
 %   DROP TABLE public."TavoliAdiacenti";
       public         heap    postgres    false            �            1259    32971    Tavolo    TABLE     �   CREATE TABLE public."Tavolo" (
    "Codice_Tavolo" integer NOT NULL,
    "Max_Avventori" integer NOT NULL,
    "Nome_Sala" name NOT NULL,
    CONSTRAINT avventori_positivo CHECK (("Max_Avventori" > 0))
);
    DROP TABLE public."Tavolo";
       public         heap    postgres    false            �            1259    32975    Tavolo_Codice_Tavolo_seq    SEQUENCE     �   CREATE SEQUENCE public."Tavolo_Codice_Tavolo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public."Tavolo_Codice_Tavolo_seq";
       public          postgres    false    215            >           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public."Tavolo_Codice_Tavolo_seq" OWNED BY public."Tavolo"."Codice_Tavolo";
          public          postgres    false    216            �           2604    33017    Cameriere ID_Cameriere    DEFAULT     �   ALTER TABLE ONLY public."Cameriere" ALTER COLUMN "ID_Cameriere" SET DEFAULT nextval('public."Cameriere_ID Cameriere_seq"'::regclass);
 I   ALTER TABLE public."Cameriere" ALTER COLUMN "ID_Cameriere" DROP DEFAULT;
       public          postgres    false    218    217    218                       2604    32976    Ristorante ID_Ristorante    DEFAULT     �   ALTER TABLE ONLY public."Ristorante" ALTER COLUMN "ID_Ristorante" SET DEFAULT nextval('public."Ristorante_ID_Ristorante_seq"'::regclass);
 K   ALTER TABLE public."Ristorante" ALTER COLUMN "ID_Ristorante" DROP DEFAULT;
       public          postgres    false    211    210            �           2604    32977    Tavolata Codice_Prenotazione    DEFAULT     �   ALTER TABLE ONLY public."Tavolata" ALTER COLUMN "Codice_Prenotazione" SET DEFAULT nextval('public."Tavolata_Codice_Prenotazione_seq"'::regclass);
 O   ALTER TABLE public."Tavolata" ALTER COLUMN "Codice_Prenotazione" DROP DEFAULT;
       public          postgres    false    214    213            �           2604    32978    Tavolo Codice_Tavolo    DEFAULT     �   ALTER TABLE ONLY public."Tavolo" ALTER COLUMN "Codice_Tavolo" SET DEFAULT nextval('public."Tavolo_Codice_Tavolo_seq"'::regclass);
 G   ALTER TABLE public."Tavolo" ALTER COLUMN "Codice_Tavolo" DROP DEFAULT;
       public          postgres    false    216    215            1          0    33014 	   Cameriere 
   TABLE DATA           H   COPY public."Cameriere" ("ID_Cameriere", "Nome", "Cognome") FROM stdin;
    public          postgres    false    218   �A       4          0    33073    CameriereTavolata 
   TABLE DATA           T   COPY public."CameriereTavolata" ("ID_Cameriere", "Codice_Prenotazione") FROM stdin;
    public          postgres    false    221   'C       (          0    32953    Cliente 
   TABLE DATA           V   COPY public."Cliente" ("Nome", "Cognome", "Numero_ID_Card", "Numero_Tel") FROM stdin;
    public          postgres    false    209   �C       3          0    33060    ClienteTavolata 
   TABLE DATA           T   COPY public."ClienteTavolata" ("Numero_ID_Card", "Codice_Prenotazione") FROM stdin;
    public          postgres    false    220   vM       )          0    32958 
   Ristorante 
   TABLE DATA           J   COPY public."Ristorante" ("Nome_Ristorante", "ID_Ristorante") FROM stdin;
    public          postgres    false    210   �P       +          0    32962    Sala 
   TABLE DATA           >   COPY public."Sala" ("Nome_Sala", "ID_Ristorante") FROM stdin;
    public          postgres    false    212   �P       ,          0    32965    Tavolata 
   TABLE DATA           [   COPY public."Tavolata" ("Data_Arrivo", "Codice_Prenotazione", "Codice_Tavolo") FROM stdin;
    public          postgres    false    213   MQ       2          0    33047    TavoliAdiacenti 
   TABLE DATA           O   COPY public."TavoliAdiacenti" ("ID_Tavolo", "ID_Tavolo_Adiacente") FROM stdin;
    public          postgres    false    219   mR       .          0    32971    Tavolo 
   TABLE DATA           Q   COPY public."Tavolo" ("Codice_Tavolo", "Max_Avventori", "Nome_Sala") FROM stdin;
    public          postgres    false    215   �R       ?           0    0    Cameriere_ID Cameriere_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public."Cameriere_ID Cameriere_seq"', 30, true);
          public          postgres    false    217            @           0    0    Ristorante_ID_Ristorante_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public."Ristorante_ID_Ristorante_seq"', 3, true);
          public          postgres    false    211            A           0    0     Tavolata_Codice_Prenotazione_seq    SEQUENCE SET     Q   SELECT pg_catalog.setval('public."Tavolata_Codice_Prenotazione_seq"', 45, true);
          public          postgres    false    214            B           0    0    Tavolo_Codice_Tavolo_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public."Tavolo_Codice_Tavolo_seq"', 24, true);
          public          postgres    false    216            �           2606    33019    Cameriere Cameriere_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public."Cameriere"
    ADD CONSTRAINT "Cameriere_pkey" PRIMARY KEY ("ID_Cameriere");
 F   ALTER TABLE ONLY public."Cameriere" DROP CONSTRAINT "Cameriere_pkey";
       public            postgres    false    218            �           2606    32980    Cliente Cliente_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT "Cliente_pkey" PRIMARY KEY ("Numero_ID_Card");
 B   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT "Cliente_pkey";
       public            postgres    false    209            �           2606    32982    Ristorante Ristorante_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT "Ristorante_pkey" PRIMARY KEY ("ID_Ristorante");
 H   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT "Ristorante_pkey";
       public            postgres    false    210            �           2606    32984    Sala Sala_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "Sala_pkey" PRIMARY KEY ("Nome_Sala");
 <   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "Sala_pkey";
       public            postgres    false    212            �           2606    32986    Tavolata Tavolata_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Tavolata_pkey" PRIMARY KEY ("Codice_Prenotazione");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Tavolata_pkey";
       public            postgres    false    213            �           2606    32988    Tavolo Tavolo_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Tavolo_pkey" PRIMARY KEY ("Codice_Tavolo");
 @   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "Tavolo_pkey";
       public            postgres    false    215            �           2606    32990 !   Ristorante unique_nome_ristorante 
   CONSTRAINT     k   ALTER TABLE ONLY public."Ristorante"
    ADD CONSTRAINT unique_nome_ristorante UNIQUE ("Nome_Ristorante");
 M   ALTER TABLE ONLY public."Ristorante" DROP CONSTRAINT unique_nome_ristorante;
       public            postgres    false    210            �           2606    32992    Cliente unique_numero_tel 
   CONSTRAINT     ^   ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT unique_numero_tel UNIQUE ("Numero_Tel");
 E   ALTER TABLE ONLY public."Cliente" DROP CONSTRAINT unique_numero_tel;
       public            postgres    false    209            �           2606    33068 #   ClienteTavolata Codice_Prenotazione    FK CONSTRAINT     �   ALTER TABLE ONLY public."ClienteTavolata"
    ADD CONSTRAINT "Codice_Prenotazione" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione");
 Q   ALTER TABLE ONLY public."ClienteTavolata" DROP CONSTRAINT "Codice_Prenotazione";
       public          postgres    false    220    3215    213            �           2606    33081 %   CameriereTavolata Codice_Prenotazione    FK CONSTRAINT     �   ALTER TABLE ONLY public."CameriereTavolata"
    ADD CONSTRAINT "Codice_Prenotazione" FOREIGN KEY ("Codice_Prenotazione") REFERENCES public."Tavolata"("Codice_Prenotazione");
 S   ALTER TABLE ONLY public."CameriereTavolata" DROP CONSTRAINT "Codice_Prenotazione";
       public          postgres    false    3215    221    213            �           2606    32998    Tavolata Codice_Tavolo    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolata"
    ADD CONSTRAINT "Codice_Tavolo" FOREIGN KEY ("Codice_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo");
 D   ALTER TABLE ONLY public."Tavolata" DROP CONSTRAINT "Codice_Tavolo";
       public          postgres    false    3217    213    215            �           2606    33076    CameriereTavolata ID_Cameriere    FK CONSTRAINT     �   ALTER TABLE ONLY public."CameriereTavolata"
    ADD CONSTRAINT "ID_Cameriere" FOREIGN KEY ("ID_Cameriere") REFERENCES public."Cameriere"("ID_Cameriere");
 L   ALTER TABLE ONLY public."CameriereTavolata" DROP CONSTRAINT "ID_Cameriere";
       public          postgres    false    221    3219    218            �           2606    33003    Sala ID_Ristorante    FK CONSTRAINT     �   ALTER TABLE ONLY public."Sala"
    ADD CONSTRAINT "ID_Ristorante" FOREIGN KEY ("ID_Ristorante") REFERENCES public."Ristorante"("ID_Ristorante");
 @   ALTER TABLE ONLY public."Sala" DROP CONSTRAINT "ID_Ristorante";
       public          postgres    false    3209    212    210            �           2606    33050    TavoliAdiacenti ID_Tavolo    FK CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "ID_Tavolo" FOREIGN KEY ("ID_Tavolo") REFERENCES public."Tavolo"("Codice_Tavolo");
 G   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "ID_Tavolo";
       public          postgres    false    219    3217    215            �           2606    33055 #   TavoliAdiacenti ID_Tavolo_Adiacente    FK CONSTRAINT     �   ALTER TABLE ONLY public."TavoliAdiacenti"
    ADD CONSTRAINT "ID_Tavolo_Adiacente" FOREIGN KEY ("ID_Tavolo_Adiacente") REFERENCES public."Tavolo"("Codice_Tavolo");
 Q   ALTER TABLE ONLY public."TavoliAdiacenti" DROP CONSTRAINT "ID_Tavolo_Adiacente";
       public          postgres    false    215    219    3217            �           2606    33008    Tavolo Nome_Sala    FK CONSTRAINT     �   ALTER TABLE ONLY public."Tavolo"
    ADD CONSTRAINT "Nome_Sala" FOREIGN KEY ("Nome_Sala") REFERENCES public."Sala"("Nome_Sala");
 >   ALTER TABLE ONLY public."Tavolo" DROP CONSTRAINT "Nome_Sala";
       public          postgres    false    215    212    3213            �           2606    33063    ClienteTavolata Numero_ID_Card    FK CONSTRAINT     �   ALTER TABLE ONLY public."ClienteTavolata"
    ADD CONSTRAINT "Numero_ID_Card" FOREIGN KEY ("Numero_ID_Card") REFERENCES public."Cliente"("Numero_ID_Card");
 L   ALTER TABLE ONLY public."ClienteTavolata" DROP CONSTRAINT "Numero_ID_Card";
       public          postgres    false    209    3205    220            1   �  x��Mo�0���ȭ��v����q�f�b@�]��	�b ���_?�7�^=�3x����K��WM��8J	Kئ$��n�^��q
;�^>9��R�ࠗ�/�'��Np?IV�;N8�Ni�J��z\�/��1�[�g�4<��E|���$;r�,����YNŷ��=��Id��>���X�n��;��7vX��D���)�'�C]����ةkV��X��,{�k>.r����d������c��D���8���w�Z�^����V�֐̹,��s��:뵴�,�s�+���)l��FX���팓FG�\=Ř�f��=CE!�˹�hwS6��1��x�r�ccu>n�h�4p2{�Y;�����v�K+�3F��7z�#���(�g�������      4   �   x���E!B�P�!��������qzC􄩅���&c`Q�ͅӎK�w�@b�L�"%�{�ݡE�)�r�]�A/XL�X�*q�ٓ.y�P9ç�Ŋ��PnHD{#{Z��
��?N����|�ToJ��u������G��$#      (   �	  x�-WI��J]�w�@ιTP ��6T�WҥR��}��oV�[��Ȉ;�l���~oiz���d׆�V.Hq�}e|ɽ�}��m|�~��Y�􉘖&PRi酷�kK��~��OZ��jVO���`��>���Ó��?�@��	�?P�/|�pQ��{ܔ��O:�v9���A/�Ơ�-�������xk�T����,�ѧ�*et`-��6Z���h�X�N�|���k?��h�l�v��8|�*�6����jM�pH�m���i����A�t[��Dfd��L��j��@�������1��J�J�t �o���� -߷=�~>���ydx���������e;��/�Q�a>#��V�&�5�����ݓ�LFS�x��0e��{6�CO���u��AK�����@�<_z�fh~��i��d�[Yx�3��J)oݵ��>�-Em3��땶[qS��ϕr=^�]ݓz�=�o7��O��B�6�b�0�p�nh>~\:TZ�|_n�S�3�D3��/m��O�|�`S����I2+Ӿơ��b�O
��䍲<*�P�*	!9Ǽ��\FT*Q�j.�d�ɉ+a��0�W7�Gw}���н�<{��!�e$&��{�ĉ�}D3��-+���V3�3���6_@гq����?Y����-{�hh�͝��y�R��1�i<c�|&��@ ����1��`��^�x�z���VFm�� [���㊂�Mx��S1!k}��qu�}�צ�uw>�w�l�)Ӥ�K�5�M���qX�[p�>�i8����:ZY�����u��+vO*ۮ�<;�ȂV0����c;�@�/�Qgi<�"���(P?t�M\	��j�EAϖ��/��yP9o0Ċ[�fa�|���&G���nh?i:7��~�(pd�)�=�����|n�oC��t��l��xc�Lzѵy�	��4�7��R%�R)T�
!,C���~����~�SD�$��r�G�^�=0��,�y]Y�X\�{!������B��W���"{?3ݭu$fi$S�B2��[��R(h�x׾_�{OҮf��/b��3���%�pk�lE���E��Ҝ�K�b07;o��th�.��N��fr����䀠�^������X�t��H�N�K�g�phA�u�������9ơYOV1\& _NQ�%�ϝX&t)uR:���ݹ��_�f�h���:�1i��׿r;i�h�8�Ć~�d6_�'K��0<0�����a6eR�<�
2�n�S���(�mKs��],9�C,��~G'��վ�.�}Q$$���b�ڇ3 &me�z�g�80'5Dw@�hՌ�狪`^��I
@�2������4鿝�EU��9x��*(C�W</��О�4�a���R����7uJF(qG&���N}��0#%ڋ�+���zX��4��$ȷQ�g"�NpS�tG��;�Mh�u�2�(t
A��qp��'FO	_���x��Z��y ٓ���.��臑o|��kv3�Py�EE~�+Â%f�	���SY�wD��^�J���0� �.�4��p���?�!�����. V�wKo;>Ώ���~��z5���H%�rV9�O�2����P�����V��++���^��˳k���.3	9��R'��x���ZZ��'<�՛r�ˍÓ�%�5�����|A
�h�B9̀hLz�����
  ���PI�s �����ǳc���u5?D�14x�r��t�������l��O���]��]C{�aQ���d���4p��d�۫{V�~9�SǬ�a�g��弬�^��B�	v�t�
���n}D$���b�p���jZA���<�����y���`=�6�"	���~8�k�����&g���4�
Yc�e��8�������*M���7�9�刈k�F���L�*�W��NhH,l��v��~�G�l�|�[K.�8\ ?�G�I%����O�#i'\���8և���_w?;�n���E����f�4䈻 	t|]���(elm�l1�p�y��H�܀�ms�q#~��$)9~r�cx1��b��)F�,�&�hӍD��b��eh��w�I���LM���l��P��@�����ڜ6�a�g(��ߺ[���H�a�t�قD��0�EFx<Gpb����2�T�)��?L�M���G���y��LֹN҂�� 1X*�su;�u���t������ �	�^�#r$ 9|��A�:T��~��)c�;��rS Ԁ��3�*MDY,\��r���΄~�m�@��S��ǒ�~H©���B��j�4�>�q��	! [(�м9H������!�L�=�v�0ɂ�i9���0�7�y)�"c
h�P��� ��G��6P�����C�D�8t���ׯ�m�i���X��gDÞ��X`
]{�Z��M=O�
��r�k'F��y�Q�ι      3   2  x��K��*@�24�E�PDTPd���;���]���M8o �{��g,��[E�R�b ����bz|����Ō�w��&ޡ���w���+-篜����G�̷��t��0�'4Z�tX �Ax6��@�}���8���������c#�E�֕�֫H����z��έ)����@A�<hP���V�/������S�^�;��� ���v�6��z�̂��]�ѹ�?$���ũ���2�a�Q���4F��������ޙ�~F
�c���;pL��r��*��^�P��4�ST�;�sKw 18h/e����ZK, q��"&u�؍�1�;�:���$�5^�]~5w<w���m�6�<S��H������S�����v��N��mnʯ;Cb�l�#$%�c��j�I�����QǏ��O,��T� c��R��h��-J;[��!�e�X];xfz1��o�`I˵Fێ'���<>Ď���z 3����ޱ�7�� ���s��� �pq,ap ��<����1�^��-W��j��	`	�����u�ZW����}��Vp���o2���D��� d���ELT�t�y>��iC<U�si{ڀΩ$�YݱoC
x��#��Y����Y�n8 !P�frh�Sfdj"�[:8�Ց6� i�;>�m��A��Q�:@84C�v�|[T����Y_�H�������Qp����[G
{��̀�M��u��T.������S��^�^�Y��;�q��Ѐ��ޛ�>^x�cӌn@���LW�1�(����k��¤gw��6e�k��-������2#�      )   2   x�(���W�,.�/J�+I�4�
NM��KA4�
I-�B2����� �?      +   C   x�S
(��M�N�I4T�4�R
NM��KA	I-�B�#t)q�ꀈ t@��J�ƨ:�"1z\\\ :�'�      ,     x�URIv�0[+wq�<ݥ�?�?	�K���v��W��4�H����N�`����&�P01`Y����V���ı�Uq0�Y鉵���L9E_�hf�b1����\s&��!'�Ӭ��|�Z�$L�;��H�%��"$Ҋ5��`�a�.R��I�o*b�&���5F����ۧ1.nt:S�>ĭ�0:]�
/�⻌Nw)6�nʱtz�5�I�z���_EWxg���KqT(s���#�ږ%��7B�K����ߟ�>��zN      2      x������ � �      .   �   x�m�;
�0�Y:�P"�s�B:�i3���=}�	��~��-��<��6�W#��� �H�3�a�h$�Kѩ��˨��� 4���� �]��d ��M(��1Mf�R�@��ڝ����2/����H���3��U�zMQ��'D�Փ~�     