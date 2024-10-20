CREATE TABLE users (
  id VARCHAR(32) NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL,
  email varchar(255) NOT NULL UNIQUE,
  img_url varchar(255),
  password varchar(255) NOT NULL,
  created_at timestamp NOT NULL,
  updated_at timestamp
);
