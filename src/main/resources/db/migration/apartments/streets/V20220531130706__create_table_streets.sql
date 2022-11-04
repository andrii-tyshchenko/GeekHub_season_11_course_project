CREATE TABLE public.streets
(
    id serial,
    name character varying NOT NULL,
    CONSTRAINT streets_pk PRIMARY KEY (id)
);