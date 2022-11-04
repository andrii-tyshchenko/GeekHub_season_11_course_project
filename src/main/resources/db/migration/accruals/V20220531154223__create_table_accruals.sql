CREATE TABLE public.accruals
(
    id bigserial,
    user_id integer NOT NULL,
    date_of_submit timestamp without time zone NOT NULL,
    CONSTRAINT accruals_pk PRIMARY KEY (id),
    CONSTRAINT accruals_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);