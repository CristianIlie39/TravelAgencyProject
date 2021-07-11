DROP DATABASE IF EXISTS travel_agency;
CREATE DATABASE IF NOT EXISTS travel_agency;
USE travel_agency;

CREATE TABLE IF NOT EXISTS continents(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS countries(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
continents_id INT NOT NULL,                           -- I created a Many To One relationship from table countries to table continents
CONSTRAINT fk_countries_continents
FOREIGN KEY(continents_id)
REFERENCES continents(id)
);

CREATE TABLE IF NOT EXISTS cities(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR (30) NOT NULL,
countries_id INT NOT NULL,                            -- I created a Many To One relationship from table cities to table countries
CONSTRAINT fk_cities_countries
FOREIGN KEY(countries_id)
REFERENCES countries(id)
);

CREATE TABLE IF NOT EXISTS airports(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
cities_id INT NOT NULL,                               -- I created a Many To One relationship from tabele airports to table cities
CONSTRAINT fk_airports_cities
FOREIGN KEY(cities_id)
REFERENCES cities(id)
);

CREATE TABLE IF NOT EXISTS flights(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
departure_date DATE NOT NULL,
departure_time TIME NOT NULL,
return_date DATE NOT NULL,
return_time TIME NOT NULL,
flight_to VARCHAR(20) NOT NULL,
price INT NOT NULL,
available_seats INT NOT NULL,
airports_id INT NOT NULL,                           -- I created a Many To One relationship from tabel flights to table airports 
CONSTRAINT fk_flights_airports
FOREIGN KEY(airports_id)
REFERENCES airports(id)
);

CREATE TABLE IF NOT EXISTS hotels(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
stars INT NOT NULL,
description VARCHAR(100) NOT NULL,
cities_id INT NOT NULL,                             -- I created a Many To One relationship from table hotels to table cities
CONSTRAINT fk_hotels_cities
FOREIGN KEY(cities_id)
REFERENCES cities(id)
);

CREATE TABLE IF NOT EXISTS rooms(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
room_type VARCHAR(20) NOT NULL,
available_rooms INT NOT NULL,
extra_bed TINYINT,
hotels_id INT,                             		    -- I created a Many To One relationship from table rooms to table hotels 
CONSTRAINT fk_rooms_hotels
FOREIGN KEY(hotels_id)
REFERENCES hotels(id)
);

CREATE TABLE IF NOT EXISTS trips(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,			
airports_id INT,                                    -- I created a Many To One relationship from table trips to table airports 
CONSTRAINT fk_trips_airports
FOREIGN KEY(airports_id)
REFERENCES airports(id),
hotels_id INT NOT NULL,                             -- I created a Many To One relationship from table trips to table hotels
CONSTRAINT fk_trips_hotels
FOREIGN KEY(hotels_id)
REFERENCES hotels(id),
departure_date DATE NOT NULL,
return_date DATE NOT NULL,
number_days INT NOT NULL,
meal_type VARCHAR(20),
adult_price DOUBLE NOT NULL,
kid_price DOUBLE NOT NULL,
promoted TINYINT NOT NULL,
stock INT NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(30) NOT NULL UNIQUE,
password VARCHAR(150) NOT NULL UNIQUE,
admin_role TINYINT NOT NULL,
logged_in TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS clients(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
surname VARCHAR(20) NOT NULL,
first_name VARCHAR(20) NOT NULL,
year_of_birth INT NOT NULL,
address VARCHAR(100) NOT NULL,
phone_number VARCHAR(20) NOT NULL,
email VARCHAR(30) NOT NULL UNIQUE,
accounts_id INT NOT NULL UNIQUE,					 -- I created a One To One relationship between table clients and table users
CONSTRAINT fk_clients_accounts
FOREIGN KEY(accounts_id)
REFERENCES accounts(id)
);

CREATE TABLE IF NOT EXISTS purchases(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
date_of_purchase DATE NOT NULL,
amount DOUBLE NOT NULL,
trips_id INT NOT NULL,                            
CONSTRAINT fk_purchases_trips
FOREIGN KEY(trips_id)
REFERENCES trips(id),
clients_id INT NOT NULL,
CONSTRAINT fk_purchases_clients
FOREIGN KEY(clients_id)
REFERENCES clients(id)
);