CREATE TABLE roles (
       id SERIAL primary key,
       role_name varchar(255)  NOT NULL,
       status varchar(5)  DEFAULT '1',
       created_date timestamp,
       created_by bigint,
       updated_date timestamp,
       updated_by bigint
)