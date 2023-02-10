CREATE TABLE users (
    user_id serial NOT NULL,
	username varchar(50) NOT NULL,
	password varchar(256) NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (user_id)
);
