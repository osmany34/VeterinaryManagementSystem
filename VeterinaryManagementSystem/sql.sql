PGDMP      
                |            VeterinarySystem    14.12    16.3 )               0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                        0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            !           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            "           1262    33640    VeterinarySystem    DATABASE     �   CREATE DATABASE "VeterinarySystem" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_T�rkiye.1254';
 "   DROP DATABASE "VeterinarySystem";
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            #           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    4            �            1259    34099    animals    TABLE     K  CREATE TABLE public.animals (
    id bigint NOT NULL,
    breed character varying(255) NOT NULL,
    colour character varying(255) NOT NULL,
    date_of_birth date NOT NULL,
    gender character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    species character varying(255) NOT NULL,
    customer_id bigint
);
    DROP TABLE public.animals;
       public         heap    postgres    false    4            �            1259    34098    animals_id_seq    SEQUENCE     �   ALTER TABLE public.animals ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.animals_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    210            �            1259    34107    appointments    TABLE     �   CREATE TABLE public.appointments (
    id bigint NOT NULL,
    appointment_date timestamp(6) without time zone NOT NULL,
    animal_id bigint,
    doctor_id bigint
);
     DROP TABLE public.appointments;
       public         heap    postgres    false    4            �            1259    34106    appointments_id_seq    SEQUENCE     �   ALTER TABLE public.appointments ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.appointments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    212    4            �            1259    34113    available_dates    TABLE     x   CREATE TABLE public.available_dates (
    id bigint NOT NULL,
    available_date date NOT NULL,
    doctor_id bigint
);
 #   DROP TABLE public.available_dates;
       public         heap    postgres    false    4            �            1259    34112    available_dates_id_seq    SEQUENCE     �   ALTER TABLE public.available_dates ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.available_dates_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    214            �            1259    34119 	   customers    TABLE     �   CREATE TABLE public.customers (
    id bigint NOT NULL,
    address character varying(255),
    city character varying(255),
    mail character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL
);
    DROP TABLE public.customers;
       public         heap    postgres    false    4            �            1259    34118    customers_id_seq    SEQUENCE     �   ALTER TABLE public.customers ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216    4            �            1259    34127    doctors    TABLE     �   CREATE TABLE public.doctors (
    id bigint NOT NULL,
    address character varying(255),
    city character varying(255),
    mail character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255)
);
    DROP TABLE public.doctors;
       public         heap    postgres    false    4            �            1259    34126    doctors_id_seq    SEQUENCE     �   ALTER TABLE public.doctors ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.doctors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218    4            �            1259    34135    vaccines    TABLE     �   CREATE TABLE public.vaccines (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    protection_finish_date date NOT NULL,
    protection_start_date date NOT NULL,
    animal_id bigint
);
    DROP TABLE public.vaccines;
       public         heap    postgres    false    4            �            1259    34134    vaccines_id_seq    SEQUENCE     �   ALTER TABLE public.vaccines ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.vaccines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    4    220                      0    34099    animals 
   TABLE DATA           g   COPY public.animals (id, breed, colour, date_of_birth, gender, name, species, customer_id) FROM stdin;
    public          postgres    false    210   �0                 0    34107    appointments 
   TABLE DATA           R   COPY public.appointments (id, appointment_date, animal_id, doctor_id) FROM stdin;
    public          postgres    false    212   �1                 0    34113    available_dates 
   TABLE DATA           H   COPY public.available_dates (id, available_date, doctor_id) FROM stdin;
    public          postgres    false    214   92                 0    34119 	   customers 
   TABLE DATA           I   COPY public.customers (id, address, city, mail, name, phone) FROM stdin;
    public          postgres    false    216   �2                 0    34127    doctors 
   TABLE DATA           G   COPY public.doctors (id, address, city, mail, name, phone) FROM stdin;
    public          postgres    false    218   [4                 0    34135    vaccines 
   TABLE DATA           l   COPY public.vaccines (id, code, name, protection_finish_date, protection_start_date, animal_id) FROM stdin;
    public          postgres    false    220   x6       $           0    0    animals_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.animals_id_seq', 11, true);
          public          postgres    false    209            %           0    0    appointments_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.appointments_id_seq', 4, true);
          public          postgres    false    211            &           0    0    available_dates_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.available_dates_id_seq', 12, true);
          public          postgres    false    213            '           0    0    customers_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.customers_id_seq', 11, true);
          public          postgres    false    215            (           0    0    doctors_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.doctors_id_seq', 12, true);
          public          postgres    false    217            )           0    0    vaccines_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.vaccines_id_seq', 10, true);
          public          postgres    false    219            v           2606    34105    animals animals_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.animals
    ADD CONSTRAINT animals_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.animals DROP CONSTRAINT animals_pkey;
       public            postgres    false    210            x           2606    34111    appointments appointments_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.appointments DROP CONSTRAINT appointments_pkey;
       public            postgres    false    212            z           2606    34117 $   available_dates available_dates_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.available_dates
    ADD CONSTRAINT available_dates_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.available_dates DROP CONSTRAINT available_dates_pkey;
       public            postgres    false    214            |           2606    34125    customers customers_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.customers DROP CONSTRAINT customers_pkey;
       public            postgres    false    216            ~           2606    34133    doctors doctors_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.doctors DROP CONSTRAINT doctors_pkey;
       public            postgres    false    218            �           2606    34141    vaccines vaccines_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.vaccines
    ADD CONSTRAINT vaccines_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.vaccines DROP CONSTRAINT vaccines_pkey;
       public            postgres    false    220            �           2606    34147 (   appointments fk95vepu86o8syqtux9gkr71bhy    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fk95vepu86o8syqtux9gkr71bhy FOREIGN KEY (animal_id) REFERENCES public.animals(id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fk95vepu86o8syqtux9gkr71bhy;
       public          postgres    false    210    212    3190            �           2606    34142 #   animals fkb36lt3kj4mrbdx5btxmp4j60n    FK CONSTRAINT     �   ALTER TABLE ONLY public.animals
    ADD CONSTRAINT fkb36lt3kj4mrbdx5btxmp4j60n FOREIGN KEY (customer_id) REFERENCES public.customers(id);
 M   ALTER TABLE ONLY public.animals DROP CONSTRAINT fkb36lt3kj4mrbdx5btxmp4j60n;
       public          postgres    false    3196    216    210            �           2606    34162 $   vaccines fkeasdy15b2kp5j4k13x2dfudqs    FK CONSTRAINT     �   ALTER TABLE ONLY public.vaccines
    ADD CONSTRAINT fkeasdy15b2kp5j4k13x2dfudqs FOREIGN KEY (animal_id) REFERENCES public.animals(id);
 N   ALTER TABLE ONLY public.vaccines DROP CONSTRAINT fkeasdy15b2kp5j4k13x2dfudqs;
       public          postgres    false    210    3190    220            �           2606    34152 (   appointments fkmujeo4tymoo98cmf7uj3vsv76    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fkmujeo4tymoo98cmf7uj3vsv76 FOREIGN KEY (doctor_id) REFERENCES public.doctors(id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fkmujeo4tymoo98cmf7uj3vsv76;
       public          postgres    false    212    3198    218            �           2606    34157 +   available_dates fknb419ilm71d71rm584rk460pk    FK CONSTRAINT     �   ALTER TABLE ONLY public.available_dates
    ADD CONSTRAINT fknb419ilm71d71rm584rk460pk FOREIGN KEY (doctor_id) REFERENCES public.doctors(id);
 U   ALTER TABLE ONLY public.available_dates DROP CONSTRAINT fknb419ilm71d71rm584rk460pk;
       public          postgres    false    218    3198    214               H  x�]�An�0E��S��l� YB[��*!@]T�L�E�$NeR��=K�lz��{uLD+Yr�͛�Ll��D����T��	J(�Eȥ�;�7��;��?�B���Z�`�+g�N;����h=(������e:#�����>C6���5$��톦	s1�Jt�ii_߳��fh��|����D���c.F\���l��Mq)�t�9^�El+��wA�BR�3��5�̌XSg*�M�eZ�*E�g�O�Pt�+�B1�z�\͡��tl����t+>�2��%��6��/����U?{�΂���q��6E����|������         ;   x�3�4202�5��50T0��20 "NCNC.#�������1X�(c���@�c����� U~�         E   x�M���0C�3��6�K��#�7���M��sq��4���f����)\�~k�g+�I����t�         �  x�]��n�0���Sp`Xr۝�iM�dI���+A"%HTQ���k�g��^=���� �?�㯻����I��L�o�9����ɡ�����U��lW(�\+����p1��q	q��	-�Si�E&�'�O�-c��bͤp�$"��7��s����FأQ��<��zom�?ق�]:�+��:��-f8Wc��v�5&��ty�eK9�%ܦy���j$.}B��ϊ݄uÞ�����\9��R�)�t8Ga��ܗ��\a7��֗�g[�ȥ�X��`֨��CU7:j5�Eg�jڽ/CA�?;ӿT��rp=�R;R�����P		wO�#q?'܏�`�C�c�N"��Jڢ��9m 7u����S}'���æ��{�̇#BxG��Z����ZnԄpg]{9Z̽��'�vM�,"�
ۚx��t��c�^��L�u�b           x�u�͎�0���Sx�d�)+:t��a1� 6���Zv��i����-�̊]���:)iQD��I��s�On�=d�c+�8[@�U�bI���A��5�B�3�F|�|�_�9�Ξ�@^y1^��i@�g(X`9l8�/_����\�x&r9F͜��N�~Fq2�!Y�9ڣ�*U����=��J��~���`O�ѩ=v��Q�4�bk0}lD�Kf	�=��έ�2�~�4���NѬOc� �
3ď��Z�y9�y���q��� �S��EY�2��YK��6�ǉ�d��ң4F��{���kq�����T@��b��f��t"��KQ�혞OS�(�b	n4C��#�����mn����0���;�5�N��u#�S�1��-�]������5�r�Jj���9�-p����ٳ{tBq�`/�*��� C~�wK+a��A5B?t{�7����	e��?!
�C[ \���M���E��}Y&*験��2�+ ��Ka�Zk�~������S��{o�N��SJ� �S�           x�]�1n�0�g�Sp*?�0��
T!�:uA�+�@RA��g��WM�P`@|����~F��"	�^a�T����ݵꮄQf�M�2��$o���2O��G��tv��,��`��H��7ό(-�ǳ(�[5��a��N5Z�"��N��ef�ѭɆHt4��&�.����I{+ڦ\\�n�4ڰ&�ˢY���g��=����S�װ!������N�N��o�'|,5n�!^2ڞ�k#�t�EW�ŕqX���y�Ɠ�n��/�|{�U�>� �����     