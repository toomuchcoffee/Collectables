ALTER TABLE collectible DROP CONSTRAINT product_line_abbreviation_fk;
UPDATE product_line SET code = upper(code);
UPDATE collectible SET product_line_code = upper(product_line_code);
ALTER TABLE collectible ADD CONSTRAINT product_line_code_fk
FOREIGN KEY (product_line_code) REFERENCES product_line (code);