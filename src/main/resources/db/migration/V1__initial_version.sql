CREATE TABLE if NOT EXISTS products
(
    product_id VARCHAR(10) NOT NULL PRIMARY KEY,
    title VARCHAR(30) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(4,2) NOT NULL
);