CREATE TABLE user
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name           VARCHAR        NOT NULL,
    last_name      VARCHAR        NOT NULL,
    user_name      VARCHAR        NOT NULL,
    password       VARCHAR(8)     NOT NULL,
    local_currency VARCHAR        NOT NULL,
    tax            DECIMAL(12, 2) NOT NULL

);

CREATE UNIQUE INDEX user_name_uk ON user (user_name);

CREATE TABLE coin
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name      VARCHAR        NOT NULL,
    ranking   INT            NOT NULL,
    price_usd DECIMAL(12, 2) NOT NULL

);

CREATE TABLE user_coin
(
    user_id INT NOT NULL,
    coin_id INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user (id),
    FOREIGN KEY(coin_id) REFERENCES coin(id),
    PRIMARY KEY (user_id, coin_id)
);
