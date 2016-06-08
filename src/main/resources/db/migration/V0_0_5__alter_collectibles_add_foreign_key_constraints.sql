ALTER TABLE collectible ADD CONSTRAINT product_line_abbreviation_fk
FOREIGN KEY (product_line_abbreviation) REFERENCES product_line (abbreviation);