CREATE TABLE IF NOT EXISTS tb_employees (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    admission_date VARCHAR(255) NOT NULL,
    employee_position VARCHAR(255) NOT NULL
);

