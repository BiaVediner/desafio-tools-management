CREATE TABLE tags (
    tags_id serial NOT NULL,
	title varchar(50) NOT NULL,
	CONSTRAINT tags_pk PRIMARY KEY (tags_id)
);

CREATE TABLE tools_tags (
    tags_id serial NOT NULL,
    tools_id serial NOT NULL,
    FOREIGN KEY(tags_id) REFERENCES tags (tags_id),
    FOREIGN KEY(tools_id) REFERENCES tools (tools_id),
	CONSTRAINT tools_tags_pk PRIMARY KEY (tags_id, tools_id)
);