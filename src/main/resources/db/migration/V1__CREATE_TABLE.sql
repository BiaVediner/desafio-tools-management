CREATE TABLE tools (
	tools_id serial NOT NULL,
	title varchar(50) NOT NULL,
	link varchar(255) NOT NULL,
	description varchar(256) NOT NULL,
	CONSTRAINT tools_pk PRIMARY KEY (tools_id)
);