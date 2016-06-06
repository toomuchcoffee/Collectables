ALTER TABLE collector ADD roles VARCHAR(255);

UPDATE collector SET roles = 'user' WHERE username = 'user';
UPDATE collector SET roles = 'user, admin' WHERE username = 'admin';