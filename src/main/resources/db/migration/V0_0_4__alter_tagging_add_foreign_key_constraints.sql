ALTER TABLE tagging ADD CONSTRAINT collectible_id_fk FOREIGN KEY (collectible_id) REFERENCES collectible (id);
ALTER TABLE tagging ADD CONSTRAINT tag_id_fk FOREIGN KEY (tag_id) REFERENCES tag (name);