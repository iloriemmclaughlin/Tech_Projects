DROP DATABASE IF EXISTS myRetailDBtest;
CREATE DATABASE myRetailDBtest;

USE myRetailDBtest;

CREATE TABLE currency(
	id INT PRIMARY KEY AUTO_INCREMENT,
    code CHAR(3),
    exchangeRate DECIMAL(7,6)
);

CREATE TABLE product(
	id INT PRIMARY KEY,
    name VARCHAR(100),
    value DECIMAL(15,2)
);
    

CREATE TABLE product_currency(
	productId INT,
    currencyId INT,
    PRIMARY KEY (productId, currencyId),
    FOREIGN KEY (productId) REFERENCES product(id),
    FOREIGN KEY (currencyId) REFERENCES currency(id)
);