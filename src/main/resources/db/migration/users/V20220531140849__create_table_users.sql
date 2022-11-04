CREATE TABLE public.users
(
    id serial,
    email varchar(320) NOT NULL,
    password varchar(64) NOT NULL,
    authority varchar(50) NOT NULL DEFAULT 'ROLE_USER',
    enabled boolean NOT NULL DEFAULT true,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT email UNIQUE (email)
);