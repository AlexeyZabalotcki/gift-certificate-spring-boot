INSERT INTO users (name)
VALUES  ('Andy'),
        ('Alex'),
        ('Mike');


INSERT INTO tag (name)
VALUES  ('tag'),
       ('tag 1'),
       ('tag 2');


INSERT INTO gift_certificate( name, description, price, duration)
VALUES ('gift', 'this is a gift', 14.00, 10),
       ('gift 1', 'this is a gift 1', 1.00, 15),
       ('gift 2', 'this is a gift 2', 50.00, 3),
       ('gift 3', 'this is a gift 3', 100.00, 40),
       ('gift 4', 'this is a gift 4', 4.00, 4);


INSERT INTO user_orders(cost, purchase_date, user_id, gift_certificate_id)
VALUES (14.00, '2023-05-11T08:13:14.767928', 2, 1),
       (1.00, '2023-05-11T08:13:14.767928', 1, 2),
       (50.00, '2023-05-11T08:13:14.767928', 3, 3),
       ( 100.00, '2023-05-11T08:13:14.767928', 1, 4);


INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (3, 3),
       (4, 1),
       (4, 2),
       (4, 3);

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('tag_id_seq', (SELECT MAX(id) FROM tag));
SELECT setval('gift_certificate_id_seq', (SELECT MAX(id) FROM gift_certificate));
SELECT setval('user_orders_id_seq', (SELECT MAX(id) FROM user_orders));
