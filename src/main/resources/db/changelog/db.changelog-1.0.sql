--liquibase formatted sql

--changeset shakratsanzhar:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(255),
    full_name VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL
);

--changeset shakratsanzhar:2
CREATE TABLE IF NOT EXISTS account
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id) ON DELETE CASCADE,
    balance DOUBLE PRECISION NOT NULL,
    initial_deposit DOUBLE PRECISION NOT NULL
);