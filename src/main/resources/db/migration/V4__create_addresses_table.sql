CREATE TABLE IF NOT EXISTS addresses
(
    address_id     SERIAL PRIMARY KEY,
    email          VARCHAR(255) NOT NULL,
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255) NULL,
    postcode       VARCHAR(8)   NOT NULL
);