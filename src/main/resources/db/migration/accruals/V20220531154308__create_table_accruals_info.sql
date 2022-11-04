CREATE TABLE public.accruals_info
(
    id bigserial,
    accrual_id bigint NOT NULL,
    service_name character varying(64) NOT NULL,
    tariff double precision NOT NULL,
    sum_of_accrual double precision NOT NULL,
    CONSTRAINT accruals_info_pk PRIMARY KEY (id),
    CONSTRAINT accruals_info_fk FOREIGN KEY (accrual_id)
        REFERENCES public.accruals (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);