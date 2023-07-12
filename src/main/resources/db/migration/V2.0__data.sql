-- product
insert into product ( title, rentable ) values ('Xbox Series X', true);
insert into product ( title, rentable ) values ('Xbox Series S', true);
insert into product ( title, rentable ) values ('Sony PS5', true);
insert into product ( title, rentable ) values ('Nintendo Switch Lite', true);
insert into product ( title, rentable ) values ('Oculus Quest 2', true);
insert into product ( title, rentable ) values ('Oculus Quest', false);
insert into product ( title, rentable ) values ('Oculus Rift S', true);
insert into product ( title, rentable ) values ('HTC Vive Cosmos', true);
insert into product ( title, rentable ) values ('iPhone X', true);
insert into product ( title, rentable ) values ('Samsung 20', false);

--price
insert into price ( commitment_months, value ) values (null, 35);
insert into price ( commitment_months, value ) values (null, 25);
insert into price ( commitment_months, value ) values (null, 17);
insert into price ( commitment_months, value ) values (null, 30);
insert into price ( commitment_months, value ) values (null, 45);

insert into price ( commitment_months, value ) values (3, 30);
insert into price ( commitment_months, value ) values (3, 20);
insert into price ( commitment_months, value ) values (3, 13);
insert into price ( commitment_months, value ) values (3, 25);
insert into price ( commitment_months, value ) values (3, 40);

insert into price ( commitment_months, value ) values (6, 25);
insert into price ( commitment_months, value ) values (6, 17);
insert into price ( commitment_months, value ) values (6, 10);
insert into price ( commitment_months, value ) values (6, 20);
insert into price ( commitment_months, value ) values (6, 35);

-- product_price
insert into product_price (product_id, price_id ) values (select id from product where title = 'Xbox Series X', select id from price where commitment_months is null and value = 35);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Xbox Series X', select id from price where commitment_months = 3 and value = 30);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Xbox Series X', select id from price where commitment_months = 6 and value = 25);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Xbox Series S', select id from price where commitment_months is null and value = 25);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Xbox Series S', select id from price where commitment_months = 3 and value = 20);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Xbox Series S', select id from price where commitment_months = 6 and value = 17);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Sony PS5', select id from price where commitment_months is null and value = 35);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Sony PS5', select id from price where commitment_months = 3 and value = 30);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Sony PS5', select id from price where commitment_months = 6 and value = 25);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Nintendo Switch Lite', select id from price where commitment_months is null and value = 17);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Nintendo Switch Lite', select id from price where commitment_months = 3 and value = 13);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Nintendo Switch Lite', select id from price where commitment_months = 6 and value = 10);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Quest 2', select id from price where commitment_months is null and value = 35);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Quest 2', select id from price where commitment_months = 3 and value = 30);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Quest 2', select id from price where commitment_months = 6 and value = 25);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Quest', select id from price where commitment_months is null and value = 30);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Quest', select id from price where commitment_months = 3 and value = 25);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Quest', select id from price where commitment_months = 6 and value = 20);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Rift S', select id from price where commitment_months is null and value = 35);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Rift S', select id from price where commitment_months = 3 and value = 30);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Oculus Rift S', select id from price where commitment_months = 6 and value = 25);

insert into product_price (product_id, price_id ) values (select id from product where title = 'HTC Vive Cosmos', select id from price where commitment_months is null and value = 45);
insert into product_price (product_id, price_id ) values (select id from product where title = 'HTC Vive Cosmos', select id from price where commitment_months = 3 and value = 40);
insert into product_price (product_id, price_id ) values (select id from product where title = 'HTC Vive Cosmos', select id from price where commitment_months = 6 and value = 35);

insert into product_price (product_id, price_id ) values (select id from product where title = 'iPhone X', select id from price where commitment_months is null and value = 35);
insert into product_price (product_id, price_id ) values (select id from product where title = 'iPhone X', select id from price where commitment_months = 3 and value = 30);
insert into product_price (product_id, price_id ) values (select id from product where title = 'iPhone X', select id from price where commitment_months = 6 and value = 25);

insert into product_price (product_id, price_id ) values (select id from product where title = 'Samsung 20', select id from price where commitment_months is null and value = 45);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Samsung 20', select id from price where commitment_months = 3 and value = 40);
insert into product_price (product_id, price_id ) values (select id from product where title = 'Samsung 20', select id from price where commitment_months = 6 and value = 35);