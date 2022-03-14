DROP DATABASE IF EXISTS myRetailDB;
CREATE DATABASE myRetailDB;

USE myRetailDB;

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

INSERT INTO product(id, name, value) VALUES
	(13860428, "The Big Lebowski", 13.49),
    (54456119, "Creamy Peanut Butter 40oz", 3.29),
    (13264003, "Jif Natural Creamy Peanut Butter - 40oz", 5.99),
    (12954218, "Kraft Macaroni & Cheese Dinner Original - 7.25oz", 0.99);

INSERT INTO currency(code, exchangeRate) VALUES
	("USD", 1.000000),
    ("EUR", 1.090000);
    
INSERT INTO product_currency(productId, currencyId) VALUES
	(13860428, 1),
    (54456119, 1),
    (13264003, 1),
    (12954218, 1),
    (13860428, 2),
    (54456119, 2),
    (13264003, 2),
    (12954218, 2);
