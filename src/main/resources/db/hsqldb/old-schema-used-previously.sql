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

CREATE TABLE instant_transfers (
  id         INTEGER IDENTITY PRIMARY KEY,
  amount  	DOUBLE,
  destination    VARCHAR(20),
);
CREATE INDEX instant_transfers_id ON instant_transfers (id);


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

