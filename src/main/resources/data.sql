DROP TABLE IF EXISTS LISTING;
 
CREATE SCHEMA LISTING;

CREATE TABLE LISTING (
  Id INT PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  price DOUBLE(255) NOT NULL,
  seller VARCHAR(250),
  description VARCHAR(250) NOT NULL,
  category VARCHAR(250)
);

DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  Id INT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
);
 
/*
INSERT INTO LISTING (Id, title, price, seller, description, category) VALUES
  (1, 'Kuroslav', 25.5, 'Bai Hui', 'prodavam kur', 'test');
*/