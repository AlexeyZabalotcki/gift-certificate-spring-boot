DROP TABLE IF EXISTS gift_certificate;
CREATE TABLE gift_certificate(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255)  NOT NULL,
    description      VARCHAR,
    price            NUMERIC(10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      TIMESTAMP DEFAULT NOW(),
    last_update_date TIMESTAMP DEFAULT NOW()
);

DROP TABLE IF EXISTS tag;
CREATE TABLE tag(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)  NOT NULL
);

DROP TABLE IF EXISTS user_orders;
CREATE TABLE IF NOT EXISTS user_orders (
    id SERIAL PRIMARY KEY,
    cost NUMERIC(19, 2) NOT NULL,
    purchase_date TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES users(id),
    gift_certificate_id BIGINT REFERENCES gift_certificate(id)
);

DROP TABLE IF EXISTS gift_certificate_tag;
CREATE TABLE gift_certificate_tag(
    gift_certificate_id INT NOT NULL,
    tag_id              INT NOT NULL,
    PRIMARY KEY (gift_certificate_id, tag_id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

---------------------------------
-- DROP TABLE IF EXISTS order_gift_certificate;
-- CREATE TABLE order_gift_certificate (
--     order_id            BIGINT NOT NULL,
--     gift_certificate_id BIGINT NOT NULL,
--     PRIMARY KEY (order_id, gift_certificate_id),
--     FOREIGN KEY (order_id) REFERENCES user_orders (id) ON DELETE CASCADE,
--     FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE
-- );

--

-- CREATE TABLE IF NOT EXISTS tag (
--                                    id BIGINT PRIMARY KEY,
--                                    name VARCHAR(255) NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS gift_certificate (
--                                                 id BIGINT PRIMARY KEY,
--                                                 name VARCHAR(255) NOT NULL,
--                                                 description TEXT NOT NULL,
--                                                 price NUMERIC(19, 2) NOT NULL,
--                                                 duration INTEGER NOT NULL,
--                                                 create_date TIMESTAMP NOT NULL,
--                                                 last_update_date TIMESTAMP NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS user (
--                                     id BIGINT PRIMARY KEY,
--                                     name VARCHAR(255) NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS orders (
--                                       id BIGINT PRIMARY KEY,
--                                       cost NUMERIC(19, 2) NOT NULL,
--                                       purchase_date TIMESTAMP NOT NULL,
--                                       user_id BIGINT REFERENCES user(id) ON DELETE CASCADE,
--                                       gift_certificate_id BIGINT REFERENCES gift_certificate(id) ON DELETE CASCADE
-- );
--
-- CREATE TABLE IF NOT EXISTS gift_certificate_tag (
--                                                     gift_certificate_id BIGINT REFERENCES gift_certificate(id) ON DELETE CASCADE,
--                                                     tag_id BIGINT REFERENCES tag(id) ON DELETE CASCADE,
--                                                     PRIMARY KEY (gift_certificate_id, tag_id)
-- );