CREATE TABLE order_detail (
    amount int NOT NULL,
    price float NOT NULL,
    order_id int NOT NULL ,
    id int NOT NULL primary key ,
    product_id int NOT NULL
)