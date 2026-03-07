CREATE TABLE person
(
    id   bigserial PRIMARY KEY,
    name text NOT NULL,
    image text,
    UNIQUE (name)
);