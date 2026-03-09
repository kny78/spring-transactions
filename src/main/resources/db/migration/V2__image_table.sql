CREATE TABLE image
(
    id bigserial PRIMARY KEY,
    blob bytea
);

alter TABLE person DROP COLUMN image;
ALTER TABLE person add COLUMN image_id bigint REFERENCES image(id);