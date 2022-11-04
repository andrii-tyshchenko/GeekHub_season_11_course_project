CREATE TABLE public.payments
(
    id bigserial,
    user_id integer NOT NULL,
    date_of_submit timestamp without time zone NOT NULL,
    amount double precision NOT NULL,
    CONSTRAINT payments_pk PRIMARY KEY (id),
    CONSTRAINT payments_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);