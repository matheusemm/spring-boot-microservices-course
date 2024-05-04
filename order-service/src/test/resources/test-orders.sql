truncate table orders cascade;
alter sequence order_id_seq restart with 100;
alter sequence order_item_id_seq restart with 100;

insert into orders (id, order_number, username, customer_name, customer_email, customer_phone, delivery_address_line1,
                    delivery_address_line2, delivery_address_city, delivery_address_state, delivery_address_zip_code,
                    delivery_address_country, status, comments, created_at, updated_at)
values (1, 'order-123', 'matheus', 'Matheus', 'matheus@example.com', '11111111', 'Alexanderstraße 123', '1. OG',
        'Berlin', 'Berlin', '12345', 'Germany', 'NEW', '', current_timestamp, current_timestamp),
       (2, 'order-456', 'matheus', 'Moreira', 'moreira@example.com', '22222222', 'Alexanderstraße 456', '2. OG',
        'Berlin', 'Berlin', '45678', 'Germany', 'DELIVERED', '', current_timestamp, current_timestamp);

insert into order_items(id, code, name, price, quantity, order_id)
values (1, 'P100', 'The Hunger Games', 34.0, 2, 1),
       (2, 'P101', 'To Kill a Mockingbird', 45.40, 1, 1),
       (3, 'P102', 'The Chronicles of Narnia', 44.50, 1, 2);
