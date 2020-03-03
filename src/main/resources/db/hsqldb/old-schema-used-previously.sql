DROP TABLE owners IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE authorities IF EXISTS;

CREATE TABLE owners (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR_IGNORECASE(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20)
);
CREATE INDEX owners_last_name ON owners (last_name);

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

