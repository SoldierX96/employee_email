create database employee_bd;

use employee_bd;

CREATE TABLE dbo.employees (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    document_number VARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    age INT,
    profile VARCHAR(50),
    admission_date DATE
);


INSERT INTO employees (name, document_number, salary, age, profile, admission_date) VALUES
-- Año 2020
('Luis Ramirez',      '20000001', 3500.00, 35, 'Developer',      '2020-01-10'),
('Ana Gomez',         '20000002', 4000.00, 29, 'Analyst',        '2020-03-25'),
('Pedro Diaz',        '20000003', 4200.00, 42, 'Tester',         '2020-06-30'),
('Lucía Torres',      '20000004', 3700.00, 31, 'Developer',      '2020-09-12'),
('Carlos Sánchez',    '20000005', 3900.00, 38, 'Support',        '2020-12-31'),

-- Año 2021
('María Lopez',       '20000006', 4100.00, 33, 'Developer',      '2021-02-03'),
('Jorge Rivas',       '20000007', 4300.00, 36, 'SysAdmin',       '2021-04-21'),
('Fernanda Cruz',     '20000008', 3600.00, 30, 'Analyst',        '2021-07-09'),
('David Mendoza',     '20000009', 4700.00, 45, 'Tester',         '2021-09-30'),
('Paula Salas',       '20000010', 3900.00, 32, 'Support',        '2021-11-15'),

-- Año 2022
('Diego Morales',     '20000011', 5000.00, 44, 'Developer',      '2022-01-01'),
('Andrea Navarro',    '20000012', 4500.00, 28, 'Analyst',        '2022-02-14'),
('Ricardo Fernandez', '20000013', 4800.00, 39, 'SysAdmin',       '2022-03-20'),
('Valeria Campos',    '20000014', 3700.00, 27, 'Support',        '2022-04-05'),
('Julio Paredes',     '20000015', 4100.00, 40, 'Tester',         '2022-05-11'),
('Sofia Luna',        '20000016', 3950.00, 35, 'Developer',      '2022-06-23'),
('Miguel Herrera',    '20000017', 3850.00, 31, 'Support',        '2022-07-18'),
('Camila Silva',      '20000018', 4050.00, 29, 'Analyst',        '2022-08-27'),
('Oscar Vega',        '20000019', 4250.00, 37, 'Developer',      '2022-09-30'),
('Laura Cornejo',     '20000020', 4150.00, 33, 'SysAdmin',       '2022-12-15');


--SELECT * FROM employees e 