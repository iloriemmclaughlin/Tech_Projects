DROP DATABASE IF EXISTS testRetailDB;
CREATE DATABASE testRetailDB;

USE testRetailDB;

CREATE TABLE currency(
	id INT PRIMARY KEY AUTO_INCREMENT,
    code CHAR(3),
    exchangeRate DECIMAL(7,6)
);

CREATE TABLE product(
	id INT PRIMARY KEY,
    name VARCHAR(100),
    value DECIMAL(8,2)
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

SELECT value, c.code FROM product p
JOIN product_currency pc ON p.id = pc.productId
JOIN currency c ON c.id = pc.currencyId
WHERE p.id = 13860428 AND c.code = "USD";

SELECT * FROM product 
JOIN product_currency pc ON product.id = pc.productId 
JOIN currency ON currency.id = pc.currencyId
WHERE productId = 13860428 AND currency.code = "USD";

SELECT value*c.exchangeRate AS value, c.code FROM product 
JOIN product_currency pc ON product.id = pc.productId 
JOIN currency c ON c.id = pc.currencyId
WHERE productId = 13860428 AND c.code = "EUR";

SELECT code FROM currency c
JOIN product_currency pc ON pc.currencyId = c.id 
WHERE pc.productId = 13860428;

SELECT c.*, p.value FROM currency c 
JOIN product_currency pc ON pc.currencyId = c.id
JOIN product p ON pc.productId = p.id WHERE pc.currencyId = 1 AND pc.productId = 13860428;

UPDATE product 
SET value = 14.00 
WHERE id = 13860428;

SELECT * FROM product;