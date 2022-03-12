DROP DATABASE IF EXISTS myRetailDB;
CREATE DATABASE myRetailDB;

USE myRetailDB;

CREATE TABLE product(
	id INT PRIMARY KEY,
    name VARCHAR(100),
    price DECIMAL(8,2),
    currency CHAR(3)
);

INSERT INTO product(id, name, price, currency) VALUES
	(13860428, "The Big Lebowski", 13.49, "USD");