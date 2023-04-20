

INSERT INTO users VALUES (1, 'testUser', 'useremail@gmail.com',
                          '{bcrypt}$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
                          true, true, true, true);

INSERT INTO roles VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');


INSERT INTO users_roles VALUES (1, 1), (1, 2)