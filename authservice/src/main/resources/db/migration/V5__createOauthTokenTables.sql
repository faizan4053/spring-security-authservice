create table oauth_access_token (
	token_id varchar(255) NOT NULL,
	token BYTEA,
	authentication_id varchar(255) DEFAULT NULL, 
	user_name varchar(255) DEFAULT NULL, 
	client_id varchar(255) DEFAULT NULL, 
	authentication BYTEA,
	refresh_token varchar(255) DEFAULT NULL, 
	PRIMARY KEY (token_id)
);

create table oauth_refresh_token ( 
	token_id varchar(255) NOT NULL,
	token BYTEA,
	authentication BYTEA,
	PRIMARY KEY (token_id)
);