CREATE SEQUENCE IF NOT EXISTS product_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE products (
                          id BIGINT PRIMARY KEY DEFAULT nextval('product_id_seq'),
                          name VARCHAR(255) NOT NULL,
                          brand VARCHAR(255) NOT NULL,
                          model VARCHAR(255) NOT NULL
);

