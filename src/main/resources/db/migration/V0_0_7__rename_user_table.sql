ALTER TABLE collector RENAME TO app_user;

ALTER TABLE ownership RENAME COLUMN collector_id TO username;