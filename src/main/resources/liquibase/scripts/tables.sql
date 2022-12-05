-- liquibase formatted sql

-- changeset ymalykh:1

CREATE TABLE IF NOT EXISTS pictures
(
    id SERIAL PRIMARY KEY,
    file_path_in_folder VARCHAR(150) NOT NULL,
    media_type VARCHAR(100) NOT NULL,
    preview OID NOT NULL
);

CREATE TABLE IF NOT EXISTS avatars
(
    id SERIAL PRIMARY KEY,
    file_path_in_folder VARCHAR(150) NOT NULL,
    media_type VARCHAR(100) NOT NULL,
    preview OID NOT NULL
);
CREATE TABLE IF NOT EXISTS clubs
(
    id SERIAL PRIMARY KEY,
    city VARCHAR(150) NOT NULL
--     clubs_admin REFERENCES members(id)
);

CREATE TABLE IF NOT EXISTS pinsets
(
    id SERIAL PRIMARY KEY,
    pinsets_name VARCHAR (150) NOT NULL,
    release_date DATE,
    picture_id BIGINT REFERENCES pictures (id),
    club_id BIGINT REFERENCES clubs(id)
);

CREATE TABLE IF NOT EXISTS members(
    id SERIAL PRIMARY KEY,
    email VARCHAR (50) NOT NULL,
    role VARCHAR (10) NOT NULL,
    password VARCHAR (255) NOT NULL,
    full_name VARCHAR (150),
    birthday DATE,
    avatar_id BIGINT REFERENCES avatars(id),
    club_id BIGINT REFERENCES clubs(id)
);

CREATE TABLE IF NOT EXISTS pins
(
    id SERIAL PRIMARY KEY,
    pins_name VARCHAR(150) NOT NULL,
    release_date DATE,
    picture_id BIGINT REFERENCES pictures (id),
    club_id BIGINT REFERENCES clubs (id),
    pinset_id BIGINT REFERENCES pinsets (id),
    member_id BIGINT REFERENCES members(id)
);

INSERT INTO members (email, role, password)
VALUES ('admin@admin.com', 'ADMIN','{bcrypt}$2a$10$XK/IbwdysgmNbJWq5lOSyuGtpm/XGrekpw3ymuraNSLKXX5EmO1HK');

-- changeset ymalykh:2
ALTER TABLE members RENAME COLUMN email TO username;





