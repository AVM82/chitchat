INSERT INTO users
VALUES (DEFAULT, 'testUser2', 'useremail2@gmail.com',
        '$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true),
       (DEFAULT, 'testUser1', 'useremail1@gmail.com',
        '$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true),
       (DEFAULT, 'testUser3', 'useremail3@gmail.com',
        '$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true);

-- INSERT INTO roles
-- VALUES (DEFAULT, 'ADMIN'),
--        (DEFAULT, 'USER');


-- INSERT INTO users_roles
-- VALUES (1, 1),
--        (1, 2),
--        (2, 2),
--        (3, 2);

INSERT INTO languages
VALUES ('uk', 'українська'),
       ('en', 'english'),
       ('de', 'deutsch');

INSERT INTO categories
VALUES (DEFAULT, 'Computer Science and IT', 10),
       (DEFAULT, 'Casual Conversation', 5),
       (DEFAULT, 'How beer can help you understand the theory of relativity', 3);

INSERT INTO chitchats
VALUES (DEFAULT, 1, 'test header', 1, 'test description', 'en', 'A1', 10, '2023-05-01T12:00', null),
       (DEFAULT, 2, 'lets go', 2, 'speak with me', 'de', 'NATIVE', 2, '2023-06-01T12:00', null),
       (DEFAULT, 3, 'lets go testing', 3, 'test this chat', 'en', 'A2', 7, '2023-05-21T10:10', null),
       (DEFAULT, 1, 'lets go', 2, 'test me', 'de', 'B1', 2, '2023-05-21T09:10', null);

INSERT INTO chitchat_users
VALUES (1, 1),
       (2, 2);

-- INSERT INTO permissions
-- VALUES (DEFAULT, 'FREE'),
--        (DEFAULT, 'SUBSCRIPTION'),
--        (DEFAULT, 'PAID');

-- INSERT INTO users_permissions
-- VALUES (1, 1),
--        (2, 1),
--        (3, 1);

INSERT INTO reminder_data
VALUES (1, '2023-05-31 14:00:00.000000', 'https://1.11', false, "en"),
       (2, '2023-05-20 13:00:00.000000', 'https://1.22', false, "en"),
       (3, '2023-05-02 11:25:03.040000', null, false, "en"),
       (4, '2023-05-11 14:05:12.100000', null, false, "en");

INSERT INTO reminder_emails
VALUES (1, 'useremail2@gmail.com'),
       (2, 'useremail3@gmail.com');

INSERT INTO user_data
VALUES (1, 'UserForTests', 'TestUser', null, 'MALE', '2023-04-01', 'uk'),
       (2, 'UserUser', 'Tester', null, 'FEMALE', '2023-05-01', 'en'),
       (3, 'ForTests', 'TesterUser', null, 'MALE', '2023-05-05', 'uk');

INSERT INTO messages
VALUES (DEFAULT, 3, 2, 'Hello test message!', '2023-05-07 18:15:01.002000', 'CHAT'),
       (DEFAULT, 2, 1, 'Test message!', '2023-05-12 16:11:05.032000', 'CHAT'),
       (DEFAULT, 1, 1, 'Test message two!', '2023-05-04 13:11:25.040000', 'CHAT');

INSERT INTO message_users
VALUES (1, 2, false),
       (2, 1, true),
       (3, 1, false);

-- INSERT INTO translation
-- VALUES (1, 'email_confirm_create_chat', 'en', 'Hello, it is test message!');
