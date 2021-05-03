ALTER TABLE accounts
ADD COLUMN first_name VARCHAR(50);

-- H2 does not support "multiple ADD COLUMN in one statement"
ALTER TABLE accounts
ADD COLUMN last_name  VARCHAR(50);