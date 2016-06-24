ALTER TABLE app_user ADD created_date TIMESTAMP;
ALTER TABLE app_user ADD last_modified_date TIMESTAMP;
ALTER TABLE app_user ADD created_user VARCHAR(255);
ALTER TABLE app_user ADD last_modified_user VARCHAR(255);

ALTER TABLE collectible ADD created_date TIMESTAMP;
ALTER TABLE collectible ADD last_modified_date TIMESTAMP;
ALTER TABLE collectible ADD created_user VARCHAR(255);
ALTER TABLE collectible ADD last_modified_user VARCHAR(255);

ALTER TABLE ownership ADD created_date TIMESTAMP;
ALTER TABLE ownership ADD last_modified_date TIMESTAMP;
ALTER TABLE ownership ADD created_user VARCHAR(255);
ALTER TABLE ownership ADD last_modified_user VARCHAR(255);

ALTER TABLE product_line ADD created_date TIMESTAMP;
ALTER TABLE product_line ADD last_modified_date TIMESTAMP;
ALTER TABLE product_line ADD created_user VARCHAR(255);
ALTER TABLE product_line ADD last_modified_user VARCHAR(255);

ALTER TABLE tag ADD created_date TIMESTAMP;
ALTER TABLE tag ADD last_modified_date TIMESTAMP;
ALTER TABLE tag ADD created_user VARCHAR(255);
ALTER TABLE tag ADD last_modified_user VARCHAR(255);
