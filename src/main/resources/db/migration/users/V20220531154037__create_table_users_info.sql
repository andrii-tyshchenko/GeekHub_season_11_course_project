CREATE TABLE public.users_info
(
    id serial,
    user_id integer NOT NULL,
    apartment_id integer,
    last_name character varying,
    first_name character varying,
    patronymic character varying,
    CONSTRAINT users_info_pk PRIMARY KEY (id),
    CONSTRAINT users_info_user_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT users_info_apartment_fk FOREIGN KEY (apartment_id)
        REFERENCES public.apartments (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);