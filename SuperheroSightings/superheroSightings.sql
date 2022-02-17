DROP DATABASE IF EXISTS superheroSightings;
CREATE DATABASE superheroSightings;

USE superheroSightings;

CREATE TABLE superhero(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE power(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE superhero_power(
	superheroId INT NOT NULL,
    powerId INT NOT NULL,
    PRIMARY KEY(superheroId, powerId),
    FOREIGN KEY (superheroId) REFERENCES superhero(id),
    FOREIGN KEY (powerId) REFERENCES power(id)
);

CREATE TABLE location(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    latitude DECIMAL(16,5),
    longitude DECIMAL(16,5)
);

CREATE TABLE organization(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    phone CHAR(15) NOT NULL
);

CREATE TABLE sightings(
	id INT PRIMARY KEY AUTO_INCREMENT,
	date DATE NOT NULL,
    superheroId INT NOT NULL,
    locationId INT NOT NULL,
    FOREIGN KEY (superheroId) REFERENCES superhero(id),
    FOREIGN KEY (locationId) REFERENCES location(id)
);

CREATE TABLE organization_superhero(
    organizationId INT NOT NULL,
    superheroId INT NOT NULL,
	PRIMARY KEY (organizationId, superheroId),
    FOREIGN KEY (organizationId) REFERENCES organization(id),
	FOREIGN KEY (superheroId) REFERENCES superhero(id)
);