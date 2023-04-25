

INSERT INTO users VALUES (DEFAULT, 'testUser', 'useremail@gmail.com',
                          '{bcrypt}$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
                          true, true, true, true);

INSERT INTO roles VALUES (DEFAULT, 'ROLE_ADMIN'), (DEFAULT, 'ROLE_USER');


INSERT INTO users_roles VALUES (1, 1), (1, 2);

INSERT INTO languages VALUES ('uk', 'українська'),
                             ('en', 'english'),
                             ('de', 'deutsch')