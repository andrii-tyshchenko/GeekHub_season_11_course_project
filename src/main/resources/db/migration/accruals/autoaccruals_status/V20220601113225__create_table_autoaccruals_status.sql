CREATE TABLE public.autoaccruals_status
(
    id bigserial,
    enabled boolean NOT NULL,
    date_of_change timestamp without time zone NOT NULL,
    service_name character varying(64),
    tariff double precision,
    CONSTRAINT autoaccruals_status_pk PRIMARY KEY (id)
);