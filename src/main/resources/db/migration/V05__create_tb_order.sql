CREATE TABLE orders (
    order_id varchar(50) NOT NULL,
    user_id int NOT NULL,
    order_status int NOT NULL DEFAULT 0, -- '0: Chưa xác nhận\r\n1: Đã xác nhận\r\n3: Đã hủy\r\n4: Đang giao\r\n5: Giao thành công',
    status varchar DEFAULT '1',
    id SERIAL primary key ,
    delivery_address varchar(225) NOT NULL,
    phone_number varchar(15) NOT NULL,
    total_money float NOT NULL,
    note text DEFAULT NULL,
    cancel_note text DEFAULT NULL,
    ctime timestamp NOT NULL DEFAULT current_timestamp,
    mtime timestamp NULL DEFAULT NULL
)