CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    address VARCHAR(255),
    base_salary NUMERIC(19, 2),
    id_type VARCHAR(50),
    id_number VARCHAR(50)
);