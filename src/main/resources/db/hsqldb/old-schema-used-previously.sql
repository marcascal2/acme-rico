DROP TABLE employees IF EXISTS;
DROP TABLE clients IF EXISTS;
DROP TABLE bank_accounts IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE authorities IF EXISTS;

CREATE TABLE bank_accounts (
  id         INTEGER IDENTITY PRIMARY KEY,
  account_number VARCHAR(20),
  amount  	DOUBLE,
  created_at    DATE,
  alias       VARCHAR(30),
);
CREATE INDEX bank_accounts_account_number ON bank_accounts (account_number);

CREATE TABLE employees (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  salary DOUBLE,
  username VARCHAR(80)
);
CREATE INDEX employees_last_name ON employees (last_name);

CREATE TABLE clients (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address VARCHAR(30),
  birth_date    DATE,
  city       VARCHAR(30),
  marital_status VARCHAR(30),
  salaty_per_year DOUBLE,
  age 		INTEGER,
  job VARCHAR(80),
  last_employ_date DATE,
  username VARCHAR(80)
);
CREATE INDEX clients_last_name ON clients (last_name);

CREATE TABLE users(
	username varchar_ignorecase(255) NOT NULL PRIMARY KEY,
	password varchar_ignorecase(255) NOT NULL,
	enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
	username varchar_ignorecase(50) NOT NULL,
	authority varchar_ignorecase(50) NOT NULL,	
);
ALTER TABLE authorities ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

