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
 
/*
INSERT INTO LISTING (Id, title, price, seller, description, category) VALUES
  (1, 'Kuroslav', 25.5, 'Bai Hui', 'prodavam kur', 'test');
*/