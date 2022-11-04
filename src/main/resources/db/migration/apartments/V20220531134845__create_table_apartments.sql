CREATE TABLE public.apartments
(
    id serial,
    building_address_id integer NOT NULL,
    section smallint NOT NULL,
    floor smallint NOT NULL,
    apartment_number integer NOT NULL,
    CONSTRAINT apartments_pk PRIMARY KEY (id),
    CONSTRAINT apartments_fk FOREIGN KEY (building_address_id)
        REFERENCES public.building_addresses (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);