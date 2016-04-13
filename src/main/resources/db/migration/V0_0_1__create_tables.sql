CREATE TABLE collector (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255)
);

CREATE TABLE collectible (
    id BIGSERIAL PRIMARY KEY,
    verbatim VARCHAR(255),
    product_line_abbreviation VARCHAR(255)
);

CREATE TABLE tagging (
    collectible_id integer,
    tag_id VARCHAR(255),
    PRIMARY KEY(collectible_id, tag_id)
);

CREATE TABLE tag (
    name VARCHAR(255) PRIMARY KEY
);

CREATE TABLE product_line (
    abbreviation VARCHAR(10) PRIMARY KEY,
    description VARCHAR(255)
);