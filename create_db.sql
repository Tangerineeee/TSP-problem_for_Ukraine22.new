DROP DATABASE IF EXISTS delivery_service;

CREATE DATABASE delivery_service DEFAULT CHARACTER SET utf8;
USE delivery_service;

CREATE TABLE cities_distances
(
    city1    VARCHAR(20) NOT NULL,
    city2    VARCHAR(20) NOT NULL,
    distance FLOAT       NOT NULL,
    PRIMARY KEY (city1, city2)
)

