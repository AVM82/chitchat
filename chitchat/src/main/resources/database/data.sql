INSERT INTO users
VALUES (DEFAULT, 'testUser2', 'useremail2@gmail.com',
        '{bcrypt}$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true),
       (DEFAULT, 'testUser1', 'useremail1@gmail.com',
        '{bcrypt}$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true),
       (DEFAULT, 'testUser3', 'useremail3@gmail.com',
        '{bcrypt}$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true);

INSERT INTO roles
VALUES (DEFAULT, 'ROLE_ADMIN'),
       (DEFAULT, 'ROLE_USER');


INSERT INTO users_roles
VALUES (1, 1),
       (1, 2);

INSERT INTO languages
VALUES ('uk', 'українська'),
       ('en', 'english'),
       ('de', 'deutsch');

INSERT INTO categories
VALUES (DEFAULT, 'Computer Science and IT', 10),
       (DEFAULT, 'Casual Conversation', 5),
       (DEFAULT, 'How beer can help you understand the theory of relativity', 3);

INSERT INTO chitchats
VALUES (DEFAULT, 1, 'lets go', 1, 'speak with me', 'en', 'A1', 10, '2023-05-01T12:00'),
       (DEFAULT, 3, 'lets go', 2, 'speak with me', 'de', 'NATIVE', 2, '2023-06-01T12:00');

INSERT INTO chitchat_users
VALUES (5, 1),
       (5, 3),
       (6, 1),
       (6, 2);

