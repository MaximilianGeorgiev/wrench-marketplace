DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  Id INT PRIMARY KEY,
  username VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
);

INSERT INTO USER (Id, username, password, email) VALUES
(1, 'Gesh', 'Gesh', 'Gesh');
