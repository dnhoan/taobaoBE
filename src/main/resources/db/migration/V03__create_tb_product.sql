CREATE TABLE products (
  id SERIAL primary key,
  name varchar(255)  NOT NULL,
  stock int NOT NULL DEFAULT 0,
  price int NOT NULL DEFAULT 0,
  color varchar(255)  NOT NULL,
  size varchar(255)  NOT NULL,
  image varchar(255)  DEFAULT NULL,
  category_id int NOT NULL ,
  status varchar(1)  DEFAULT '1',
  description varchar(225)  DEFAULT NULL
)