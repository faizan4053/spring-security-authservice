CREATE TABLE AUTH_USER 
(
	id bigserial PRIMARY KEY,
	first_name VARCHAR(40),
	last_name VARCHAR(40),
	email VARCHAR(20) UNIQUE,
	PASSWORD VARCHAR(256), 
	account_non_expired BOOLEAN NOT NULL,
	account_non_locked BOOLEAN NOT NULL,
	credentials_non_expired BOOLEAN NOT NULL,
	enabled BOOLEAN NOT NULL,
	created timestamp NOT NULL,
	created_by varchar(255) NOT NULL,
	modified timestamp NOT NULL,
	modified_by varchar(255) NOT NULL
);