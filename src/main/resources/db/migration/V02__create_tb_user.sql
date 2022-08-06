CREATE TABLE users (
   id SERIAL primary key,
   fullname varchar(255)  NOT NULL,
   gender int NOT NULL DEFAULT 1,
   phone_number varchar(255)  NOT NULL,
   address varchar(255)  NOT NULL,
   email varchar(255)  NOT NULL,
   password varchar(255)  NOT NULL,
   image varchar(255)  DEFAULT NULL,
   role int NOT NULL DEFAULT 0,
   status varchar(5)  DEFAULT '1'
)