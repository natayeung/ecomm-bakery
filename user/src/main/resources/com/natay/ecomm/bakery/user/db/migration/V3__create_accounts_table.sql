CREATE TABLE IF NOT EXISTS accounts
(
    account_id SERIAL PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(10) NOT NULL
);