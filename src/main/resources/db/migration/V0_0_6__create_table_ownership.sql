CREATE TABLE ownership (
  id BIGSERIAL PRIMARY KEY,
  collector_id VARCHAR(255) REFERENCES collector (username) NOT NULL,
  collectible_id INTEGER REFERENCES collectible (id) NOT NULL,
  price NUMERIC(8,2)
);