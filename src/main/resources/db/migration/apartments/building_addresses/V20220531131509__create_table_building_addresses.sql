CREATE TABLE public.building_addresses
(
    id serial,
    street_id integer NOT NULL,
    building integer NOT NULL,
    CONSTRAINT building_addresses_pk PRIMARY KEY (id),
    CONSTRAINT building_addresses_fk FOREIGN KEY (street_id)
        REFERENCES public.streets (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);