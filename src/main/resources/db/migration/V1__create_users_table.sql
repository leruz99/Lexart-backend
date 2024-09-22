CREATE SEQUENCE IF NOT EXISTS users_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq'),
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
                       modified TIMESTAMP WITHOUT TIME ZONE,
                       last_login TIMESTAMP WITHOUT TIME ZONE,
                       token VARCHAR(255),
                       is_active BOOLEAN DEFAULT FALSE,
                       role VARCHAR(50) NOT NULL
);