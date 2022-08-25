CREATE TABLE User
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name           VARCHAR        NOT NULL,
    last_name      VARCHAR        NOT NULL,
    user_name      VARCHAR        NOT NULL,
    password       VARCHAR        NOT NULL,
    local_currency VARCHAR        NOT NULL,
    tax            DECIMAL(12, 2) NOT NULL

);
