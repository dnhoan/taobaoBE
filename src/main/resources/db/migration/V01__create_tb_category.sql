CREATE TABLE categories (
    id SERIAL primary key,
    name varchar(100) unique not null,
    status int
)