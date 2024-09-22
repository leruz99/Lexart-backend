CREATE SEQUENCE IF NOT EXISTS product_data_id_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE product_data (
                              id BIGINT PRIMARY KEY DEFAULT nextval('product_data_id_seq'),
                              price NUMERIC(10, 2) NOT NULL,
                              color VARCHAR(255) NOT NULL,
                              product_id BIGINT,
                              FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);
