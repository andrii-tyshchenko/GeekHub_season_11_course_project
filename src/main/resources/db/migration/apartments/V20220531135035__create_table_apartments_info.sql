CREATE TABLE public.apartments_info
(
    apartment_id integer NOT NULL,
    square double precision NOT NULL,
    account_number integer NOT NULL,
    CONSTRAINT apartments_info_pk PRIMARY KEY (apartment_id),
    CONSTRAINT apartments_info_fk FOREIGN KEY (apartment_id)
        REFERENCES public.apartments (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);